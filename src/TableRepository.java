import java.sql.*;
import java.util.ArrayList;

public class TableRepository {

    //  conexión a la base de datos
    static final String DB_URL = "jdbc:mysql://localhost:3306/pubs";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "jose1";

    // Método para obtener los nombres de las tablas
    public ArrayList<String> getTables() {
        ArrayList<String> tables = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SHOW TABLES")) {

            while (resultSet.next()) {
                tables.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las tablas: " + e.getMessage());
        }
        return tables;
    }

    // Método para obtener los datos de una tabla específica
    public ArrayList<ArrayList<String>> getTableData(String tableName) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            ArrayList<String> headers = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                headers.add(metaData.getColumnName(i));
            }
            data.add(headers);

            while (resultSet.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los datos de la tabla: " + e.getMessage());
        }
        return data;
    }

    // Método para agregar un campo a una tabla
    public boolean addField(String tableName, String fieldName) {
        String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + fieldName + " VARCHAR(255)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al añadir el campo: " + e.getMessage());
            return false;
        }
    }

    // Método para modificar un valor en una tabla
    public boolean updateValue(String tableName, String key, String fieldName, String newValue) {
        String sql = "UPDATE " + tableName + " SET " + fieldName + " = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newValue);
            preparedStatement.setString(2, key);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error al modificar el valor: " + e.getMessage());
            return false;
        }
    }
 // Método para agregar un registro a la base de datos
 public boolean addRecord(String tableName, String values) {
    // Consulta SQL sin el campo 'id' si es autoincremental
    String sql = "INSERT INTO " + tableName + " (usuario, password, nivel) VALUES (?, ?, ?)";
    
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        
        String[] valueArray = values.split(",");
        
        if (valueArray.length != 5) {
            System.out.println("Número de valores no coincide con las columnas.");
            return false;
        }

        // Establecer los valores en el PreparedStatement
        preparedStatement.setString(1, valueArray[0].trim()); 
        preparedStatement.setString(2, valueArray[1].trim()); 
        preparedStatement.setString(3, valueArray[2].trim()); 
        
        int rowsInserted = preparedStatement.executeUpdate();
        
        return rowsInserted > 0;
        
    } catch (SQLException e) {
        System.out.println("Error al agregar el registro: " + e.getMessage());
        return false;
    }
}

    // Método para eliminar un registro de una tabla
    public boolean deleteRecord(String tableName, String key) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, key);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el registro: " + e.getMessage());
            return false;
        }
    }

   // Método para consultar las ventas por tienda
public int getSalesByStore(String storeName) {
    String sql = """
        SELECT SUM(sa.qty) AS total_ventas
        FROM stores s
        JOIN sales sa ON s.stor_id = sa.stor_id
        WHERE s.stor_name = ?
    """;
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, storeName); 
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("total_ventas"); 
        }
    } catch (SQLException e) {
        System.out.println("Error al consultar las ventas por tienda: " + e.getMessage());
    }
    return 0; 
}
 //metodo cosultar ventas totales y promedio de la ventas
public double[] getTotalAndAverageSales() {
    double[] stats = new double[2];
    // Asegúrate de hacer un JOIN con la tabla titles para obtener el precio de cada producto
    String sql = """
        SELECT 
            SUM(s.qty * t.price) AS total_ventas,
            AVG(s.qty * t.price) AS promedio_ventas
        FROM 
            sales s
        JOIN 
            titles t ON s.title_id = t.title_id
    """;
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql)) {
        if (resultSet.next()) {
            stats[0] = resultSet.getDouble("total_ventas"); 
            stats[1] = resultSet.getDouble("promedio_ventas"); 
        }
    } catch (SQLException e) {
        System.out.println("Error al consultar las ventas totales y promedio: " + e.getMessage());
    }
    return stats;
 }

     // Método para obtener los libros y autores
     public ArrayList<String[]> getBooksAndAuthors() {
        ArrayList<String[]> booksAndAuthors = new ArrayList<>();

        // Consulta SQL que une las tablas 'titles', 'titleauthor' y 'authors'
        String query = """
            SELECT 
                t.title AS Libro, 
                CONCAT(a.au_fname, ' ', a.au_lname) AS Autor
            FROM 
                titles t
            JOIN 
                titleauthor ta ON t.title_id = ta.title_id
            JOIN 
                authors a ON ta.au_id = a.au_id
        """;

        // Establecer la conexión y ejecutar la consulta
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String[] row = new String[2];
                row[0] = resultSet.getString("Libro");  
                row[1] = resultSet.getString("Autor");  
                booksAndAuthors.add(row);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los libros y autores: " + e.getMessage());
        }

        return booksAndAuthors;
    }
}