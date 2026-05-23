import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

// ============================================================================
// CLASE VENTA (Aplicación estricta de POO)
// ============================================================================
class Venta {
    // Atributos privados (Encapsulamiento)
    private String producto;
    private int cantidad;
    private double precio;
    private double total;

    // Constructor por defecto
    public Venta() {
        this.producto = "";
        this.cantidad = 0;
        this.precio = 0.0;
        this.total = 0.0;
    }

    // Métodos Setters y Getters
    public void setProducto(String producto) { this.producto = producto; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }
    public double getTotal() { return total; }

    // Método para calcular el total de forma autónoma
    public void calcularTotal() {
        this.total = this.cantidad * this.precio;
    }

    // REQUERIMIENTO: Guardar el registro actual en el archivo de texto
    public void guardarEnArchivo(String nombreArchivo) {
        // Habilitamos el modo append (true) para añadir filas al final del archivo
        try (FileWriter fw = new FileWriter(nombreArchivo, true);
             PrintWriter archivoEscritura = new PrintWriter(fw)) {
            
            // Guardamos los atributos separados por el delimitador '|'
            archivoEscritura.println(producto + "|" + cantidad + "|" + precio + "|" + total);
            
        } catch (IOException e) {
            System.out.println("  [Error] No se pudo escribir en el archivo: " + e.getMessage());
        }
    }

    // REQUERIMIENTO: Mostrar la factura en pantalla
    public void generarFactura() {
        System.out.println("\n=========================================");
        System.out.println("           FACTURA DE VENTA              ");
        System.out.println("=========================================");
        System.out.println("  Producto:   " + producto);
        System.out.println("  Cantidad:   " + cantidad);
        System.out.printf("  Precio Unit: $%.2f\n", precio);
        System.out.println("  ---------------------------------------");
        System.out.printf("  TOTAL:      $%.2f\n", total);
        System.out.println("=========================================");
    }
}

// ============================================================================
// CLASE PRINCIPAL: SistemaVentas
// ============================================================================
public class Ape5Ejercicio9 {

    private static final String NOMBRE_ARCHIVO = "ventas.txt";
    private static final Scanner teclado = new Scanner(System.in);

    // REQUERIMIENTO: Mostrar historial leyendo desde el archivo
    public static void mostrarHistorialVentas() {
        File archivo = new File(NOMBRE_ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("\n[Aviso] No existen ventas registradas en el historial.");
            return;
        }

        double granTotalAcumulado = 0.0;
        int contadorVentas = 0;

        System.out.println("\n====================================================================");
        System.out.println("                     HISTORIAL GENERAL DE VENTAS                    ");
        System.out.println("====================================================================");
        System.out.printf("  %-20s %-12s %-15s %-15s\n", "Producto", "Cantidad", "Precio Unit.", "Total Venta");
        System.out.println("  ------------------------------------------------------------------");

        try (FileReader fr = new FileReader(archivo);
             BufferedReader archivoLectura = new BufferedReader(fr)) {

            String linea;
            while ((linea = archivoLectura.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                // Segmentamos la línea usando la barra vertical como expresión regular
                String[] partes = linea.split("\\|");

                if (partes.length == 4) {
                    String prod = partes[0];
                    int cant = Integer.parseInt(partes[1]);
                    double prec = Double.parseDouble(partes[2]);
                    double tot = Double.parseDouble(partes[3]);

                    granTotalAcumulado += tot;
                    contadorVentas++;

                    // Mostrar fila con formato ordenado
                    System.out.printf("  %-20s %-12d $%-14.2f $%-14.2f\n", prod, cant, prec, tot);
                }
            }

            System.out.println("====================================================================");
            System.out.println("  Total de ventas procesadas: " + contadorVentas);
            System.out.printf("  INGRESOS TOTALES ACUMULADOS: $%.2f\n", granTotalAcumulado);
            System.out.println("====================================================================");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al procesar el archivo de historial: " + e.getMessage());
        }
    }

    // PROGRAMA PRINCIPAL
    public static void main(String[] args) {
        int opcion = 0;

        do {
            System.out.println("\n==================================");
            System.out.println("        SISTEMA DE VENTAS        ");
            System.out.println("==================================");
            System.out.println("1. Registrar Nueva Venta (Facturar)");
            System.out.println("2. Mostrar Historial de Ventas");
            System.out.println("3. Salir del programa");
            System.out.println("----------------------------------");
            System.out.print("Seleccione una opcion (1-3): ");

            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();

                switch (opcion) {
                    case 1:
                        Venta nuevaVenta = new Venta(); // Instanciación del objeto
                        
                        System.out.println("\n--- INGRESO DE DATOS DE LA VENTA ---");
                        System.out.print("  Nombre del producto: ");
                        teclado.nextLine(); // Limpieza del buffer del Scanner
                        String prod = teclado.nextLine();
                        nuevaVenta.setProducto(prod);

                        // Validación de cantidad mayor a cero
                        int cant;
                        while (true) {
                            System.out.print("  Cantidad: ");
                            if (teclado.hasNextInt()) {
                                cant = teclado.nextInt();
                                if (cant > 0) break;
                            } else {
                                teclado.next(); // Descartar caracter erróneo
                            }
                            System.out.println("  [Error] Cantidad debe ser un entero mayor a 0.");
                        }
                        nuevaVenta.setCantidad(cant);

                        // Validación de precio mayor a cero
                        double prec;
                        while (true) {
                            System.out.print("  Precio unitario: ");
                            if (teclado.hasNextDouble()) {
                                prec = teclado.nextDouble();
                                if (prec > 0.0) break;
                            } else {
                                teclado.next(); // Descartar caracter erróneo
                            }
                            System.out.println("  [Error] El precio debe ser un numero mayor a 0.");
                        }
                        nuevaVenta.setPrecio(prec);

                        // Ejecución de la lógica interna mediante métodos del objeto
                        nuevaVenta.calcularTotal();
                        nuevaVenta.guardarEnArchivo(NOMBRE_ARCHIVO);
                        nuevaVenta.generarFactura();
                        break;

                    case 2:
                        mostrarHistorialVentas();
                        break;

                    case 3:
                        System.out.println("\nSaliendo del sistema de ventas... ¡Hasta luego!");
                        break;

                    default:
                        System.out.println("[Error] La opcion seleccionada no existe.");
                }
            } else {
                System.out.println("[Error] Entrada no valida. Debe ingresar un numero.");
                teclado.next(); // Limpieza del token inválido
                opcion = 0;
            }

        } while (opcion != 3);

        teclado.close();
    }
}