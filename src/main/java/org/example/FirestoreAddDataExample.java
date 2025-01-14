package org.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;

public class FirestoreAddDataExample {

    public FirestoreAddDataExample(String studentId, String firstName, String lastName, int age) {
        try {
            // Check if FirebaseApp is already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                // Initialize Firestore with service account key
                FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }

            Firestore db = FirestoreClient.getFirestore();

            // Check if the "students" collection exists
            if (isCollectionAvailable(db, "students")) {
                //System.out.println("Collection 'students' exists.");
            } else {
                System.out.println("Collection 'students' does not exist. Creating a new one.");
                JOptionPane.showMessageDialog(null, "Collection 'students' does not exist. Creating a new one.", "Error", JOptionPane.WARNING_MESSAGE);
            }

            // Reference the "students" collection
            CollectionReference studentsRef = db.collection("students");

            // Check if the studentId already exists in the Firestore collection
            DocumentReference studentDocRef = studentsRef.document(studentId);
            DocumentSnapshot snapshot = studentDocRef.get().get();

            // If the document already exists, show an error
            if (snapshot.exists()) {
                System.out.println("Duplicate studentId found.");
                JOptionPane.showMessageDialog(null, "Duplicate studentId found.", "Duplicate Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method to prevent adding the duplicate
            }

            // Instead of using `add`, use `document(studentId)` to set the `studentId` as the document ID
            Student student = new Student(studentId, firstName, lastName, age);
            studentsRef.document(studentId).set(student).get();

            System.out.println("Data added successfully!");
            JOptionPane.showMessageDialog(null, "Document added successfully : " + studentId, "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException | InterruptedException | java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
        }
    }

    // Method to check if a collection exists
    private boolean isCollectionAvailable(Firestore db, String collectionName) {
        try {
            // Iterate over the collections using the Iterable returned by listCollections
            for (CollectionReference collection : db.listCollections()) {
                if (collection.getId().equalsIgnoreCase(collectionName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error checking collections: " + e.getMessage());
        }
        return false;
    }
}
