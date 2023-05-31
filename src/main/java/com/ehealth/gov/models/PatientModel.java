package com.ehealth.gov.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="patients")
public class PatientModel
{
    @Id
    @GeneratedValue
    int id;
    
    String patientName;
    String patientAddress;
    String patientPhone;


    public PatientModel() {
    }

    public PatientModel(int id, String patientName, String patientAddress, String patientPhone) {
        this.id = id;
        this.patientName = patientName;
        this.patientAddress = patientAddress;
        this.patientPhone = patientPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

}
