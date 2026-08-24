/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalsystem;


//Represents the hospital ward with 20 beds organized * in a 4 by 5 configuration (4 rows of 5 beds).
// Allocates and releases beds and prepares bed-related reports.
public class Ward {
 public static final int ROWS = 4;
 public static final int COLUMNS = 5;
 public static final int TOTAL_BEDS = ROWS * COLUMNS; //20 beds

    private Bed[][] beds;

    //constructor, creates a 4 x 5 grid of beds, numbered B01 to B20
    //bed numbers are assigned row by row 
    public Ward() {
        beds = new Bed[ROWS][COLUMNS];
        int bedNumber = 1;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                beds[row][col] = new Bed(bedNumber);
                bedNumber++;
            }
        }
    }

    //builds a single-digit bed numbers with a leading zero bed label like "B01" or no just the B and the number for double digits like "B20"
    //
    private String formatBedNumber(int bedNumber) {
        if (bedNumber < 10) {
            return "B0" + bedNumber;
        } else {
            return "B" + bedNumber;
        }
    }

    //This finds the first available (unoccupied) bed by scanning the 2D array
    // and returns null if no beds are available
    private Bed findAvailableBed() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (!beds[row][col].isOccupied()) {
                    return beds[row][col];
                }
            }
        }
        return null;
    }

    //To find a bed object using its bed number by scanning the 2D array
    private Bed getBed(int bedNumber) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (beds[row][col].getBedNumber() == bedNumber) {
                    return beds[row][col];
                }
            }
        }
        return null;
    }

    //To allocate an available bed to an inpatient
    //and thes returns the bed number allocated, or -1 if no beds are available
 
    public int allocateBed(String patientId) {
        Bed bed = findAvailableBed();
        if (bed == null) {
            return -1; 
        }
        bed.allocate(patientId);
        return bed.getBedNumber();
    }

    //To release a bed when an ipatient is discharged
    // and returns true if the bed was found and released, false otherwise
    public boolean releaseBed(int bedNumber) {
        Bed bed = getBed(bedNumber);
        if (bed != null && bed.isOccupied()) {
            bed.release();
            return true;
        }
        return false;
    }

    //This is to check whether a specific bed is already occupied
    
    public boolean isBedOccupied(int bedNumber) {
        Bed bed = getBed(bedNumber);
        return bed != null && bed.isOccupied();
    }

    //To display the complete ward layout in a 4 x 5 grid
    //an "X" shows an occupied bed, "O" shows an available bed
    public void displayWardLayout() {
        System.out.println("===Ward Layout (4 x 5)===");
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Bed bed = beds[row][col];
                String status = bed.isOccupied() ? "X" : "O";
                System.out.print(formatBedNumber(bed.getBedNumber()) + "[" + status + "] ");
            }
            System.out.println();
        }
        System.out.println("(O = Available, X = Occupied)");
    }

    //To display all available (unoccupied) beds
    public void displayAvailableBeds() {
        System.out.println("===Available Beds===");
        boolean found = false;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Bed bed = beds[row][col];
                if (!bed.isOccupied()) {
                    System.out.println("Bed " + formatBedNumber(bed.getBedNumber()));
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No beds are currently available.");
        }
    }

    //To display all occupied beds along with the patient ID assigned to them
    public void displayOccupiedBeds() {
        System.out.println("===Occupied Beds===");
        boolean found = false;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Bed bed = beds[row][col];
                if (bed.isOccupied()) {
                    System.out.println("Bed " + formatBedNumber(bed.getBedNumber()) + " - Patient ID: " + bed.getPatientId());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No beds are currently occupied.");
        }
    }

    //To count the number of occupied beds
    public int getOccupiedBedCount() {
        int count = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (beds[row][col].isOccupied()) {
                    count++;
                }
            }
        }
        return count;
    }

    //This is to count the number of available beds
    public int getAvailableBedCount() {
        return TOTAL_BEDS - getOccupiedBedCount();
    }

    //To calculate the ward occupancy percentage
    public double getOccupancyPercentage() {
        return ((double) getOccupiedBedCount() / TOTAL_BEDS) * 100;
    }
}


