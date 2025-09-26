package EjercicioCopiarBinario;

import java.io.*;
import java.util.Scanner;

public class EjercicioImagenes {
    static void main() {
        boolean fin =  false;
        Scanner sc = new Scanner(System.in);
        while (!fin) {
            System.out.println("Dame el nombre de la imágen a copiar: ");
            String nombre = sc.nextLine();

            try (InputStream ficheroIn = new FileInputStream(nombre);) {
                byte[] buffer = new byte[4];
                int datos =  ficheroIn.read(buffer);
                int cabecera = Byte.toUnsignedInt(buffer[0]);

                if (cabecera == 137){
                    copiarImagen("png", nombre, buffer, datos, ficheroIn);
                } else if (cabecera == 255){
                    copiarImagen("jpeg", nombre, buffer, datos, ficheroIn);
                }


            } catch (FileNotFoundException e){
                System.out.println("Fichero no encontrado");
                e.printStackTrace();
            } catch (IOException e){
                System.out.println("Ha ocurrido algún error");
            }
        }
    }

    public static void copiarImagen (String extension, String nombre, byte[] buffer, int datos, InputStream ficheroIn) throws FileNotFoundException, IOException{
        try (OutputStream ficheroOut = new FileOutputStream(nombre+"-copia." +  extension);){
            ficheroOut.write(buffer, 0, datos);
            while ((datos = ficheroIn.read(buffer)) != -1){
                ficheroOut.write(buffer, 0, datos);
            }
        }
        System.out.println("Imagen copiada con éxito.");
    }
}
