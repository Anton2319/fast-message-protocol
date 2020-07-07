package Node;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class DBConnector implements Closeable {
    private static String url;
    private static String user;
    private static String password;
    private static Connection con;
    public DBConnector(String jdbcURL, String user, String password) {
        url = jdbcURL;
        DBConnector.user = user;
        DBConnector.password = password;
    }
    public ResultSet query(String query) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        query = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            return rs;
        }
        finally {
            try { stmt.close(); } catch(SQLException se) {}
        }
    }
    @Override
    public void close() throws IOException {
        try { con.close(); } catch(SQLException se) {}
    }
}
