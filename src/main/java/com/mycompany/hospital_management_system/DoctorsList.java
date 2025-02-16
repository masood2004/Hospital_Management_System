/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital_management_system;

/**
 *
 * @author hmaso
 */
class DNode {

    Doctor doctor;
    DNode next, previous;

    public DNode() {
    }

    public DNode(Doctor doctor) {
        this.doctor = doctor;
        next = null;
        previous = null;
    }

}

public class DoctorsList {

    DNode head, tail;

    public DoctorsList() {
        head = null;
        tail = null;
    }

    public void insert(Doctor doctor) {
        DNode node = new DNode(doctor);

        if (head == null || tail == null) {
            head = node;
            tail = node;
        } else {
            head.next = node;
            node.previous = head;
            head = node;
        }
    }

    public Doctor searchByID(String Id) {
        DNode temp = head;
        while (temp != null) {
            if (temp.doctor.getId().equals(Id)) {
                return temp.doctor;
            }
            temp = temp.previous;
        }
        return null;
    }

    public Doctor searchByContact(String contact) {
        DNode temp = head;
        while (temp != null) {
            if (temp.doctor.getContact().equals(contact)) {
                return temp.doctor;
            }
            temp = temp.previous;
        }
        return null;
    }

    public Doctor getAtIndex(int index) {
        DNode temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.previous;
        }
        return temp.doctor;
    }

    public int size() {
        DNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.previous;
        }
        return count;
    }

    public void AllDoctorInfo() {
        DNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            System.out.println(count + ":   " + temp.doctor.toString());
            temp = temp.previous;
        }
    }

}
