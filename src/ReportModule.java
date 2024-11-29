import java.util.ArrayList;

public class ReportModule {
    public static void generateSalesReport(ArrayList<Sales> sales) {
        // Calcula el total de ventas y otros indicadores
        double totalSales = 0;
        for (Sales sale : sales) { 
            totalSales += sale.getSaleAmount();
        }
        System.out.println("Total de Ventas: " + totalSales);
    }
}
