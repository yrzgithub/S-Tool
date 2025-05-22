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
         String sanitizedQuery = org.owasp.esapi.ESAPI.encoder().encodeForHTML(query);
         out.println("<html><body><h1>Search Results for: " + sanitizedQuery + "</h1></body></html>");

         // Vulnerability 2 (New): SQL Injection (Simulated)
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM products WHERE name = ?");
         statement.setString(1, query);
         String filePath = "/var/www/data/" +  query; // Using user input directly in a path
         String canonicalPath = Paths.get(basePath, filePath).normalize().toAbsolutePath().toString();
         String safeFilePath = canonicalPath.startsWith(basePath) ? canonicalPath : null;
         if (safeFilePath != null) {
         out.println("<p>Attempting to access file: " + safeFilePath + "</p>");
         // Proceed with file access using safeFilePath
         } else {
         out.println("<p>Invalid file path requested.</p>");
         // Handle invalid path appropriately, e.g., log the event and return an error
         }
         // In a real scenario, code might try to read this file, e.g., using Files.readString(Paths.get(filePath))

         out.println("</body></html>");
     }
  }
