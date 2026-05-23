// Librerías necesarias
#include <iostream>
#include <fstream>
#include "Cuenta.h"

using namespace std;

int main() {

    // Crear objeto cuenta
    Cuenta cuenta;

    // Variables
    string usuario;
    double saldoInicial;
    double monto;
    int opcion;

    // Archivo para guardar datos
    ofstream archivo("cajero.txt");

    // Ingreso de datos
    cout << "Ingrese el nombre del usuario: ";
    getline(cin, usuario);

    cout << "Ingrese el saldo inicial: ";
    cin >> saldoInicial;

    // Guardar datos en el objeto
    cuenta.setUsuario(usuario);
    cuenta.setSaldo(saldoInicial);

    // Menú principal
    do {

        cout << "\n======= CAJERO AUTOMATICO =======" << endl;
        cout << "1. Consultar saldo" << endl;
        cout << "2. Depositar dinero" << endl;
        cout << "3. Retirar dinero" << endl;
        cout << "4. Salir" << endl;
        cout << "Seleccione una opcion: ";
        cin >> opcion;

        switch(opcion) {

            case 1:

                // Mostrar saldo
                cuenta.consultarSaldo();
                break;

            case 2:

                // Depositar dinero
                cout << "Ingrese el monto a depositar: ";
                cin >> monto;

                cuenta.depositar(monto);

                cout << "Deposito realizado correctamente" << endl;
                break;

            case 3:

                // Retirar dinero
                cout << "Ingrese el monto a retirar: ";
                cin >> monto;

                cuenta.retirar(monto);
                break;

            case 4:

                cout << "Saliendo del sistema..." << endl;
                break;

            default:

                cout << "Opcion invalida" << endl;
        }

    } while(opcion != 4);

    // Guardar datos en archivo
    archivo << "Usuario: " << cuenta.getUsuario() << endl;
    archivo << "Saldo final: $" << cuenta.getSaldo() << endl;

    // Cerrar archivo
    archivo.close();

    return 0;
}