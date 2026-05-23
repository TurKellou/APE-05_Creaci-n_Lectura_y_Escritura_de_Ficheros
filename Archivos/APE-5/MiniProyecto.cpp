#include <iostream>
#include <fstream>
#include <string>
using namespace std;

// 🔹 CLASE (para captura)
class Contacto {
public:
    string nombre, telefono, correo;

    Contacto(string n, string t, string c) {
        nombre = n;
        telefono = t;
        correo = c;
    }

    string toString() {
        return nombre + "," + telefono + "," + correo;
    }
};

string archivo = "agenda.txt";

// 🔹 MÉTODOS
void agregar() {
    ofstream file(archivo, ios::app);
    string n, t, c;

    cout << "Nombre: ";
    getline(cin, n);
    cout << "Telefono: ";
    getline(cin, t);
    cout << "Correo: ";
    getline(cin, c);

    Contacto con(n, t, c);
    file << con.toString() << endl;

    file.close();
}

void mostrar() {
    ifstream file(archivo);
    string linea;

    while (getline(file, linea)) {
        cout << linea << endl;
    }

    file.close();
}

void buscar() {
    ifstream file(archivo);
    string linea, nombre;
    bool encontrado = false;

    cout << "Nombre a buscar: ";
    getline(cin, nombre);

    while (getline(file, linea)) {
        if (linea.find(nombre) != string::npos) {
            cout << "Encontrado: " << linea << endl;
            encontrado = true;
        }
    }

    if (!encontrado)
        cout << "No encontrado\n";

    file.close();
}

void eliminar() {
    ifstream file(archivo);
    ofstream temp("temp.txt");
    string linea, nombre;

    cout << "Nombre a eliminar: ";
    getline(cin, nombre);

    while (getline(file, linea)) {
        if (linea.find(nombre) == string::npos) {
            temp << linea << endl;
        }
    }

    file.close();
    temp.close();

    remove("agenda.txt");
    rename("temp.txt", "agenda.txt");

    cout << "Contacto eliminado\n";
}

int main() {
    int op;

    do {
        cout << "\n1. Agregar\n2. Mostrar\n3. Buscar\n4. Eliminar\n5. Salir\n";
        cout << "Opcion: ";
        cin >> op;
        cin.ignore();

        switch (op) {
            case 1: agregar(); break;
            case 2: mostrar(); break;
            case 3: buscar(); break;
            case 4: eliminar(); break;
        }

    } while (op != 5);

    return 0;
}