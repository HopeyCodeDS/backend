# Krystal Distribution Group - Mineral Flow System

## 🏗️ Project Overview

The **Mineral Flow System** is a comprehensive logistics management platform designed for **Krystal Distribution Group (KdG)** to efficiently manage the flow of raw materials from arrival scheduling to final warehousing and shipping. The system follows **Hexagonal Architecture** (ports and adapters) principles and implements **Domain-Driven Design** (DDD) to create a robust, scalable, and maintainable solution.

### 🎯 Core Objectives
- **Optimize Material Flow**: Streamline the entire logistics process from truck arrival to ship departure
- **Real-time Operations**: Provide comprehensive monitoring and management of warehouse operations
- **Automated Processes**: Implement intelligent automation for stock allocation, fee calculation, and compliance tracking
- **Multi-Context Integration**: Seamlessly coordinate operations across landside, warehousing, waterside, and invoicing contexts

---

## 🏛️ Architecture Overview

### **Hexagonal Architecture Implementation**
The system is built using **Hexagonal Architecture** (Ports and Adapters) and strictly following the **DDD (Domain Driven Design)** principles with clear separation between:
- **Domain Layer**: Core business logic, entities, and value objects
- **Application Layer**: Use cases and application services
- **Infrastructure Layer**: External systems, databases, and messaging

### **Bounded Contexts**
The system is organized into four distinct bounded contexts, each handling specific domain responsibilities:

#### 🚛 LandsideContext
- **Purpose**: Manages truck arrivals, appointments, and weighing operations
- **Key Features**:
  - Appointment scheduling and management
  - License plate recognition and gate control
  - Weighing bridge assignment and operation
  - Arrival compliance tracking
  - Real-time truck status monitoring

#### 🏭 WarehousingContext
- **Purpose**: Handles warehouse operations, stock management, and material allocation
- **Key Features**:
  - Warehouse assignment and capacity management
  - Conveyor belt operations and PDT generation
  - FIFO stock allocation strategy
  - Inventory tracking and overflow management
  - Payload delivery processing

#### ⚓ WatersideContext
- **Purpose**: Manages shipping operations, vessel management, and port activities
- **Key Features**:
  - Shipping order processing and validation
  - Inspection operations (IO) management
  - Bunkering operations (BO) coordination
  - PO-SO matching and validation
  - Vessel loading and departure management

#### 💰 InvoicingContext
- **Purpose**: Handles financial operations, fee calculations, and billing
- **Key Features**:
  - Daily storage fee calculations (9:00 AM)
  - Commission fee processing (1% on fulfilled POs)
  - Purchase order management and tracking
  - Invoice generation and payment processing
  - Financial reporting and analytics

---

## 🚀 User Stories Implementation

### **Landside Operations (Stories 1-6)**
1. Appointment Scheduling
2. Truck Recognition
3. Weighing Bridge Assignment
4. Weight Registration
5. Conveyor Belt Docking
6. Weighbridge Ticket Generation

### **Warehouse Management (Stories 7-11)**
7. Arrival Compliance Monitoring  
8. On-Site Truck Monitoring  
9. Warehouse Overview  
10. PO submission  
11. PO Fulfillment Tracking
#11. Automatic Stock Allocation and volume adjustment

### **Shipping Operations (Stories 12-21)**
12. Shipping Order Processing  
13. Inspection Operations  
14. Bunkering Operations  
15. PO-SO Matching  
16. Shipment arrivals 
Vessel Operations Overview  
17. Automatic Oldest Stock Allocation
18. Shipping Order processing
19. Warehouse/ship operations(vessel loading) completion 
20. Ship operation overview
21.  Warehouse volume adjustment

### **Financial Management (Stories 22-23)**
22. Commission Calculation  
23. Daily Storage Fee Calculation  

---

## 🛠️ Technology Stack

### **Backend Technologies**
- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- Gradle

### **Architecture & Design**
- Hexagonal Architecture
- Domain-Driven Design
- Event Sourcing
- CQRS Pattern
- Event-Driven Architecture

### **Infrastructure & Services**
- MySQL 9.0.1 (port 13306)
- RabbitMQ
- Keycloak 25.0.5 (port 8180)
- Docker Compose

### **Security & Authentication**
- OAuth 2.0 (JWT-based authentication)
- Role-Based Access Control
- CORS Configuration

---

## 📂 Project Structure

```plaintext
backend/
├── application/                 # Main application module
├── common/                      # Shared components and events
├── landsideContext/              # Landside operations
├── warehousingContext/           # Warehouse management
├── watersideContext/             # Shipping operations
├── invoicingContext/             # Financial operations
├── infrastructure/               # Docker configuration
└── buildSrc/                     # Gradle build configuration
````

Each bounded context follows the same structure:

* `domain/` — Entities, value objects, and domain services
* `ports/` — Inbound and outbound interfaces
* `core/` — Use case implementations
* `adapters/` — Web controllers, repositories, and external integrations

---

## 🚀 Getting Started

### **Prerequisites**

* Java 17+
* Gradle 8.x
* Docker & Docker Compose
* IntelliJ IDEA (recommended)

### **Setup Steps**

```bash
# 1. Clone Repository
git clone https://gitlab.com/kdg-ti/programming6/students/24-25/momoduopeyemi/backend.git
cd backend

# 2. Start Infrastructure
cd infrastructure
docker-compose up -d

# 3. Build & Run
./gradlew build
./gradlew bootRun
```

### **Service URLs**

* MySQL → `localhost:13306`
* Keycloak → `http://localhost:8180/auth/`
* RabbitMQ → `localhost:5672` (AMQP) / `localhost:15672` (Management)

---

## 🧪 Testing

**Test Files**:

* Landside → `landside-tests.http`
* Warehousing → `warehousing-tests.http`
* Waterside → `waterside-tests.http`
* Invoicing → `invoicing-tests.http`

**Order of testing scenario(security-based) with test files**

- Appointment scheduling by Seller `landside-tests.http`
- Get all appointments by Warehouse Manager `landside-tests.http`
- Recognize truck arrival by truck driver `landside-tests.http`
- Weighing bridge assignment to trucks `landside-tests.http`
- Register Weight and Receive Warehouse Number by truck driver `landside-tests.http`
- Check warehouse assignment status for the trucks `landside-tests.http`
- Deliver Payload of all trucks by truck driver `landside-tests.http`
- Retrieve all PDTs `landside-tests.http`
- Just check the storage cost preparation after payload delivery by accountant `landside-tests.http`
- Generate weighbridge ticket by truck driver before EXIT `landside-tests.http`
- Get Arrival compliance data by warehouse manager `warehousing-tests.http`
- Truck on Site by warehouse manager `warehousing-tests.http`
- Check warehouse overview by warehouse manager `warehousing-tests.http`
- Purchase order submission by the buyer `invoicing-tests.http`
- Shipping order submission by the ship captain (waterside-test.http)
- SO matching with PO by Foreman `waterside-tests.http`
- Inspection operations by the Inspector `waterside-tests.http`
- Bunkering Operation by the Bunkering Officer `waterside-tests.http`
- Check the storage fee cost preparation after vessel loading `waterside-tests.http`
- Port Operations overview by the ship captain `waterside-tests.http`


Run tests in IntelliJ HTTP Client or VS Code REST Client.

---

## 📊 Business Rules & Constraints

* **Truck Capacity**: 40 trucks/hour max
* **Warehouse Capacity**: 500 kt (80% threshold, 110% overflow)
* **Bunkering Operations**: Max 6/day
* **Arrival Windows**: 1-hour slots

**Pricing**:

* Gypsum: \$1/t/day
* Iron Ore: \$5/t/day
* Cement: \$3/t/day
* Petcoke: \$10/t/day
* Slag: \$7/t/day
* 1% commission on fulfilled POs

---

## 📄 License

Licensed under MIT License.

---
