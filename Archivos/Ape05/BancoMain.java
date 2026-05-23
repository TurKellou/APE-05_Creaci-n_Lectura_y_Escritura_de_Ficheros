// Librerías necesarias
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class BancoMain{

    public static void main(String[] args) {

        // Objeto Scanner
        Scanner entrada = new Scanner(System.in);

        // Crear objeto cuenta
        Cuenta cuenta = new Cuenta();

        // Variables
        String usuario;
        double saldoInicial;
        double monto;
        int opcion;

        try {

            // Archivo para guardar datos
            FileWriter archivo = new FileWriter("cajerojava.txt");

            // Ingreso de datos
            System.out.print("Ingrese el nombre del usuario: ");
            usuario = entrada.nextLine();

            System.out.print("Ingrese el saldo inicial: ");
            saldoInicial = entrada.nextDouble();

            // Guardar datos en el objeto
            cuenta.setUsuario(usuario);
            cuenta.setSaldo(saldoInicial);

            // Menú principal
            do {

                System.out.println("\n======= CAJERO AUTOMATICO =======");
                System.out.println("1. Consultar saldo");
                System.out.println("2. Depositar dinero");
                System.out.println("3. Retirar dinero");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opcion: ");
                opcion = entrada.nextInt();

                switch(opcion) {

                    case 1:

                        // Consultar saldo
                        cuenta.consultarSaldo();
                        break;

                    case 2:

                        // Depositar dinero
                        System.out.print("Ingrese el monto a depositar: ");
                        monto = entrada.nextDouble();

                        cuenta.depositar(monto);

                        System.out.println("Deposito realizado correctamente");
                        break;

                    case 3:

                        // Retirar dinero
                        System.out.print("Ingrese el monto a retirar: ");
                        monto = entrada.nextDouble();

                        cuenta.retirar(monto);
                        break;

                    case 4:

                        System.out.println("Saliendo del sistema...");
                        break;

                    default:

                        System.out.println("Opcion invalida");
                }

            } while(opcion != 4);

            // Guardar datos en archivo
            archivo.write("Usuario: " + cuenta.getUsuario() + "\n");
            archivo.write("Saldo final: $" + cuenta.getSaldo() + "\n");

            // Cerrar archivo
            archivo.close();

        } catch(IOException e) {

            System.out.println("Error al crear el archivo");
        }

        // Cerrar Scanner
        entrada.close();
    }
}
