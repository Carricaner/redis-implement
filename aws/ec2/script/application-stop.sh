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

check_repo_cloned() {
    REPO_NAME="redis-implement"
    REPO_OWNER="Carricaner"
    REPO_DIR="$REPO_NAME"

    if [ ! -d "$REPO_DIR" ]; then
        echo "Repository '$REPO_OWNER/$REPO_NAME' is not cloned. Cloning now..."
        gh repo clone "$REPO_OWNER/$REPO_NAME"
        if [ $? -ne 0 ]; then
            echo "Failed to clone repository. Exiting."
            exit 1
        fi
    else
        echo "Repository '$REPO_OWNER/$REPO_NAME' is already cloned."
    fi

    # Change into the repository directory
    cd "$REPO_DIR" || { echo "Failed to change directory to $REPO_DIR. Exiting."; exit 1; }
}

check_gh_login
check_repo_cloned
docker-compose -f ./env/dev/docker-compose.yaml down