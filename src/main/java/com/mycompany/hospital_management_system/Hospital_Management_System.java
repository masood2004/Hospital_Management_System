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
        Patient p1 = new Patient("23", "ASDF", "02301232130");
        Patient p2 = new Patient("24", "ASDFasdf", "023023432130");
        Patient p3 = new Patient("22", "ASasdfDF", "0230123432130");

        PatientList plist = new PatientList();
        plist.insert(p1);
        plist.insert(p2);
        plist.insert(p3);
        
        System.out.println(plist.searchByID("23").getName());
        System.out.println(plist.searchByID("23").getContact());
                
        
       
    }
}
