import java.sql.*;
import java.util.Map;

import com.mysql.cj.jdbc.Driver;

public class database {
    public void añadir(String query, Map info) throws SQLException {
        query = query.replace(" ", "_");
        System.out.println(query);



        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.0.14:3306",
                "root",
                "***REMOVED***");
        DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
        Statement stmt = conn.createStatement();
        stmt.execute("USE wallapop");
        System.out.println("Database selected");


        try{
            if (existe(conn, query)) {
                System.out.println("Ya existe");
                stmt.executeUpdate("INSERT INTO " + query + " (titulo, precio, descripcion, fecha, estado, url, imagen) VALUES ('" + info.get("titulo") + "', '" + info.get("precio") + "', '" + info.get("descripcion") + "', '" + info.get("fecha") + "', '" + info.get("estado") + "', '" + info.get("url") + "', '" + info.get("imagen") + "')");

            } else {
                System.out.println("No existe");
                stmt.execute("CREATE TABLE " + query + " (titulo VARCHAR(255), descripcion LONGTEXT, fecha VARCHAR(255), precio VARCHAR(255), estado VARCHAR(255), url VARCHAR(255), imagen VARCHAR(255))");
                stmt.executeUpdate("INSERT INTO " + query + " (titulo, precio, descripcion, fecha, estado, url, imagen) VALUES ('" + info.get("titulo") + "', '" + info.get("precio") + "', '" + info.get("descripcion") + "', '" + info.get("fecha") + "', '" + info.get("estado") + "', '" + info.get("url") + "', '" + info.get("imagen") + "')");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private boolean existe(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});

        return resultSet.next();
    }
}
