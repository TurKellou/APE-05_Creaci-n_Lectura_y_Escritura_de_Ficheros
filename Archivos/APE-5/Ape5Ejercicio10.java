import java.io.*;
import java.util.*;

class Contacto {
    String nombre, telefono, correo;

    public Contacto(String n, String t, String c) {
        nombre = n;
        telefono = t;
        correo = c;
    }

    public String toString() {
        return nombre + "," + telefono + "," + correo;
    }
}

public class Ape5Ejercicio10 {
    static final String archivo = "agenda.txt";
    static Scanner sc = new Scanner(System.in);

    public static void agregar() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));

        System.out.print("Nombre: ");
        String n = sc.nextLine();
        System.out.print("Telefono: ");
        String t = sc.nextLine();
        System.out.print("Correo: ");
        String c = sc.nextLine();

        Contacto con = new Contacto(n, t, c);
        bw.write(con.toString());
        bw.newLine();
        bw.close();

        System.out.println("Guardado");
    }

    public static void mostrar() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;

        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }
        br.close();
    }

    public static void buscar() throws IOException {
        System.out.print("Buscar nombre: ");
        String nombre = sc.nextLine();

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        boolean encontrado = false;

        while ((linea = br.readLine()) != null) {
            if (linea.contains(nombre)) {
                System.out.println("Encontrado: " + linea);
                encontrado = true;
            }
        }

        if (!encontrado)
            System.out.println("No encontrado");

        br.close();
    }

    public static void main(String[] args) throws IOException {
        int op;

        do {
            System.out.println("\n1. Agregar\n2. Mostrar\n3. Buscar\n4. Salir");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1: agregar(); break;
                case 2: mostrar(); break;
                case 3: buscar(); break;
            }

        } while (op != 4);
    }
}