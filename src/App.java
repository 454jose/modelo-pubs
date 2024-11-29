import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    static final String DB_URL = "jdbc:mysql://localhost:3306/pubs";
    static final String USER = "root";
    static final String PASS = "jose1";

    public static void main(String[] args) {
        Connection conn = null;

        try {
            // Intentar establecer la conexión
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Conexión exitosa a la base de datos 'pubs'");
        } catch (SQLException e) {

            switch (e.getSQLState()) {
                case "08001": 
                    System.out.println("Error: No se pudo conectar al servidor. Verifica la dirección y el puerto.");
                    break;
                case "28000": 
                    System.out.println("Error: Usuario o contraseña incorrectos. Verifica las credenciales.");
                    break;
                case "42000": // Base de datos no encontrada
                    System.out.println("Error: Base de datos no encontrada. Verifica el nombre de la base de datos.");
                    break;
                default:
                    System.out.println("Error inesperado al conectar a la base de datos: " + e.getMessage());
                    break;
            }
        } finally {
            // Cerrar conexión manualmente si es necesario
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Conexión cerrada correctamente.");
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
}
