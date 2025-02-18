# Hospital Management System

## Overview
The Hospital Management System is designed to manage hospital operations such as patient data, doctor details, prescriptions, and checkups efficiently. It aims to simplify the process of managing patient information, keeping track of doctors and appointments, and generating prescriptions.

## Features
- **Patient Management**: Add, update, delete, and view patient details.
- **Doctor Management**: Manage doctor information including specialties and availability.
- **Checkups**: Record and view checkup details for patients.
- **Data Storage**: All data is stored in text files for persistence.

## Technologies Used
- **Java**: Core language used for application development.
- **Text Files**: Simple storage for patient, doctor, checkup, and prescription data.
- **Maven**: Build automation tool used for project management.

## Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/masood2004/Hospital_Management_System.git
   ```

2. **Install Maven** (if not already installed)
   Follow the instructions on the official [Maven Installation Guide](https://maven.apache.org/install.html).

3. **Build the Project**
   Navigate to the project directory and run:
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   After the project is built successfully, you can run the application from the terminal:
   ```bash
   mvn exec:java
   ```

## Usage
1. Add patient details.
2. Manage doctor profiles.
3. Record checkup details for patients.
4. Generate prescriptions for patients based on their checkups.
5. All data is saved in respective text files (`Doctors.txt`, `Patients.txt`, `Checkups.txt`, `Prescriptions.txt`).

## File Structure
```
/Hospital_Management_System
    /src
        /main
            /java
                /com
                    /mycompany
                        /hospital_management_system
                            - Main.java
                            - Patient.java
                            - Doctor.java
                            - Checkup.java
                            - Prescription.java
    /target
    /data
        - Doctors.txt
        - Patients.txt
        - Checkups.txt
        - Prescriptions.txt
    - pom.xml
    - LICENSE
    - README.md
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
