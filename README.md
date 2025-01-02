# Redis Implementation Project 
[![CircleCI](https://dl.circleci.com/status-badge/img/circleci/ME2opz6NQmyqhFno6cPKqT/GK1356dpRPA8usjBpKgo9V/tree/main.svg?style=svg&circle-token=CCIPRJ_TH5gpCTpUKuDbRVZhkZfYg_9966e532fc7572cb64b672a14d71454c466f8807)](https://dl.circleci.com/status-badge/redirect/circleci/ME2opz6NQmyqhFno6cPKqT/GK1356dpRPA8usjBpKgo9V/tree/main)

## Description


## Prerequisites

- Docker (docker + docker compose)


## Commands

- Run the app in a Docker container
  
  ```shell
  docker-compose -f ./env/dev/docker-compose.yaml up --build 
  ```

- Copy S3 object

  ```shell
  aws s3 cp s3://myoptions-v2/project/redis-implementation/build/redis-implementation-1.0.0.jar build/libs/redis-implementation-1.0.0.jar
  ```


## Notes
- For AWS Elastic Cache, we need to set up AWS Direct Connect & Transit Gateway because it cannot be connected publicly by default.
  - However, it is accessible for the EC2 instances under the same VPC.

## Todos
- [] Create a start script to make it easy to start the app.