package org.example;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FirestoreSearchDataExample {

    public FirestoreSearchDataExample(String studentId) {
        try {
            // Initialize Firebase only if it's not already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://text-399f0.firebaseio.com")
                        .build();

                FirebaseApp.initializeApp(options);
            }

            // Get Firestore instance
            Firestore db = FirestoreClient.getFirestore();

            // Search for a student by Student Id
            searchStudentById(db, studentId);

        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void searchStudentById(Firestore db, String studentId) throws ExecutionException, InterruptedException {
        // Reference the "students" collection
        CollectionReference students = db.collection("students");

        // Create a query to find students with the given studentId
        Query query = students.whereEqualTo("studentId", studentId);

        // Execute the query
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        // Process the results
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        if (documents.isEmpty()) {
            System.out.println("No student found with studentId : " + studentId);
            JOptionPane.showMessageDialog(null, "No student found with studentId : " + studentId, "No Data Found", JOptionPane.ERROR_MESSAGE);
        } else {
            for (QueryDocumentSnapshot document : documents) {
                // Convert document to Student object
                Student student = document.toObject(Student.class);

                // Print student details
                System.out.println("Student ID: " + student.getStudentId());
                System.out.println("First Name: " + student.getFirstName());
                System.out.println("Last Name: " + student.getLastName());
                System.out.println("Age: " + student.getAge());

                // Display the student information in a dialog
                JOptionPane.showMessageDialog(null,
                        "Student ID: " + student.getStudentId() + "\n" +
                                "First Name: " + student.getFirstName() + "\n" +
                                "Last Name: " + student.getLastName() + "\n" +
                                "Age: " + student.getAge(),
                        "Student Id : " + studentId,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
