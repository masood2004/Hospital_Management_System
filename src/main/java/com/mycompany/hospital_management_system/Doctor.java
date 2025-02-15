/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital_management_system;

/**
 *
 * @author hmaso
 */
public class Doctor {
    private String Id, Name, Contact, Speciality;
    private int fees;

    public Doctor() {
    }

    
    
    public Doctor(String Id, String Name, String Contact, String Speciality, int fees) {
        this.Id = Id;
        this.Name = Name;
        this.Contact = Contact;
        this.Speciality = Speciality;
        this.fees = fees;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpecialty(String Speciality) {
        this.Speciality = Speciality;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return "Doctor{" + "Id=" + Id + ", Name=" + Name + ", Contact=" + Contact + ", Speciality=" + Speciality + ", fees=" + fees + '}';
    }
    
    
}
