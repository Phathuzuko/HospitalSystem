/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalsystem;

//Represents an Inpatient, patients who have been assigned a bed in the hospital room. 
//Inherits from the base Patient class, and adds the ward and bed info necessary for bed management.

public class Inpatient extends Patient {
 private int wardNumber;
    private int bedNumber;

    //constructor, calls the Patient constructor using super() to
    //initialise the inherited attributes, and then it sets the ward/bed info.
   
    public Inpatient(String patientId, String firstName, String lastName, int age, String gender, String medicalCondition, int wardNumber, int bedNumber) {
        super(patientId, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    //getters
    public int getWardNumber() {
        return this.wardNumber;
    }

    public int getBedNumber() {
        return this.bedNumber;
    }

    //setters
    public void setWardNumber(int wardNumber) {
        this.wardNumber = wardNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }
    
     private String formatBedNumber(int number) {
        if (number < 10) {
            return "B0" + number;
        } else {
            return "B" + number;
        }
    }

    //To Override displayDetails() to include the ward and bed information on top of the standard patient details
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: " + (bedNumber == 0 ? "Not Allocated" : formatBedNumber(bedNumber)));
    }
    }
      

