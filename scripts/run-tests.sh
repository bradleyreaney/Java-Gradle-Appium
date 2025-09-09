#!/bin/bash

# Mobile Automation Test Runner Script
# This script helps run the mobile automation tests with proper setup

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Function to check prerequisites
check_prerequisites() {
    print_status "Checking prerequisites..."
    
    # Check Java
    if command_exists java; then
        JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
        print_success "Java found: $JAVA_VERSION"
    else
        print_error "Java is not installed. Please install Java 11 or higher."
        exit 1
    fi
    
    # Check Gradle
    if command_exists gradle; then
        GRADLE_VERSION=$(gradle --version | grep "Gradle" | cut -d' ' -f2)
        print_success "Gradle found: $GRADLE_VERSION"
    else
        print_warning "Gradle not found in PATH, using gradlew wrapper"
    fi
    
    # Check Appium
    if command_exists appium; then
        APPIUM_VERSION=$(appium --version)
        print_success "Appium found: $APPIUM_VERSION"
    else
        print_error "Appium is not installed. Please install Appium globally: npm install -g appium"
        exit 1
    fi
    
    # Check if Appium server is running
    if curl -s http://localhost:4723/status >/dev/null 2>&1; then
        print_success "Appium server is running on localhost:4723"
    else
        print_warning "Appium server is not running. Please start it with: appium"
        print_status "Starting Appium server in background..."
        appium &
        APPIUM_PID=$!
        sleep 5
        if curl -s http://localhost:4723/status >/dev/null 2>&1; then
            print_success "Appium server started successfully"
        else
            print_error "Failed to start Appium server"
            exit 1
        fi
    fi
}

# Function to build the project
build_project() {
    print_status "Building the project..."
    
    if [ -f "./gradlew" ]; then
        ./gradlew clean build
    else
        gradle clean build
    fi
    
    if [ $? -eq 0 ]; then
        print_success "Project built successfully"
    else
        print_error "Project build failed"
        exit 1
    fi
}

# Function to run tests
run_tests() {
    local test_class="$1"
    local test_method="$2"
    
    print_status "Running tests..."
    
    if [ -f "./gradlew" ]; then
        if [ -n "$test_class" ] && [ -n "$test_method" ]; then
            ./gradlew test --tests "$test_class.$test_method"
        elif [ -n "$test_class" ]; then
            ./gradlew test --tests "$test_class"
        else
            ./gradlew test
        fi
    else
        if [ -n "$test_class" ] && [ -n "$test_method" ]; then
            gradle test --tests "$test_class.$test_method"
        elif [ -n "$test_class" ]; then
            gradle test --tests "$test_class"
        else
            gradle test
        fi
    fi
    
    if [ $? -eq 0 ]; then
        print_success "Tests completed successfully"
    else
        print_error "Tests failed"
        exit 1
    fi
}

# Function to show help
show_help() {
    echo "Mobile Automation Test Runner"
    echo ""
    echo "Usage: $0 [OPTIONS]"
    echo ""
    echo "Options:"
    echo "  -h, --help              Show this help message"
    echo "  -c, --check             Check prerequisites only"
    echo "  -b, --build             Build project only"
    echo "  -t, --test [CLASS]      Run specific test class"
    echo "  -m, --method CLASS.METHOD Run specific test method"
    echo "  --all                   Run all tests (default)"
    echo ""
    echo "Examples:"
    echo "  $0                      # Run all tests"
    echo "  $0 --check              # Check prerequisites"
    echo "  $0 --build              # Build project only"
    echo "  $0 --test HomePageTest  # Run HomePageTest class"
    echo "  $0 --method HomePageTest.testHomePageDisplayed  # Run specific test method"
}

# Main script logic
main() {
    local check_only=false
    local build_only=false
    local test_class=""
    local test_method=""
    
    # Parse command line arguments
    while [[ $# -gt 0 ]]; do
        case $1 in
            -h|--help)
                show_help
                exit 0
                ;;
            -c|--check)
                check_only=true
                shift
                ;;
            -b|--build)
                build_only=true
                shift
                ;;
            -t|--test)
                test_class="$2"
                shift 2
                ;;
            -m|--method)
                IFS='.' read -r test_class test_method <<< "$2"
                shift 2
                ;;
            --all)
                # Default behavior
                shift
                ;;
            *)
                print_error "Unknown option: $1"
                show_help
                exit 1
                ;;
        esac
    done
    
    # Execute based on options
    if [ "$check_only" = true ]; then
        check_prerequisites
    elif [ "$build_only" = true ]; then
        check_prerequisites
        build_project
    else
        check_prerequisites
        build_project
        run_tests "$test_class" "$test_method"
    fi
    
    print_success "Script completed successfully"
}

# Run main function with all arguments
main "$@"
