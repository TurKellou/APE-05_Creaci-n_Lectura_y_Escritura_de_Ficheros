// Librerías necesarias
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        // Objeto Scanner para ingresar datos
        Scanner entrada = new Scanner(System.in);

        // Pedir cantidad de productos
        System.out.print("Ingrese la cantidad de productos: ");
        int cantidadProductos = entrada.nextInt();

        // Crear arreglo de objetos
        Producto productos[] = new Producto[cantidadProductos];

        // Variables temporales
        int codigo;
        int cantidad;
        String nombre;
        double precio;

        // Variable para guardar el total general
        double totalInventario = 0;

        try {

            // Crear archivo
            FileWriter archivo = new FileWriter("inventariojava.txt");

            // Ciclo para registrar productos
            for(int i = 0; i < cantidadProductos; i++) {

                // Crear objeto
                productos[i] = new Producto();

                System.out.println("\n============================");
                System.out.println("REGISTRO DEL PRODUCTO " + (i + 1));
                System.out.println("============================");

                // Ingreso de datos
                System.out.print("Ingrese el codigo: ");
                codigo = entrada.nextInt();

                entrada.nextLine();

                System.out.print("Ingrese el nombre: ");
                nombre = entrada.nextLine();

                System.out.print("Ingrese la cantidad: ");
                cantidad = entrada.nextInt();

                System.out.print("Ingrese el precio: ");
                precio = entrada.nextDouble();

                // Guardar datos
                productos[i].setCodigo(codigo);
                productos[i].setNombre(nombre);
                productos[i].setCantidad(cantidad);
                productos[i].setPrecio(precio);

                // Calcular total general
                totalInventario += productos[i].calcularTotal();

                // Guardar en archivo
                archivo.write("Codigo: " + codigo + "\n");
                archivo.write("Nombre: " + nombre + "\n");
                archivo.write("Cantidad: " + cantidad + "\n");
                archivo.write("Precio: $" + precio + "\n");
                archivo.write("Total: $" + productos[i].calcularTotal() + "\n");
                archivo.write("----------------------\n");
            }

            // Cerrar archivo
            archivo.close();

        } catch(IOException e) {

            System.out.println("Error al crear el archivo");
        }

        // Mostrar inventario
        System.out.println("\n======= INVENTARIO =======");

        for(int i = 0; i < cantidadProductos; i++) {

            productos[i].mostrarProducto();
        }

        // Mostrar total general
        System.out.println("\nValor total del inventario: $" + totalInventario);

        // Cerrar Scanner
        entrada.close();
    }
}