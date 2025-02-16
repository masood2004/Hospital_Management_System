/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital_management_system;

/**
 *
 * @author hmaso
 */
public class Checkup {
    Doctor Doctor;
    Patient Patient;
    int Priority;
    String Recommandation, Date;

    public Checkup(Doctor Doctor, Patient Patient, int Priority, String Recommandation, String Date) {
        this.Doctor = Doctor;
        this.Patient = Patient;
        this.Priority = Priority;
        this.Recommandation = Recommandation;
        this.Date = Date;
    }

    public Doctor getDoctor() {
        return Doctor;
    }

    public void setDoctor(Doctor Doctor) {
        this.Doctor = Doctor;
    }

    public Patient getPatient() {
        return Patient;
    }

    public void setPatient(Patient Patient) {
        this.Patient = Patient;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int Priority) {
        this.Priority = Priority;
    }

    public String getRecommandation() {
        return Recommandation;
    }

    public void setRecommandation(String Recommandation) {
        this.Recommandation = Recommandation;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    @Override
    public String toString() {
        return "Checkup{" + "Doctor=" + Doctor.toString() + ", Patient=" + Patient.toString() + ", Priority=" + Priority
                + ", Recommandation=" + Recommandation + ", Date=" + Date + '}';
    }

}
