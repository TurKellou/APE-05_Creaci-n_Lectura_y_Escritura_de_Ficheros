// Clase Producto
public class Producto {

    // Atributos privados
    private int codigo;
    private String nombre;
    private int cantidad;
    private double precio;

    // Constructor vacío
    public Producto() {

        codigo = 0;
        nombre = "";
        cantidad = 0;
        precio = 0;
    }

    // Métodos SET
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Métodos GET
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    // Método para calcular el total
    public double calcularTotal() {

        return cantidad * precio;
    }

    // Método para mostrar información
    public void mostrarProducto() {

        System.out.println("\nCodigo: " + codigo);
        System.out.println("Nombre: " + nombre);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Precio: $" + precio);
        System.out.println("Total: $" + calcularTotal());
    }
}