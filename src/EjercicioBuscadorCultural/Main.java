package EjercicioBuscadorCultural;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    static void main() {
        Scanner sc = new Scanner(System.in);
        boolean fin = false;
        String opcion = "";
        TreeSet<String> tiposEventos =  new TreeSet<>();
        boolean municipios = false;
        while (!fin) {
            System.out.println("Quieres buscar por evento o por localidad? (evento/localidad)");
            opcion = sc.nextLine();
            if (opcion.equalsIgnoreCase("evento")){
                fin = true;
                tiposEventos = buscarEventos("tipos", "");
            } else if (opcion.equalsIgnoreCase("localidad")){
                fin = true;
                municipios = true;
                tiposEventos = buscarEventos("localidad", "");
            } else {
                System.out.println("Opcion invalida");
            }
        }
        fin = false;
        while (!fin) {
            mostrarSet(tiposEventos);
            System.out.println("Escribe el nombre de elemento a mostrar:");
            opcion = sc.nextLine();
            if (tiposEventos.contains(opcion)) {
                fin = true;
                TreeSet<String> titulos = new TreeSet<>();
                if (municipios){
                    titulos = buscarEventos("eventosPorMunicipio", opcion);
                } else {
                    titulos = buscarEventos("eventosPorTipo", opcion);
                }
                mostrarTitulos(titulos);
            }
        }


    }

    public static void mostrarTitulos(TreeSet<String> titulos){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        File f = new File("./Agenda2024.xml");
        EventoHandler2 handler2 = new EventoHandler2(titulos);

        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(f, handler2);

        } catch (ParserConfigurationException e) {
            System.err.println("Error al configurar el SAXParser");
            e.printStackTrace();
        } catch (SAXException e) {
            System.err.println("Error genérico con el SAXParser");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de entrada/salida");
            e.printStackTrace();
        }
    }

    public static void mostrarSet(Set<String> set) {
        int contador = 1;
        for (String s : set) {
            System.out.println(contador + ". " + s);
            contador++;
        }
    }

    public static TreeSet<String> buscarEventos(String tipo, String tipoOmunicipio){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        File f = new File("./Agenda2024.xml");
        EventoHandler eventoHandler = new EventoHandler(tipo, tipoOmunicipio);

        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(f, eventoHandler);

        } catch (ParserConfigurationException e) {
            System.err.println("Error al configurar el SAXParser");
            e.printStackTrace();
        } catch (SAXException e) {
            System.err.println("Error genérico con el SAXParser");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de entrada/salida");
            e.printStackTrace();
        }

        if (!tipoOmunicipio.isEmpty()){
            return eventoHandler.getTitulos();
        }
        return eventoHandler.getTiposEventos();



    }
}
