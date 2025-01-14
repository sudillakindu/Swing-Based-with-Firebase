package org.example;

public class Student {
    private String firstName;
    private String lastName;
    private String studentId;
    private int age;

    // Default constructor (required for Firebase to deserialize)
    public Student() {}

    // Constructor for creating student objects
    public Student(String studentId, String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.age = age;
    }

    // Getter and Setter for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter for studentId
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

