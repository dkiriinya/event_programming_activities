package com.university;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Seeds authentic Kenyan student demographic records into the Neon PostgreSQL database.
 */
public class SeedStudents {

    private static final String[] FIRST_NAMES = {
        // Male given names
        "Brian", "Kevin", "Dennis", "Victor", "Evans", "Ian", "Collins", "Emmanuel",
        "Kelvin", "Peter", "James", "John", "David", "Samuel", "Joseph", "Daniel",
        "Boniface", "Titus", "Geoffrey", "Silas", "Alex", "Dominic", "Felix", "Gideon",
        "Anthony", "Meshack", "Timothy", "Paul", "Stephen", "Vincent", "Edwin", "Kennedy",
        // Female given names
        "Mercy", "Faith", "Brenda", "Sharon", "Cynthia", "Joy", "Stacy", "Grace",
        "Esther", "Mary", "Ruth", "Jackline", "Lilian", "Caroline", "Christine", "Beatrice",
        "Hellen", "Naomi", "Vivian", "Dorcas", "Millicent", "Sheila", "Irene", "Ann",
        "Gloria", "Prudence", "Phyllis", "Diana", "Joan", "Janet", "Florence", "Purity"
    };

    private static final String[] LAST_NAMES = {
        // Central Kenya
        "Mwangi", "Kamau", "Njoroge", "Maina", "Kariuki", "Waweru", "Githinji", "Njenga",
        "Kimani", "Macharia", "Ndung'u", "Wambui", "Wanjiku", "Nyambura", "Muthoni", "Wangari",
        // Western / Nyanza (Luo)
        "Ochieng", "Odhiambo", "Otieno", "Onyango", "Omondi", "Owino", "Okoth", "Ogutu",
        "Awuor", "Atieno", "Akoth", "Achieng", "Anyango", "Adhiambo", "Okeyo", "Ouma",
        // Western (Luhya)
        "Wanyonyi", "Wafula", "Barasa", "Simiyu", "Juma", "Masinde", "Wanjala", "Nekesa",
        "Nasimiyu", "Nafula", "Khisa", "Wekesa",
        // Rift Valley (Kalenjin)
        "Kiprop", "Kipkoech", "Kiptoo", "Cheruiyot", "Rotich", "Korir", "Bett", "Kipchumba",
        "Chebet", "Jelagat", "Chepkemoi", "Jepchirchir", "Cherono", "Kiplagat", "Tanui",
        // Eastern (Kamba)
        "Musyoka", "Mutua", "Kioko", "Mwende", "Mutuku", "Mutiso", "Kaloki", "Mumbua",
        "Kasyoka", "Wayua", "Mutie", "Kyalo",
        // Kisii
        "Mogaka", "Nyachae", "Onyancha", "Makori", "Ombati", "Kerubo", "Kwamboka", "Moraa", "Kemunto",
        // Meru / Embu
        "Kiriinya", "Muriuki", "Murithi", "Mutwiri", "Mwenda", "Nkatha", "Karimi", "Kananu", "Makena", "Kawira",
        // Coastal
        "Mwashumbe", "Mwamburi", "Mzera", "Bakari", "Ali", "Omar", "Mwanaidi", "Rehema", "Fatuma", "Asha"
    };

    public static void main(String[] args) {
        System.out.println("===============================================================================");
        System.out.println(" > Neon PostgreSQL Student Data Seeder (100 Authentic Kenyan Records)");
        System.out.println("===============================================================================");

        long startTime = System.currentTimeMillis();

        try (Connection conn = DatabaseUtil.getConnection()) {
            // Ensure schema exists
            DatabaseUtil.initializeDatabase();

            // Fetch available program IDs
            List<Integer> programIds = new ArrayList<>();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT program_id FROM programs ORDER BY program_id")) {
                while (rs.next()) {
                    programIds.add(rs.getInt(1));
                }
            }

            if (programIds.isEmpty()) {
                System.out.println("Adding default academic programs...");
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate("INSERT INTO programs (program_name, required_units) VALUES ('Bachelor of Computer Science', 56), ('Diploma in IT', 24)");
                }
                programIds.add(1);
                programIds.add(2);
            }

            System.out.printf("Available academic programs: %d%n", programIds.size());

            String sql = "INSERT INTO students (registration_number, first_name, last_name, email, program_id) " +
                         "VALUES (?, ?, ?, ?, ?) " +
                         "ON CONFLICT (registration_number) DO UPDATE SET " +
                         "first_name = EXCLUDED.first_name, " +
                         "last_name = EXCLUDED.last_name, " +
                         "email = EXCLUDED.email, " +
                         "program_id = EXCLUDED.program_id";

            Random rand = new Random(42); // fixed seed for repeatable student roster
            int totalToGenerate = 100;

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 1; i <= totalToGenerate; i++) {
                    String firstName = FIRST_NAMES[rand.nextInt(FIRST_NAMES.length)];
                    String lastName = LAST_NAMES[rand.nextInt(LAST_NAMES.length)];

                    // Authentic Kenyan university registration number formats:
                    // e.g. BCS-0101/2026, DIT-0102/2026, BIT-0103/2026
                    String regPrefix = (i % 2 == 0) ? "BCS" : "DIT";
                    String regNumber = String.format("%s-%04d/2026", regPrefix, 100 + i);

                    // Authentic university email format
                    String cleanFirst = firstName.toLowerCase().replaceAll("[^a-z]", "");
                    String cleanLast = lastName.toLowerCase().replaceAll("[^a-z]", "");
                    String email = String.format("%s.%s%02d@student.university.ac.ke", cleanFirst, cleanLast, (i % 99) + 1);

                    int progId = programIds.get(rand.nextInt(programIds.size()));

                    pstmt.setString(1, regNumber);
                    pstmt.setString(2, firstName);
                    pstmt.setString(3, lastName);
                    pstmt.setString(4, email);
                    pstmt.setInt(5, progId);

                    pstmt.addBatch();
                }

                int[] results = pstmt.executeBatch();
                System.out.printf("Successfully executed batch insert for %d student records!%n", results.length);
            }

            // Verify total count in database
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM students")) {
                if (rs.next()) {
                    System.out.println("Total student records currently in Neon PostgreSQL: " + rs.getInt(1));
                }
            }

            // Print 10 sample preview records
            System.out.println("\n--- Sample Roster Preview (First 10 Records) ---");
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(
                     "SELECT s.registration_number, s.first_name, s.last_name, s.email, p.program_name " +
                     "FROM students s JOIN programs p ON s.program_id = p.program_id " +
                     "ORDER BY s.student_id ASC LIMIT 10")) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.printf(" [%2d] %-14s | %-10s %-12s | %-38s | %s%n",
                        count,
                        rs.getString("registration_number"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("program_name")
                    );
                }
            }

            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("✓ Neon Student Seeding Completed in %.2fs!%n", elapsed / 1000.0);
            System.out.println("===============================================================================");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Seeding failed: " + e.getMessage());
            System.exit(1);
        }
    }
}
