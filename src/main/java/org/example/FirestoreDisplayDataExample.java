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

public class FirestoreDisplayDataExample {

    public FirestoreDisplayDataExample() {
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

            // Reference the "students" collection
            CollectionReference studentsRef = db.collection("students");

            // Retrieve data from Firestore
            ApiFuture<QuerySnapshot> query = studentsRef.get();
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            // Loop through all student documents
            for (QueryDocumentSnapshot document : documents) {
                Student student = document.toObject(Student.class);

                System.out.println("-------------------");
                System.out.println("Student ID: " + student.getStudentId());
                System.out.println("First Name: " + student.getFirstName());
                System.out.println("Last Name: " + student.getLastName());
                System.out.println("Age: " + student.getAge());
                System.out.println("-------------------");

                JOptionPane.showMessageDialog(null,
                        "-------------------" + "\n" +
                                "Student ID: " + student.getStudentId() + "\n" +
                                "First Name: " + student.getFirstName() + "\n" +
                                "Last Name: " + student.getLastName() + "\n" +
                                "Age: " + student.getAge() + "\n" +
                                "-------------------",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            }

        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
