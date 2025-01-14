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
import java.util.concurrent.ExecutionException;

public class FirestoreUpdateDocumentExample {

    public FirestoreUpdateDocumentExample(String studentId, String firstName, String lastName, int age) {
        try {
            // Initialize Firebase only if it's not already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }

            Firestore db = FirestoreClient.getFirestore();

            // Example: Update fields for the document where studentId is "S00234"
            updateDocumentByStudentId(db, studentId, firstName, lastName, age);

        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to update a document based on studentId
    public static void updateDocumentByStudentId(Firestore db, String studentId, String firstName, String lastName, int age) throws ExecutionException, InterruptedException {
        // Query to find the document with the matching studentId
        Query query = db.collection("students").whereEqualTo("studentId", studentId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        // Get the query results
        QuerySnapshot snapshot = querySnapshot.get();

        // Check if any document exists with the matching studentId
        if (!snapshot.isEmpty()) {
            // Get the first document from the results (assuming studentId is unique)
            DocumentSnapshot document = snapshot.getDocuments().get(0);

            // Reference to the document to update
            DocumentReference docRef = db.collection("students").document(document.getId());

            // Update fields of the document (e.g., updating the "lastName" and "age")
            ApiFuture<WriteResult> writeResult = docRef.update(
                    "firstName", firstName,               // Example: updating firstName
                    "lastName", lastName,     // Example: updating lastName
                    "age", age                                       // Example: updating age
            );

            // Output confirmation
            System.out.println("Document with studentId " + studentId + " has been updated.");
            JOptionPane.showMessageDialog(null, "Document with studentId : " + studentId + " has been updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("No document found with studentId : " + studentId);
            JOptionPane.showMessageDialog(null, "No document found with studentId : " + studentId, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
