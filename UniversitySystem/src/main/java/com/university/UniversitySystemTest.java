package com.university;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Headless end-to-end integration verifier testing full CRUD operations
 * (Create, Read, Update, Delete) against the live Neon PostgreSQL database service.
 */
public class UniversitySystemTest {

    public static void main(String[] args) {
        System.out.println("===============================================================================");
        System.out.println(" > Neon PostgreSQL End-to-End CRUD Service Test");
        System.out.println("===============================================================================");

        long startTime = System.currentTimeMillis();
        try {
            System.out.println("1. Loading JDBC configuration...");
            String jdbcUrl = DatabaseUtil.getJdbcUrl();
            System.out.println("   Target URL: " + jdbcUrl);

            System.out.println("2. Establishing SSL connection to Neon...");
            try (Connection conn = DatabaseUtil.getConnection()) {
                System.out.println("   Connection successfully established!");

                System.out.println("3. Initializing schema (programs & students tables)...");
                DatabaseUtil.initializeDatabase();

                // Generate unique test record
                String testReg = "CRUD-" + (System.currentTimeMillis() % 100000);
                String initialFirst = "Don";
                String initialLast = "Gitonga";
                String initialEmail = testReg.toLowerCase() + "@university.ac.ke";

                // --- 1. CREATE ---
                System.out.println("4. [CREATE] Inserting test student record...");
                String insertSql = "INSERT INTO students (registration_number, first_name, last_name, email) " +
                                   "VALUES (?, ?, ?, ?) RETURNING student_id";

                int generatedId = -1;
                try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                    stmt.setString(1, testReg);
                    stmt.setString(2, initialFirst);
                    stmt.setString(3, initialLast);
                    stmt.setString(4, initialEmail);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            generatedId = rs.getInt(1);
                        }
                    }
                }
                System.out.printf("   ✓ Created student ID %d: %s (%s %s)%n", generatedId, testReg, initialFirst, initialLast);

                // --- 2. READ ---
                System.out.println("5. [READ] Verifying student record from Neon...");
                String querySql = "SELECT student_id, registration_number, first_name, last_name, email FROM students WHERE student_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(querySql)) {
                    stmt.setInt(1, generatedId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.printf("   ✓ Read verified: ID %d | Reg: %s | Name: %s %s | Email: %s%n",
                                rs.getInt("student_id"),
                                rs.getString("registration_number"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("email")
                            );
                        } else {
                            throw new RuntimeException("Read failed: Record ID " + generatedId + " not found!");
                        }
                    }
                }

                // --- 3. UPDATE ---
                System.out.println("6. [UPDATE] Modifying student record in Neon...");
                String updatedFirst = "Don-Updated";
                String updatedEmail = "don.updated." + testReg.toLowerCase() + "@university.ac.ke";
                String updateSql = "UPDATE students SET first_name = ?, email = ? WHERE student_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setString(1, updatedFirst);
                    stmt.setString(2, updatedEmail);
                    stmt.setInt(3, generatedId);
                    int updatedRows = stmt.executeUpdate();
                    System.out.printf("   ✓ Updated student ID %d (rows affected: %d)%n", generatedId, updatedRows);
                }

                // Verify the update
                try (PreparedStatement stmt = conn.prepareStatement(querySql)) {
                    stmt.setInt(1, generatedId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next() && rs.getString("first_name").equals(updatedFirst)) {
                            System.out.printf("   ✓ Update verified: Name is now '%s %s' | Email: %s%n",
                                rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"));
                        } else {
                            throw new RuntimeException("Update verification failed!");
                        }
                    }
                }

                // --- 4. DELETE ---
                System.out.println("7. [DELETE] Deleting test student record from Neon...");
                String deleteSql = "DELETE FROM students WHERE student_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                    stmt.setInt(1, generatedId);
                    int deletedRows = stmt.executeUpdate();
                    System.out.printf("   ✓ Deleted student ID %d (rows affected: %d)%n", generatedId, deletedRows);
                }

                // Verify the deletion
                try (PreparedStatement stmt = conn.prepareStatement(querySql)) {
                    stmt.setInt(1, generatedId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("   ✓ Deletion verified: Record no longer exists in Neon PostgreSQL!");
                        } else {
                            throw new RuntimeException("Delete verification failed: Record still exists!");
                        }
                    }
                }

                // Print active directory count
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM students")) {
                    if (rs.next()) {
                        System.out.printf("8. Active student directory size: %d students in Neon PostgreSQL%n", rs.getInt(1));
                    }
                }
            }

            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("✓ Neon PostgreSQL End-to-End CRUD Test PASSED in %.2fs!%n", elapsed / 1000.0);
            System.out.println("===============================================================================");
            System.exit(0);

        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - startTime;
            System.err.println("-------------------------------------------------------------------------------");
            System.err.printf("✗ Neon PostgreSQL CRUD Test FAILED in %.2fs: %s%n", elapsed / 1000.0, e.getMessage());
            e.printStackTrace();
            System.err.println("===============================================================================");
            System.exit(1);
        }
    }
}
