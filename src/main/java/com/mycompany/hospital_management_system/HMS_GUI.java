package com.mycompany.hospital_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.*;

public class HMS_GUI extends JFrame {

    private DoctorsList dList = new DoctorsList();
    private PatientList pList = new PatientList();
    private CheckupList cList = new CheckupList();

    private DefaultTableModel doctorTableModel;
    private DefaultTableModel patientTableModel;
    private DefaultTableModel checkupTableModel;

    public HMS_GUI() {
        setTitle("Hospital Management System");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLookAndFeel();

        // Load data from files
        Hospital_Management_System.readDFile(dList);
        Hospital_Management_System.readPFile(pList);
        Hospital_Management_System.readCFile(cList, dList, pList);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(60, 63, 65));
        tabbedPane.setForeground(Color.BLACK);

        // Doctors Panel
        JPanel doctorPanel = new JPanel(new BorderLayout());
        doctorPanel.setBackground(new Color(240, 240, 240));
        doctorPanel.add(createDoctorForm(), BorderLayout.NORTH);
        doctorPanel.add(createDoctorTable(), BorderLayout.CENTER);
        tabbedPane.addTab("Doctors", doctorPanel);

        // Patients Panel
        JPanel patientPanel = new JPanel(new BorderLayout());
        patientPanel.setBackground(new Color(240, 240, 240));
        patientPanel.add(createPatientForm(), BorderLayout.NORTH);
        patientPanel.add(createPatientTable(), BorderLayout.CENTER);
        tabbedPane.addTab("Patients", patientPanel);

        // Checkups Panel
        JPanel checkupPanel = new JPanel(new BorderLayout());
        checkupPanel.setBackground(new Color(240, 240, 240));
        checkupPanel.add(createCheckupForm(), BorderLayout.NORTH);
        checkupPanel.add(createCheckupTable(), BorderLayout.CENTER);
        tabbedPane.addTab("Checkups", checkupPanel);

        // Add tabbed pane to frame
        add(tabbedPane);

        updateDoctorTable();
        updatePatientTable();
        updateCheckupTable();
    }

    private void setLookAndFeel() {
        try {
            // Set look and feel to Nimbus (modern and clean UI)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private JPanel createDoctorForm() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

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
        btnAddDoctor.setBackground(new Color(50, 150, 250));
        btnAddDoctor.setForeground(Color.BLACK);
        btnAddDoctor.setFocusPainted(false);
        btnAddDoctor.setFont(new Font("Arial", Font.BOLD, 14));

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
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblId = new JLabel("Patient ID:");
        JTextField txtId = new JTextField();
        JLabel lblName = new JLabel("Name:");
        JTextField txtName = new JTextField();
        JLabel lblContact = new JLabel("Contact:");
        JTextField txtContact = new JTextField();

        JButton btnAddPatient = new JButton("Add Patient");
        btnAddPatient.setBackground(new Color(50, 150, 250));
        btnAddPatient.setForeground(Color.BLACK);
        btnAddPatient.setFocusPainted(false);
        btnAddPatient.setFont(new Font("Arial", Font.BOLD, 14));

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
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblDoctorId = new JLabel("Doctor ID:");
        JTextField txtDoctorId = new JTextField();
        JLabel lblPatientId = new JLabel("Patient ID:");
        JTextField txtPatientId = new JTextField();
        JLabel lblPriority = new JLabel("Priority (1-Followup, 2-Normal, 3-Emergency):");
        JTextField txtPriority = new JTextField();

        JButton btnAddCheckup = new JButton("Add Checkup");
        btnAddCheckup.setBackground(new Color(50, 150, 250));
        btnAddCheckup.setForeground(Color.BLACK);
        btnAddCheckup.setFocusPainted(false);
        btnAddCheckup.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(lblDoctorId);
        panel.add(txtDoctorId);
        panel.add(lblPatientId);
        panel.add(txtPatientId);
        panel.add(lblPriority);
        panel.add(txtPriority);
        panel.add(btnAddCheckup);

        btnAddCheckup.addActionListener(_ -> {
            try {
                int doctorId = Integer.parseInt(txtDoctorId.getText());
                int patientId = Integer.parseInt(txtPatientId.getText());
                int priority = Integer.parseInt(txtPriority.getText());

                Doctor doctor = dList.searchByID(doctorId);
                Patient patient = pList.searchByID(patientId);

                if (doctor == null || patient == null) {
                    JOptionPane.showMessageDialog(null, "Invalid Doctor or Patient ID.");
                    return;
                }

                Checkup checkup = new Checkup(doctor, patient, priority,
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
        styleTable(doctorTable);
        updateDoctorTable();
        return new JScrollPane(doctorTable);
    }

    private JScrollPane createPatientTable() {
        String[] columnNames = { "ID", "Name", "Contact" };
        patientTableModel = new DefaultTableModel(columnNames, 0);
        JTable patientTable = new JTable(patientTableModel);
        styleTable(patientTable);
        updatePatientTable();
        return new JScrollPane(patientTable);
    }

    private JScrollPane createCheckupTable() {
        String[] columnNames = { "Doctor", "Patient", "Priority", "Date" };
        checkupTableModel = new DefaultTableModel(columnNames, 0);
        JTable checkupTable = new JTable(checkupTableModel);
        styleTable(checkupTable);
        return new JScrollPane(checkupTable);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(102, 178, 255));
        table.setBorder(new EmptyBorder(10, 10, 10, 10));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(Color.GRAY);
        table.setShowGrid(true);
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
                    checkup.getDate()
            });
            temp = temp.previous;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HMS_GUI().setVisible(true));
    }
}
