/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalsystem;


//Class representing each and every a single hospital bed. 
//Each bed has its own bed number assigned,
// whether if it is occupied or not,
//and if it is who is the patient who is currently allocated to it
public class Bed {
    private int bedNumber;
    private boolean occupied;
    private String patientId; //ID of the patient occupying this bed, null if empty

    //constructor, that starts off every bed empty
    public Bed(int bedNumber) {
        this.bedNumber = bedNumber;
        this.occupied = false;
        this.patientId = null;
    }

    //getters
    public int getBedNumber() {
        return this.bedNumber;
    }

    public boolean isOccupied() {
        return this.occupied;
    }

    public String getPatientId() {
        return this.patientId;
    }

    //This is to allocate a bed to a patient
    public void allocate(String patientId) {
        this.occupied = true;
        this.patientId = patientId;
    }

    //This is  to release a bed when a patient is discharged
    public void release() {
        this.occupied = false;
        this.patientId = null;
    }
}
