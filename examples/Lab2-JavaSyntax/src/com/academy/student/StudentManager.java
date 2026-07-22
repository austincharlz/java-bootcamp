package com.academy.student;

import java.util.Scanner;

public class StudentManager {

    private static final int MAX_STUDENTS = 20;

    private final Student[] students = new Student[MAX_STUDENTS];
    private int studentCount = 0;
    private final Scanner scanner;

    public StudentManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayMenu() {
        System.out.println("====================================");
        System.out.println("Student Management System");
        System.out.println("====================================");
        System.out.println("1. Add Student");
        System.out.println("2. Display Students");
        System.out.println("3. Search Student");
        System.out.println("4. Average Marks");
        System.out.println("5. Exit");
        System.out.print("Enter Choice : ");
    }

    // addStudent() Method
    public void addStudent() {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Student list is full!");
            return;
        }

        int id = readPositiveInt("Student ID: ");

        if (findStudentIndex(id) != -1) {
            System.out.println("Student ID already exists!");
            return;
        }

        String name = readNonEmptyLine("Name: ");
        String course = readNonEmptyLine("Course: ");
        double marks = readValidMarks();

        students[studentCount] = new Student(id, name, course, marks);
        studentCount++;

        System.out.println("Student Added Successfully.");
    }

    // Helper Methods Below: readPositiveInt, readNonEmptyLine, readValidMarks, findStudentIndex
    private int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                int value = Integer.parseInt(scanner.nextLine());

                if (value > 0) {
                    return value;
                }

                System.out.println("Please enter a positive integer.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    private double readValidMarks() {
        while (true) {
            System.out.print("Marks: ");

            try {
                double marks = Double.parseDouble(scanner.nextLine());

                if (marks >= 0 && marks <= 100) {
                    return marks;
                }

                System.out.println("Marks must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid marks. Try again.");
            }
        }
    }

    private int findStudentIndex(int id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentId() == id) {
                return i;
            }
        }

        return -1;
    }

    // displayStudents() Method
    public void displayStudents() {
        if (studentCount == 0) {
            System.out.println("No students to display.");
            return;
        }

        System.out.println();
        System.out.println("----------------------------------------------------------");
        System.out.printf("%-8s %-20s %-15s %-8s", "ID", "Name", "Course", "Marks");
        System.out.println();
        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < studentCount; i++) {
            System.out.printf("%-8d %-20s %-15s %-8.2f%n",
                    students[i].getStudentId(),
                    students[i].getName(),
                    students[i].getCourse(),
                    students[i].getMarks());
            System.out.println("----------------------------------------------------------");
        }

        System.out.println();
        return;
    }

    // searchStudent() Method
    public void searchStudent() {
        if (studentCount == 0) {
            System.out.println("No students to search.");
            return;
        }

        System.out.print("Student ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        int index = findStudentIndex(id);                   // Prompt for ID and uses helper function to find index.

        if (index == -1) {
            System.out.println("Student Not Found.");
            return;
        } else {
            System.out.println();
            students[index].display();
        }
        return;
    }

    // calculateAverage() Method
    public void calculateAverage() {
        if (studentCount == 0) {
            System.out.println("No students available.");
        }

        double sum = 0;
        for (int i = 0; i <= studentCount - 1; i++) {
            sum += students[i].getMarks();
        }

        System.out.println();
        System.out.printf("Average Marks : %.2f%n", sum / studentCount);
    }
}