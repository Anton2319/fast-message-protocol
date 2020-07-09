package Node;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

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
    public ArrayList<String[]> query(String query) {
        ArrayList<String[]> line = new ArrayList<String[]>();
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            final int columnCount = resultSetMetaData.getColumnCount();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; ++i) {
                    row[i - 1] = rs.getString(i);
                }
                line.add(row);
            }
            return line;
        }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
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
