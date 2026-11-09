package com.university;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Headless end-to-end integration verifier for Neon PostgreSQL database service.
 */
public class UniversitySystemTest {

    public static void main(String[] args) {
        System.out.println("===============================================================================");
        System.out.println(" > Neon PostgreSQL End-to-End Service Test");
        System.out.println("===============================================================================");

        long startTime = System.currentTimeMillis();
        try {
            System.out.println("1. Loading JDBC configuration...");
            String jdbcUrl = DatabaseUtil.getJdbcUrl();
            // Mask password if present in display
            String maskedUrl = jdbcUrl.replaceAll(":[^:@/]+@", ":****@");
            System.out.println("   Target URL: " + maskedUrl);

            System.out.println("2. Establishing SSL connection to Neon...");
            try (Connection conn = DatabaseUtil.getConnection()) {
                System.out.println("   Connection successfully established!");

                System.out.println("3. Initializing schema (programs & students tables)...");
                DatabaseUtil.initializeDatabase();

                System.out.println("4. Inserting test record...");
                String testReg = "TEST-" + (System.currentTimeMillis() % 100000);
                String testFirst = "Don";
                String testLast = "Gitonga";
                String testEmail = testReg.toLowerCase() + "@university.edu";

                String insertSql = "INSERT INTO students (registration_number, first_name, last_name, email) " +
                                   "VALUES (?, ?, ?, ?) " +
                                   "ON CONFLICT (registration_number) DO UPDATE SET first_name = EXCLUDED.first_name";

                try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                    stmt.setString(1, testReg);
                    stmt.setString(2, testFirst);
                    stmt.setString(3, testLast);
                    stmt.setString(4, testEmail);
                    int rows = stmt.executeUpdate();
                    System.out.println("   Inserted test student: " + testReg + " (" + testFirst + " " + testLast + "), rows affected: " + rows);
                }

                System.out.println("5. Querying database records...");
                try (Statement queryStmt = conn.createStatement();
                     ResultSet rs = queryStmt.executeQuery("SELECT student_id, registration_number, first_name, last_name, email FROM students ORDER BY student_id DESC LIMIT 5")) {
                    int count = 0;
                    while (rs.next()) {
                        count++;
                        System.out.printf("   [%d] ID: %d | Reg: %s | Name: %s %s | Email: %s%n",
                            count,
                            rs.getInt("student_id"),
                            rs.getString("registration_number"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email")
                        );
                    }
                    System.out.println("   Query retrieved " + count + " active record(s).");
                }
            }

            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("✓ Neon PostgreSQL End-to-End Test PASSED in %.2fs!%n", elapsed / 1000.0);
            System.out.println("===============================================================================");
            System.exit(0);

        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - startTime;
            System.err.println("-------------------------------------------------------------------------------");
            System.err.printf("✗ Neon PostgreSQL Test FAILED in %.2fs: %s%n", elapsed / 1000.0, e.getMessage());
            e.printStackTrace();
            System.err.println("===============================================================================");
            System.exit(1);
        }
    }
}
