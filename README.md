# Redis Implementation Project

## Description


## Prerequisites

- Docker (docker + docker compose)


## Commands

- Run the app in a Docker container
  
  ```shell
  docker compose -f ./env/dev/docker-compose.yaml up --build 
  ```
  


## Notes
- For AWS Elastic Cache, we need to set up AWS Direct Connect & Transit Gateway because it cannot be connected publicly by default.
  - However, it is accessible for the EC2 instances under the same VPC.

## Todos
- [] Create a start script to make it easy to start the app.