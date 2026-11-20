package AccesoDatos.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToPSQL {
    // 1. Instancia estática única (Patrón Singleton)
    private static ConnectionToPSQL instancia;
    private Connection conexion;

    // 2. Parámetros de conexión para PostgreSQL
    private final String url = "jdbc:postgresql://localhost:5432/bdpractica?escapeSyntaxCallMode=callIfNoReturn";
    private final String usuario = "ngynx";   // Cambia por tu usuario de PostgreSQL
    private final String password = "ngynx"; // Cambia por tu contraseña

    // 3. Constructor privado para evitar que creen objetos con 'new'
    private ConnectionToPSQL() throws SQLException {
        try {
            // Establecemos la conexión utilizando el driver de PostgreSQL
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar a la base de datos PostgreSQL: " + e.getMessage());
        }
    };

    // 4. Método público global para obtener la única instancia de la clase
    public static ConnectionToPSQL getInstancia() throws SQLException {
        if (instancia == null || instancia.getConexion().isClosed()) {
            instancia = new ConnectionToPSQL();
        }
        return instancia;
    };

    // 5. Método para obtener el objeto Connection de Java SQL
    public Connection getConexion() {
        return conexion;
    };

    // uso
    // Connection conn = ConnectionToPSQL.getInstancia().getConexion();
}
