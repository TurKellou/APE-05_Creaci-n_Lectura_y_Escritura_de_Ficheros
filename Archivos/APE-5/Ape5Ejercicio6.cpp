#include <iostream>
#include <fstream>
#include <string>
#include <sstream>

using namespace std;

// ============================================================================
// FUNCIONES AUXILIARES DE VALIDACIÓN
// ============================================================================

// Valida de forma básica que la fecha tenga el formato numérico DD/MM/AAAA
bool validarFecha(const string& fecha) {
    if (fecha.length() != 10) return false;
    if (fecha[2] != '/' || fecha[5] != '/') return false;
    
    // Verifica que el resto sean números
    for (int i = 0; i < 10; i++) {
        if (i != 2 && i != 5) {
            if (fecha[i] < '0' || fecha[i] > '9') return false;
        }
    }
    return true;
}

// Solicita la fecha y fuerza al ciclo a repetirse hasta que sea válida
string solicitarFecha() {
    string fecha;
    while (true) {
        cout << "  Ingrese fecha (DD/MM/AAAA): ";
        cin >> fecha;
        if (validarFecha(fecha)) {
            return fecha;
        }
        cout << "  [Error] Formato de fecha incorrecto. Use DD/MM/AAAA.\n";
    }
}

// Fuerza al usuario a ingresar únicamente 'A' o 'F'
char solicitarEstado() {
    char estado;
    while (true) {
        cout << "  Ingrese estado (A = Asistio, F = Falto): ";
        cin >> estado;
        estado = toupper(estado); // Lo convierte a mayúscula automáticamente
        if (estado == 'A' || estado == 'F') {
            return estado;
        }
        cout << "  [Error] Estado invalido. Solo se permite A o F.\n";
    }
}

// ============================================================================
// FUNCIONES DE CONTROL DE ASISTENCIA (MANEJO DE ARCHIVOS)
// ============================================================================

// REQUERIMIENTO 1: Registrar nombre, fecha y estado en el archivo
void registrarAsistencia() {
    // ios::app abre el archivo en modo "append" para añadir texto al final sin borrar lo anterior
    ofstream archivoEscritura("asistencia.txt", ios::app);
    
    if (!archivoEscritura.is_open()) {
        cout << "Error: No se pudo abrir el archivo para guardar datos.\n";
        return;
    }

    string nombre, fecha;
    char estado;

    cout << "\n--- NUEVO REGISTRO DE ASISTENCIA ---\n";
    cout << "  Nombre del estudiante: ";
    cin.ignore(); // Limpia el buffer antes de leer una cadena completa con espacios
    getline(cin, nombre);
    
    fecha = solicitarFecha();
    estado = solicitarEstado();

    // Guardamos los datos separados por un caracter delimitador (pipe '|') para facilitar la lectura posterior
    archivoEscritura << nombre << "|" << fecha << "|" << estado << "\n";
    archivoEscritura.close();

    cout << "\n>> Asistencia registrada con exito en el sistema.\n";
}

// REQUERIMIENTO 2 y 3: Mostrar historial completo y contar las faltas totales
void mostrarHistorialYFaltas() {
    ifstream archivoLectura("asistencia.txt");
    
    if (!archivoLectura.is_open()) {
        cout << "\n[Aviso] No hay registros previos de asistencia (El archivo aun no existe).\n";
        return;
    }

    string linea;
    int totalFaltas = 0;
    int totalRegistros = 0;

    cout << "\n=========================================================\n";
    cout << "              HISTORIAL DE ASISTENCIA\n";
    cout << "=========================================================\n";
    cout << "  %-20s %-15s %-10s\n";
    cout << "  -------------------------------------------------------\n";

    // Ciclo para leer el archivo línea por línea hasta el final
    while (getline(archivoLectura, linea)) {
        if (linea.empty()) continue;

        stringstream ss(linea);
        string nombre, fecha, estadoStr;

        // Descomponemos la línea usando el delimitador '|'
        getline(ss, nombre, '|');
        getline(ss, fecha, '|');
        getline(ss, estadoStr, '|');

        char estado = estadoStr[0];

        // Traducimos el caracter plano a un texto legible para el usuario
        string textoEstado = (estado == 'A') ? "Asistio" : "Falto";

        // Si es una falta, incrementamos el contador
        if (estado == 'F') {
            totalFaltas++;
        }

        // Imprimimos la fila formateada de forma ordenada
        cout << "  " << nombre;
        for (size_t i = nombre.length(); i < 22; i++) cout << " ";
        cout << fecha << "         " << textoEstado << "\n";
        
        totalRegistros++;
    }

    archivoLectura.close();

    cout << "=========================================================\n";
    cout << " Total de registros procesados: " << totalRegistros << "\n";
    cout << " Total de FALTAS contabilizadas: " << totalFaltas << "\n";
    cout << "=========================================================\n";
}

// ============================================================================
// PROGRAMA PRINCIPAL
// ============================================================================
int main() {
    int opcion;

    // Ciclo principal del menú de control de asistencia
    do {
        cout << "\n==================================\n";
        cout << "    SISTEMA CONTROL DE ASISTENCIA \n";
        cout << "==================================\n";
        cout << "1. Registrar Asistencia\n";
        cout << "2. Mostrar Historial y Faltas\n";
        cout << "3. Salir del programa\n";
        cout << "----------------------------------\n";
        cout << "Seleccione una opcion (1-3): ";
        
        // Validación para evitar bucles infinitos si ingresan letras en el menú
        if (!(cin >> opcion)) {
            cout << "Opcion no valida. Intente de nuevo.\n";
            cin.clear();
            cin.ignore(10000, '\n');
            continue;
        }

        switch (opcion) {
            case 1:
                registrarAsistencia();
                break;
            case 2:
                mostrarHistorialYFaltas();
                break;
            case 3:
                cout << "\nSaliendo del sistema de asistencia... ¡Hasta luego!\n";
                break;
            default:
                cout << "[Error] La opcion elegida no existe. Intente con 1, 2 o 3.\n";
        }
    } while (opcion != 3); // Rompe el ciclo cuando la opción es 3

    return 0;
}
