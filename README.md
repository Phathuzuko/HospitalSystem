# Hospital Patient Admission System

A console-based, menu-driven Java application built for **MediCare Hospital**, replacing their old paper-based patient admission process. The system allows administrative staff to register patients, manage bed allocation, and view ward reports — all through a simple text menu.

## Overview

MediCare Hospital previously tracked patient information and bed availability using paper files, which was slow and prone to errors like misplaced records and incorrect bed allocations. This application digitises that process for a single hospital ward containing 20 beds.

## Features

### 1. Patient Management
- Register a new patient (with duplicate Patient ID prevention)
- Search for a patient by Patient ID
- Update an existing patient's details
- Delete a patient
- Display all registered patients

### 2. Bed Management
- Ward modelled as a 4 x 5 grid (20 beds total), stored using a two-dimensional array
- Allocate an available bed to an Inpatient
- Release a bed when a patient is discharged
- Display the full ward layout
- Display all available beds
- Display all occupied beds
- Prevents allocation when no beds are available

### 3. Reports
- Total number of registered patients
- Total number of occupied beds
- Ward occupancy percentage

### 4. Patient Categories
Patients fall into one of three categories, represented using an enum:
- **Inpatient** — extends the base `Patient` class, requires a hospital bed, and stores ward/bed information
- **Outpatient** — uses the base `Patient` class, no bed required
- **Emergency** — uses the base `Patient` class, no bed required

### 5. Unit Testing
JUnit tests cover:
- Patient registration, search, update, and deletion
- Duplicate Patient ID rejection
- Bed allocation and release
- Prevention of allocation when the ward is full

## Project Structure

```
src/main/java/com/mycompany/hospitalsystem/
├── Hospital.java              - Console menu and program entry point
├── HospitalService.java   - Core business logic linking patients and beds
├── Ward.java               - Manages the 20-bed ward (2D array)
├── Bed.java                - Represents a single hospital bed
├── Patient.java            - Base patient class
├── Inpatient.java          - Patient subclass requiring a bed
└── PatientCategory.java    - Enum: INPATIENT, OUTPATIENT, EMERGENCY

src/test/java/com/mycompany/hospitalsystem/
├── WardTest.java
└── HospitalServiceTest.java
```

## Technologies Used

- Java (JDK 25)
- Maven
- JUnit 4

## How to Run

1. Clone this repository
2. Open the project in NetBeans (or your preferred Java IDE)
3. Ensure dependencies are resolved via Maven (`pom.xml`)
4. Run `Main.java`
5. Follow the on-screen menu options

## How to Run the Tests

Right-click the project in NetBeans and select **Test**, or run:

```
mvn test
```

## Assumptions

- The hospital contains only one ward, with exactly 20 beds
- Each Inpatient may occupy only one bed at a time
- Outpatients and Emergency patients do not require a hospital bed
- All information is stored in memory while the program is running (no persistent storage)

## Author

Student project for PROG6112 – Programming 1B (The Independent Institute of Education)
