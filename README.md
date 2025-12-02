******************************************************************************************************

//Overview

This project provides an API to calculate reward points for a given customer based on their transactions.
The implementation follows clean code principles, proper error handling, test coverage, and scalable design.

******************************************************************************************************

//Features

1)Calculate rewards for a customer for any given time range

2)Dynamic timeframe support (e.g., last 3 months, last 6 months, custom dates)

3)Clean and modular Java 8 code

4)Validation, exception handling & logging

5)Unit test cases for multiple scenarios

6)Asynchronous API call simulation

7)Readable and consistent coding standards

******************************************************************************************************
//Tech Stack

1)Java 17 version

2)Spring Boot

3)Spring Web

4)Lombok

5)JUnit / Mockito

6)In-Memory Repositories (as per assignment)

******************************************************************************************************
//Project Structure
```
src/main/java
 └── com.customer.rewards
      ├── controller
      ├── service
      ├── repository
      ├── model
      ├── dto
      ├── exception
```

******************************************************************************************************
//URL

Get :   /api/rewards/customer/{customerId}?start=YYYY-MM-DD&end=YYYY-MM-DD

******************************************************************************************************
example:

curl --location --request GET 'http://localhost:8080/api/rewards/3?end=2025-11-30&start=2025-10-01'

******************************************************************************************************
//Sample Response
```json
{
    "customerId": 3,
    "customerName": "Bob Johnson",
    "totalRewardPoints": 120,
    "totalTransactions": 2,
    "monthlyRewardTransactions": [
        {
            "id": 9,
            "year": 2025,
            "month": 11,
            "monthName": "November",
            "amount": 120.0,
            "rewardPoints": 90
        },
        {
            "id": 8,
            "year": 2025,
            "month": 10,
            "monthName": "October",
            "amount": 80.0,
            "rewardPoints": 30
        }
    ]
}
```
******************************************************************************************************
2) URL

curl --location --request GET 'http://localhost:8080/api/rewards/3/async'


******************************************************************************************************

2)Sample Response

```json
{
    "customerId": 3,
    "customerName": "Bob Johnson",
    "totalRewardPoints": 120,
    "totalTransactions": 2,
    "monthlyRewardTransactions": [
        {
            "id": 9,
            "year": 2025,
            "month": 11,
            "monthName": "November",
            "amount": 120.0,
            "rewardPoints": 90
        },
        {
            "id": 8,
            "year": 2025,
            "month": 10,
            "monthName": "October",
            "amount": 80.0,
            "rewardPoints": 30
        }
    ]
}
```


