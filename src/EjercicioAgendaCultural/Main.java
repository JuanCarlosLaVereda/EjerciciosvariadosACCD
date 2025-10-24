package EjercicioAgendaCultural;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static void main() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Dame el tipo de evento a buscar: ");
        String tipo = sc.nextLine();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            EventoHandler eventoHandler = new EventoHandler(tipo);
            File file = new File("Agenda2024.xml");
            parser.parse(file, eventoHandler);

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
}
