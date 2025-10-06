package EjercicioBlocDeNotas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Buenas soy un bloc de notas, dime si quieres sobreescribir o guardar (G/S):");
        String opcion = "";
        String dato = "";
        while (!(opcion = sc.nextLine()).equalsIgnoreCase("g") && !opcion.equalsIgnoreCase("s")) {
            System.out.println("Necesito una respuesta correcta, S para sobreescribir, G para guarda");
        }

        List<String> datos = new ArrayList<>();
        System.out.println("Perfecto, que quieres escribir?");
        while (!(dato = sc.nextLine()).equals("FIN1")) {
            datos.add(dato);
        }

        if (opcion.equalsIgnoreCase("g")) {
            guardarDatos(datos);
        } else {
            sobreescribirDatos(datos);
        }




    }

    public static void guardarDatos(List<String> datos) {

        try (FileReader ficheroIn = new FileReader("notas.txt");
             BufferedReader br = new BufferedReader(ficheroIn);
             FileWriter ficheroOut = new FileWriter("notas.txt");
             BufferedWriter bw = new BufferedWriter(ficheroOut);) {
            String linea;

            while ((linea = br.readLine()) != null){
                bw.write(linea);
                bw.newLine();
            }

            for (String lineaDato : datos){
                bw.write(lineaDato);
                bw.newLine();
            }
            System.out.println("Datos añadidos correctamente.");

        } catch (IOException e){
            System.err.println("Algo ha fallado");
        }
    }

    public static void sobreescribirDatos(List<String> datos) {
        try (FileWriter ficheroOut = new FileWriter("notas.txt");
             BufferedWriter bw = new BufferedWriter(ficheroOut);){
            for (String lineaDato : datos) {
                bw.write(lineaDato);
                bw.newLine();
            }
            System.out.println("Datos añadidos correctamente.");

        } catch (IOException e) {
            System.err.println("Algo ha fallado");
        }

    }
}
