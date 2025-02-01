# Real-world features with Redis [![CircleCI](https://dl.circleci.com/status-badge/img/circleci/ME2opz6NQmyqhFno6cPKqT/GK1356dpRPA8usjBpKgo9V/tree/main.svg?style=svg&circle-token=CCIPRJ_TH5gpCTpUKuDbRVZhkZfYg_9966e532fc7572cb64b672a14d71454c466f8807)](https://dl.circleci.com/status-badge/redirect/circleci/ME2opz6NQmyqhFno6cPKqT/GK1356dpRPA8usjBpKgo9V/tree/main)

## Description

Given Redis's reputation as a widely used in-memory storage solution, this project leverages modern
techniques to implement real-world features, including:

- Rate Limiter
- Bloom Filter
- Distributed Lock
- Buffer Ring

## Features

### Concept of Clean Architecture

The whole project is designed under the concept of clean architecture and divided into two parts
including: `Core` & `External`, as below.

- `Core`
    - the most important part of the project
    - includes:
        - `Domain`: most important entities in the whole project
        - `Config`
        - `Use case`
    - must not reply on `External`
    - must communicate with `External` through `Interface`
    - needs unit tests
    - in this way, we can extract `Core` part and place it elsewhere with ease.
- `External`
    - responsible for communicating with the outside world
    - includes:
        - `Entry`: deals with incoming requests, like HTTP requests.
        - `Adapter` sends commands to or gets data from the outside world, like upload a file to AWS
          S3
    - able to use everything in `Core`

<img src="./assets/redis-impl.svg" width="500" alt="my clean architecture design"/>

## Prerequisites

- Docker (w/ docker compose)
- Redis

## Steps

1. Set up Redis information in `src/main/resources/dev/redis.yml`, and the format is like:

    ```yaml
    redis:
      host:
      port:
      username:
      password:
    ```

2. Run the app

    ```shell
    docker-compose -f ./env/dev/docker-compose.yaml up --build
    ```

## Other Notes

- If you desire using AWS Elastic Cache for Redis, setting up AWS Direct Connect & Transit Gateway
  is required because it cannot be
  connected publicly by default, even it is accessible for EC2s under the same VPC.

## Future Work

- [Optional] Try to upload the image to AWS ECR
