# Redis Implementation Project

## Description

Since well-known in-memory storage application, Redis, is widely used,
the project is aiming at implementing some famous applications,
including rate limiters, bloom filters, distributed lock and so forth.

## Prerequisites

- Docker (docker & docker compose)

## Steps

1. If there is no `.env` file under the root directory, run below:

    ```shell
      ./gradlew writeProjectInfoToEnvFile
    ```

2. Run below commands under the root directory

    ```shell
      ./gradlew build && docker-compose -d up 
    ```

## Features

### Rate limiter

- Includes `Sliding window`, `fixed window`, `token bucket` & `leaky bucket`.
- Under the hood, the former two were implemented by Redis' `sorted set`
  while the later two are implemented by Redis' `hash`.
- Tests:
    - To test the rate limiter's function<br>
      `GET http://localhost:8080/rate/test`
    - To refresh the rate limiter's record<br>
      `DELETE http://localhost:8080/rate`

### Bloom Filter

- Used Redis' `BitMap` to implement it.
- Tests:
    - To create an item in the bloom filter<br>
      `POST http://localhost:8080/bloom-filter/{clientId}}`
    - To check if the item exist in the bloom filter<br>
      `GET http://localhost:8080/bloom-filter/{clientId}`

### Other

- The project is modeling <ins>clean architecture</ins>,
  consisting of core, adapter & entrypoint.
  The three main parts are interacting with one another with interfaces.
- The core is under the protection of the unit tests.

### Pub/Sub

- We can use an API to send message to a specific topic.
- The server served as a Redis subscriber with topic of `my-topic`
- Test:
    - To send a message to a specific topic <br>
      `POST` `http://localhost:8080/pub-sub/{topic}/{message}` <br>
      Then, the message will be printed in the log of the server.
  