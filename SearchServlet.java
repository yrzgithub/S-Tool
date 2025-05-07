import java.sql.Statement;
import java.nio.file.Files;
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
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
         out.println("<html><body><h1>Search Results for: " + query + "</h1></body></html>"); // Line 13: Reflected XSS

         // Vulnerability 2 (New): SQL Injection (Simulated)
         String sqlQuery = "SELECT * FROM products WHERE name = '"  + query + "'";
         String filePath = "/var/www/data/" +  query; // Using user input directly in a path
         out.println("<p>Attempting to access file (unsafe): " +  filePath +  "</p>");
         // In a real scenario, code might try to read this file, e.g., using Files.readString(Paths.get(filePath))

         out.println("</body></html>");
     }
  }
