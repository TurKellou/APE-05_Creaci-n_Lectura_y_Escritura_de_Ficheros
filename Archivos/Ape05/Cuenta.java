// Clase Cuenta
public class Cuenta {

    // Atributos privados
    private String usuario;
    private double saldo;

    // Constructor vacío
    public Cuenta() {

        usuario = "";
        saldo = 0;
    }

    // Métodos SET
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Métodos GET
    public String getUsuario() {
        return usuario;
    }

    public double getSaldo() {
        return saldo;
    }

    // Método para depositar dinero
    public void depositar(double monto) {

        saldo += monto;
    }

    // Método para retirar dinero
    public void retirar(double monto) {

        // Verificar saldo disponible
        if(monto <= saldo) {

            saldo -= monto;
            System.out.println("Retiro realizado correctamente");

        } else {

            System.out.println("Saldo insuficiente");
        }
    }

    // Método para consultar saldo
    public void consultarSaldo() {

        System.out.println("Saldo actual: $" + saldo);
    }
} 
    

