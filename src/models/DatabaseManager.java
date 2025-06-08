package models;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class DatabaseManager {

    private static boolean initialized = false;
    private static final String DB_URL = "jdbc:sqlite:database.db?busy_timeout=30000&journal_mode=WAL&synchronous=NORMAL";
    private static final int POOL_SIZE = 5;
    private static final BlockingQueue<Connection> connectionPool = new ArrayBlockingQueue<>(POOL_SIZE);
    private static final Object lock = new Object();
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            // Initialize connection pool
            for (int i = 0; i < POOL_SIZE; i++) {
                try {
                    Connection conn = createNewConnection();
                    if (conn != null) {
                        connectionPool.offer(conn);
                    }
                } catch (SQLException e) {
                    System.err.println("Error creating initial connection: " + e.getMessage());
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        }
    }

    private static Connection createNewConnection() throws SQLException {
        try {
            // Check if database file exists
            File dbFile = new File("database.db");
            if (!dbFile.exists()) {
                System.out.println("Database file does not exist, creating new file...");
                dbFile.createNewFile();
            }

            Connection conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);

            // Enable WAL mode and set pragmas
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA journal_mode=WAL");
                stmt.execute("PRAGMA synchronous=NORMAL");
                stmt.execute("PRAGMA busy_timeout=30000");
                stmt.execute("PRAGMA foreign_keys=ON");
            }

            return conn;
        } catch (SQLException | IOException e) {
            System.err.println("Error creating database connection: " + e.getMessage());
            throw new SQLException("Failed to create database connection", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                Connection conn = connectionPool.poll();
                if (conn == null || conn.isClosed()) {
                    conn = createNewConnection();
                }
                return conn;
            } catch (SQLException e) {
                retries++;
                if (retries >= MAX_RETRIES) {
                    throw e;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new SQLException("Connection interrupted", ie);
                }
            }
        }
        throw new SQLException("Failed to get connection after " + MAX_RETRIES + " retries");
    }

    public static void releaseConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    // Reset any pending transactions
                    try {
                        conn.rollback();
                    } catch (SQLException e) {
                        // Ignore rollback errors
                    }
                    connectionPool.offer(conn);
                }
            } catch (SQLException e) {
                System.err.println("Error releasing connection: " + e.getMessage());
            }
        }
    }

    public static void initializeDatabase() {
        if (initialized) {
            System.out.println("Database already initialized");
            return;
        }

        System.out.println("Starting database initialization...");
        try (Connection conn = getConnection()) {
            initializeDatabase(conn);
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database: " + e.getMessage());
        }
    }

    private static void initializeDatabase(Connection conn) throws SQLException {
        if (initialized) {
            System.out.println("Database already initialized");
            return;
        }

        System.out.println("Starting database initialization...");
        try (Statement stmt = conn.createStatement()) {
            System.out.println("Creating tables...");

            // Create Users table
            System.out.println("Creating users table...");
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    user_type TEXT NOT NULL,
                    full_name TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    phone TEXT,
                    department TEXT,
                    student_id TEXT UNIQUE,
                    blood_type TEXT,
                    age INTEGER,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
            System.out.println("Users table created successfully");

            // Create Departments table
            System.out.println("Creating departments table...");
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS departments (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT UNIQUE NOT NULL,
                    description TEXT,
                    head_doctor_id INTEGER,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (head_doctor_id) REFERENCES users(id)
                )
            """);
            System.out.println("Departments table created successfully");

            // Create Appointments table
            System.out.println("Creating appointments table...");
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS appointments (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    patient_id INTEGER NOT NULL,
                    doctor_id INTEGER NOT NULL,
                    appointment_time TIMESTAMP NOT NULL,
                    appointment_type TEXT NOT NULL,
                    status TEXT NOT NULL,
                    notes TEXT,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (patient_id) REFERENCES users(id),
                    FOREIGN KEY (doctor_id) REFERENCES users(id)
                )
            """);
            System.out.println("Appointments table created successfully");

            // Clear existing data in correct order
            System.out.println("Clearing existing data...");
            stmt.execute("DELETE FROM appointments");
            stmt.execute("DELETE FROM departments");
            stmt.execute("DELETE FROM users WHERE user_type = 'Doctor'");
            System.out.println("Existing data cleared");

            // Insert default admin user first
            System.out.println("Inserting default admin user...");
            PreparedStatement pstmt = conn.prepareStatement("INSERT OR IGNORE INTO users (id, username, password, user_type, full_name, email, department, student_id, age, blood_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, 1);
            pstmt.setString(2, "admin");
            pstmt.setString(3, "admin123");
            pstmt.setString(4, "Admin");
            pstmt.setString(5, "Mr.Leulekal Nahusenay");
            pstmt.setString(6, "leulekal@aau.edu.et");
            pstmt.setString(7, "Administration");
            pstmt.setString(8, "ADM001");
            pstmt.setInt(9, 18);
            pstmt.setString(10, "A+");
            pstmt.executeUpdate();
            System.out.println("Admin user inserted successfully");
            pstmt.close();

            // Insert default patient user
            System.out.println("Inserting default patient user...");
            pstmt = conn.prepareStatement("INSERT OR IGNORE INTO users (id, username, password, user_type, full_name, email, department, student_id, age, blood_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, 3);
            pstmt.setString(2, "patient");
            pstmt.setString(3, "patient123");
            pstmt.setString(4, "Patient");
            pstmt.setString(5, "Biniyam Aman");
            pstmt.setString(6, "biniyam@aau.edu.et");
            pstmt.setString(7, "Biomedical");
            pstmt.setString(8, "UGR/5643/16");
            pstmt.setInt(9, 20);
            pstmt.setString(10, "A+");
            pstmt.executeUpdate();
            System.out.println("Patient user inserted successfully");
            pstmt.close();

            // Insert default departments
            System.out.println("Inserting default departments...");

            // General Medicine
            pstmt = conn.prepareStatement("INSERT INTO departments (name, description) VALUES (?, ?)");
            pstmt.setString(1, "General Medicine");
            pstmt.setString(2, "General medical care and consultations");
            pstmt.executeUpdate();
            System.out.println("Inserted General Medicine department");
            pstmt.close();

            // Cardiology
            pstmt = conn.prepareStatement("INSERT INTO departments (name, description) VALUES (?, ?)");
            pstmt.setString(1, "Cardiology");
            pstmt.setString(2, "Heart and cardiovascular care");
            pstmt.executeUpdate();
            System.out.println("Inserted Cardiology department");
            pstmt.close();

            // Orthopedics
            pstmt = conn.prepareStatement("INSERT INTO departments (name, description) VALUES (?, ?)");
            pstmt.setString(1, "Orthopedics");
            pstmt.setString(2, "Bone and joint care");
            pstmt.executeUpdate();
            System.out.println("Inserted Orthopedics department");
            pstmt.close();

            // Neurology
            pstmt = conn.prepareStatement("INSERT INTO departments (name, description) VALUES (?, ?)");
            pstmt.setString(1, "Neurology");
            pstmt.setString(2, "Brain and nervous system care");
            pstmt.executeUpdate();
            System.out.println("Inserted Neurology department");
            pstmt.close();

            // Dentistry
            pstmt = conn.prepareStatement("INSERT INTO departments (name, description) VALUES (?, ?)");
            pstmt.setString(1, "Dentistry");
            pstmt.setString(2, "Dental care and oral health");
            pstmt.executeUpdate();
            System.out.println("Inserted Dentistry department");
            pstmt.close();

            // Insert default doctor users
            System.out.println("Inserting default doctors...");

            // General Medicine Doctor
            pstmt = conn.prepareStatement("INSERT INTO users (id, username, password, user_type, full_name, email, department, student_id, age, blood_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, 2);
            pstmt.setString(2, "doctor");
            pstmt.setString(3, "doctor123");
            pstmt.setString(4, "Doctor");
            pstmt.setString(5, "Dr. Kena Fayera");
            pstmt.setString(6, "kena.doc@aau.edu.et");
            pstmt.setString(7, "General Medicine");
            pstmt.setString(8, "DOC001");
            pstmt.setInt(9, 35);
            pstmt.setString(10, "O+");
            pstmt.executeUpdate();
            System.out.println("General Medicine doctor inserted successfully");
            pstmt.close();

            // Cardiology Doctor
            pstmt = conn.prepareStatement("INSERT INTO users (id, username, password, user_type, full_name, email, department, student_id, age, blood_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, 4);
            pstmt.setString(2, "cardio");
            pstmt.setString(3, "cardio123");
            pstmt.setString(4, "Doctor");
            pstmt.setString(5, "Dr. Semere Hailu");
            pstmt.setString(6, "semere.doc@aau.edu.et");
            pstmt.setString(7, "Cardiology");
            pstmt.setString(8, "DOC002");
            pstmt.setInt(9, 42);
            pstmt.setString(10, "B+");
            pstmt.executeUpdate();
            System.out.println("Cardiology doctor inserted successfully");
            pstmt.close();

            // Orthopedics Doctor
            pstmt = conn.prepareStatement("INSERT INTO users (id, username, password, user_type, full_name, email, department, student_id, age, blood_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, 5);
            pstmt.setString(2, "ortho");
            pstmt.setString(3, "ortho123");
            pstmt.setString(4, "Doctor");
            pstmt.setString(5, "Dr. Yisakor Tamirat");
            pstmt.setString(6, "yisakor.doc@aau.edu.et");
            pstmt.setString(7, "Orthopedics");
            pstmt.setString(8, "DOC003");
            pstmt.setInt(9, 38);
            pstmt.setString(10, "AB+");
            pstmt.executeUpdate();
            System.out.println("Orthopedics doctor inserted successfully");
            pstmt.close();

            // Neurology Doctor
            pstmt = conn.prepareStatement("INSERT INTO users (id, username, password, user_type, full_name, email, department, student_id, age, blood_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, 6);
            pstmt.setString(2, "neuro");
            pstmt.setString(3, "neuro123");
            pstmt.setString(4, "Doctor");
            pstmt.setString(5, "Dr. Sewyishal Netsanet");
            pstmt.setString(6, "sewyishal.doc@aau.edu.et");
            pstmt.setString(7, "Neurology");
            pstmt.setString(8, "DOC004");
            pstmt.setInt(9, 45);
            pstmt.setString(10, "A-");
            pstmt.executeUpdate();
            System.out.println("Neurology doctor inserted successfully");
            pstmt.close();

            // Dentistry Doctor
            pstmt = conn.prepareStatement("INSERT INTO users (id, username, password, user_type, full_name, email, department, student_id, age, blood_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, 7);
            pstmt.setString(2, "dental");
            pstmt.setString(3, "dental123");
            pstmt.setString(4, "Doctor");
            pstmt.setString(5, "Dr. Leulekal Nahusenay");
            pstmt.setString(6, "leulekal.doc@aau.edu.et");
            pstmt.setString(7, "Dentistry");
            pstmt.setString(8, "DOC005");
            pstmt.setInt(9, 40);
            pstmt.setString(10, "O-");
            pstmt.executeUpdate();
            System.out.println("Dentistry doctor inserted successfully");
            pstmt.close();

            System.out.println("Database initialization completed successfully");
            initialized = true;
        }
    }
}
