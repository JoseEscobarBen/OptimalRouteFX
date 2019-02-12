
package Clases;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bus implements Comparable<Bus>{

    private SimpleStringProperty Bus = new SimpleStringProperty();
    private SimpleStringProperty Recorrido = new SimpleStringProperty();
    private String paraderoInicial;
    private List<Paradero> paradero = new ArrayList<>();
    private List<Ruta> ruta = new ArrayList<>();

    public Bus(){}
    
    public String getBus() {
        return Bus.get();
    }

    public void setBus(String nombre) {
        BusProperty().set(nombre);
    }
    
    public StringProperty BusProperty() { 
         if (Bus == null) Bus = new SimpleStringProperty(this, "Bus");
         return Bus; 
    }

    public List getParadero() {
        return paradero;
    }

    public void setParadero(List paradero) {
        this.paradero = paradero;
    }

    public String getParaderoInicial() {
        return paraderoInicial;
    }

    public void setParaderoInicial(String paraderoInicial) {
        this.paraderoInicial = paraderoInicial;
    }
    
    public int getRecorrido() {
        return Integer.parseInt(Recorrido.get());
    }

    public void setRecorrido(long distancia) {
        RecorridoProperty().set(""+distancia);
    }
    
    public StringProperty RecorridoProperty() { 
         if (Recorrido == null) Recorrido = new SimpleStringProperty(this, "Recorrido");
         return Recorrido; 
    }

    public List getRuta() {
        return ruta;
    }

    public void setRuta(List ruta) {
        this.ruta = ruta;
    }
    
    public boolean compareNombres(String nombre){
        return Bus.equals(nombre);
    }
    
    @Override
    public int compareTo(Bus bus){
        if(compareNombres(bus.getBus())){
            return 0;
        }
        return -1;
    }
}
