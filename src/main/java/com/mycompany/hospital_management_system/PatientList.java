/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital_management_system;

/**
 *
 * @author hmaso
 */
class PNode {

    Patient patient;
    PNode next, previous;

    public PNode() {
    }

    public PNode(Patient patient) {
        this.patient = patient;
        next = null;
        previous = null;
    }

}

public class PatientList {

    PNode head, tail;

    public PatientList() {
        head = null;
        tail = null;
    }

    public void insert(Patient patient) {
        PNode node = new PNode(patient);

        if (head == null || tail == null) {
            head = node;
            tail = node;
        } else {
            head.next = node;
            node.previous = head;
            head = node;
        }
    }

    public Patient searchByID(String Id) {
        PNode temp = head;
        while (temp != null) {
            if (temp.patient.getId().equals(Id)) {
                return temp.patient;
            }
            temp = temp.previous;
        }
        return null;
    }

    public Patient searchByName(String Name) {
        PNode temp = head;
        while (temp != null) {
            if (temp.patient.getName().equals(Name)) {
                return temp.patient;
            }
            temp = temp.previous;
        }
        return null;
    }

    public Patient searchByContact(String Contact) {
        PNode temp = head;
        while (temp != null) {
            if (temp.patient.getContact().equals(Contact)) {
                return temp.patient;
            }
            temp = temp.previous;
        }
        return null;
    }

    public int size() {
        PNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.previous;
        }
        return count;
    }

    public void AllPatientInfo() {
        PNode temp = head;
        int count = 0;

        while (temp != null) {
            count++;
            System.out.println(count + ": " + temp.patient.toString());
            temp = temp.previous;
        }
    }

}
