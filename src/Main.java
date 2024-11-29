import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private final static TableRepository tableRepository = new TableRepository(); // Instancia del repositorio de tablas

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int opcion = 0;

        do {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Ver tablas de la base de datos");
            System.out.println("2. Añadir campo en la tabla");
            System.out.println("3. Modificar un valor en la tabla");
            System.out.println("4. Añadir registro de la tabla");
            System.out.println("5. Eliminar un registro de la tabla");
            System.out.println("6. Consultar ventas por tienda");
            System.out.println("7. Consultar libros y autores"); 
            System.out.println("8. Consultar ventas Totales y promedio ");
            System.out.println("9. Salir");
            System.out.print("Elige una opcion: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1:
                        viewTables(scanner);
                        break;
                    case 2:
                        addFieldToTable(scanner);
                        break;
                    case 3:
                        modifyTableValue(scanner);
                        break;
                    case 4:
                        addTableRecord(scanner);
                         break;
                    case 5:
                        deleteTableRecord(scanner);
                         break;
                    case 6:
                        querySalesByStore(scanner);
                        break;
                    case 7:
                        viewBooksAndAuthors(scanner); 
                        break;
                    case 8:
                        queryTotalAndAverageSales();
                        break;
                    case 9:
                        System.out.println("Saliendo...");
                        break;
                    
                    default:
                        System.out.println("Opción inválida. Por favor, intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Entrada no válida. Por favor, ingresa un número.");
                scanner.nextLine(); 
            }
        } while (opcion != 9);
        
        scanner.close(); 
    }
    
    // Método para ver las tablas en la base de datos
    private static void viewTables(Scanner scanner) {
        ArrayList<String> tables = tableRepository.getTables();
        if (tables.isEmpty()) {
            System.out.println("No se encontraron tablas en la base de datos.");
        } else {
            System.out.println("Tablas disponibles:");
            for (int i = 0; i < tables.size(); i++) {
                System.out.println((i + 1) + ". " + tables.get(i));
            }
            System.out.print("Elige una tabla para ver los datos (0 para regresar): ");
            int tableOption = scanner.nextInt();
            scanner.nextLine(); 

            if (tableOption == 0) {
                System.out.println("Regresando al menú principal...");
            } else if (tableOption > 0 && tableOption <= tables.size()) {
                String selectedTable = tables.get(tableOption - 1);
                ArrayList<ArrayList<String>> tableData = tableRepository.getTableData(selectedTable);
                if (tableData.isEmpty()) {
                    System.out.println("La tabla no contiene datos.");
                } else {
                    ArrayList<String> headers = tableData.get(0);
                    System.out.println(String.join(" | ", headers)); 
                    for (int i = 1; i < tableData.size(); i++) {
                        System.out.println(String.join(" | ", tableData.get(i))); 
                    }
                }
            } else {
                System.out.println("Opción inválida. Por favor, elige un número entre 1 y " + tables.size() + ".");
            }
        }
    }

    // Método para añadir un campo a la tabla
    private static void addFieldToTable(Scanner scanner) {
        System.out.print("Introduce el nombre de la tabla: ");
        String tableName = scanner.nextLine();
        System.out.print("Introduce el nombre del campo: ");
        String fieldName = scanner.nextLine();
        boolean success = tableRepository.addField(tableName, fieldName);
        if (success) {
            System.out.println("Campo añadido exitosamente.");
        } else {
            System.out.println("Error al añadir el campo.");
        }
    }

    // Método para modificar un valor en la tabla
    private static void modifyTableValue(Scanner scanner) {
        System.out.print("Introduce el nombre de la tabla: ");
        String tableName = scanner.nextLine();
        System.out.print("Introduce la clave del registro a modificar: ");
        String key = scanner.nextLine();
        System.out.print("Introduce el nombre del campo: ");
        String fieldName = scanner.nextLine();
        System.out.print("Introduce el nuevo valor: ");
        String newValue = scanner.nextLine();
        boolean success = tableRepository.updateValue(tableName, key, fieldName, newValue);
        if (success) {
            System.out.println("Valor modificado exitosamente.");
        } else {
            System.out.println("Error al modificar el valor.");
        }
    }

    // Método para agregar un registro en la base de datos
    private static void addTableRecord(Scanner scanner) {
        System.out.print("Introduce el nombre de la tabla: ");
        String tableName = scanner.nextLine();
        
        System.out.print("Introduce los valores para el nuevo registro separados por coma (en el orden de las columnas): ");
        String values = scanner.nextLine();
        
        // Llamar al método addRecord para agregar el registro
        boolean success = tableRepository.addRecord(tableName, values);
        
        if (success) {
            System.out.println("Registro agregado exitosamente.");
            
     
            String[] columns = {"id","usuario","password","nivel","campusano55","campusano432"};
            
            String[] recordValues = values.split(","); 

            System.out.printf("%-10s%-15s%-15s%-10s%-20s%-20s%n", 
                              columns[0], columns[1], columns[2], columns[3], columns[4], columns[5]);

            System.out.printf("%-10s%-15s%-15s%-10s%-20s%-20s%n", 
                              "2", 
                              recordValues[0].trim(), 
                              recordValues[1].trim(), 
                              recordValues[2].trim(), 
                              (recordValues.length > 3 ? recordValues[3].trim() : "null"), 
                              (recordValues.length > 4 ? recordValues[4].trim() : "null")); 
        } else {
            System.out.println("Error al agregar el registro.");
        }
    }    

    // Método para consultar ventas por tienda
    private static void querySalesByStore(Scanner scanner) {
        System.out.print("Introduce el nombre de la tienda: ");
        String storeName = scanner.nextLine();
        int sales = tableRepository.getSalesByStore(storeName);
        System.out.println("Ventas totales en la tienda " + storeName + ": " + sales);
    }

    // Método para consultar ventas totales y promedio
    private static void queryTotalAndAverageSales() {
        double[] stats = tableRepository.getTotalAndAverageSales();
        System.out.println("Ventas totales: " + stats[0]);
        System.out.println("Promedio de ventas: " + stats[1]);
    }

        // Método para eliminar un registro de la tabla
    private static void deleteTableRecord(Scanner scanner) {
        System.out.print("Introduce el nombre de la tabla: ");
        String tableName = scanner.nextLine();
    
        System.out.print("Introduce el ID del registro a eliminar: ");
        String recordId = scanner.nextLine();
    
        boolean success = tableRepository.deleteRecord(tableName, recordId);
    
        if (success) {
        System.out.println("Registro eliminado exitosamente.");
     } else {
        System.out.println("No se pudo eliminar el registro. Verifique si el ID es correcto.");
    }
 }
    // Método para ver libros y autores
    private static void viewBooksAndAuthors(Scanner scanner) {
        ArrayList<String[]> booksAndAuthors = tableRepository.getBooksAndAuthors(); 
    
        if (booksAndAuthors.isEmpty()) {
            System.out.println("No se encontraron libros y autores.");
        } else {
            System.out.printf("%-50s %-30s%n", "Libro", "Autor"); 
            System.out.println("=".repeat(80)); // Línea divisoria
    
            for (String[] row : booksAndAuthors) {
                System.out.printf("%-50s %-30s%n", row[0], row[1]);
            }
        }
    }
}
