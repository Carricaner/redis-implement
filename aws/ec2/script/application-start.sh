#!/bin/bash
pwd
ls -la
docker-compose -f ./redis-implement/env/dev/docker-compose.yaml up --build -d