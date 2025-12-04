******************************************************************************************************
This project exposes REST APIs to calculate customer reward points based on their monthly transactions.

******************************************************************************************************

Features

✔️ Calculate rewards for any customer

✔️ Support for dynamic time ranges (last 3 months, 6 months, custom dates)

✔️ Clean, modular Java 17 code

✔️ Proper validation, exceptions, and logging

✔️ JUnit + Mockito test cases

✔️ Readable and consistent coding standards

******************************************************************************************************
Tech Stack

✔️ Java 17

✔️ Spring Boot

✔️ Lombok

✔️ JUnit / Mockito

✔️ In-Memory Repositories

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

How to Run the Application
=> Prerequisites

✔️ JDK 17 installed

✔️ Maven installed

✔️ Any IDE (IntelliJ / Eclipse)

1. Clone the project
```
git clone <your-repo-url>

```

2. Build the project
```
mvn clean install

```
3. Run the application
```
mvn spring-boot:run

```
Application starts on:
```
http://localhost:8080

```

******************************************************************************************************
✔️ API Endpoint

GET:   
```
/api/rewards/customer/{customerId}?start=YYYY-MM-DD&end=YYYY-MM-DD

```
******************************************************************************************************
Example request:
```
curl --location --request GET 'http://localhost:8080/api/rewards/3?end=2025-11-30&start=2025-10-01'

```
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


