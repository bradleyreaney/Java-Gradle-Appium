#!/bin/bash

# Mobile Automation Environment Setup Script
# This script helps set up the development environment for mobile automation

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

# Function to install Homebrew (macOS)
install_homebrew() {
    if ! command_exists brew; then
        print_status "Installing Homebrew..."
        /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
        print_success "Homebrew installed successfully"
    else
        print_success "Homebrew is already installed"
    fi
}

# Function to install Java
install_java() {
    if ! command_exists java; then
        print_status "Installing Java..."
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # macOS
            if command_exists brew; then
                brew install openjdk@11
                print_success "Java installed successfully"
            else
                print_error "Please install Homebrew first or install Java manually"
                exit 1
            fi
        else
            print_error "Please install Java 11 or higher manually for your operating system"
            exit 1
        fi
    else
        JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
        print_success "Java is already installed: $JAVA_VERSION"
    fi
}

# Function to install Node.js and npm
install_nodejs() {
    if ! command_exists node; then
        print_status "Installing Node.js..."
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # macOS
            if command_exists brew; then
                brew install node
                print_success "Node.js installed successfully"
            else
                print_error "Please install Homebrew first or install Node.js manually"
                exit 1
            fi
        else
            print_error "Please install Node.js manually for your operating system"
            exit 1
        fi
    else
        NODE_VERSION=$(node --version)
        print_success "Node.js is already installed: $NODE_VERSION"
    fi
}

# Function to install Appium
install_appium() {
    if ! command_exists appium; then
        print_status "Installing Appium..."
        npm install -g appium
        print_success "Appium installed successfully"
    else
        APPIUM_VERSION=$(appium --version)
        print_success "Appium is already installed: $APPIUM_VERSION"
    fi
}

# Function to install iOS dependencies
install_ios_dependencies() {
    if [[ "$OSTYPE" == "darwin"* ]]; then
        print_status "Installing iOS dependencies..."
        
        # Install Xcode Command Line Tools
        if ! xcode-select -p >/dev/null 2>&1; then
            print_status "Installing Xcode Command Line Tools..."
            xcode-select --install
            print_success "Xcode Command Line Tools installed"
        else
            print_success "Xcode Command Line Tools are already installed"
        fi
        
        # Install iOS WebDriverAgent
        print_status "Installing iOS WebDriverAgent..."
        npm install -g appium-doctor
        appium-doctor --ios
        print_success "iOS dependencies check completed"
    else
        print_warning "iOS dependencies can only be installed on macOS"
    fi
}

# Function to create necessary directories
create_directories() {
    print_status "Creating necessary directories..."
    
    mkdir -p logs
    mkdir -p screenshots
    mkdir -p test-data
    mkdir -p config
    
    print_success "Directories created successfully"
}

# Function to download dependencies
download_dependencies() {
    print_status "Downloading project dependencies..."
    
    if [ -f "./gradlew" ]; then
        ./gradlew build --refresh-dependencies
    else
        gradle build --refresh-dependencies
    fi
    
    print_success "Dependencies downloaded successfully"
}

# Function to show help
show_help() {
    echo "Mobile Automation Environment Setup Script"
    echo ""
    echo "Usage: $0 [OPTIONS]"
    echo ""
    echo "Options:"
    echo "  -h, --help              Show this help message"
    echo "  --java-only             Install Java only"
    echo "  --node-only             Install Node.js only"
    echo "  --appium-only           Install Appium only"
    echo "  --ios-only              Install iOS dependencies only"
    echo "  --deps-only             Download project dependencies only"
    echo "  --all                   Install everything (default)"
    echo ""
    echo "This script will install:"
    echo "  - Homebrew (macOS only)"
    echo "  - Java 11+"
    echo "  - Node.js and npm"
    echo "  - Appium"
    echo "  - iOS dependencies (macOS only)"
    echo "  - Project dependencies"
}

# Main script logic
main() {
    local java_only=false
    local node_only=false
    local appium_only=false
    local ios_only=false
    local deps_only=false
    
    # Parse command line arguments
    while [[ $# -gt 0 ]]; do
        case $1 in
            -h|--help)
                show_help
                exit 0
                ;;
            --java-only)
                java_only=true
                shift
                ;;
            --node-only)
                node_only=true
                shift
                ;;
            --appium-only)
                appium_only=true
                shift
                ;;
            --ios-only)
                ios_only=true
                shift
                ;;
            --deps-only)
                deps_only=true
                shift
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
    if [ "$java_only" = true ]; then
        install_java
    elif [ "$node_only" = true ]; then
        install_nodejs
    elif [ "$appium_only" = true ]; then
        install_appium
    elif [ "$ios_only" = true ]; then
        install_ios_dependencies
    elif [ "$deps_only" = true ]; then
        download_dependencies
    else
        # Install everything
        if [[ "$OSTYPE" == "darwin"* ]]; then
            install_homebrew
        fi
        install_java
        install_nodejs
        install_appium
        install_ios_dependencies
        create_directories
        download_dependencies
    fi
    
    print_success "Environment setup completed successfully"
    print_status "You can now run tests using: ./scripts/run-tests.sh"
}

# Run main function with all arguments
main "$@"
