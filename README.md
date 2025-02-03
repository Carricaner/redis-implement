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
    - in this way, we can extract `Core` part and place it elsewhere with ease, which makes
      microservices possible.
- `External`
    - responsible for communicating with the outside world
    - includes:
        - `Entry`: deals with incoming requests, like HTTP requests.
        - `Adapter` sends commands to or gets data from the outside world, like upload a file to AWS
          S3
    - able to use everything in `Core`

<img src="https://the-general.s3.ap-northeast-1.amazonaws.com/project/redis-impl.svg" width="500" alt="my clean architecture design"/>

### Adopts TestContainers to do the integration tests

[TestContainers](https://testcontainers.com/) is an open source library which makes it easy to
manipulate containers, which is a great option to choose to do the integration tests!

### Used Circle CI & AWS CodeDeploy to test and deploy the project

- the flow is like:
    ```mermaid
    flowchart LR
    test --> build --> C[upload built jar to S3] --> deploy[deploy using AWS CodeDeploy]
    ```
- The benefit of using `AWS CodeDeploy` is that it can deploy the instances tagged with designated
  tags, which increase scalability and avoid managing the instances through SSH by ourselves.

  ```mermaid
  flowchart TD
  A[Circle CI Executor]
  I1(("tagged instance"))
  I2(("tagged instance"))
  I3(("untagged instance"))
  I4(("tagged instance"))
  I5(("untagged instance"))
  
  A -->|deploy| I1
  A -->|deploy| I2
  A -.->|Do nothing...| I3
  A -->|deploy| I4
  A -.->|Do nothing...| I5 
  ```

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

## API Document

### Ring Buffer

- Get API request records
    - description: The API displays every request's information which is stored in a ring buffer.
    - HTTP Method: `GET`
    - Endpoint: `/ring-buffer/api-records`

### Distributed Lock

- Get the counter's number
    - description: The API is to acquire the lock first and then get the number of the counter.
    - HTTP Method: `GET`
    - Endpoint: `/distributed-lock/read-write-lock/my-lock`<br><br>

- Get API request records
    - description: The API is to acquire the lock first and then increase the number of the counter
      by 1.
    - HTTP Method: `POST`
    - Endpoint: `/distributed-lock/read-write-lock/my-lock`

### Bloom Filter

- Check if a client's ID exists or not
    - description: Check if a client's ID exists in a bloom filter or not.
    - HTTP Method: `GET`
    - Endpoint: `/bloom-filter/{clientId}`<br><br>

- Mark a specific client's ID as existed
    - description: Mark a client's ID as existed in a bloom filter.
    - HTTP Method: `POST`
    - Endpoint: `/bloom-filter/{clientId}`

### Rate Limiter

- When an API is annotated with `@RateLimited`, a rate limiter is applied.
  Incoming requests are intercepted and rate-limited accordingly.
- `@RateLimited` can be added on either class level or method level, and, if both added, the later
  one will override the former one.
- Please see the details on `org.example.core.configuration.web.interceptor`

## Notes

- If you desire using AWS Elastic Cache for Redis, setting up AWS Direct Connect & Transit Gateway
  is required because it cannot be
  connected publicly by default, even it is accessible for EC2s under the same VPC.

## Future Work

- [Optional] Try to upload the image to AWS ECR
