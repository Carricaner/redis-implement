#!/bin/bash

check_gh_login() {
    if ! gh auth status &>/dev/null; then
        echo "GitHub CLI is not logged in. Logging in now..."
        echo "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" | gh auth login --with-token
        if [ $? -ne 0 ]; then
            echo "Failed to log in to GitHub CLI. Exiting."
            exit 1
        fi
    else
        echo "GitHub CLI is already logged in."
    fi
}

update_or_clone_repo() {
    REPO_NAME="redis-implement"
    REPO_OWNER="Carricaner"
    REPO_DIR="$REPO_NAME"

    if [ -d "$REPO_DIR" ]; then
        echo "Repository '$REPO_OWNER/$REPO_NAME' already exists. Pulling latest changes..."
        cd "$REPO_DIR" || { echo "Failed to change directory to $REPO_DIR. Exiting."; exit 1; }
        git pull origin main || { echo "Failed to pull latest changes. Exiting."; exit 1; }
    else
        echo "Repository '$REPO_OWNER/$REPO_NAME' does not exist locally. Cloning now..."
        gh repo clone "$REPO_OWNER/$REPO_NAME"
        if [ $? -ne 0 ]; then
            echo "Failed to clone repository. Exiting."
            exit 1
        fi
        cd "$REPO_DIR" || { echo "Failed to change directory to $REPO_DIR. Exiting."; exit 1; }
    fi
}

# Main script execution
check_gh_login
update_or_clone_repo

# Debugging: Print current working directory and list files
echo "Current working directory: $(pwd)"
echo "Contents of $(pwd):"
ls -R

# Stop Docker Compose services
if [ -f "./env/dev/docker-compose.yaml" ]; then
    docker-compose -f ./env/dev/docker-compose.yaml down
else
    echo "Error: Docker Compose file './env/dev/docker-compose.yaml' not found. Exiting."
    exit 1
fi
