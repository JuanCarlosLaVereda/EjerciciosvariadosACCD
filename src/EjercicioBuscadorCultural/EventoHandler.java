package EjercicioBuscadorCultural;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

public class EventoHandler extends DefaultHandler {

    private StringBuilder valorElemento;
    private String elementoActual;
    private TreeSet<String> etiquetas = new TreeSet<>();
    private String buscar;
    private String tipoOmunicipio;
    private TreeSet<String> titulos;
    private String titulo;

    public EventoHandler(String buscar) {
        this.buscar = buscar;
    }

    public EventoHandler(String buscar, String tipoOmunicipio) {
        this.buscar = buscar;
        this.tipoOmunicipio = tipoOmunicipio;
    }

    public TreeSet<String> getTiposEventos() {
        return etiquetas;
    }

    public TreeSet<String> getTitulos() {
        return titulos;
    }

    @Override
    public void startDocument(){
        etiquetas = new TreeSet<>();
        titulos = new TreeSet<>();
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        elementoActual = qName;
        valorElemento = new StringBuilder();


    }

    @Override
    public void characters(char[] ch, int start, int length) {

        if (valorElemento != null) {
            valorElemento.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        if (valorElemento != null && buscar.equals("tipos")) {
            if (elementoActual.equals("tipo_evento")) {
                etiquetas.add(valorElemento.toString());
            }
        } else if (buscar.equals("localidad")) {
            if (elementoActual.equals("municipio")) {
                if (!valorElemento.toString().equals("")) {
                    etiquetas.add(valorElemento.toString());
                }

            }
        } else if (buscar.equals("eventosPorTipo") || buscar.equals("eventosPorMunicipio")) {
            if (elementoActual.equals("titulo_evento")){
                this.titulo = valorElemento.toString();
            }
            if (buscar.equals("eventosPorTipo")){
                if (elementoActual.equals("tipo_evento") && valorElemento.toString().equals(tipoOmunicipio)){
                    titulos.add(titulo);
                }
            }
            if (buscar.equals("eventosPorMunicipio")){
                if (elementoActual.equals("municipio") && valorElemento.toString().equals(tipoOmunicipio)){
                   titulos.add(titulo);
                }
            }
        }

    }

    @Override
    public void endDocument() {
    }


}
