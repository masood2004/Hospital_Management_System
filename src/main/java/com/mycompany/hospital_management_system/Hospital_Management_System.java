/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.hospital_management_system;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author hmaso
 */
public class Hospital_Management_System {

    public static void main(String[] args) {
        PatientList pList = new PatientList();
        DoctorsList dList = new DoctorsList();
        Scanner sc = new Scanner(System.in);

        readPFile(pList);

        readDFile(dList);

        String choice;
        OUTER: while (true) {
            MainMenu();
            choice = sc.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println("\n Doctor ID");
                    String id = sc.nextLine();
                    System.out.println("\n Doctor Name");
                    String name = sc.nextLine();
                    System.out.println("\n Doctor Contact");
                    String contact = sc.nextLine();
                    System.out.println("\n Doctor Specialization");
                    String specialization = sc.nextLine();
                    System.out.println("\n Doctor Fee");
                    int fee = sc.nextInt();
                    sc.nextLine();
                    Doctor d = new Doctor(id, name, contact, specialization, fee);
                    dList.insert(d);

                    writeDFile(dList);
                    System.out.println("Doctor added successfully and saved to file.");
                }
                case "2" -> {
                    System.out.println("\n Patient ID");
                    String id = sc.nextLine();
                    System.out.println("\n Patient Name");
                    String name = sc.nextLine();
                    System.out.println("\n Patient Contact");
                    String contact = sc.nextLine();
                    Patient patient = new Patient(id, name, contact);
                    pList.insert(patient);

                    writePFile(pList);
                    System.out.println("Patient added successfully and saved to file.");
                }
                case "3" -> dList.AllDoctorInfo();
                case "4" -> pList.AllPatientInfo();
                case "5" -> {
                    System.out.println("\nWelcome to Checkup Menu \n");
                    CheckupList[] cList = new CheckupList[dList.size()];
                    for (int i = 0; i < cList.length; i++) {
                        cList[i] = new CheckupList();
                        Doctor doctor = dList.getAtIndex(i);
                        System.out.println("\n\nEnter Patient for Doctor");
                        System.out.println("Doctor Name: " + doctor.getName());
                        System.out.println("Doctor Specialization: " + doctor.getSpeciality());
                        System.out.println("Doctor Fee: " + doctor.getFees());

                        System.out.println("All Patients: ");
                        pList.AllPatientInfo();

                        while (true) {
                            System.out.println("Enter Patient ID for Checkup or 0 to Exit");
                            String id = sc.nextLine();
                            if (id.equals("0")) {
                                break;
                            }
                            System.out.println("Priority '3' for Emergency, '2' for Normal, '1' for Followup");
                            String priority = sc.nextLine();

                            int p = 1;
                            if (priority.equals("3")) {
                                p = 3;
                            } else if (priority.equals("2")) {
                                p = 2;
                            }
                            Patient patient = pList.searchByID(id);
                            if (patient == null) {
                                System.out.println("Patient not found");
                            } else {
                                Checkup checkup = new Checkup(doctor, patient, p, "",
                                        "" + java.util.Calendar.getInstance().getTime().toString());
                                cList[i].enqueue(checkup);
                            }
                        }
                    }
                    for (int i = 0; i < cList.length; i++) {
                        System.out.println(
                                "\n\n Patient " + (i + 1) + " In  Queue for Doctor " + dList.getAtIndex(i).getName());
                        for (int j = 0; j < cList[i].size(); j++) {
                            System.out.println("Enter Recommendation for Patient: " + cList[i].getPatient(j));
                            String rec = sc.nextLine();
                            cList[i].addRecommandation(j, rec);
                        }
                    }
                }
                case "0" -> {
                    break OUTER;
                }
                default -> {
                }
            }
        }
        sc.close();
    }

    public static void MainMenu() {
        System.out.println("\n==========================================");
        System.out.println("      *****  HMS - Hospital Management System  *****      ");
        System.out.println("==========================================");
        System.out.println("                 MAIN MENU                ");
        System.out.println("==========================================\n");

        System.out.println("  [1]  Insert New Doctor");
        System.out.println("  [2]  Insert New Patient");
        System.out.println("------------------------------------------");
        System.out.println("  [3]  Display All Doctors");
        System.out.println("  [4]  Display All Patients");
        System.out.println("------------------------------------------");
        System.out.println("  [5]  Checkup Menu");
        System.out.println("------------------------------------------");
        System.out.println("  [0]  Exit");

        System.out.println("\n==========================================");
        System.out.print("  Please enter your choice: ");
    }

    public static void writeDFile(DoctorsList dList) {
        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter("Doctors.txt"))) {
            for (int i = 0; i < dList.size(); i++) {
                Doctor doctor = dList.getAtIndex(i);
                myWriter.write(doctor.getId() + "," + doctor.getName() + "," + doctor.getContact() + ","
                        + doctor.getSpeciality() + "," + doctor.getFees() + "\n");
            }
            System.out.println("Successfully wrote to Doctors.txt.");
        } catch (IOException e) {
            System.out.println("Error writing to Doctors.txt: " + e.getMessage());
        }
    }

    public static void readDFile(DoctorsList dList) {
        File myObj = new File("Doctors.txt");
        try {
            if (!myObj.exists()) {
                myObj.createNewFile(); // Create file if it doesn’t exist
                System.out.println("Doctors.txt file created.");
                return; // Exit since there is nothing to read
            }

            try (Scanner myReader = new Scanner(myObj)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (!data.isEmpty()) {
                        String[] doctor = data.split(",");
                        dList.insert(
                                new Doctor(doctor[0], doctor[1], doctor[2], doctor[3], Integer.parseInt(doctor[4])));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Doctors.txt: " + e.getMessage());
        }
    }

    public static void writePFile(PatientList pList) {
        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter("Patients.txt"))) {
            for (int i = 0; i < pList.size(); i++) {
                Patient patient = pList.getAtIndex(i);
                myWriter.write(patient.getId() + "," + patient.getName() + "," + patient.getContact() + "\n");
            }
            System.out.println("Successfully wrote to Patients.txt.");
        } catch (IOException e) {
            System.out.println("Error writing to Patients.txt: " + e.getMessage());
        }
    }

    public static void readPFile(PatientList pList) {
        File myObj = new File("Patients.txt");
        try {
            if (!myObj.exists()) {
                myObj.createNewFile(); // Create file if it doesn’t exist
                System.out.println("Patients.txt file created.");
                return; // Exit since there is nothing to read
            }

            try (Scanner myReader = new Scanner(myObj)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if (!data.isEmpty()) {
                        String[] patient = data.split(",");
                        pList.insert(new Patient(patient[0], patient[1], patient[2]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Patients.txt: " + e.getMessage());
        }
    }

}
