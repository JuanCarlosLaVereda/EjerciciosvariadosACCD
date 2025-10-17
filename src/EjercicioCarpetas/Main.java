package EjercicioCarpetas;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void main() {
        String azules = "./azul.txt";
        String rojos = "./rojo.txt";
        File f_azules = new File(azules);
        File f_rojos = new File(rojos);
        String participantes = "participantes.txt";
        int cantidadRojos = 0;
        int cantidadAzules = 0;

        List<String> listaParticipantes = new ArrayList<>();
        cantidadRojos = anyadirParticipantes(f_azules, listaParticipantes);
        cantidadAzules = anyadirParticipantes(f_rojos, listaParticipantes);
        Collections.sort(listaParticipantes);
        crearArchivo(listaParticipantes,  participantes);

        System.out.println("Participantes totales del equipo rojo: " + cantidadRojos);
        System.out.println("Participantes totales del equipo azul: " + cantidadAzules);
        System.out.println("Total participantes: " + listaParticipantes.size());

        //***************************AMPLIACION***************************//
        Scanner sc = new Scanner(System.in);
        boolean fin = false;
        String ganador = "";
        System.out.println("Quien ha ganado?");
        while (!fin) {
            ganador = sc.nextLine();
            if (ganador.equalsIgnoreCase("rojo" ) || ganador.equalsIgnoreCase("azul")){
                fin = true;
            } else {
                System.out.println("Introduzca al ganador correctamente.");
            }
        }
        File newDir = new File(f_azules.getParent() + File.separatorChar + "Medallas");
        if (!newDir.exists()) {
            boolean created = newDir.mkdir();
            if (created){
                System.out.println("Carpeta creada correctamente.");
            } else {
                System.err.println("Ha ocurrido un error al crear la carpeta");
            }
        }

        listaParticipantes.clear();
        if (ganador.equalsIgnoreCase("rojo")) {
            anyadirParticipantes(f_rojos, listaParticipantes);
        } else {
            anyadirParticipantes(f_azules, listaParticipantes);
        }
        Collections.sort(listaParticipantes);
        String abecedario = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        File f_medallas =  new File(newDir.getPath() + File.separatorChar + "medallero.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f_medallas))){
            bw.write("" + ganador.toUpperCase());
            bw.newLine();
            bw.newLine();
            for (char letra : abecedario.toCharArray()){
                int medallas = 0;
                for (String participante : listaParticipantes) {
                    if (participante.charAt(0)==letra) {
                        medallas++;
                    }
                }
                bw.write(letra + " " + medallas);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo.");
        }

        System.out.println("Medallas añadidas correctamente.");




    }

    public static int anyadirParticipantes(File equipo, List<String> listaParticipantes) {
        int cantidadParticipantes = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(equipo))){
            String participante;
            while ((participante = br.readLine()) != null) {
                listaParticipantes.add(participante.trim());
                cantidadParticipantes++;
            }
            System.out.println("Participantes añadidos correctamente.");
        } catch (FileNotFoundException e) {
            System.err.println("No se encontro el archivo.");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo.");
        }
        return cantidadParticipantes;
    }

    public static void crearArchivo(List<String> listaParticipantes, String participantes) {
        File f_participantes = new File(participantes);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f_participantes))){
            for (String participante : listaParticipantes) {
                bw.write(participante);
                bw.newLine();
            }
            System.out.println("Archivo participantes creado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo.");
        }
    }
}
