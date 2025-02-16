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
        Hospital_Management_System.readCFile(cList, dList, pList); // Load Checkups from file
        Hospital_Management_System.readPrescriptionFile(cList, dList, pList); // Load Prescriptions from file

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

        // Checkup Queue Panel (new tab)
        JPanel checkupQueuePanel = createCheckupQueueTab(); // Checkup Queue form and table
        tabbedPane.addTab("Checkup Queue", checkupQueuePanel);

        // Prescriptions Panel (new tab)
        JPanel prescriptionsPanel = createPrescriptionsTab(); // Prescriptions form and table
        tabbedPane.addTab("Prescriptions", prescriptionsPanel);

        // Add tabbed pane to frame
        add(tabbedPane);

        updateDoctorTable();
        updatePatientTable();
        updateCheckupQueueTable(new DefaultTableModel()); // Initialize Checkup Queue table
        updatePrescriptionTable(new DefaultTableModel()); // Initialize Prescription table
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

        btnAddDoctor.addActionListener(e -> {
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

                txtId.setText("");
                txtName.setText("");
                txtContact.setText("");
                txtSpeciality.setText("");
                txtFee.setText("");

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

        btnAddPatient.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String name = txtName.getText();
                String contact = txtContact.getText();

                Patient patient = new Patient(id, name, contact);
                pList.insert(patient);
                Hospital_Management_System.writePFile(pList);
                updatePatientTable();

                txtId.setText("");
                txtName.setText("");
                txtContact.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid ID.");
            }
        });

        return panel;
    }

    private JPanel createCheckupQueueTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a form to add patients to the queue
        JPanel formPanel = new JPanel(new GridLayout(4, 2));

        JLabel lblDoctorId = new JLabel("Doctor ID:");
        JTextField txtDoctorId = new JTextField();
        JLabel lblPatientId = new JLabel("Patient ID:");
        JTextField txtPatientId = new JTextField();
        JLabel lblPriority = new JLabel("Priority (1 - Followup, 2 - Normal, 3 - Emergency):");
        JTextField txtPriority = new JTextField();

        JButton btnAddToQueue = new JButton("Add to Queue");

        formPanel.add(lblDoctorId);
        formPanel.add(txtDoctorId);
        formPanel.add(lblPatientId);
        formPanel.add(txtPatientId);
        formPanel.add(lblPriority);
        formPanel.add(txtPriority);
        formPanel.add(btnAddToQueue);

        panel.add(formPanel, BorderLayout.NORTH);

        // Create a table to display the queue
        String[] columnNames = { "Doctor", "Patient", "Priority" };
        DefaultTableModel queueTableModel = new DefaultTableModel(columnNames, 0);
        JTable queueTable = new JTable(queueTableModel);
        JScrollPane scrollPane = new JScrollPane(queueTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        btnAddToQueue.addActionListener(e -> {
            try {
                int doctorId = Integer.parseInt(txtDoctorId.getText());
                int patientId = Integer.parseInt(txtPatientId.getText());
                int priority = Integer.parseInt(txtPriority.getText());

                Doctor doctor = dList.searchByID(doctorId);
                Patient patient = pList.searchByID(patientId);

                if (doctor != null && patient != null) {
                    Checkup checkup = new Checkup(doctor, patient, priority, "", "");
                    cList.enqueue(checkup);
                    updateCheckupQueueTable(queueTableModel);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Doctor or Patient ID.");
                }

                // Clear fields
                txtDoctorId.setText("");
                txtPatientId.setText("");
                txtPriority.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid numbers.");
            }
        });

        return panel;
    }

    private JPanel createPrescriptionsTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a form to enter prescription details
        JPanel formPanel = new JPanel(new GridLayout(3, 2));

        JLabel lblPatientId = new JLabel("Patient ID:");
        JTextField txtPatientId = new JTextField();
        JLabel lblPrescription = new JLabel("Prescription:");
        JTextField txtPrescription = new JTextField();

        JButton btnSavePrescription = new JButton("Save Prescription");

        formPanel.add(lblPatientId);
        formPanel.add(txtPatientId);
        formPanel.add(lblPrescription);
        formPanel.add(txtPrescription);
        formPanel.add(btnSavePrescription);

        panel.add(formPanel, BorderLayout.NORTH);

        // Create a table to show patients who have completed their checkups
        String[] columnNames = { "Doctor", "Patient", "Priority", "Prescription" };
        DefaultTableModel prescriptionTableModel = new DefaultTableModel(columnNames, 0);
        JTable prescriptionTable = new JTable(prescriptionTableModel);
        JScrollPane scrollPane = new JScrollPane(prescriptionTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        btnSavePrescription.addActionListener(e -> {
            try {
                int patientId = Integer.parseInt(txtPatientId.getText());
                String prescription = txtPrescription.getText();

                // Search for the patient in the queue
                Patient patient = pList.searchByID(patientId);
                if (patient != null) {
                    // Update the prescription details for this patient in the queue
                    for (int i = 0; i < cList.size(); i++) {
                        Checkup checkup = cList.getAtIndex(i);
                        if (checkup.getPatient().getId() == patientId) {
                            checkup.setRecommandation(prescription); // Add prescription
                            updatePrescriptionTable(prescriptionTableModel);
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Patient ID.");
                }

                // Clear fields
                txtPatientId.setText("");
                txtPrescription.setText("");
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

    private void updateCheckupQueueTable(DefaultTableModel queueTableModel) {
        queueTableModel.setRowCount(0); // Clear the table before updating

        CNode temp = cList.head;
        while (temp != null) {
            Checkup checkup = temp.checkup;
            queueTableModel.addRow(new Object[] {
                    checkup.getDoctor().getName(),
                    checkup.getPatient().getName(),
                    checkup.getPriority()
            });
            temp = temp.previous;
        }
    }

    private void updatePrescriptionTable(DefaultTableModel prescriptionTableModel) {
        prescriptionTableModel.setRowCount(0); // Clear the table before updating

        CNode temp = cList.head;
        while (temp != null) {
            Checkup checkup = temp.checkup;
            if (checkup.getRecommandation().isEmpty()) {
                continue; // Skip patients without prescription
            }
            prescriptionTableModel.addRow(new Object[] {
                    checkup.getDoctor().getName(),
                    checkup.getPatient().getName(),
                    checkup.getPriority(),
                    checkup.getRecommandation()
            });
            temp = temp.previous;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HMS_GUI().setVisible(true));
    }
}
