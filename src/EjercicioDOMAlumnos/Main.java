package EjercicioDOMAlumnos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Main {
    static void main() {
        File file = new File("./alumnos.xml");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList nodelist = doc.getElementsByTagName("alumno");
            mostrarAlumnos(nodelist, doc);
            cambiarNotas(nodelist);
            anyadirAlumno("Adrián", "7.3", doc, nodelist);
            eliminarAlumnos("Miguel", nodelist);
            mostrarAlumnos(nodelist, doc);

/*            // clases necesarias finalizar la creación del archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("ejercicio3.xml"));*/



        } catch (ParserConfigurationException e) {
            System.err.println("Error de configuración del parser.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de entrada/salida");
            e.printStackTrace();
        } catch (SAXException e) {
            System.err.println("Error al leer el archivo.");
            e.printStackTrace();
        }

    }

    public static void eliminarAlumnos(String nombre, NodeList nodelist) {
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node node = nodelist.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getElementsByTagName("nombre").item(0).getTextContent().equals(nombre)) {
                    node.getParentNode().removeChild(node);
                }
            }
        }
    }

    public static void anyadirAlumno(String nombre, String nota, Document doc, NodeList nodelist) {
        Element element = (Element) doc.getElementsByTagName("alumnos").item(0);
        Element alumno = doc.createElement("alumno");
        Element nombre_alumno = doc.createElement("nombre");
        Element nota_alumno = doc.createElement("nota");
        alumno.setAttribute("id", nodelist.getLength()+1 + "");
        nombre_alumno.setTextContent(nombre);
        nota_alumno.setTextContent(nota);
        alumno.appendChild(nombre_alumno);
        alumno.appendChild(nota_alumno);
        element.appendChild(alumno);
    }

    public static void cambiarNotas(NodeList nodelist) {
        Element element =  (Element) nodelist.item(1);
        element.getElementsByTagName("nota").item(0).setTextContent("9.5");
        element =  (Element) nodelist.item(7);
        element.getElementsByTagName("nota").item(0).setTextContent("10");
        element =  (Element) nodelist.item(18);
        element.getElementsByTagName("nota").item(0).setTextContent("6");
    }

    public static void mostrarAlumnos(NodeList nodelist, Document doc) {
        System.out.println("---------------------------------------------------");
        System.out.println("Elemento raiz: "  + doc.getDocumentElement().getNodeName());
        System.out.println("--------------------------------");

        for (int i = 0; i < nodelist.getLength(); i++) {
            Node node = nodelist.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                System.out.println("Alumno ID: " + element.getAttribute("id"));
                System.out.println("Nombre: " + element.getElementsByTagName("nombre").item(0).getTextContent());
                System.out.println("Nota: " +  element.getElementsByTagName("nota").item(0).getTextContent());
                System.out.println("--------------------------------");
            }
        }
    }
}
