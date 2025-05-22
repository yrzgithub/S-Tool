import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.directory.Attributes;
import java.util.Hashtable;

public class LdapInjectionExample {

    public void searchUser(String username) {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:389");

        try {
            DirContext ctx = new InitialDirContext(env);

            // Vulnerable: Directly embedding user input into LDAP query
            // Line 42
            javax.naming.directory.Attributes attributes = null;
                    try (javax.naming.directory.DirContext ctx = new javax.naming.directory.InitialDirContext(env)) {
                        javax.naming.directory.SearchControls controls = new javax.naming.directory.SearchControls();
                        controls.setSearchScope(javax.naming.directory.SearchControls.SUBTREE_SCOPE);
                        String[] attrIDs = { "uid" };
                        controls.setReturningAttributes(attrIDs);
                        String base = "dc=example,dc=com"; // Replace with your LDAP base DN
                        String filter = "(objectClass=user)";
                        NamingEnumeration<SearchResult> results = ctx.search(base, filter, controls);
                        while (results.hasMore()) {
                            SearchResult result = results.next();
                            attributes = result.getAttributes();
                            if(attributes.get("uid").get().equals(username)){
                                break;
                            }
                        }
                    } catch (NamingException e) {
                        throw new RuntimeException("LDAP search failed", e);
                    } finally{
                        //No cleanup needed for this approach
                    }

            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration<SearchResult> results = ctx.search("dc=example,dc=com", searchFilter, searchControls);

            while (results.hasMore()) {
                SearchResult searchResult = results.next();
                Attributes attrs = searchResult.getAttributes();
                System.out.println("User: " + attrs.get("cn"));
            }

            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LdapInjectionExample example = new LdapInjectionExample();
        example.searchUser("(cn=*"); // Potentially malicious input
    }
}