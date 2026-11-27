# Saga Pattern Using Orkes Conductor

[![Java CI with Gradle](https://github.com/hendisantika/spring-saga-pattern/actions/workflows/gradle.yml/badge.svg)](https://github.com/hendisantika/spring-saga-pattern/actions/workflows/gradle.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.2.0-brightgreen?logo=springboot)
![Gradle](https://img.shields.io/badge/Gradle-9.9.2-02303A?logo=gradle)

This is an example project showing how to build event driven applications that implement the
**Saga pattern** (orchestration-based) using [Conductor](https://github.com/conductor-oss/conductor) /
[Orkes Conductor](https://orkes.io/).

A food delivery order is orchestrated by the `FoodDeliveryWorkflow`. Every step is executed by a
Spring-managed Conductor worker. When a step fails, compensating tasks are executed to roll back the
previous steps.

## Tech Stack

| Component              | Version     |
|------------------------|-------------|
| Java (toolchain)       | 25          |
| Spring Boot            | 4.2.0       |
| Gradle (wrapper)       | 9.9.2       |
| Orkes Conductor Client | 5.1.0       |
| SQLite JDBC            | 3.54.1.0    |
| Lombok (freefair)      | 9.8.3       |
| JUnit                  | 6.x         |

## Saga Flow

```text
Happy path : order_food ─► check_inventory ─► make_payment ─► ship_food ─► notify_driver / notify_customer
Compensate : (on failure) cancel_delivery / cancel_payment / cancel_order
```

| Worker task       | Description                                  |
|-------------------|----------------------------------------------|
| `order_food`      | Creates the order                            |
| `check_inventory` | Checks restaurant inventory for the order    |
| `make_payment`    | Charges the customer                         |
| `ship_food`       | Assigns a driver and ships the food          |
| `notify_driver`   | Notifies the driver                          |
| `notify_customer` | Notifies the customer                        |
| `cancel_payment`  | Compensation – refunds the payment           |
| `cancel_delivery` | Compensation – cancels the shipment          |
| `cancel_order`    | Compensation – cancels the order             |

## Pre-requisites

1. JDK 25 (e.g. [Temurin](https://adoptium.net/))
2. Docker (only if you run Conductor locally)
3. A running Conductor server – either:
    - [Orkes Developer Playground](https://play.orkes.io/) (default configuration), or
    - a local Conductor server:

      ```shell
      docker run --init -p 8081:8081 -p 1234:5000 conductoross/conductor-standalone:3.15.0
      ```

## Configuration

Edit `src/main/resources/application.properties`:

```properties
# Local app server port
server.port=8080
# Conductor server (Playground by default; use http://localhost:8081/api for a local server)
conductor.server.url=https://play.orkes.io/api
# Obtain key & secret from https://play.orkes.io/ → Applications → create application → generate key/secret
conductor.security.client.key-id=<key>
conductor.security.client.secret=<secret>
# Task Domain
conductor.worker.all.domain=saga
```

> ⚠️ **Never commit real credentials.** Prefer environment variables instead, e.g.
> `CONDUCTOR_SECURITY_CLIENT_KEYID` and `CONDUCTOR_SECURITY_CLIENT_SECRET`.

Make sure the `FoodDeliveryWorkflow` (version `1`) workflow definition and its task definitions exist on
your Conductor server (see the [Baeldung article](https://www.baeldung.com/orkes-conductor-saga-pattern-spring-boot)).

## Build & Run

```shell
# build & run tests
./gradlew build

# run the application
./gradlew bootRun
```

The application starts on `http://localhost:8080` and uses a local SQLite database (`food_delivery.db`).

## Trigger the Food Delivery Flow

Create an order by calling the `POST /saga/triggerFoodDeliveryFlow` API:

```shell
curl --location 'http://localhost:8080/saga/triggerFoodDeliveryFlow' \
 --header 'Content-Type: application/json' \
 --data '{
     "customerEmail": "tester.qa@example.com",
     "customerName": "Tester QA",
     "customerContact": "+1(605)123-5674",
     "address": "350 East 62nd Street, NY 10065",
     "restaurantId": 2,
     "foodItems": [
         {
             "item": "Chicken with Broccoli",
             "quantity": 1
         },
         {
             "item": "Veggie Fried Rice",
             "quantity": 1
         },
         {
             "item": "Egg Drop Soup",
             "quantity": 2
         }
     ],
     "additionalNotes": [
         "Do not put spice.",
         "Send cutlery."
     ],
     "paymentMethod" : {
         "type": "Credit Card",
         "details": {
             "number": "1234 4567 3325 1345",
             "cvv": "123",
             "expiry": "05/2022"
         }
     },
     "paymentAmount": 45.34,
     "deliveryInstructions": "Leave at the door!"
  }'
```

The response contains the started `workflowId`; follow its execution in the Conductor UI.

## CI/CD & Dependency Management

- **GitHub Actions** – [`.github/workflows/gradle.yml`](.github/workflows/gradle.yml) builds and tests the project
  with JDK 25 (Temurin) on every push/PR to `main`, and submits the dependency graph for Dependabot alerts.
- **Dependabot** – [`.github/dependabot.yml`](.github/dependabot.yml) keeps dependencies up to date:
    - Gradle dependencies & wrapper: daily (05:00 Asia/Jakarta), grouped into `spring-boot`,
      `test-dependencies` and `build-plugins` for minor/patch updates.
    - GitHub Actions: weekly, grouped into a single PR.
    - Conventional commit prefixes (`chore(deps)`, `ci(deps)`) and labels are applied automatically.

## Relevant Articles

- [Saga Pattern in a Microservices Architecture](https://www.baeldung.com/orkes-conductor-saga-pattern-spring-boot)
- [Conductor OSS](https://github.com/conductor-oss/conductor)
