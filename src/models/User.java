package models;

public class User {

    private static User currentUser = null;
    private int id;
    private String username;
    private String password;
    private String userType;
    private String fullName;
    private String email;
    private String department;
    private String studentId;
    private String bloodType;
    private int age;

    public User(int id, String username, String password, String userType, String fullName, String email, String department, String studentId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.studentId = studentId;
        this.bloodType = "";
        this.age = 0;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Static method for authentication
    public static User authenticate(String username, String password) {
        // Default users for testing
        User user = null;
        if (username.equals("admin") && password.equals("admin123")) {
            user = new User(1, "admin", "admin123", "Admin", "Mr. Leulekal Nahusenay", "leulekal.adm@aau.edu.et", "Administration", "ADM001");
        } else if (username.equals("doctor") && password.equals("doctor123")) {
            user = new User(2, "doctor", "doctor123", "Doctor", "Dr. Kena Fayera", "kena.fayera.doc@aau.edu.et", "Doctor", "DOC001");
        } else if (username.equals("patient") && password.equals("patient123")) {
            user = new User(3, "patient", "patient123", "Patient", "Biniyam Aman ", "biniyam@aau.edu.et", "Biomedical", "UGR/5643/16");
        }
        currentUser = user;
        return user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
