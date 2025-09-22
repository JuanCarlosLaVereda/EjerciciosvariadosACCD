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
                while ((datos = ficheroIn.read()) != -1){
                    ficheroOut.write(datos);
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
