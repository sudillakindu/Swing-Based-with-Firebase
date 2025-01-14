package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateAndShowGUI {

    private JFrame frame;
    private JTextField txtStudentId;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtAge;
    private JLabel lblStatus;

    public CreateAndShowGUI() {
        // Create the main frame
        frame = new JFrame("Firestore Update Document");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(8, 2, 10, 10));

        // Add components to the frame
        JLabel lblStudentId = new JLabel("Student ID:");
        txtStudentId = new JTextField();

        JLabel lblFirstName = new JLabel("First Name:");
        txtFirstName = new JTextField();

        JLabel lblLastName = new JLabel("Last Name:");
        txtLastName = new JTextField();

        JLabel lblAge = new JLabel("Age:");
        txtAge = new JTextField();

        JButton btnAdd = new JButton("Add Student");
        JButton btnUpdate = new JButton("Update Document");
        JButton btnDelete = new JButton("Delete Document");
        JButton btnSearch = new JButton("Search Document");
        JButton btnDisplay = new JButton("Display All Document");
        JButton btnChart = new JButton("View Chart");

        lblStatus = new JLabel("Status: ");
        lblStatus.setForeground(Color.BLUE);

        frame.add(lblStudentId);
        frame.add(txtStudentId);
        frame.add(lblFirstName);
        frame.add(txtFirstName);
        frame.add(lblLastName);
        frame.add(txtLastName);
        frame.add(lblAge);
        frame.add(txtAge);

        frame.add(lblStatus);
        frame.add(new JLabel("")); // Placeholder

        frame.add(btnAdd);
        frame.add(btnUpdate);
        frame.add(btnDelete);
        frame.add(btnSearch);
        frame.add(btnDisplay);
        frame.add(btnChart);

        // Set the frame visibility
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        // Action listener for the "Add Student" button
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (!checkEmptyValidation() || !checkStudentIDValidation() || !checkFirstNameAndLastNameValidation() || !checkAgeValidation()) {
//                    return; // Exit if any validation fails
//                }

                String studentId = txtStudentId.getText();
                String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String ageStr = txtAge.getText();

                int age;
                try {
                    age = Integer.parseInt(ageStr);
                    // Attempt to update Firestore
                    try {
                        FirestoreAddDataExample firestoreAddDataExample = new FirestoreAddDataExample(studentId, firstName, lastName, age);
                        clearData();
                    } catch (com.google.api.gax.rpc.AlreadyExistsException ex) {
                        JOptionPane.showMessageDialog(frame, "Document with the same student ID already exists: " + studentId, "Error", JOptionPane.WARNING_MESSAGE);
                    } catch (com.google.api.gax.rpc.PermissionDeniedException ex) {
                        JOptionPane.showMessageDialog(frame, "Permission denied. Check your Firebase rules.", "Error", JOptionPane.WARNING_MESSAGE);
                    } catch (com.google.api.gax.rpc.NotFoundException ex) {
                        JOptionPane.showMessageDialog(frame, "Document not found for the update.", "Error", JOptionPane.WARNING_MESSAGE);
                    } catch (IllegalStateException ex) {
                        JOptionPane.showMessageDialog(frame, "FirebaseApp name [DEFAULT] already exists!", "Error", JOptionPane.WARNING_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "An unexpected error occurred.", "Error", JOptionPane.WARNING_MESSAGE);
                        ex.printStackTrace();
                    }
                } catch (NumberFormatException ex) {
                    lblStatus.setText("Status: Age must be a valid number.");
                }
            }
        });

        // Action listener for the "Update Document" button
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (!checkEmptyValidation() || !checkStudentIDValidation() || !checkFirstNameAndLastNameValidation() || !checkAgeValidation()) {
//                    return; // Exit if any validation fails
//                }

                String studentId = txtStudentId.getText();
                String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String ageStr = txtAge.getText();

                int age;
                try {
                    age = Integer.parseInt(ageStr);
                    // Attempt to update Firestore
                    try {
                        FirestoreUpdateDocumentExample firestoreUpdateDocumentExample = new FirestoreUpdateDocumentExample(studentId, firstName, lastName, age);
                        clearData();
                    } catch (com.google.api.gax.rpc.AlreadyExistsException ex) {
                        JOptionPane.showMessageDialog(frame, "Document with the same student ID already exists: " + studentId, "Error", JOptionPane.WARNING_MESSAGE);
                    } catch (com.google.api.gax.rpc.PermissionDeniedException ex) {
                        JOptionPane.showMessageDialog(frame, "Permission denied. Check your Firebase rules.", "Error", JOptionPane.WARNING_MESSAGE);
                    } catch (com.google.api.gax.rpc.NotFoundException ex) {
                        JOptionPane.showMessageDialog(frame, "Document not found for the update.", "Error", JOptionPane.WARNING_MESSAGE);
                    } catch (IllegalStateException ex) {
                        JOptionPane.showMessageDialog(frame, "FirebaseApp name [DEFAULT] already exists!", "Error", JOptionPane.WARNING_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "An unexpected error occurred.", "Error", JOptionPane.WARNING_MESSAGE);
                        ex.printStackTrace();
                    }
                } catch (NumberFormatException ex) {
                    lblStatus.setText("Status: Age must be a valid number.");
                }
            }
        });

        // Action listener for the "Delete Document" button
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (!checkStudentIDValidation()) {
//                    return; // Exit if any validation fails
//                }

                String studentId = txtStudentId.getText();

                // Attempt to delete Firestore
                try {
                    FirestoreDeleteDocumentExample firestoreDeleteDocumentExample = new FirestoreDeleteDocumentExample(studentId);
                    clearData();
                } catch (com.google.api.gax.rpc.AlreadyExistsException ex) {
                    JOptionPane.showMessageDialog(frame, "Document with the same student ID already exists: " + studentId, "Error", JOptionPane.WARNING_MESSAGE);
                } catch (com.google.api.gax.rpc.PermissionDeniedException ex) {
                    JOptionPane.showMessageDialog(frame, "Permission denied. Check your Firebase rules.", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (com.google.api.gax.rpc.NotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "Document not found for the delete.", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(frame, "FirebaseApp name [DEFAULT] already exists!", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An unexpected error occurred.", "Error", JOptionPane.WARNING_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Action listener for the "Search Document" button
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (!checkStudentIDValidation()) {
//                    return; // Exit if any validation fails
//                }

                String studentId = txtStudentId.getText();

                // Attempt to search Firestore
                try {
                    FirestoreSearchDataExample firestoreSearchDataExample = new FirestoreSearchDataExample(studentId);
                    clearData();
                } catch (com.google.api.gax.rpc.AlreadyExistsException ex) {
                    JOptionPane.showMessageDialog(frame, "Document with the same student ID already exists: " + studentId, "Error", JOptionPane.WARNING_MESSAGE);
                } catch (com.google.api.gax.rpc.PermissionDeniedException ex) {
                    JOptionPane.showMessageDialog(frame, "Permission denied. Check your Firebase rules.", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (com.google.api.gax.rpc.NotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "Document not found for the search.", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(frame, "FirebaseApp name [DEFAULT] already exists!", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An unexpected error occurred.", "Error", JOptionPane.WARNING_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Action listener for the "Display All Document" button
        btnDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Attempt to display all Firestore
                try {
                    FirestoreDisplayDataExample firestoreDisplayDataExample = new FirestoreDisplayDataExample();
                    clearData();
                } catch (com.google.api.gax.rpc.AlreadyExistsException ex) {
                    JOptionPane.showMessageDialog(frame, "Document with the same student ID already exists.", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (com.google.api.gax.rpc.PermissionDeniedException ex) {
                    JOptionPane.showMessageDialog(frame, "Permission denied. Check your Firebase rules.", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (com.google.api.gax.rpc.NotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "Document not found for the display all.", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(frame, "FirebaseApp name [DEFAULT] already exists!", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An unexpected error occurred.", "Error", JOptionPane.WARNING_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Action listener for the "View Chart" button
        btnChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Attempt to display all Firestore data in charts
                try {
                    ChartView chartView = new ChartView();
                } catch (com.google.api.gax.rpc.AlreadyExistsException ex) {
                    JOptionPane.showMessageDialog(frame, "A chart or document with the same name already exists. Please verify.", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (com.google.api.gax.rpc.PermissionDeniedException ex) {
                    JOptionPane.showMessageDialog(frame, "Permission denied. Ensure your Firebase security rules are correctly set up.", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (com.google.api.gax.rpc.NotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "No data found in the Firestore database for display. Verify collection data.", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(frame, "The FirebaseApp instance named [DEFAULT] already exists. Restarting the application may resolve this.", "Error", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });


    }

    public boolean checkEmptyValidation() {
        String studentId = txtStudentId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String ageStr = txtAge.getText();

        // Validate input fields are not empty
        if (studentId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || ageStr.isEmpty()) {
            lblStatus.setText("Status: All fields are required.");
            return false;
        }

        lblStatus.setText("Status: ");
        return false;
    }

    public boolean checkStudentIDValidation() {
        String studentId = txtStudentId.getText();

        // Validate student ID format (example: allow only alphanumeric values)
        if (!studentId.matches("^[a-zA-Z0-9]+$")) {
            lblStatus.setText("Status: Student ID must be alphanumeric.");
            return false;
        }

        lblStatus.setText("Status: ");
        return false;
    }

    public boolean checkFirstNameAndLastNameValidation() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();

        // Validate first and last names (example: only letters allowed)
        if (!firstName.matches("^[a-zA-Z ]+$") || !lastName.matches("^[a-zA-Z ]+$")) {
            lblStatus.setText("Status: Names can only contain letters and spaces.");
            return false;
        }

        lblStatus.setText("Status: ");
        return false;
    }

    public boolean checkAgeValidation() {
        String ageStr = txtAge.getText();

        // Validate age is a valid integer and within range
        try {
            int age = Integer.parseInt(ageStr);
            if (age <= 0 || age > 120) { // Age range check
                lblStatus.setText("Status: Age must be between 1 and 120.");
                return false;
            }
        } catch (NumberFormatException ex) {
            lblStatus.setText("Status: Age must be a valid number.");
        }

        lblStatus.setText("Status: ");
        return false;
    }

    public void clearData() {
        txtStudentId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAge.setText("");
    }

}
