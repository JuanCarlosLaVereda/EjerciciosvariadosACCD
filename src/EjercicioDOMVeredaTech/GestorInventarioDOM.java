package EjercicioDOMVeredaTech;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GestorInventarioDOM {
    static Document doc;
    static NodeList nodeList;

    static void main() {
        Scanner sc = new Scanner(System.in);
        boolean fin = false;
        String opcion = "";

        while (!fin) {
            opcion = mostrarMenu();
            if (opcion.equals("1")) {
                System.out.println("Ingrese el nombre del archivo a cargar sin extension: ");
                opcion = sc.nextLine();
                cargarXML(opcion);
            } else if (opcion.equals("2")) {
                boolean valido = false;
                while (!valido) {
                    System.out.println("----------Operaciones de consulta----------");
                    System.out.println("1. Mostrar Inventario");
                    System.out.println("2. Buscar Productos por stock bajo");
                    System.out.println("3. Atras");
                    opcion = sc.nextLine();
                    if (opcion.equals("1")) {
                        mostrarInventario();
                    } else if (opcion.equals("2")) {
                        System.out.println("Ingrese el límte: ");
                        opcion = sc.nextLine();
                        int opcionNumerica;
                        try {
                            opcionNumerica = Integer.parseInt(opcion);
                            buscarProductosStockBajo(opcionNumerica);
                        } catch (NumberFormatException e) {
                            System.out.println("Ingrese un numero valido");
                        }

                    } else if (opcion.equals("3")) {
                        valido = true;
                    } else {
                        System.out.println("Por favor seleccione una opción valida.");
                    }
                }

            } else if (opcion.equals("3")) {
                boolean valido = false;
                System.out.println("----------Operaciones de consulta----------");
                System.out.println("Ingrese la ID del producto: ");
                String id =  sc.nextLine();
                System.out.println("Ingrese el nuevo stock que se desea cambiar: ");
                int stock = sc.nextInt();
                actualizarStock(stock, id);

            } else if (opcion.equals("4")) {
                System.out.println("----------Operaciones de creación----------");
                System.out.println("Ingrese el id del producto: ");
                String id = sc.nextLine();
                System.out.println("Ingrese la categoria del producto: ");
                String categoria = sc.nextLine();
                System.out.println("Ingrese el nombre del producto: ");
                String nombre = sc.nextLine();
                System.out.println("Ingrese la marca del producto: ");
                String marca = sc.nextLine();
                System.out.println("Ingrese la precio del producto: ");
                double precio = sc.nextDouble();
                System.out.println("Ingreese el stock del producto: ");
                int stock = sc.nextInt();

                anadirProducto(id, categoria, nombre, marca, precio, stock);


            } else if (opcion.equals("5")) {
                System.out.println("----------Operaciones de eliminación----------");
                System.out.println("Ingrese el id del producto: ");
                String id = sc.nextLine();
                eliminarProducto(id);


            } else if (opcion.equals("6")) {
                System.out.println("----------Persistencia----------");
                System.out.println("Ingrese el nombre del archivo a guardar");
                String nombreArchivo =  sc.nextLine();
                guardarXML(nombreArchivo);
            } else if (opcion.equals("7")) {
                System.out.println("Adeuuuuuu!!!!!");
                fin = true;
            } else {
                System.out.println("Por favor seleccione una opción valida.");
            }


        }

    }

    public static void guardarXML(String rutaSalida){
        if (doc != null){
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("./" + rutaSalida + ".xml"));

                transformer.transform(source, result);
                System.out.println("Archivo guardado correctamente.");

            } catch (TransformerConfigurationException e) {
                throw new RuntimeException(e);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void eliminarProducto(String idProducto){
        if (doc != null){
            Element productoAeliminar = buscarProductoPorId(idProducto);
            if (productoAeliminar != null) {
                productoAeliminar.getParentNode().removeChild(productoAeliminar);
                System.out.println("Producto eliminado correctamente");
            } else {
                System.out.println("Producto no encontrado.");
            }
        }

    }

    public static void anadirProducto(String id, String categoria, String nombre, String marca, double precio, int stock){
        if (doc != null){
            Element element = doc.getDocumentElement();

            Element producto = doc.createElement("producto");
            producto.setAttribute("id", id);
            Element element_categoria = doc.createElement("categoria");
            Element element_nombre = doc.createElement("nombre");
            Element element_marca = doc.createElement("marca");
            Element element_precio = doc.createElement("precio");
            Element element_stock = doc.createElement("stock");

            element_categoria.setTextContent(categoria);
            element_nombre.setTextContent(nombre);
            element_marca.setTextContent(marca);
            element_precio.setTextContent(precio+"");
            element_stock.setTextContent(stock+"");
            producto.appendChild(element_categoria);
            producto.appendChild(element_nombre);
            producto.appendChild(element_marca);
            producto.appendChild(element_precio);
            producto.appendChild(element_stock);
            element.appendChild(producto);
        }
    }

    public static void actualizarStock(int stock, String id) {
        if (doc != null){
            Element element = buscarProductoPorId(id);
            if (element != null) {
                element.getElementsByTagName("stock").item(0).setTextContent(String.valueOf(stock));
                System.out.println("Stock Actualizado Correctamente");
            } else {
                System.out.println("Elemento no encontrado.");
            }
        }

    }

    public static Element buscarProductoPorId(String id){
        if (doc != null){
            Element elementBuscar = null;

            for (int i = 0; i<nodeList.getLength(); i++){
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    if (element.getAttribute("id").equals(id)){
                        elementBuscar = element;
                    }
                }
            }
            return elementBuscar;
        }
        return null;

    }

    public static void buscarProductosStockBajo(int limiteStock){
        if (doc != null){
            for (int i = 0; i<nodeList.getLength(); i++){

                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    if (Integer.parseInt(element.getElementsByTagName("stock").item(0).getTextContent()) < limiteStock){
                        mostrarElemento(element);
                    }
                }
            }
        }
    }

    public static String mostrarMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("--------------Menú--------------");
        System.out.println("1. Cargar el Documento");
        System.out.println("2. Operaciones de Consulta");
        System.out.println("3. Operaciones de Modificación");
        System.out.println("4. Operaciones de Creación");
        System.out.println("5. Operaciones de Eliminación");
        System.out.println("6. Persistencia");
        System.out.println("7. Salir");
        return sc.next();

    }

    public static void mostrarInventario(){
        if (doc != null){
            System.out.println("---------------------------------------------------");
            System.out.println("Elemento raiz: "  + doc.getDocumentElement().getNodeName());
            System.out.println("--------------------------------");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node =  nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    mostrarElemento(element);
                }
            }
        }
    }

    public static void mostrarElemento(Element element){
        System.out.println("ID: " + element.getAttribute("id"));
        System.out.println("Categoria: " + element.getElementsByTagName("categoria").item(0).getTextContent());
        System.out.println("Nombre: " + element.getElementsByTagName("nombre").item(0).getTextContent());
        System.out.println("Marca: " + element.getElementsByTagName("marca").item(0).getTextContent());
        System.out.println("Precio: " + element.getElementsByTagName("precio").item(0).getTextContent());
        System.out.println("Stock: " + element.getElementsByTagName("stock").item(0).getTextContent());
        System.out.println("------------------------------------------");
    }

    public static  void cargarXML(String ruta) {
        File file = new File("./" + ruta + ".xml");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            doc = dbBuilder.parse(file);
            nodeList = doc.getElementsByTagName("producto");

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
}
