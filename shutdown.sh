#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

docker compose down

cd ./redis-cluster
docker compose down

docker network rm redis-network
