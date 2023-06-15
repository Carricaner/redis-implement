# Redis Implementation Project

## Description

Since well-known in-memory storage application, Redis, is widely used,
the project is aiming at implementing some famous applications,
including rate limiters, bloom filters, distributed lock and so forth.

## Prerequisites

- Docker (docker & docker compose)

## Features

### Rate limiter

- Includes `Sliding window`, `fixed window`, `token bucket` & `leaky bucket`.
- Under the hood, the former two were implemented by Redis' `sorted set`
  while the later two are implemented by Redis' `hash`.

#### Test

1. To test the rate limiter's function<br>
   `GET http://localhost:8080/rate/test`
2. To refresh the rate limiter's record<br>
   `DELETE http://localhost:8080/rate`

### Bloom Filter

- Used Redis' `BitMap` to implement it.

#### Test

1. To create an item in the bloom filter<br>
   `POST http://localhost:8080/bloom-filter/{clientId}}`
2. To check if the item exist in the bloom filter<br>
   `GET http://localhost:8080/bloom-filter/{clientId}`

### Other

- The project is modeling <ins>clean architecture</ins>,
  consisting of core, adapter & entrypoint.
  The three main parts are interacting with one another with interfaces.
- The core is under the protection of the unit tests.

## Steps

1. If there is no `.env` file under the root directory, run below:

    ```shell
      ./gradlew writeProjectInfoToEnvFile
    ```

2. Run below commands under the root directory

    ```shell
      ./gradlew build && docker-compose -d up 
    ```
3. Use the provided APIs to test the application 