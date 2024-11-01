# Mineral Flow System - Krystal Distribution Group

## Project Overview

The **Mineral Flow System** is designed to manage the logistics of truck drivers, sellers, warehouse managers, and other roles involved in the transportation, weighing, and docking of mineral goods. The system follows **hexagonal architecture** (ports and adapters) and uses **RabbitMQ** to enable communication between different bounded contexts such as **LandsideContext**, **WarehousingContext**, and **WatersideContext**.

This project implements user stories 1-5 and handles the flow from truck arrival to dock assignment, PDT generation, and reweighing at a new bridge.

---

## User Stories Implemented

### **User Story 1:**
> _As a seller, I want to create appointments for trucks so that they can arrive at the facility within the designated time window._

In this user story, the system allows a **seller** to create an appointment for a truck's arrival at the facility. The seller provides truck details such as the license plate, the material being transported, and the arrival window. The system registers the appointment, allowing the truck to be recognized when it arrives.

### **User Story 2:**
> _As a truck driver, I want to be recognized by my license plate so that the gate opens for the truck to enter the facility._

When a truck arrives, the system automatically scans the license plate. If the truck is within the scheduled arrival window, the gate opens and the truck is allowed to enter the facility.

### **User Story 3:**
> _As a truck driver, I want to receive the weighing bridge number when accessing the site so I know where to go._

Upon entering the facility, the truck driver is assigned a weighing bridge where the truck will be weighed. This number is provided immediately after the license plate is recognized, ensuring that the driver knows where to proceed.

### **User Story 4:**
> _As a truck driver, I want to be automatically weighed at the weighing bridge so the weight of my truck is registered._

Once the truck reaches the assigned weighing bridge, the system automatically weighs the truck and logs the weight. This data is used for processing the delivery and any further actions such as warehouse assignment.

### **User Story 5:**
> _As a truck driver, I want to dock to the correct conveyor belt and receive my copy of the PDT and new weighing bridge number._

After being weighed, the truck is assigned to the correct conveyor belt based on the material it carries. The system generates a **Proof of Delivery Ticket (PDT)**, which includes the truck’s license plate, material, conveyor belt number, and the next weighing bridge number.

### **User Story 6:**
> _As a truck driver, I want to pass the weighing bridge and get a Weighbridge Ticket (WBT) that includes: The gross weight upon arrival, tare weight, net weight, timestamp of weighing, and truck license plate number._

In this case, the truck driver needs to pass through the weighing bridge and automatically receive a Weighbridge Ticket (WBT) upon weighing. This ticket serves as an official record that documents the truck's gross weight, tare weight, and net weight, along with the timestamp and license plate number.


### **User Story :**
> _As a warehouse manager, I want to check if trucks arrive within the scheduled arrival windows_

This story implies that the warehouse manager needs real-time insights into truck arrivals and their adherence to their scheduled arrival times.

---

## Project Architecture

The system is built using **hexagonal architecture** (also known as ports and adapters architecture), which emphasizes clear separation between the core business logic (use cases and domain models) and external systems (user interfaces, databases, and messaging systems).

### **Bounded Contexts**
The project is divided into several bounded contexts, each handling a specific domain:

- **LandsideContext**: Manages truck appointments, license plate recognition, and interaction with the weighing bridge.
- **WarehousingContext**: Handles dock and conveyor belt assignments, and generates PDTs.
- **WeighingContext**: Manages the weighing process and assigns new weighing bridge numbers.
- **InvoicingContext**: Manages invoicing and payment processing.

### **RabbitMQ for Inter-Context Communication**
To maintain loose coupling between these contexts, all communication between contexts happens through **RabbitMQ**. Events such as **DockingEvent** are published when key actions happen, and other contexts listen to these events and act accordingly.

---

## Technologies Used

- **Java (Spring Boot)**: Core language and framework for developing the application.
- **RabbitMQ**: Messaging broker used for communication between bounded contexts.
- **Docker**: For managing RabbitMQ instances (optional).
- **Gradle**: Build automation tool.

---

## Getting Started

### Prerequisites

Ensure that the following are installed on your machine:

- **Java 17** (or higher)
- **Gradle**
- **Docker** (if running RabbitMQ in a container)
- **RabbitMQ** (can be run locally or in Docker)

### Installing RabbitMQ

If you don’t already have RabbitMQ running, you can use Docker to spin up a RabbitMQ container:

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
```
### Clone the Repository

```githubexpressionlanguage
    git clone https://gitlab.com/kdg-ti/programming6/students/24-25/momoduopeyemi/backend.git 
    cd backend
```

### Running the Application

The application exposes various endpoints to handle truck operations and system events.

#### Create Appointment (User Story 1)

```http request
POST http://localhost:8192/landsideContext/appointments
Content-Type: application/json

{
"sellerId": "b78c50cd-a93b-4881-a37a-bc79408ef9d5",
"plateNumber": "BE_VUB-T90",
"materialType": "IRON_ORE",
"arrivalWindow": "2024-11-03T09:15:00"
}
```
#### Open Gate on Truck Arrival (User Story 2)

```http request
GET http://localhost:8192/landsideContext/trucks/open/BE_VUB-T90
```
#### Get Assigned Weighing Bridge (User Story 3)

```http request
GET http://localhost:8192/landsideContext/weighing-bridge/BE_VUB-T90
```
#### Weigh Truck at the Weighing Bridge (User Story 4)

```http request
POST http://localhost:8192/landsideContext/weighing-bridge/weigh?licensePlate=BE_VUB-T90&weight=12000
```
### Test Endpoint for Docking a Truck(Start Docking Process) - 1
## Retrieve the Generated PDT(The PDT is Asynchronously retrieved during the inter-context communication)
```http request
POST http://localhost:8192/landsideContext/dock?licensePlate=BE_VUB-T95
Content-Type: application/json
```
### Test Endpoint for Docking a Truck(Start Docking Process) - 2
## Retrieve the Generated PDT(The PDT is Asynchronously retrieved during the inter-context communication)
```http request
POST http://localhost:8192/landsideContext/dock?licensePlate=BE_VUB-T92
Content-Type: application/json
```

#### Get Assigned Weighing Bridge (User Story 5)

```http request
GET http://localhost:8192/landsideContext/weighing-bridge/BE_VUB-T90
```

#### Weigh a Truck and Generate Weighbridge Ticket (WBT)

```http request
POST http://localhost:8192/landsideContext/weighing-bridge/generate-ticket
Content-Type: application/json

{
  "licensePlate": "BE_VUB-T90",
  "grossWeight": 12000,
  "tareWeight": 7000
}
```

### Retrieve All Payload Delivery Tickets
```http request
GET http://localhost:8193/warehousingContext/pdt
Content-Type: application/json
```

### Retrieve a Specific Payload Delivery Ticket by License Plate
```http request
GET http://localhost:8193/warehousingContext/pdt/BE_VUB-T95
Content-Type: application/json
```

### Generate a Weigh Bridge Ticket - 1

```http request
POST http://localhost:8192/landsideContext/weighbridge/generate-ticket
Content-Type: application/json

{
"licensePlate": "BE_VUB-T95",
"materialType": "IRON_ORE",
"tareWeight": 10000,
"weighingBridgeNumber": "Bridge-1"
}
```

### Generate a Weigh Bridge Ticket - 2
POST http://localhost:8192/landsideContext/weighbridge/generate-ticket
Content-Type: application/json

{
"licensePlate": "BE_VUB-T92",
"tareWeight": 18000
}





### Future Features

Invoicing Context: Handle invoicing and payment processing for sellers based on truck deliveries.
Ship Captain and Bunkering Officer: Implement user stories related to ship docking and bunkering operations in the WatersideContext.


### Contributing

Feel free to open issues or submit pull requests to contribute to the project. All contributions are welcome!

### License

This project is licensed under the MIT License. See the LICENSE file for details.

