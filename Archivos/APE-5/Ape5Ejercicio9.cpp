#include <iostream>
#include <fstream>
#include <string>
#include <iomanip>
#include <sstream>

using namespace std;

// ============================================================================
// CLASE VENTA (Aplicación de POO)
// ============================================================================
class Venta {
private:
    // Atributos encapsulados
    string producto;
    int cantidad;
    double precio;
    double total;

public:
    // Constructor por defecto
    Venta() {
        producto = "";
        cantidad = 0;
        precio = 0.0;
        total = 0.0;
    }

    // Métodos Setters y Getters (Métodos de acceso)
    void setProducto(string prod) { producto = prod; }
    void setCantidad(int cant) { cantidad = cant; }
    void setPrecio(double prec) { precio = prec; }
    
    string getProducto() { return producto; }
    int getCantidad() { return cantidad; }
    double getPrecio() { return precio; }
    double getTotal() { return total; }

    // Método para calcular el total automáticamente
    void calcularTotal() {
        total = cantidad * precio;
    }

    // REQUERIMIENTO: Guardar el registro en el archivo de texto
    void guardarEnArchivo() {
        // Modo ios::app para añadir datos al final sin borrar lo existente
        ofstream archivoEscritura("ventas.txt", ios::app);
        
        if (!archivoEscritura.is_open()) {
            cout << "  [Error] No se pudo abrir el archivo para registrar la venta.\n";
            return;
        }

        // Guardamos los atributos separados por el delimitador '|'
        archivoEscritura << producto << "|" << cantidad << "|" << precio << "|" << total << "\n";
        archivoEscritura.close();
    }

    // REQUERIMIENTO: Mostrar la factura de la venta actual en pantalla
    void generarFactura() {
        cout << "\n=========================================\n";
        cout << "           FACTURA DE VENTA              \n";
        cout << "=========================================\n";
        cout << "  Producto:   " << producto << "\n";
        cout << "  Cantidad:   " << cantidad << "\n";
        cout << "  Precio Unit:" << fixed << setprecision(2) << " $" << precio << "\n";
        cout << "  ---------------------------------------\n";
        cout << "  TOTAL:      " << fixed << setprecision(2) << " $" << total << "\n";
        cout << "=========================================\n";
    }
};

// ============================================================================
// FUNCIÓN GLOBAL: HISTORIAL DE VENTAS (Manejo de Archivos)
// ============================================================================
void mostrarHistorialVentas() {
    ifstream archivoLectura("ventas.txt");
    
    if (!archivoLectura.is_open()) {
        cout << "\n[Aviso] No existen ventas registradas en el historial (Archivo no encontrado).\n";
        return;
    }

    string linea;
    double granTotalAcumulado = 0.0;
    int contadorVentas = 0;

    cout << "\n====================================================================\n";
    cout << "                     HISTORIAL GENERAL DE VENTAS                    \n";
    cout << "====================================================================\n";
    cout << "  " << left << setw(20) << "Producto" 
         << setw(12) << "Cantidad" 
         << setw(15) << "Precio Unit." 
         << setw(15) << "Total Venta" << "\n";
    cout << "  ------------------------------------------------------------------\n";

    // Ciclo para leer el archivo línea por línea
    while (getline(archivoLectura, linea)) {
        if (linea.empty()) continue;

        stringstream ss(linea);
        string prod, cantStr, precStr, totStr;

        // Descomposición de la línea usando el delimitador '|'
        getline(ss, prod, '|');
        getline(ss, cantStr, '|');
        getline(ss, precStr, '|');
        getline(ss, totStr, '|');

        // Conversión de las cadenas leídas a sus tipos de datos correspondientes
        int cant = stoi(cantStr);
        double prec = stod(precStr);
        double tot = stod(totStr);

        granTotalAcumulado += tot;
        contadorVentas++;

        // Imprimir la fila con formato alineado
        cout << "  " << left << setw(20) << prod 
             << setw(12) << cant 
             << " $" << setw(13) << fixed << setprecision(2) << prec 
             << " $" << setw(13) << fixed << setprecision(2) << tot << "\n";
    }

    archivoLectura.close();

    cout << "====================================================================\n";
    cout << "  Total de ventas procesadas: " << contadorVentas << "\n";
    cout << "  INGRESOS TOTALES ACUMULADOS: $" << fixed << setprecision(2) << granTotalAcumulado << "\n";
    cout << "====================================================================\n";
}

// ============================================================================
// PROGRAMA PRINCIPAL
// ============================================================================
int main() {
    int opcion;
    
    do {
        cout << "\n==================================\n";
        cout << "        SISTEMA DE VENTAS        \n";
        cout << "==================================\n";
        cout << "1. Registrar Nueva Venta (Facturar)\n";
        cout << "2. Mostrar Historial de Ventas\n";
        cout << "3. Salir del programa\n";
        cout << "----------------------------------\n";
        cout << "Seleccione una opcion (1-3): ";
        
        // Validación para evitar bucles infinitos con caracteres inválidos
        if (!(cin >> opcion)) {
            cout << "[Error] Entrada invalida. Intente de nuevo.\n";
            cin.clear();
            cin.ignore(10000, '\n');
            continue;
        }

        switch (opcion) {
            case 1: {
                Venta nuevaVenta; // Instanciación del objeto
                string prod;
                int cant;
                double prec;

                cout << "\n--- INGRESO DE DATOS DE LA VENTA ---\n";
                cout << "  Nombre del producto: ";
                cin.ignore(); // Limpia el buffer
                getline(cin, prod);
                nuevaVenta.setProducto(prod);

                // Validación de cantidad
                do {
                    cout << "  Cantidad: ";
                    if (cin >> cant && cant > 0) break;
                    cout << "  [Error] Cantidad debe ser un entero mayor a 0.\n";
                    cin.clear(); cin.ignore(10000, '\n');
                } while (true);
                nuevaVenta.setCantidad(cant);

                // Validacion de precio
                do {
                    cout << "  Precio unitario: ";
                    if (cin >> prec && prec > 0.0) break;
                    cout << "  [Error] El precio debe ser un numero mayor a 0.\n";
                    cin.clear(); cin.ignore(10000, '\n');
                } while (true);
                nuevaVenta.setPrecio(prec);

                // Procesamiento mediante métodos del objeto
                nuevaVenta.calcularTotal();
                nuevaVenta.guardarEnArchivo();
                nuevaVenta.generarFactura();
                break;
            }
            case 2:
                mostrarHistorialVentas();
                break;
            case 3:
                cout << "\nSaliendo del sistema de ventas... ¡Hasta luego!\n";
                break;
            default:
                cout << "[Error] La opcion seleccionada no existe.\n";
        }
    } while (opcion != 3);

    return 0;
}



