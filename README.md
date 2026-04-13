# Automated Greenhouse Management System (AGMS)

## Introduction

The Automated Greenhouse Management System (AGMS) is a cloud-native microservices-based application designed to automate greenhouse operations using real-time IoT data. The system integrates with an external IoT provider to monitor environmental conditions such as temperature and humidity and automatically performs actions to maintain optimal conditions for crop growth.

This system is developed using Spring Boot and Spring Cloud technologies, following a scalable and maintainable microservices architecture.

---

## Objectives

* Automate greenhouse environmental control
* Monitor real-time sensor data
* Apply rule-based decision making
* Manage zones and crops efficiently
* Ensure scalability using microservices architecture
* Implement secure communication using API Gateway

---

## System Architecture

The system follows a microservices architecture consisting of infrastructure services and domain services.

### Infrastructure Services

* Eureka Server – Service discovery
* Config Server – Centralized configuration management
* API Gateway – Request routing and security using JWT

### Core Microservices

| Service Name                   | Port | Description                                   |
| ------------------------------ | ---- | --------------------------------------------- |
| Zone Service                   | 8081 | Manages greenhouse zones and threshold values |
| Sensor Telemetry Service       | 8082 | Fetches real-time IoT sensor data             |
| Automation and Control Service | 8083 | Applies rules and triggers actions            |
| Crop Lifecycle Service         | 8084 | Manages crop lifecycle and status             |

---

## System Workflow

1. Sensor Telemetry Service retrieves data from the external IoT API every 10 seconds.
2. The retrieved data includes temperature and humidity values.
3. Sensor data is sent to the Automation and Control Service.
4. Automation Service requests zone threshold values from Zone Service.
5. Based on predefined rules:

    * If temperature is greater than the maximum threshold, TURN_FAN_ON is triggered.
    * If temperature is lower than the minimum threshold, TURN_HEATER_ON is triggered.
    * Otherwise, the system remains in NORMAL state.
6. The decision is stored in the Automation Service database for logging and monitoring.

---

## External IoT Integration

Base URL:

```
http://104.211.95.241:8080/api
```

Features:

* JWT-based authentication
* Device telemetry retrieval
* Real-time data integration

---

## Security Implementation

Security is implemented at the API Gateway using JWT authentication.

Request Header:

```
Authorization: Bearer <token>
```

Behavior:

* Valid token: Request is forwarded to the appropriate microservice
* Invalid or missing token: Returns 401 Unauthorized

---

## Database Design

Each microservice uses its own database following the database-per-service pattern.

| Service            | Database      |
| ------------------ | ------------- |
| Zone Service       | zone_db       |
| Sensor Service     | sensor_db     |
| Automation Service | automation_db |
| Crop Service       | crop_db       |

---

## Technologies Used

* Java 17
* Spring Boot
* Spring Cloud (Eureka, Config Server, API Gateway)
* OpenFeign and RestTemplate
* MySQL
* JWT Authentication
* External IoT API Integration

---

## System Setup and Execution

### Step 1: Start Config Server

```
http://localhost:8888
```

### Step 2: Start Eureka Server

```
http://localhost:8761
```

### Step 3: Start API Gateway

```
http://localhost:8080
```

### Step 4: Start Microservices in Order

1. Zone Service
2. Sensor Telemetry Service
3. Automation and Control Service
4. Crop Lifecycle Service

---

## API Endpoints

### Sensor Telemetry Service

```
GET /api/sensors/latest
```

### Automation and Control Service

```
POST /api/automation/process
GET /api/automation/logs
```

### Zone Service

```
POST /api/zones
GET /api/zones/{id}
PUT /api/zones/{id}
DELETE /api/zones/{id}
```

### Crop Lifecycle Service

```
POST /api/crops
PUT /api/crops/{id}/status
GET /api/crops
```

---

## Screenshots

Add the following screenshots inside an "images" folder in the repository and reference them below.

### Eureka Dashboard

![Eureka Dashboard](<img width="1920" height="1080" alt="Eureka" src="https://github.com/user-attachments/assets/d6d1a30e-878a-446e-b0ce-c3fe4304a570" />)

---

## Postman Collection
```
postman/AGMS.postman_collection.json
```
---

## Project Structure

```
AGMS-Microservice-CW
│
├── eureka-server
├── config-server
├── api-gateway
├── zone-service
├── sensor-service
├── automation-service
├── crop-service
├── images
├── postman
└── README.md
```

---

## Testing

* Use Postman to test APIs
* Send requests through API Gateway
* Include JWT token in request headers
* Verify responses from all services
* Check logs and database updates

---

## Service Monitoring

Eureka Dashboard:

```
http://localhost:8761
```

All services should be in UP status.

---

## Key Features

* Real-time IoT data processing
* Automated decision making using rule engine
* Scalable microservices architecture
* Centralized configuration management
* Secure API Gateway with JWT authentication

---

## Conclusion

The AGMS system demonstrates how microservices architecture can be used to build a scalable and intelligent greenhouse automation solution. By integrating IoT data with automated decision-making, the system reduces manual effort and improves operational efficiency.

---

## Author

Nethushi Navodya
GDSE Software Architecture Coursework
