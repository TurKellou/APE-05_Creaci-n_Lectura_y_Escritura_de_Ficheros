import java.io.*;
import java.util.*;
 
// ============================================================
//  CLASE Libro - Representa un libro en la biblioteca
// ============================================================
class Libro {
    private int    codigo;
    private String titulo;
    private String autor;
    private String estado; // "disponible" o "prestado"
 
    // Constructor
    public Libro(int codigo, String titulo, String autor, String estado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor  = autor;
        this.estado = estado;
    }
 
    // Getters
    public int    getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getAutor()  { return autor;  }
    public String getEstado() { return estado; }
 
    // Mostrar info del libro en consola
    public void mostrar() {
        System.out.printf("%-6d %-30s %-22s %s%n",
                          codigo, titulo, autor, estado);
    }
 
    // Convertir a línea CSV para guardar en archivo
    public String aTexto() {
        return codigo + "," + titulo + "," + autor + "," + estado;
    }
 
    // Crear un Libro desde una línea CSV
    public static Libro desdeTexto(String linea) {
        String[] p = linea.split(",", 4);
        return new Libro(Integer.parseInt(p[0]), p[1], p[2], p[3]);
    }
}
 
// ============================================================
//  CLASE Biblioteca - Gestiona la colección de libros
// ============================================================
class Biblioteca {
    private List<Libro>  libros  = new ArrayList<>();
    private final String ARCHIVO = "libros.txt";
 
    // Constructor: carga libros existentes
    public Biblioteca() { cargarDesdeArchivo(); }
 
    // ── Cargar todos los libros desde el archivo ──────────────
    private void cargarDesdeArchivo() {
        libros.clear();
        File f = new File(ARCHIVO);
        if (!f.exists()) return;
 
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.isBlank())
                    libros.add(Libro.desdeTexto(linea));
            }
        } catch (IOException e) {
            System.out.println("[!] Error al leer archivo: " + e.getMessage());
        }
    }
 
    // ── Guardar todos los libros en el archivo ────────────────
    private void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Libro l : libros)
                pw.println(l.aTexto());
        } catch (IOException e) {
            System.out.println("[!] Error al guardar archivo: " + e.getMessage());
        }
    }
 
    // ── Encabezado de tabla ────────────────────────────────────
    private void encabezado() {
        System.out.println("\n" + "-".repeat(70));
        System.out.printf("%-6s %-30s %-22s %s%n", "COD", "TITULO", "AUTOR", "ESTADO");
        System.out.println("-".repeat(70));
    }
 
    // ── 1. Registrar un nuevo libro ───────────────────────────
    public void registrarLibro(Scanner sc) {
        System.out.println("\n=== REGISTRAR LIBRO ===");
        System.out.print("Codigo   : "); int cod = Integer.parseInt(sc.nextLine());
        System.out.print("Titulo   : "); String tit = sc.nextLine();
        System.out.print("Autor    : "); String aut = sc.nextLine();
        System.out.print("Estado (disponible / prestado): "); String est = sc.nextLine();
 
        // Validar estado
        if (!est.equals("disponible") && !est.equals("prestado")) {
            System.out.println("[!] Estado invalido. Se usara 'disponible'.");
            est = "disponible";
        }
 
        libros.add(new Libro(cod, tit, aut, est));
        guardarEnArchivo();
        System.out.println("[OK] Libro registrado correctamente.");
    }
 
    // ── 2. Buscar libro por titulo o autor ────────────────────
    public void buscarLibro(Scanner sc) {
        System.out.println("\n=== BUSCAR LIBRO ===");
        System.out.print("Ingrese titulo o autor: ");
        String termino = sc.nextLine().toLowerCase();
 
        boolean encontrado = false;
        encabezado();
        for (Libro l : libros) {
            if (l.getTitulo().toLowerCase().contains(termino) ||
                l.getAutor().toLowerCase().contains(termino)) {
                l.mostrar();
                encontrado = true;
            }
        }
        if (!encontrado)
            System.out.println("  No se encontraron resultados para: " + termino);
        System.out.println("-".repeat(70));
    }
 
    // ── 3. Mostrar libros disponibles ─────────────────────────
    public void mostrarDisponibles() {
        System.out.println("\n=== LIBROS DISPONIBLES ===");
        encabezado();
        long cnt = libros.stream()
                         .filter(l -> l.getEstado().equals("disponible"))
                         .peek(Libro::mostrar)
                         .count();
        System.out.println("-".repeat(70));
        System.out.println("Total disponibles: " + cnt);
    }
 
    // ── 4. Mostrar libros prestados ───────────────────────────
    public void mostrarPrestados() {
        System.out.println("\n=== LIBROS PRESTADOS ===");
        encabezado();
        long cnt = libros.stream()
                         .filter(l -> l.getEstado().equals("prestado"))
                         .peek(Libro::mostrar)
                         .count();
        System.out.println("-".repeat(70));
        System.out.println("Total prestados: " + cnt);
    }
 
    // ── 5. Mostrar todos los libros ───────────────────────────
    public void mostrarTodos() {
        System.out.println("\n=== TODOS LOS LIBROS ===");
        encabezado();
        libros.forEach(Libro::mostrar);
        System.out.println("-".repeat(70));
        System.out.println("Total: " + libros.size() + " libro(s)");
    }
}
 
// ============================================================
//  CLASE MAIN - Menú principal
// ============================================================
public class LibrosVirtual {
    public static void main(String[] args) {
        Biblioteca bib = new Biblioteca();
        Scanner sc = new Scanner(System.in);
        int opcion;
 
        do {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║     BIBLIOTECA VIRTUAL       ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. Registrar libro          ║");
            System.out.println("║  2. Buscar libro             ║");
            System.out.println("║  3. Ver disponibles          ║");
            System.out.println("║  4. Ver prestados            ║");
            System.out.println("║  5. Ver todos los libros     ║");
            System.out.println("║  0. Salir                    ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Opcion: ");
 
            opcion = Integer.parseInt(sc.nextLine());
 
            switch (opcion) {
                case 1 -> bib.registrarLibro(sc);
                case 2 -> bib.buscarLibro(sc);
                case 3 -> bib.mostrarDisponibles();
                case 4 -> bib.mostrarPrestados();
                case 5 -> bib.mostrarTodos();
                case 0 -> System.out.println("Hasta luego!");
                default-> System.out.println("[!] Opcion invalida.");
            }
        } while (opcion != 0);
 
        sc.close();
    }
}
