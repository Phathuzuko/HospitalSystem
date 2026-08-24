/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalsystem;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Student
 */
public class HospitalServicesTest {

    //This is to test registering a patient
    @Test
    public void testRegisterPatient() {
        HospitalService service = new HospitalService();
        Patient patient = new Patient("P001", "John", "Smith", 30, "Male", "Flu", PatientCategory.OUTPATIENT);

        boolean result = service.registerPatient(patient);

        assertTrue(result);
        assertEquals(1, service.getTotalPatients());
    }

    //To Test that duplicate Patient IDs are rejected
    @Test
    public void testRegisterDuplicatePatientId() {
        HospitalService service = new HospitalService();
        Patient patient1 = new Patient("P001", "John", "Smith", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        Patient patient2 = new Patient("P001", "Jane", "Doe", 25, "Female", "Cold", PatientCategory.OUTPATIENT);
        service.registerPatient(patient1);

        boolean result = service.registerPatient(patient2);

        assertFalse(result);
        assertEquals(1, service.getTotalPatients());
    }

    //To test searching for a patient by Patient ID
    @Test
    public void testSearchForPatient() {
        HospitalService service = new HospitalService();
        Patient patient = new Patient("P001", "John", "Smith", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        service.registerPatient(patient);

        Patient found = service.findPatientById("P001");
        Patient notFound = service.findPatientById("P999");

        assertNotNull(found);
        assertEquals("John", found.getFirstName());
        assertNull(notFound);
    }

    //To test that updating a patient's details works
    @Test
    public void testUpdatePatientDetails() {
        HospitalService service = new HospitalService();
        Patient patient = new Patient("P001", "John", "Smith", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        service.registerPatient(patient);

        boolean result = service.updatePatient("P001", "Johnny", "Smithson", 31, "Male", "Recovered");
        Patient updated = service.findPatientById("P001");

        assertTrue(result);
        assertEquals("Johnny", updated.getFirstName());
        assertEquals("Smithson", updated.getLastName());
        assertEquals(31, updated.getAge());
    }

    //This tests deleting a patient successfully 
    @Test
    public void testDeletePatient() {
        HospitalService service = new HospitalService();
        Patient patient = new Patient("P001", "John", "Smith", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        service.registerPatient(patient);

        boolean result = service.deletePatient("P001");

        assertTrue(result);
        assertEquals(0, service.getTotalPatients());
    }

    //To test allocating a bed to an inpatient
    @Test
    public void testAllocateBed() {
        HospitalService service = new HospitalService();
        Inpatient inpatient = new Inpatient("P002", "Mary", "Jones", 45, "Female", "Surgery", 1, 0);
        service.registerPatient(inpatient);

        int bedNumber = service.allocateBedToPatient("P002");

        assertTrue(bedNumber > 0);
        assertEquals(bedNumber, inpatient.getBedNumber());
        assertTrue(service.getWard().isBedOccupied(bedNumber));
    }

    //To test releasing a bed from an inpatient
    @Test
    public void testReleaseBed() {
        HospitalService service = new HospitalService();
        Inpatient inpatient = new Inpatient("P002", "Mary", "Jones", 45, "Female", "Surgery", 1, 0);
        service.registerPatient(inpatient);
        int bedNumber = service.allocateBedToPatient("P002");

        boolean released = service.releaseBedFromPatient("P002");

        assertTrue(released);
        assertFalse(service.getWard().isBedOccupied(bedNumber));
    }
}
    
