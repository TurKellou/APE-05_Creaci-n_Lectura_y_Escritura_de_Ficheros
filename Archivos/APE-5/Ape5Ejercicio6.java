import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

// ============================================================================
// CLASE PRINCIPAL: SistemaAsistencia
// ============================================================================
public class Ape5Ejercicio6 {

    private static final String NOMBRE_ARCHIVO = "asistencia.txt";
    private static final Scanner teclado = new Scanner(System.in);

    // Valida de forma básica que la fecha tenga el formato numérico DD/MM/AAAA
    public static boolean validarFecha(String fecha) {
        if (fecha.length() != 10) return false;
        if (fecha.charAt(2) != '/' || fecha.charAt(5) != '/') return false;
        
        // Verifica que los demás caracteres sean dígitos
        for (int i = 0; i < 10; i++) {
            if (i != 2 && i != 5) {
                if (fecha.charAt(i) < '0' || fecha.charAt(i) > '9') return false;
            }
        }
        return true;
    }

    // Solicita la fecha y fuerza al ciclo a repetirse hasta que sea válida
    public static String solicitarFecha() {
        String fecha;
        while (true) {
            System.out.print("  Ingrese fecha (DD/MM/AAAA): ");
            fecha = teclado.next();
            if (validarFecha(fecha)) {
                return fecha;
            }
            System.out.println("  [Error] Formato de fecha incorrecto. Use DD/MM/AAAA.");
        }
    }

    // Fuerza al usuario a ingresar únicamente 'A' o 'F'
    public static char solicitarEstado() {
        String entrada;
        while (true) {
            System.out.print("  Ingrese estado (A = Asistio, F = Falto): ");
            entrada = teclado.next().toUpperCase(); // Convierte a mayúscula automáticamente
            if (entrada.length() == 1 && (entrada.charAt(0) == 'A' || entrada.charAt(0) == 'F')) {
                return entrada.charAt(0);
            }
            System.out.println("  [Error] Estado invalido. Solo se permite A o F.");
        }
    }

    // REQUERIMIENTO 1: Registrar nombre, fecha y estado en el archivo
    public static void registrarAsistencia() {
        // El parámetro true en FileWriter habilita el modo "append" (añadir al final)
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             PrintWriter archivoEscritura = new PrintWriter(fw)) {
            
            System.out.println("\n--- NUEVO REGISTRO DE ASISTENCIA ---");
            System.out.print("  Nombre del estudiante: ");
            teclado.nextLine(); // Limpia el buffer del Scanner
            String nombre = teclado.nextLine();
            
            String fecha = solicitarFecha();
            char estado = solicitarEstado();

            // Guardamos los datos separados por la barra vertical '|'
            archivoEscritura.println(nombre + "|" + fecha + "|" + estado);
            System.out.println("\n>> Asistencia registrada con exito en el archivo.");
            
        } catch (IOException e) {
            System.out.println("Error al intentar escribir en el archivo: " + e.getMessage());
        }
    }

    // REQUERIMIENTO 2 y 3: Mostrar historial completo y contar las faltas totales
    public static void mostrarHistorialYFaltas() {
        File archivo = new File(NOMBRE_ARCHIVO);
        
        // Comprobamos de antemano si el archivo físico existe en el disco
        if (!archivo.exists()) {
            System.out.println("\n[Aviso] No hay registros previos de asistencia (El archivo aun no existe).");
            return;
        }

        int totalFaltas = 0;
        int totalRegistros = 0;

        System.out.println("\n=========================================================");
        System.out.println("              HISTORIAL DE ASISTENCIA");
        System.out.println("=========================================================");
        System.out.printf("  %-22s %-15s %-10s\n", "Estudiante", "Fecha", "Estado");
        System.out.println("  -------------------------------------------------------");

        try (FileReader fr = new FileReader(archivo);
             BufferedReader archivoLectura = new BufferedReader(fr)) {
            
            String linea;
            // Ciclo para leer el archivo línea por línea hasta llegar al final (null)
            while ((linea = archivoLectura.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                // Dividimos la cadena usando la barra vertical como separador
                // Usamos \\| porque en Java split requiere una expresión regular y el | es un caracter reservado
                String[] partes = linea.split("\\|");
                
                if (partes.length == 3) {
                    String nombre = partes[0];
                    String fecha = partes[1];
                    char estado = partes[2].charAt(0);

                    String textoEstado = (estado == 'A') ? "Asistio" : "Falto";

                    if (estado == 'F') {
                        totalFaltas++;
                    }

                    System.out.printf("  %-22s %-15s %-10s\n", nombre, fecha, textoEstado);
                    totalRegistros++;
                }
            }
            
            System.out.println("=========================================================");
            System.out.println(" Total de registros procesados: " + totalRegistros);
            System.out.println(" Total de FALTAS contabilizadas: " + totalFaltas);
            System.out.println("=========================================================");

        } catch (IOException e) {
            System.out.println("Error al intentar leer el archivo: " + e.getMessage());
        }
    }

    // ============================================================================
    // PROGRAMA PRINCIPAL
// ============================================================================
    public static void main(String[] args) {
        int opcion = 0;

        // Ciclo principal del menú de control de asistencia
        do {
            System.out.println("\n==================================");
            System.out.println("    SISTEMA CONTROL DE ASISTENCIA ");
            System.out.println("==================================");
            System.out.println("1. Registrar Asistencia");
            System.out.println("2. Mostrar Historial y Faltas");
            System.out.println("3. Salir del programa");
            System.out.println("----------------------------------");
            System.out.print("Seleccione una opcion (1-3): ");

            // Validación para evitar bucles infinitos por ingreso de letras
            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                
                switch (opcion) {
                    case 1:
                        registrarAsistencia();
                        break;
                    case 2:
                        mostrarHistorialYFaltas();
                        break;
                    case 3:
                        System.out.println("\nSaliendo del sistema de asistencia... ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("[Error] La opcion elegida no existe. Intente con 1, 2 o 3.");
                }
            } else {
                System.out.println("[Error] Entrada no valida. Debe ingresar un numero entero.");
                teclado.next(); // Descarta la entrada incorrecta del buffer
                opcion = 0; // Reinicia la opción para que continúe el ciclo
            }
            
        } while (opcion != 3);

        teclado.close(); // Cerramos el recurso del teclado
    }
}
