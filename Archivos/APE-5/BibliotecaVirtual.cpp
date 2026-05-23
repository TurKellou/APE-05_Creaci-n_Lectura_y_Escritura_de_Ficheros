#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

// === CLASE ENTIDAD ===
class Libro {
private:
    string codigo;
    string titulo;
    string autor;
    string estado; // "Terminado" o "En proceso"

public:
    Libro(string c, string t, string a, string e) {
        codigo = c;
        titulo = t;
        autor = a;
        estado = e;
    }

    string getCodigo() { return codigo; }
    string getTitulo() { return titulo; }
    string getAutor() { return autor; }
    string getEstado() { return estado; }

    // Convierte el objeto a una línea de texto para el archivo
    string toString() {
        return codigo + "|" + titulo + "|" + autor + "|" + estado;
    }
};

// === CLASE DE LÓGICA / CONTROL ===
class Biblioteca {
private:
    string nombreArchivo = "biblioteca.txt";

public:
    // Método para registrar un libro
    void registrarLibro(Libro libro) {
        ofstream archivo(nombreArchivo, ios::app); // ios::app para agregar al final
        if (archivo.is_open()) {
            archivo << libro.toString() << "\n";
            archivo.close();
            cout << "¡Libro registrado con éxito en el archivo!\n";
        } else {
            cout << "Error al abrir el archivo para guardar.\n";
        }
    }

    // Método para buscar un libro por código o título
    void buscarLibro(string criterio) {
        ifstream archivo(nombreArchivo);
        string linea;
        bool encontrado = false;

        if (archivo.is_open()) {
            while (getline(archivo, linea)) {
                stringstream ss(linea);
                string cod, tit, aut, est;
                
                getline(ss, cod, '|');
                getline(ss, tit, '|');
                getline(ss, aut, '|');
                getline(ss, est, '|');

                if (cod == criterio || tit == criterio) {
                    cout << "\n--- Libro Encontrado ---" << endl;
                    cout << "Código: " << cod << " | Título: " << tit << " | Autor: " << aut << " | Estado: " << est << endl;
                    encontrado = true;
                    break;
                }
            }
            archivo.close();
            if (!encontrado) cout << "No se encontró ningún libro que coincida con: " << criterio << endl;
        }
    }

    // Método para mostrar clasificados por estado
    void mostrarPorEstado() {
        ifstream archivo(nombreArchivo);
        string linea;

        if (!archivo.is_open()) {
            cout << "Aún no hay libros registrados.\n";
            return;
        }

        vector<string> terminados;
        vector<string> enProceso;

        while (getline(archivo, linea)) {
            stringstream ss(linea);
            string cod, tit, aut, est;
            
            getline(ss, cod, '|');
            getline(ss, tit, '|');
            getline(ss, aut, '|');
            getline(ss, est, '|');

            string info = "Cod: " + cod + " - " + tit + " (" + aut + ")";
            if (est == "Terminado") {
                terminados.push_back(info);
            } else {
                enProceso.push_back(info);
            }
        }
        archivo.close();

        cout << "\n=== LIBROS TERMINADOS (Disponibles para lectura completa) ===" << endl;
        for (const string& l : terminados) cout << "  • " << l << endl;
        if (terminados.empty()) cout << "  (Ninguno)\n";

        cout << "\n=== LIBROS EN PROCESO (Lectura en curso/Prestados) ===" << endl;
        for (const string& l : enProceso) cout << "  • " << l << endl;
        if (enProceso.empty()) cout << "  (Ninguno)\n";
    }
};

// === INTERFAZ / MAIN ===
int main() {
    Biblioteca miBiblioteca;
    int opcion;

    do {
        cout << "\n--- MENU BIBLIOTECA VIRTUAL ---\n";
        cout << "1. Registrar Libro\n";
        cout << "2. Buscar Libro\n";
        cout << "3. Mostrar Disponibles y En Proceso\n";
        cout << "4. Salir\n";
        cout << "Seleccione una opcion: ";
        cin >> opcion;
        cin.ignore(); // Limpiar el buffer del salto de línea

        if (opcion == 1) {
            string cod, tit, aut, est;
            int estOpcion;
            cout << "Código: "; getline(cin, cod);
            cout << "Título: "; getline(cin, tit);
            cout << "Autor: "; getline(cin, aut);
            cout << "Estado (1. Terminado / 2. En proceso): "; cin >> estOpcion;
            est = (estOpcion == 1) ? "Terminado" : "En proceso";

            Libro nuevoLibro(cod, tit, aut, est);
            miBiblioteca.registrarLibro(nuevoLibro);

        } else if (opcion == 2) {
            string criterio;
            cout << "Ingrese código o título a buscar: ";
            getline(cin, criterio);
            miBiblioteca.buscarLibro(criterio);

        } else if (opcion == 3) {
            miBiblioteca.mostrarPorEstado();
        }

    } while (opcion != 4);

    return 0;
}