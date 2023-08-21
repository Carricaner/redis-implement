#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

# Sources
. "./scripts/functions/setup.sh"
. "./scripts/functions/cluster-build.sh"
. "./scripts/functions/app-build.sh"
. "./scripts/functions/app-build.sh"

# Set up the environments
setup_env

# Build a Redis cluster from submodule
build_redis_cluster

# clean, rebuild & run the application
run_app
