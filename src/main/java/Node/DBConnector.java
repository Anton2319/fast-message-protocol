package Node;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DBConnector {
    private String url;
    private String user;
    private String password;

    public DBConnector(String jdbcURL, String user, String password) {
        url = jdbcURL;
        this.user = user;
        this.password = password;
    }

    public ArrayList<Map<String, String>> query(String query) throws SQLException {
        ArrayList<Map<String, String>> line = new ArrayList<>();
        // Death ladder
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            try(Statement stmt = con.createStatement()) {
                try(ResultSet rs = stmt.executeQuery(query)) {
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    final int columnCount = resultSetMetaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, String> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; ++i) {
                            String columnName = resultSetMetaData.getColumnName(i);
                            row.put(columnName.toLowerCase(), rs.getString(i));
                        }
                        line.add(row);
                    }
                    return line;
                }
            }
        }
    }
    public void queryUpdate(String query) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            try(Statement stmt = con.createStatement()) {
                try {
                    stmt.executeUpdate(query);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    stmt.close();
                }
            }
        }
    }

    public static Optional<String> getRowValue(ArrayList<Map<String, String>> line, String fieldName) {
        return line.stream()
                .map(d -> d.get(fieldName))
                .findFirst();
    }

}
