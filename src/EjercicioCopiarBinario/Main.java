package EjercicioCopiarBinario;

import java.io.*;
import java.util.Scanner;

public class Main {
    static void main() {
        Scanner sc = new Scanner(System.in);
        boolean fin = false;

        while (!fin){
            System.out.println("Pasame el nombre del fichero a copiar:");
            String nombreFichero = sc.nextLine();
            try (InputStream ficheroIn = new FileInputStream("./" + nombreFichero);
                 OutputStream ficheroOut = new FileOutputStream("fichero-copia.dat");){
                int datos;
                byte[] buffer = new byte[128];
                while ((datos = ficheroIn.read(buffer)) != -1){
                    ficheroOut.write(buffer, 0, datos);
                }
                fin = true;
            } catch (FileNotFoundException e){
                System.err.println("No se encontro el fichero " + nombreFichero);
            } catch (IOException e){
                e.printStackTrace();
            }
        }


    }
}
