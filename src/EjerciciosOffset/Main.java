package EjerciciosOffset;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean fin = false;
        Scanner sc = new Scanner(System.in);
        while (!fin) {
            System.out.println("Dame el nombre de la imágen a copiar: ");
            String nombre = sc.nextLine();
            try (InputStream ficheroIn = new FileInputStream(nombre)) {
                byte[] buffer = new byte[18];
                ficheroIn.read(buffer, 0, 2);
                ficheroIn.read(buffer, 2, 4);
                int size = (buffer[2] & 0x0FF) | ((buffer[3] & 0x0FF) << 8) | ((buffer[4] & 0x0FF) << 16) | ((buffer[5] & 0x0FF) << 24);
                System.out.println("El tamaño de la imagen es: " + size + " bytes");
                ficheroIn.read(buffer, 6, 12);
                ficheroIn.read(buffer, 6, 4);
                int anchura = (buffer[6] & 0x0FF) | ((buffer[7] & 0x0FF) << 8) | ((buffer[8] & 0x0FF) << 16) | ((buffer[9] & 0x0FF) << 24);
                System.out.println("La anchura de la imagen es: " + anchura);
                ficheroIn.read(buffer, 10, 4);
                int altura = (buffer[10] & 0x0FF) | ((buffer[11] & 0x0FF) << 8) | ((buffer[12] & 0x0FF) << 16) | ((buffer[13] & 0x0FF) << 24);
                System.out.println("La altura de la imagen es: " + altura);
                ficheroIn.read(buffer, 14, 4);
                ficheroIn.read(buffer, 14, 4);
                int compresion = (buffer[14] & 0x0FF) | ((buffer[15] & 0x0FF) << 8) | ((buffer[16] & 0x0FF) << 16) | ((buffer[17] & 0x0FF) << 24);
                switch (compresion) {
                    case 0 -> System.out.println("Sin compresión (BI_RGB)");
                    case 1 -> System.out.println("Compresión RLE 8 bits (BI_RLE8)");
                    case 2 -> System.out.println("Compresión RLE 4 bits (BI_RLE4)");
                    case 3 -> System.out.println("Máscara de bits (BI_BITFIELDS)");
                    case 4 -> System.out.println("JPEG (BI_JPEG)");
                    case 5 -> System.out.println("PNG (BI_PNG)");
                    default -> System.out.println("Tipo de compresión desconocido");
                }
                fin =  true;

            } catch (FileNotFoundException e) {
                System.out.println("Archivo no encontrado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
