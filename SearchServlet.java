import java.sql.Statement;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;
 import java.io.PrintWriter;
// Imports potentially needed for new vulnerabilities (though not fully implemented)
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;
 
 public class SearchServlet extends HttpServlet {
      protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
          String query = request.getParameter("query");
          if (query != null) {
              query = org.owasp.encoder.Encode.forHtml(query);
          }
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
         out.println("<html><body><h1>Search Results for: " + query + "</h1></body></html>"); // Line 13: Reflected XSS

         // Vulnerability 2 (New): SQL Injection (Simulated)
         String sqlQuery = "SELECT * FROM products WHERE name = ?";
         PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
         preparedStatement.setString(1, query);
         ResultSet resultSet = preparedStatement.executeQuery();
         
         String filePath = null;
         try {
             String query = query.replaceAll("[^a-zA-Z0-9._-]", "");
         
             Path basePath = Paths.get("/var/www/data").toAbsolutePath().normalize();
             Path requestedPath = Paths.get("/var/www/data", query).toAbsolutePath().normalize();
         
             if (!requestedPath.startsWith(basePath)) {
                 throw new IllegalArgumentException("Invalid path: Path traversal detected.");
             }
         
             filePath = requestedPath.toString();
         
         } catch (IllegalArgumentException e) {
             System.err.println("Error processing file path: " + e.getMessage());
             filePath = null; 
         }
         out.println("<p>Attempting to access file (unsafe): " +  filePath +  "</p>");
         // In a real scenario, code might try to read this file, e.g., using Files.readString(Paths.get(filePath))

         out.println("</body></html>");
     }
  }
