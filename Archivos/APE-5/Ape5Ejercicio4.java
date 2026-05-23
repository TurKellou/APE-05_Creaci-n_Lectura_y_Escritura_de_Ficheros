// Librerías necesarias
import java.io.*;
import java.util.Scanner;

// Clase Contacto
class Contacto {

    // Atributos
    String nombre;
    String telefono;
    String correo;

    // Constructor
    public Contacto(String n, String t, String c) {

        nombre = n;
        telefono = t;
        correo = c;
    }

    // Método para convertir los datos en texto
    public String toString() {

        return nombre + "," + telefono + "," + correo;
    }
}

// Clase principal
public class Ape5Ejercicio4 {

    // Nombre del archivo
    static String archivo = "agenda.txt";

    // Método para agregar contactos
    public static void agregar(Scanner entrada) {

        try {

            // Abrir archivo en modo agregar
            FileWriter file = new FileWriter(archivo, true);

            String n, t, c;

            // Ingreso de datos
            System.out.print("Nombre: ");
            n = entrada.nextLine();

            System.out.print("Telefono: ");
            t = entrada.nextLine();

            System.out.print("Correo: ");
            c = entrada.nextLine();

            // Crear objeto contacto
            Contacto con = new Contacto(n, t, c);

            // Guardar en archivo
            file.write(con.toString() + "\n");

            // Cerrar archivo
            file.close();

            System.out.println("Contacto guardado");

        } catch(IOException e) {

            System.out.println("Error al guardar");
        }
    }

    // Método para mostrar contactos
    public static void mostrar() {

        try {

            // Abrir archivo para lectura
            BufferedReader file = new BufferedReader(new FileReader(archivo));

            String linea;

            // Leer línea por línea
            while((linea = file.readLine()) != null) {

                System.out.println(linea);
            }

            // Cerrar archivo
            file.close();

        } catch(IOException e) {

            System.out.println("Error al leer el archivo");
        }
    }

    // Método para buscar contacto
    public static void buscar(Scanner entrada) {

        try {

            // Abrir archivo
            BufferedReader file = new BufferedReader(new FileReader(archivo));

            String linea;
            String nombre;

            boolean encontrado = false;

            // Pedir nombre
            System.out.print("Nombre a buscar: ");
            nombre = entrada.nextLine();

            // Buscar contacto
            while((linea = file.readLine()) != null) {

                if(linea.contains(nombre)) {

                    System.out.println("Encontrado: " + linea);
                    encontrado = true;
                }
            }

            // Verificar si no existe
            if(!encontrado) {

                System.out.println("No encontrado");
            }

            // Cerrar archivo
            file.close();

        } catch(IOException e) {

            System.out.println("Error al buscar");
        }
    }

    // Método principal
    public static void main(String[] args) {

        // Scanner para leer datos
        Scanner entrada = new Scanner(System.in);

        int op;

        do {

            // Menú
            System.out.println("\n1. Agregar");
            System.out.println("2. Mostrar");
            System.out.println("3. Buscar");
            System.out.println("4. Salir");

            System.out.print("Opcion: ");
            op = entrada.nextInt();
            entrada.nextLine();

            // Opciones
            switch(op) {

                case 1:

                    agregar(entrada);
                    break;

                case 2:

                    mostrar();
                    break;

                case 3:

                    buscar(entrada);
                    break;
            }

        } while(op != 4);

        // Cerrar Scanner
        entrada.close();
    }
}