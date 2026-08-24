/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitalsystem;

import java.util.Scanner;

public class HospitalSystem {

 private static Scanner scanner = new Scanner(System.in);
    private static HospitalService hospitalService = new HospitalService();

    public static void main(String[] args) {
        int choice;
        do {
            displayMainMenu();
            choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                    case 2:
                    searchPatient();
                    break;
                case 3:
                    updatePatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    hospitalService.displayAllPatients();
                    break;
                case 6:
                    allocateBed();
                    break;
                case 7:
                    releaseBed();
                    break;
                case 8:
                    hospitalService.getWard().displayWardLayout();
                    break;
                case 9:
                    hospitalService.getWard().displayAvailableBeds();
                    break;
                case 10:
                    hospitalService.getWard().displayOccupiedBeds();
                    break;
                case 11:
                    displayReports();
                    break;
                case 12:
                    sortPatients();
                    break;
                case 0:
                    System.out.println("Exiting the Hospital Patient Admission System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    //builds single-digit bed numbers with a leading zero bed label like B01 or no zero for double digits B20
    
    private static String formatBedNumber(int bedNumber) {
        if (bedNumber < 10) {
            return "B0" + bedNumber;
        } else {
            return "B" + bedNumber;
        }
    }

    //displays the main menu options
    private static void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println(" MEDICARE HOSPITAL - PATIENT ADMISSION SYSTEM");
        System.out.println("========================================");
        System.out.println("1.  Register a new patient");
        System.out.println("2.  Search for a patient");
        System.out.println("3.  Update patient details");
        System.out.println("4.  Delete a patient");
        System.out.println("5.  Display all registered patients");
        System.out.println("6.  Allocate a bed to an inpatient");
        System.out.println("7.  Release a bed");
        System.out.println("8.  Display ward layout");
        System.out.println("9.  Display available beds");
        System.out.println("10. Display occupied beds");
        System.out.println("11. Display reports");
        System.out.println("12. Sort patients");
        System.out.println("0.  Exit");
    }

    //Feature 1 - register a new patient
    //the patient category is asked for first, since Inpatients require extra ward/bed information and an automatic bed allocation attempt
    private static void registerPatient() {
        System.out.println("\n===Register New Patient===");
        String patientId = getStringInput("Enter Patient ID: ");

        //This is to prevent duplicate Patient IDs before asking for the rest of the details
        if (hospitalService.findPatientById(patientId) != null) {
            System.out.println("Error: A patient with this ID already exists.");
            return;
        }

        String firstName = getStringInput("Enter First Name: ");
        String lastName = getStringInput("Enter Last Name: ");
        int age = getIntInput("Enter Age: ");
        String gender = getStringInput("Enter Gender: ");
        String medicalCondition = getStringInput("Enter Medical Condition: ");

        System.out.println("Select Patient Category:");
        System.out.println("1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");
        int categoryChoice = getIntInput("Enter choice: ");

        if (categoryChoice == 1) {
            //Inpatients need a bed allocated as soon as they are registered
            int wardNumber = 1; 
            Inpatient patient = new Inpatient(patientId, firstName, lastName, age, gender, medicalCondition, wardNumber, 0);
            hospitalService.registerPatient(patient);
            int bedNumber = hospitalService.allocateBedToPatient(patientId);
            if (bedNumber == -1) {
                System.out.println("Patient registered, but no beds are currently available.");
            } else {
                System.out.println("Patient registered and allocated Bed " + formatBedNumber(bedNumber));
            }
        } else if (categoryChoice == 2) {
            Patient patient = new Patient(patientId, firstName, lastName, age, gender, medicalCondition, PatientCategory.OUTPATIENT);
            hospitalService.registerPatient(patient);
            System.out.println("Outpatient registered successfully.");
        } else {
            Patient patient = new Patient(patientId, firstName, lastName, age, gender, medicalCondition, PatientCategory.EMERGENCY);
            hospitalService.registerPatient(patient);
            System.out.println("Emergency patient registered successfully.");
        }
    }

    //This is to search for a patient by Patient ID and then display their details
    private static void searchPatient() {
        String patientId = getStringInput("\nEnter Patient ID to search: ");
        Patient patient = hospitalService.findPatientById(patientId);
        if (patient == null) {
            System.out.println("No patient found with ID: " + patientId);
        } else {
            patient.displayDetails();
        }
    }

    //To update an existing patient's details
    private static void updatePatient() {
        String patientId = getStringInput("\nEnter Patient ID to update: ");
        Patient patient = hospitalService.findPatientById(patientId);
        if (patient == null) {
            System.out.println("No patient found with ID: " + patientId);
            return;
        }
        String firstName = getStringInput("Enter new First Name: ");
        String lastName = getStringInput("Enter new Last Name: ");
        int age = getIntInput("Enter new Age: ");
        String gender = getStringInput("Enter new Gender: ");
        String medicalCondition = getStringInput("Enter new Medical Condition: ");

        hospitalService.updatePatient(patientId, firstName, lastName, age, gender, medicalCondition);
        System.out.println("Patient details updated successfully.");
    }

    //To delete a patient and it automatically releases their bed if they had one
    private static void deletePatient() {
        String patientId = getStringInput("\nEnter Patient ID to delete: ");
        boolean deleted = hospitalService.deletePatient(patientId);
        if (deleted) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("No patient found with ID: " + patientId);
        }
    }

    //Feature 2 - allocate a bed to an existing inpatient
    private static void allocateBed() {
        String patientId = getStringInput("\nEnter Patient ID to allocate a bed: ");
        int bedNumber = hospitalService.allocateBedToPatient(patientId);
        if (bedNumber == -1) {
            System.out.println("Unable to allocate a bed. The patient may not exist, may not be an Inpatient,");
            System.out.println("may already have a bed, or no beds may currently be available.");
        } else {
            System.out.println("Bed " + formatBedNumber(bedNumber) + " allocated successfully.");
        }
    }

    //To release a bed when a patient is discharged
    private static void releaseBed() {
        String patientId = getStringInput("\nEnter Patient ID to release their bed: ");
        boolean released = hospitalService.releaseBedFromPatient(patientId);
        if (released) {
            System.out.println("Bed released successfully.");
        } else {
            System.out.println("Unable to release bed. Please check the Patient ID.");
        }
    }

    //Feature 3 - display all the summary reports
    private static void displayReports() {
        System.out.println("\n===Ward Reports===");
        System.out.println("Total registered patients: " + hospitalService.getTotalPatients());
        System.out.println("Total occupied beds: " + hospitalService.getTotalOccupiedBeds());
        System.out.println("Total occupied beds: " + hospitalService.getTotalOccupiedBeds());

        double occupancyPercentage = hospitalService.getWardOccupancyPercentage();

        
        int roundedValue = (int) Math.round(occupancyPercentage * 100); 
        int wholePart = roundedValue / 100;
        int fractionPart = roundedValue % 100;

        //To add the fractional part with a leading zero if needed 
        String fractionText;
        if (fractionPart < 10) {
            fractionText = "0" + fractionPart;
        } else {
            fractionText = "" + fractionPart;
        }

        System.out.println("Ward occupancy percentage: " + wholePart + "." + fractionText + "%");
    }
    

    //This is to allow the user to sort the patient list by surname or Patient ID
    private static void sortPatients() {
        System.out.println("\nSort by:");
        System.out.println("1. Surname");
        System.out.println("2. Patient ID");
        int choice = getIntInput("Enter choice: ");
        if (choice == 1) {
            hospitalService.sortPatientsBySurname();
            System.out.println("Patients sorted by surname.");
        } else if (choice == 2) {
            hospitalService.sortPatientsById();
            System.out.println("Patients sorted by Patient ID.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    //This method is to safely read a String from the console
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    //This method if for safely reading an int from the console,
    //reasking the user if invalid input is entered
    private static int getIntInput(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
        return value;
    }
}

    

