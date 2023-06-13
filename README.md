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

### Bloom Filter

- Used Redis' `BitMap` to implement it.

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
