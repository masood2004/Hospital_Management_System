/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital_management_system;

/**
 *
 * @author hmaso
 */

class CNode {
    Checkup checkup;
    CNode next, previous;

    public CNode(Checkup checkup) {
        next = null;
        previous = null;
        this.checkup = checkup;
    }

}

public class CheckupList {

    CNode head, tail;

    public CheckupList() {
        head = null;
        tail = null;
    }

    public void enqueue(Checkup checkup) {
        CNode node = new CNode(checkup);

        if (head == null || tail == null) {
            head = node;
            tail = node;
        } else if (head.checkup.getPriority() < checkup.getPriority()) {
            head.next = node;
            node.previous = head;
            head = node;
        } else if (tail.checkup.getPriority() >= checkup.getPriority()) {
            tail.previous = node;
            node.next = tail;
            tail = node;
        } else {
            CNode temp = tail;
            while (temp != null) {
                if (temp.checkup.getPriority() >= checkup.getPriority()) {
                    break;
                }
                temp = temp.next;
            }
            node.next = temp;
            node.previous = temp.previous;
            temp.previous.next = node;
            temp.previous = node;
        }
    }

    public Checkup dequeue() {
        if (head == null) {
            return null;
        }
        CNode checkup = head;
        head = head.next;
        return checkup.checkup;
    }

    /**
     *
     * @param index
     * @return
     */
    public Patient getPatient(int index) {
        CNode temp = head;
        int i = 0;
        while (temp != null) {
            if (index == i) {
                break;
            }
            i++;
            temp = temp.previous;
        }
        return temp.checkup.getPatient();
    }

    public int size() {
        CNode temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.previous;
        }
        return count;
    }

    public Checkup getAtIndex(int index) {
        CNode temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.previous;
        }
        return temp.checkup;
    }

    public void Print() {
        CNode temp = head;
        while (temp != null) {
            System.out.println(temp.checkup.getPriority());
            temp = temp.previous;
        }
    }

}
