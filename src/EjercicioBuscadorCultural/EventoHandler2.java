package EjercicioBuscadorCultural;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.TreeSet;

public class EventoHandler2 extends DefaultHandler {
    private StringBuilder valorElemento;
    private String elementoActual;
    TreeSet titulosAbuscar;
    boolean buscar;

    public EventoHandler2(TreeSet titulosAbuscar) {
        this.titulosAbuscar = titulosAbuscar;
        buscar = false;
    }

    @Override
    public void startDocument(){
        System.out.println("---------------Mostrando todos los resultados---------------");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        valorElemento = new StringBuilder();
        elementoActual = qName;

    }

    @Override
    public void characters(char[] ch, int start, int length) {
        valorElemento.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName){

        if (valorElemento != null && elementoActual.equals("titulo_evento")&& titulosAbuscar.contains(valorElemento.toString())){
            buscar = true;
            System.out.println("--------------Evento--------------");
            System.out.println(valorElemento.toString());
        } else if (valorElemento != null && elementoActual.equals("Row")){
            buscar = false;
        }

        if (valorElemento != null && buscar && elementoActual.equals("tipo_evento")){
            System.out.println("Tipo de evento: " + valorElemento.toString());
        }
        if (valorElemento != null && buscar && elementoActual.equals("fecha_inicio")){
            System.out.println("Fecha de inicio: " + valorElemento.toString());
        }
        if (valorElemento != null && buscar && elementoActual.equals("municipio")){
            System.out.println("Municipio: " + valorElemento.toString());
        }
        if (valorElemento != null && buscar && elementoActual.equals("direccion")){
            System.out.println("Direccion: " + valorElemento.toString());
            buscar = false;
        }
        if (elementoActual.equals("direccion") && valorElemento == null){
            buscar = false;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
