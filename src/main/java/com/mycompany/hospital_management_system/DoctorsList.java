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

    }
}
