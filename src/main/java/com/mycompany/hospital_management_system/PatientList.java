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

    public Patient searchByID(String id) {
        PNode temp = head;
        while (temp != null) {
            if(temp.patient.getId().equals(id)){
                return temp.patient;
            }
            temp = temp.previous;
        }
        return null;
    }

}
