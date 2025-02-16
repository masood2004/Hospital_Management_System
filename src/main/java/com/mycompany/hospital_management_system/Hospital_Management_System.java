/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.hospital_management_system;

import java.io.File;
import java.io.FileNotFoundException;
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
                            sc.close();
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
    }

    public static void MainMenu() {
        System.out.println("\n\n ||     *****   HMS  *****     ||");
        System.out.println("        || Main Menu ||");
        System.out.println("\nEnter 1 for Insert New Doctor");
        System.out.println("Enter 2 for Insert New Patient");
        System.out.println("\nEnter 3 for Display All Doctors");
        System.out.println("Enter 4 for Display All Patients");

        System.out.println("Enter 5 for Checkup Menu");
        System.out.println("Enter 0 for Exit");
    }

    public static void writeDFile(DoctorsList dList) {
        try {
            String data = "";
            for (int i = 0; i < dList.size(); i++) {
                Doctor doctor = dList.getAtIndex(i);
                data += doctor.getId() + "," + doctor.getName() + "," + doctor.getContact() + ","
                        + doctor.getSpeciality() + "," + doctor.getFees() + "\n";
            }
            FileWriter myWriter = new FileWriter("Doctors.txt");
            myWriter.write(data);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void readDFile(DoctorsList dList) {
        try {
            File myObj = new File("Doctors.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.length() > 5) {
                    String[] doctor = data.split(",");
                    dList.insert(new Doctor(doctor[0], doctor[1], doctor[2], doctor[3], Integer.parseInt(doctor[4])));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writePFile(PatientList pList) {
        try {
            String data = "";
            for (int i = 0; i < pList.size(); i++) {
                Patient patient = pList.getAtIndex(i);
                data += patient.getId() + "," + patient.getName() + "," + patient.getContact() + "\n";
            }
            FileWriter myWriter = new FileWriter("Patients.txt");
            myWriter.write(data);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void readPFile(PatientList pList) {
        try {
            File myObj = new File("Patients.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.length() > 5) {
                    String[] patient = data.split(",");
                    pList.insert(new Patient(patient[0], patient[1], patient[2]));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
