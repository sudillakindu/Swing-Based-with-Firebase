package org.example.chart;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;

public class FirestorePieChartRealTimeExample {

    private DefaultPieDataset dataset;
    private JFrame frame;

    public FirestorePieChartRealTimeExample() {
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

            // Set up the real-time listener and display the chart
            setupRealTimeListener(db);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupRealTimeListener(Firestore db) {
        // Initialize dataset
        dataset = new DefaultPieDataset();

        // Create the initial pie chart
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Student Ages Distribution",
                dataset,
                true,
                true,
                false
        );

        // Set up the JFrame to display the chart
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Student Ages Pie Chart (Real-Time)");
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);

        // Add a snapshot listener to the "students" collection
        db.collection("students").addSnapshotListener((snapshots, e) -> {
            if (e != null) {
                e.printStackTrace();
                return;
            }

            if (snapshots != null && !snapshots.isEmpty()) {
                updateDataset(snapshots);
            }
        });
    }

    private void updateDataset(QuerySnapshot snapshots) {
        // Clear the current dataset
        dataset.clear();

        // Iterate through the snapshots and populate the dataset
        for (QueryDocumentSnapshot document : snapshots) {
            String firstName = document.getString("firstName");
            Long age = document.getLong("age");

            if (firstName != null && age != null) {
                dataset.setValue(firstName, age);
            }
        }

        // Refresh the JFrame
        SwingUtilities.invokeLater(() -> frame.repaint());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FirestorePieChartRealTimeExample::new);
    }
}