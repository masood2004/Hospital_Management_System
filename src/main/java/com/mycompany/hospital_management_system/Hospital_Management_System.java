/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.hospital_management_system;

/**
 *
 * @author hmaso
 */
public class Hospital_Management_System {

    public static void main(String[] args) {
        Patient p1 = new Patient("18", "Ayesha", "03174742145");
        Patient p2 = new Patient("24", "ASDFasdf", "023023432130");
        Patient p3 = new Patient("22", "ASasdfDF", "0230123432130");
        Patient p4 = new Patient("22", "ASazxscF", "0230123430");

        PatientList plist = new PatientList();
        plist.insert(p1);
        plist.insert(p2);
        plist.insert(p3);
        plist.insert(p4);

//        System.out.println(plist.searchByID("18"));
//        System.out.println(plist.searchByName("Ayesha"));
//        System.out.println(plist.searchByContact("03174742145"));

        Doctor d1 = new Doctor("000", "James", "1239015470983", "Skin", 2500);
        Doctor d2 = new Doctor("001", "Jones", "1239043983", "Eyes", 3500);
        Doctor d3 = new Doctor("002", "Sirius", "123123420983", "Child", 1500);

        DoctorsList Dlist = new DoctorsList();

        Dlist.insert(d1);
        Dlist.insert(d2);
        Dlist.insert(d3);
        
        Dlist.AllDoctorInfo();
        
        CheckupList checkupList = new CheckupList();
        
        Checkup c1 = new Checkup(d1, p1, 3, "A", "55");
        Checkup c2 = new Checkup(d2, p2, 2, "B", "55");
        Checkup c3 = new Checkup(d3, p3, 4, "C", "54");
        Checkup c4 = new Checkup(d1, p4, 1, "D", "55");
        Checkup c5 = new Checkup(d3, p3, 4, "E", "54");

        checkupList.enqueue(c1);
        checkupList.enqueue(c2);
        checkupList.enqueue(c3);
        checkupList.enqueue(c4);
        checkupList.enqueue(c5);
        checkupList.Print();
    }
}
