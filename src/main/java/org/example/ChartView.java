package org.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class ChartView {

    private DefaultCategoryDataset lineChartDataset;
    private DefaultCategoryDataset barChartDataset;
    private DefaultPieDataset pieChartDataset;
    private JFrame frame;

    public ChartView() {
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

            // Set up datasets
            lineChartDataset = new DefaultCategoryDataset();
            barChartDataset = new DefaultCategoryDataset();
            pieChartDataset = new DefaultPieDataset();

            // Create the frame and add charts
            setupFrame();
            setupRealTimeListeners(db);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupFrame() {
        frame = new JFrame("Chart View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3));
        frame.setSize(1200, 600);

        // Create and add Line Chart
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Student Ages Distribution (Line Chart)",
                "First Name",
                "Age",
                lineChartDataset
        );
        ChartPanel lineChartPanel = new ChartPanel(lineChart);
        frame.add(lineChartPanel);

        // Create and add Bar Chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Student Ages Distribution (Bar Chart)",
                "First Name",
                "Age",
                barChartDataset
        );
        ChartPanel barChartPanel = new ChartPanel(barChart);
        frame.add(barChartPanel);

        // Create and add Pie Chart
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Student Ages Distribution (Pie Chart)",
                pieChartDataset,
                true,
                true,
                false
        );
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        frame.add(pieChartPanel);

        frame.setVisible(true);
    }

    private void setupRealTimeListeners(Firestore db) {
        db.collection("students").addSnapshotListener((snapshots, e) -> {
            if (e != null) {
                e.printStackTrace();
                return;
            }

            if (snapshots != null && !snapshots.isEmpty()) {
                updateDatasets(snapshots);
            }
        });
    }

    private void updateDatasets(QuerySnapshot snapshots) {
        // Clear all datasets
        lineChartDataset.clear();
        barChartDataset.clear();
        pieChartDataset.clear();

        // Populate datasets
        for (QueryDocumentSnapshot document : snapshots) {
            String firstName = document.getString("firstName");
            Long age = document.getLong("age");

            if (firstName != null && age != null) {
                lineChartDataset.addValue(age, "Age", firstName);
                barChartDataset.addValue(age, "Age", firstName);
                pieChartDataset.setValue(firstName, age);
            }
        }

        // Refresh the frame
        SwingUtilities.invokeLater(() -> frame.repaint());
    }

}