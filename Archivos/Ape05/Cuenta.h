// Librerías necesarias
#include <iostream>
#include <fstream>

using namespace std;

// Clase Cuenta
class Cuenta {

    // Atributos privados
    private:
        string usuario;
        double saldo;

    public:

        // Constructor vacío
        Cuenta() {

            usuario = "";
            saldo = 0;
        }

        // Métodos SET
        void setUsuario(string u) {
            usuario = u;
        }

        void setSaldo(double s) {
            saldo = s;
        }

        // Métodos GET
        string getUsuario() {
            return usuario;
        }

        double getSaldo() {
            return saldo;
        }

        // Método para depositar dinero
        void depositar(double monto) {

            saldo += monto;
        }

        // Método para retirar dinero
        void retirar(double monto) {

            // Verificar si tiene suficiente saldo
            if(monto <= saldo) {

                saldo -= monto;
                cout << "Retiro realizado correctamente" << endl;

            } else {

                cout << "Saldo insuficiente" << endl;
            }
        }

        // Método para mostrar saldo
        void consultarSaldo() {

            cout << "Saldo actual: $" << saldo << endl;
        }
};