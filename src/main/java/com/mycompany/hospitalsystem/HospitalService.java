/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalsystem;

import java.util.ArrayList;


//Core service class that maintains the list of registered patients
//links patient management with bed allocation in the Ward
//Does the business rules like having no duplicate Patient IDs and only allowing Inpatients to be allocated a bed.

public class HospitalService {

    private ArrayList<Patient> patients;
    private Ward ward;

    //constructor
    public HospitalService() {
        patients = new ArrayList<>();
        ward = new Ward();
    }

    //getters, used by the menu and by the unit tests
    public ArrayList<Patient> getPatients() {
        return this.patients;
    }

    public Ward getWard() {
        return this.ward;
    }

    //Feature 1: register a new patient
    //THis returns false if a patient with the same ID already exists
    //thus it prevents duplicate Patient IDs, from happening 
    public boolean registerPatient(Patient patient) {
        if (findPatientById(patient.getPatientId()) != null) {
            return false; //duplicate patient ID
        }
        patients.add(patient);
        return true;
    }

    //To search for a patient using their Patient ID
    //THis also returns null if no patient with that ID is found
    public Patient findPatientById(String patientId) {
        for (Patient patient : patients) {
            if (patient.getPatientId().equalsIgnoreCase(patientId)) {
                return patient;
            }
        }
        return null;
    }

    //To update an existing patient's details
    //This returns true if the patient was found and updated
    public boolean updatePatient(String patientId, String firstName, String lastName, int age, String gender, String medicalCondition) {
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            return false;
        }
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);
        return true;
    }

    //This is to delete a patient from the system
    // and if the patient was an inpatient, their bed is released first
    //and it also returns true if the patient was found and removed
    public boolean deletePatient(String patientId) {
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            return false;
        }
        if (patient instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) patient;
            ward.releaseBed(inpatient.getBedNumber());
        }
        patients.remove(patient);
        return true;
    }

    //To display all registered patients
    //Inpatient's overridden displayDetails() is called automatically
    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients are currently registered.");
            return;
        }
        System.out.println("===All Registered Patients===");
        for (Patient patient : patients) {
            patient.displayDetails();
            System.out.println("----------------------------");
        }
    }

    //Feature 2: allocate a bed to an inpatient
    //The only type of patientsthat may be allocated a bed are Inpatients
    //This returns the allocated bed number,if allocation was not possible, or is already assigned a bed
    public int allocateBedToPatient(String patientId) {
        Patient patient = findPatientById(patientId);
        if (patient == null || !(patient instanceof Inpatient)) {
            return -1; 
        }
        Inpatient inpatient = (Inpatient) patient;
        if (inpatient.getBedNumber() != 0) {
            return -1; 
        }
        int bedNumber = ward.allocateBed(patientId);
        if (bedNumber != -1) {
            inpatient.setBedNumber(bedNumber);
        }
        return bedNumber;
    }

    //This releases a bed when an inpatient is discharged
    public boolean releaseBedFromPatient(String patientId) {
        Patient patient = findPatientById(patientId);
        if (patient == null || !(patient instanceof Inpatient)) {
            return false;
        }
        Inpatient inpatient = (Inpatient) patient;
        boolean released = ward.releaseBed(inpatient.getBedNumber());
        if (released) {
            inpatient.setBedNumber(0); //0 indicates the patient no longer has a bed
        }
        return released;
    }

    //Feature 3: reports
    //The total number of registered patients
    public int getTotalPatients() {
        return patients.size();
    }

    //The total number of occupied beds
    public int getTotalOccupiedBeds() {
        return ward.getOccupiedBedCount();
    }

    //The ward occupancy percentage
    public double getWardOccupancyPercentage() {
        return ward.getOccupancyPercentage();
    }

    //This sorts patients by surname (last name) using a manual bubble sort
    public void sortPatientsBySurname() {
      int n = patients.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                Patient current = patients.get(j);
                Patient next = patients.get(j + 1);
                if (current.getLastName().compareToIgnoreCase(next.getLastName()) > 0) {
                    //swap current and next
                    patients.set(j, next);
                    patients.set(j + 1, current);
                }
            }
        }
    }

    //This sorts patients by Patient ID using a manual bubble sort
    public void sortPatientsById() {
        int n = patients.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                Patient current = patients.get(j);
                Patient next = patients.get(j + 1);
                if (current.getPatientId().compareToIgnoreCase(next.getPatientId()) > 0) {
                    //swap current and next
                    patients.set(j, next);
                    patients.set(j + 1, current);
                }
            }
        }
    }
}

