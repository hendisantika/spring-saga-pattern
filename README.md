# Saga Pattern Using Orkes Conductor

This is an example project showing how to build event driven applications
using [Conductor](https://github.com/conductor-oss/conductor)

# Pre-requisites

1. Docker
2. Running conductor server

**Start the conductor server**

```shell
docker run --init -p 8081:8081 -p 1234:5000 conductoross/conductor-standalone:3.15.0
```

### The application is running; the next step is to create an order by calling the triggerRideBookingFlow API from the application.

```shell
$ curl --location 'http://localhost:8081/triggerFoodDeliveryFlow' \
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

### Relevant Articles:

- [Saga Pattern in a Microservices Architecture](https://www.baeldung.com/orkes-conductor-saga-pattern-spring-boot)
