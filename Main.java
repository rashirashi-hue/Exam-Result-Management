import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Student {
    private int rollNo;
    private String name;
    private double marks;
    private String grade;

    public Student(int rollNo, String name, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
        this.grade = calculateGrade(marks);
    }

    private String calculateGrade(double marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 35) return "D";
        else return "F";
    }

    public int getRollNo() { return rollNo; }
    public String getName() { return name; }
    public double getMarks() { return marks; }
    public String getGrade() { return grade; }

    @Override
    public String toString() {
        return String.format("Roll No: %-4d | Name: %-15s | Marks: %-6.2f | Grade: %s",
                rollNo, name, marks, grade);
    }
}

public class ExamResultManagementSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample data for quick testing
        students.add(new Student(103, "Aarav Sharma", 88.5));
        students.add(new Student(101, "Priya Verma", 94.0));
        students.add(new Student(104, "Rohan Patel", 72.5));
        students.add(new Student(102, "Neha Gupta", 81.0));

        int choice;
        do {
            System.out.println("\n=== EXAM RESULT MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Student Result");
            System.out.println("2. Display All Results");
            System.out.println("3. Sort by Marks (High to Low)");
            System.out.println("4. Sort by Name (A-Z)");
            System.out.println("5. Sort by Roll No (Ascending)");
            System.out.println("6. Search by Roll No (Linear Search)");
            System.out.println("7. Search by Name (Partial/Case-Insensitive)");
            System.out.println("8. Binary Search by Roll No (Requires Roll No Sort)");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> displayStudents();
                case 3 -> sortByMarksDesc();
                case 4 -> sortByNameAsc();
                case 5 -> sortByRollNoAsc();
                case 6 -> searchByRollNoLinear();
                case 7 -> searchByName();
                case 8 -> searchByRollNoBinary();
                case 0 -> System.out.println("Exiting system. Bye!");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 0);
    }

    // 1. Add Student in ArrayList
    private static void addStudent() {
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine();

        // Duplicate roll no check
        for (Student s : students) {
            if (s.getRollNo() == roll) {
                System.out.println("Error: Roll No " + roll + " already exists!");
                return;
            }
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Marks (0-100): ");
        double marks = sc.nextDouble();

        students.add(new Student(roll, name, marks));
        System.out.println("Student result added successfully!");
    }

    // 2. Display All
    private static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("\n--- Student Results ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // 3. Sorting by Marks (Descending)
    private static void sortByMarksDesc() {
        students.sort(Comparator.comparingDouble(Student::getMarks).reversed());
        System.out.println("Sorted by Marks (High to Low):");
        displayStudents();
    }

    // 4. Sorting by Name (Ascending)
    private static void sortByNameAsc() {
        students.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
        System.out.println("Sorted by Name (A-Z):");
        displayStudents();
    }

    // 5. Sorting by Roll No (Ascending)
    private static void sortByRollNoAsc() {
        students.sort(Comparator.comparingInt(Student::getRollNo));
        System.out.println("Sorted by Roll No (Ascending):");
        displayStudents();
    }

    // 6. Linear Search by Roll No
    private static void searchByRollNoLinear() {
        System.out.print("Enter Roll No to search: ");
        int targetRoll = sc.nextInt();

        for (Student s : students) {
            if (s.getRollNo() == targetRoll) {
                System.out.println("Found: " + s);
                return;
            }
        }
        System.out.println("Student with Roll No " + targetRoll + " not found.");
    }

    // 7. Search by Name
    private static void searchByName() {
        System.out.print("Enter Name to search: ");
        String query = sc.nextLine().toLowerCase();
        boolean found = false;

        for (Student s : students) {
            if (s.getName().toLowerCase().contains(query)) {
                System.out.println("Found: " + s);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching student found for \"" + query + "\".");
        }
    }

    // 8. Binary Search by Roll No (List must be sorted by Roll No first)
    private static void searchByRollNoBinary() {
        sortByRollNoAsc(); // Ensure list is sorted by rollNo
        System.out.print("Enter Roll No for Binary Search: ");
        int targetRoll = sc.nextInt();

        int left = 0;
        int right = students.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midRoll = students.get(mid).getRollNo();

            if (midRoll == targetRoll) {
                System.out.println("Found via Binary Search: " + students.get(mid));
                return;
            } else if (midRoll < targetRoll) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println("Student with Roll No " + targetRoll + " not found.");
    }
}