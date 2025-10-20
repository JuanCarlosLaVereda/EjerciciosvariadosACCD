package EjercicioBiblioteca;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {
        List<Libro> libros = new ArrayList<>();

        libros.add(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", 1605, null));
        libros.add(new Libro("Lazarillo de Tormes", "Anónimo", 1554, null));
        libros.add(new Libro("Relato de un naufrago", "Gabriel García Márquez", 1955, null));

        guardarBiblioteca(libros);
        cargarBiblioteca("./biblioteca.dat");

        System.out.println(libros.toString());



    }

    public static List<Libro> cargarBiblioteca(String nombre){
        List<Libro> libros = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombre))) {
            libros = (List<Libro>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error general de I/O");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Clase no encontrada");
            e.printStackTrace();
        }
        return libros;

    }

    public static void guardarBiblioteca(List<Libro> libros) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("biblioteca.dat"))) {
            oos.writeObject(libros);
            System.out.println("Biblioteca guardada con exito.");

        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error general de I/O");
            e.printStackTrace();
        }
    }
}
