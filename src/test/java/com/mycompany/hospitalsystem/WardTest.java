/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalsystem;
import org.junit.Test;
import static org.junit.Assert.*;

public class WardTest {
     //This is to test that a new ward starts with all 20 beds available
    @Test
    public void testWardStartsWithAllBedsAvailable() {
        Ward ward = new Ward();

        assertEquals(20, ward.getAvailableBedCount());
        assertEquals(0, ward.getOccupiedBedCount());
    }

    //This is a test to see that, allocating a bed reduces the available bed count
    @Test
    public void testAllocateBed() {
        Ward ward = new Ward();

        int bedNumber = ward.allocateBed("P001");

        assertTrue(bedNumber > 0);
        assertEquals(19, ward.getAvailableBedCount());
        assertTrue(ward.isBedOccupied(bedNumber));
    }

    // This is to test that releasing a bed makes it available again
    @Test
    public void testReleaseBed() {
        Ward ward = new Ward();
        int bedNumber = ward.allocateBed("P001");

        boolean released = ward.releaseBed(bedNumber);

        assertTrue(released);
        assertFalse(ward.isBedOccupied(bedNumber));
    }

    //To test if bed allocation fails once all 20 beds are occupied, as it should
    @Test
    public void testPreventAllocationWhenFull() {
        Ward ward = new Ward();
        for (int i = 1; i <= Ward.TOTAL_BEDS; i++) {
            ward.allocateBed("P" + i);
        }

        int result = ward.allocateBed("P999");

        assertEquals(-1, result);
    }

    //This is to test the occupancy percentage is calculated correctly
    @Test
    public void testOccupancyPercentage() {
        Ward ward = new Ward();
        ward.allocateBed("P001");
        ward.allocateBed("P002");

        double expected = (2.0 / 20) * 100;

        assertEquals(expected, ward.getOccupancyPercentage(), 0.001);
    }
}
    

