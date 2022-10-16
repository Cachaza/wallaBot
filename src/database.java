import java.sql.*;
import java.util.Map;

import com.mysql.cj.jdbc.Driver;

public class database {
    public void añadir(String query, Map info) throws SQLException {
        System.out.println(query);
        query = query.replace(" ", "_");

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.0.14:3306",
                "root",
                "***REMOVED***");
        DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
        Statement stmt = conn.createStatement();
        stmt.execute("USE wallapop");
        System.out.println("Database selected");

        try {
            stmt.executeUpdate("INSERT INTO " + query + " (titulo, precio, descripcion, fecha, estado, url, imagen) VALUES ('" + info.get("titulo") + "', '" + info.get("precio") + "', '" + info.get("descripcion") + "', '" + info.get("fecha") + "', '" + info.get("estado") + "', '" + info.get("url") + "', '" + info.get("imagen") + "')");

        } catch (SQLException e) {
            System.out.println("Error Code = " + e.getErrorCode());
            System.out.println("SQL state = " + e.getSQLState());
            System.out.println("Message = " + e.getMessage());
            System.out.println("printTrace /n");

        }




    }
    private boolean existe(Connection conexion, String tableName) throws SQLException {


        DatabaseMetaData meta = conexion.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});

        tableName = tableName.replace(" ", "_");

        return resultSet.next();

    }

    public void crearTabla(String query) throws SQLException {
        query = query.replace(" ", "_");
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.0.14:3306",
                "root",
                "***REMOVED***");
        Statement stmt = conn.createStatement();
        stmt.execute("USE wallapop");

        if (!existe(conn, query)) {
            System.out.println("La tabla no existe");
            stmt.execute("CREATE TABLE " + query + " (titulo VARCHAR(255), descripcion LONGTEXT, fecha VARCHAR(255), precio VARCHAR(255), estado VARCHAR(255), url VARCHAR(255), imagen VARCHAR(255))");
            System.out.println("Tabla creada");
        } else {
            System.out.println("Tabla ya existe");
        }

    }

}
