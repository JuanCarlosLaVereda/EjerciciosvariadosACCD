package EjercicioAgendaCultural;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class EventoHandler extends DefaultHandler {

    private StringBuilder valorElemento;
    private String elementoActual;
    private String buscarEvento;
    private int contador;
    private int contadorTotal;

    public EventoHandler(String buscarEvento) {
        this.buscarEvento = buscarEvento;
        this.contador = 0;
    }

    @Override
    public void startDocument(){
        System.out.println("---Iniciando lectura---");
    }

    @Override
    public void startElement(String uri, String localName, String qname, Attributes attributes){
        elementoActual = qname;
        valorElemento = new StringBuilder();

    }

    @Override
    public void characters(char[] ch, int start, int length){
        if (valorElemento != null){
            valorElemento.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qname){

        if (valorElemento != null && !valorElemento.isEmpty()){
            if (qname.equalsIgnoreCase("tipo_evento")){
                contadorTotal++;
                if (valorElemento.toString().equalsIgnoreCase(buscarEvento)){
                    contador++;
                }
            }
        }
        elementoActual = null;
    }

    @Override
    public void endDocument(){
        System.out.println("Eventos procesados en total: " + contadorTotal);
        System.out.println("Eventos totales encontrados con el tipo '" + buscarEvento + "': " + contador);
        System.out.println("---Fin de lectura---");


    }
}
