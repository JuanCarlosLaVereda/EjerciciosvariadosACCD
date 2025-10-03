package EjerciciosOffset;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static void main() {
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
                System.out.println(Arrays.toString(buffer));
  /*              byte[] tamanyo = new byte[4];
                byte[] reservado = new byte[12];
                byte[] ancho = new byte[4];
                byte[] alto = new byte[4];
                byte[] reservado2 = new byte[8];
                byte[] tamanyoImagen =  new byte[4];*/
/*                ficheroIn.read(tipo);
                ficheroIn.read(tamanyo, 0, tamanyo.length);
                ficheroIn.read(reservado, 0, reservado.length);
                ficheroIn.read(ancho, 0, ancho.length);
                ficheroIn.read(alto, 0, alto.length);
                ficheroIn.read(reservado2, 0, reservado2.length);
                ficheroIn.read(tamanyoImagen, 0, tamanyoImagen.length);*/

/*                System.out.println(Arrays.toString(ancho));
                System.out.println(Arrays.toString(alto));
                System.out.println(Arrays.toString(tamanyoImagen));*/



            } catch (FileNotFoundException e) {
                System.out.println("Archivo no encontrado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
