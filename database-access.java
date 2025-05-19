import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLInjectionExample {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "root";
        String password = "password";

        // Example input from user
        String userInput = "anything' OR 'x'='x";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            // Vulnerable line - Line 45: SQL Injection vulnerability
            String query = "SELECT * FROM users WHERE username = '" + userInput + "'";

            ResultSet resultSet = statement.executeQuery(query);  // This line executes the query

            while (resultSet.next()) {
                System.out.println("User: " + resultSet.getString("username"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Vulnerability description: SQL Injection
    // This part of code is vulnerable to SQL Injection because it concatenates user input directly into the SQL statement.
    // An attacker can manipulate the SQL query by providing malicious input.

    // Vulnerability fix recommendation:
    // Ensure to validate and sanitize any untrusted data before processing it in your SQL queries.
    // Use parameterized queries or prepared statements in your SQL queries to prevent SQL Injection.
    // For example, use `PreparedStatement` in Java:
    //
    // String query = "SELECT * FROM users WHERE username = ?";
    // PreparedStatement preparedStatement = connection.prepareStatement(query);
    // preparedStatement.setString(1, userInput);
    //
    // ResultSet resultSet = preparedStatement.executeQuery(); // Secure execution
}