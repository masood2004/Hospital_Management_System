package com.mycompany.hospital_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HMS_GUI extends JFrame {

    private DoctorsList dList = new DoctorsList();
    private PatientList pList = new PatientList();
    private CheckupList cList = new CheckupList();

    private DefaultTableModel doctorTableModel;
    private DefaultTableModel patientTableModel;
    private DefaultTableModel checkupTableModel;

    public HMS_GUI() {
        setTitle("Hospital Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load data from files
        Hospital_Management_System.readDFile(dList);
        Hospital_Management_System.readPFile(pList);
        Hospital_Management_System.readCFile(cList, dList, pList);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Doctors Panel
        JPanel doctorPanel = new JPanel(new BorderLayout());
        doctorPanel.add(createDoctorForm(), BorderLayout.NORTH);
        doctorPanel.add(createDoctorTable(), BorderLayout.CENTER);
        tabbedPane.addTab("Doctors", doctorPanel);

        // Patients Panel
        JPanel patientPanel = new JPanel(new BorderLayout());
        patientPanel.add(createPatientForm(), BorderLayout.NORTH);
        patientPanel.add(createPatientTable(), BorderLayout.CENTER);
        tabbedPane.addTab("Patients", patientPanel);

        // Checkups Panel
        JPanel checkupPanel = new JPanel(new BorderLayout());
        checkupPanel.add(createCheckupForm(), BorderLayout.NORTH);
        checkupPanel.add(createCheckupTable(), BorderLayout.CENTER);
        tabbedPane.addTab("Checkups", checkupPanel);

        // Add tabbed pane to frame
        add(tabbedPane);

        updateDoctorTable();
        updatePatientTable();
        updateCheckupTable();
    }

    private JPanel createDoctorForm() {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel lblId = new JLabel("Doctor ID:");
        JTextField txtId = new JTextField();
        JLabel lblName = new JLabel("Name:");
        JTextField txtName = new JTextField();
        JLabel lblContact = new JLabel("Contact:");
        JTextField txtContact = new JTextField();
        JLabel lblSpeciality = new JLabel("Specialization:");
        JTextField txtSpeciality = new JTextField();
        JLabel lblFee = new JLabel("Fee:");
        JTextField txtFee = new JTextField();

        JButton btnAddDoctor = new JButton("Add Doctor");

        panel.add(lblId);
        panel.add(txtId);
        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblContact);
        panel.add(txtContact);
        panel.add(lblSpeciality);
        panel.add(txtSpeciality);
        panel.add(lblFee);
        panel.add(txtFee);
        panel.add(btnAddDoctor);

        btnAddDoctor.addActionListener(_ -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                String contact = txtContact.getText();
                String speciality = txtSpeciality.getText();
                int fee = Integer.parseInt(txtFee.getText());

                Doctor doctor = new Doctor(id, name, contact, speciality, fee);
                dList.insert(doctor);
                Hospital_Management_System.writeDFile(dList);
                updateDoctorTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid numbers for ID and Fee.");
            }
        });

        return panel;
    }

    private JPanel createPatientForm() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel lblId = new JLabel("Patient ID:");
        JTextField txtId = new JTextField();
        JLabel lblName = new JLabel("Name:");
        JTextField txtName = new JTextField();
        JLabel lblContact = new JLabel("Contact:");
        JTextField txtContact = new JTextField();

        JButton btnAddPatient = new JButton("Add Patient");

        panel.add(lblId);
        panel.add(txtId);
        panel.add(lblName);
        panel.add(txtName);
        panel.add(lblContact);
        panel.add(txtContact);
        panel.add(btnAddPatient);

        btnAddPatient.addActionListener(_ -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                String contact = txtContact.getText();

                Patient patient = new Patient(id, name, contact);
                pList.insert(patient);
                Hospital_Management_System.writePFile(pList);
                updatePatientTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid ID.");
            }
        });

        return panel;
    }

    private JPanel createCheckupForm() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JLabel lblDoctorId = new JLabel("Doctor ID:");
        JTextField txtDoctorId = new JTextField();
        JLabel lblPatientId = new JLabel("Patient ID:");
        JTextField txtPatientId = new JTextField();
        JLabel lblPriority = new JLabel("Priority (1-Followup, 2-Normal, 3-Emergency):");
        JTextField txtPriority = new JTextField();
        JLabel lblRecommendation = new JLabel("Recommendation:");
        JTextField txtRecommendation = new JTextField();

        JButton btnAddCheckup = new JButton("Add Checkup");

        panel.add(lblDoctorId);
        panel.add(txtDoctorId);
        panel.add(lblPatientId);
        panel.add(txtPatientId);
        panel.add(lblPriority);
        panel.add(txtPriority);
        panel.add(lblRecommendation);
        panel.add(txtRecommendation);
        panel.add(btnAddCheckup);

        btnAddCheckup.addActionListener(_ -> {
            try {
                int doctorId = Integer.parseInt(txtDoctorId.getText());
                int patientId = Integer.parseInt(txtPatientId.getText());
                int priority = Integer.parseInt(txtPriority.getText());
                String recommendation = txtRecommendation.getText();

                Doctor doctor = dList.searchByID(doctorId);
                Patient patient = pList.searchByID(patientId);

                if (doctor == null || patient == null) {
                    JOptionPane.showMessageDialog(null, "Invalid Doctor or Patient ID.");
                    return;
                }

                Checkup checkup = new Checkup(doctor, patient, priority, recommendation,
                        "" + java.util.Calendar.getInstance().getTime());
                cList.enqueue(checkup);
                Hospital_Management_System.writeCFile(cList);
                updateCheckupTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid numbers.");
            }
        });

        return panel;
    }

    private JScrollPane createDoctorTable() {
        String[] columnNames = { "ID", "Name", "Contact", "Specialization", "Fee" };
        doctorTableModel = new DefaultTableModel(columnNames, 0);
        JTable doctorTable = new JTable(doctorTableModel);
        updateDoctorTable();
        return new JScrollPane(doctorTable);
    }

    private JScrollPane createPatientTable() {
        String[] columnNames = { "ID", "Name", "Contact" };
        patientTableModel = new DefaultTableModel(columnNames, 0);
        JTable patientTable = new JTable(patientTableModel);
        updatePatientTable();
        return new JScrollPane(patientTable);
    }

    private JScrollPane createCheckupTable() {
        String[] columnNames = { "Doctor", "Patient", "Priority", "Recommendation", "Date" };
        checkupTableModel = new DefaultTableModel(columnNames, 0);
        JTable checkupTable = new JTable(checkupTableModel);
        return new JScrollPane(checkupTable);
    }

    private void updateDoctorTable() {
        doctorTableModel.setRowCount(0); // Clear table before updating

        for (int i = 0; i < dList.size(); i++) {
            Doctor doctor = dList.getAtIndex(i);
            doctorTableModel.addRow(new Object[] {
                    doctor.getId(), doctor.getName(), doctor.getContact(), doctor.getSpeciality(), doctor.getFees()
            });
        }
    }

    private void updatePatientTable() {
        patientTableModel.setRowCount(0); // Clear table before updating

        for (int i = 0; i < pList.size(); i++) {
            Patient patient = pList.getAtIndex(i);
            patientTableModel.addRow(new Object[] {
                    patient.getId(), patient.getName(), patient.getContact()
            });
        }
    }

    private void updateCheckupTable() {
        checkupTableModel.setRowCount(0); // Clear table before updating

        CNode temp = cList.head;
        while (temp != null) {
            Checkup checkup = temp.checkup;
            checkupTableModel.addRow(new Object[] {
                    checkup.getDoctor().getName(),
                    checkup.getPatient().getName(),
                    checkup.getPriority(),
                    checkup.getRecommendation(),
                    checkup.getDate()
            });
            temp = temp.previous;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HMS_GUI().setVisible(true));
    }
}
