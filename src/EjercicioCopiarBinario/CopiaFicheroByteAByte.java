package EjercicioCopiarBinario;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class CopiaFicheroByteAByte {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean ficheroCopiado = false;
        do {


            System.out.println("Introduce el nombre del fichero que quieres copiar: ");
            String nombreFicheroOriginal = scanner.nextLine();

            String nombreFicheroCopia = nombreFicheroOriginal.replace(".dat", "-copia.dat");

            try (FileInputStream fi = new FileInputStream(nombreFicheroOriginal);
                 FileOutputStream fo = new FileOutputStream(nombreFicheroCopia)) {

                int unByte; // Usamos un int para leer el byte y detectar el fin del archivo

                // Leer del fichero original byte a byte
                while ((unByte = fi.read()) != -1) {
                    // Escribir el byte leído en el fichero de copia
                    fo.write(unByte);
                }

                /* También puede ser así:

                int unByte = fi.read(); // Leer el primer byte antes de entrar al bucle

                while (unByte != -1) { // La condición verifica si no hemos llegado al final del archivo
                    fo.write(unByte); // Escribir el byte en el archivo de destino
                    unByte = fi.read(); // Leer el siguiente byte para la próxima iteración
                }

                */

                System.out.println("Fichero copiado");
                ficheroCopiado = true;

            } catch (IOException e) {
                System.err.println("¡Error al copiar el fichero!");
                e.printStackTrace();
            }
        }
        while (!ficheroCopiado);
        scanner.close();
    }
}