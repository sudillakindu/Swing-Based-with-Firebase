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

public class FirestoreDeleteDocumentExample {

    public FirestoreDeleteDocumentExample(String studentId) {
        try {
            // Initialize Firebase with the service account key, if not already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://text-399f0.firebaseio.com") // Your database URL
                        .build();

                FirebaseApp.initializeApp(options);
            }

            Firestore db = FirestoreClient.getFirestore();

            // Example: Delete the document where studentId is "S00234"
            deleteDocumentByStudentId(db, studentId);

        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a document by studentId (if studentId is a field inside the document)
    public static void deleteDocumentByStudentId(Firestore db, String studentId) throws ExecutionException, InterruptedException {
        // Query for documents where studentId field matches the given value
        Query query = db.collection("students").whereEqualTo("studentId", studentId);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        // Retrieve the result of the query
        QuerySnapshot snapshot = querySnapshot.get();

        // Check if any documents were returned
        if (!snapshot.isEmpty()) {
            // Get the first document in the result
            DocumentSnapshot document = snapshot.getDocuments().get(0);

            // Reference to the document to delete
            DocumentReference docRef = db.collection("students").document(document.getId());

            // Delete the document
            deleteDocument(docRef);
            System.out.println("Document with studentId " + studentId + " has been deleted.");
            JOptionPane.showMessageDialog(null, "Document with studentId " + studentId + " has been deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("No document found with studentId: " + studentId);
            JOptionPane.showMessageDialog(null, "No document found with studentId : " + studentId, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to delete the document
    public static void deleteDocument(DocumentReference docRef) {
        try {
            // Delete the document
            ApiFuture<WriteResult> writeResult = docRef.delete();
            writeResult.get(); // Ensure the operation completes
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error deleting document: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
