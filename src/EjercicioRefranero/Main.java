package EjercicioRefranero;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    static void main() {
        eliminarDuplicados();
        Scanner sc = new Scanner(System.in);
        System.out.println("Dame una palabra a buscar entre refranes");
        String palabra = sc.next();
        List<String> refranesEncontrados = buscarRefranes(palabra);
        if(refranesEncontrados.isEmpty()){
            System.out.println("No se encontro ningún refran con esa palabra");
        } else {
            System.out.println("Refranes encontrados:");
            for (String refran : refranesEncontrados) {
                System.out.println(refran);
            }
            System.out.println("¿Quieres guardar los refranes en un archivo? Si/No");
            String opcion = sc.next();
            if (opcion.equalsIgnoreCase("si")) {
                String palabraMayuscula = palabra.substring(0, 1).toUpperCase() + palabra.substring(1);
                crearCarpeta(palabraMayuscula);
                guardarRefranes(refranesEncontrados, palabraMayuscula);
            } else {
                System.out.println("De acuerdo hasta luego.");
            }
        }


    }

    public static List<String> buscarRefranes(String palabra) {
        List<String> listaRefranes = new ArrayList<>();
        File refranes = new File("./refranes.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(refranes))){
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.contains(palabra)) {
                    listaRefranes.add(linea);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado");
        } catch (IOException e) {
            System.err.println("Error al buscar el archivo");
        }
        return listaRefranes;
    }

    public static void guardarRefranes(List<String> listaRefranes, String palabra) {
        Scanner sc = new Scanner(System.in);
        LocalDate fechaActual =  LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nombre =  formatter.format(fechaActual);
/*        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd");
        String hoy = formatter2.format(fechaActual);*/
        File carpeta = new File("./" + palabra);
        File[] archivos =  carpeta.listFiles();
        boolean sobreescribir = true;


        if (archivos != null) {
            for (File file : archivos) {
                String nombreCompleto = file.getName();
                int ultimoPunto = nombreCompleto.lastIndexOf('.');
                String nombreArchivo = (ultimoPunto == -1) ? nombreCompleto : nombreCompleto.substring(0, ultimoPunto);
                LocalDate fechaArchivo = LocalDate.parse(nombreArchivo, formatter);


                if (!fechaArchivo.isBefore(fechaActual.minusWeeks(1))) {
                    if (fechaArchivo.isEqual(fechaActual)) {
                        System.out.println("El archivo se ha hecho hoy también, quieres abortar? Si/No");
                        if (sc.next().equalsIgnoreCase("no")) {
                            sobreescribir = true;
                        } else  {
                            sobreescribir = false;
                            System.out.println("Abortado con éxito");
                        }
                    }
                } else {
                    file.delete();
                }

            }
        }
        if (sobreescribir) {
            File file = new File("./" + palabra + File.separatorChar + nombre + ".txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
                for (String refran : listaRefranes){
                    bw.write(refran);
                    bw.newLine();
                }

            } catch (IOException e) {
                System.err.println("Error al buscar el archivo");
            }
            System.out.println("Archivo creado correctamente.");
        }

    }

    public static void eliminarDuplicados(){
        File refranes = new File("./refranes.txt");
        Set<String> listaRefranes = new HashSet<>();
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(refranes))){
            String linea;
            while ((linea = br.readLine()) != null) {
                if (listaRefranes.contains(linea)) {
                    contador++;
                }
                listaRefranes.add(linea);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado");
        } catch (IOException e) {
            System.err.println("Error al buscar el archivo");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(refranes))){
            for(String refran : listaRefranes){
                bw.write(refran);
                bw.newLine();
            }
            System.out.println("Refranes duplicados encontrados: " + contador);
        } catch (IOException e) {
            System.err.println("Error al buscar el archivo");
        }
    }
    public static void crearCarpeta(String palabra){
        File carpeta = new File("./" + File.separatorChar + palabra);
        if (!carpeta.exists()) {
            if (carpeta.mkdir()) {
                System.out.println("Carpeta creada con exito.");
            } else {
                System.out.println("Error al crear la carpeta");
            }
        }
    }
}
