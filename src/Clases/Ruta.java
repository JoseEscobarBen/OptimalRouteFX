
package Clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ruta implements Comparable<Ruta>{
    private SimpleStringProperty Origen = new SimpleStringProperty();
    private SimpleStringProperty Fin = new SimpleStringProperty();
    private SimpleStringProperty Entrega = new SimpleStringProperty();
    private SimpleStringProperty Distancia = new SimpleStringProperty();

    public Ruta(){}
    
    public Ruta(String origen, String fin, String entrega, long distancia){
        setOrigen(origen);
        setFin(fin);
        setEntrega(entrega);
        setDistancia(distancia);
    }
    
    public String getOrigen() {
        return Origen.get();
    }
    
    public void setOrigen(String Inicio) {
        InicioProperty().set(Inicio);
    }

    public StringProperty InicioProperty() { 
         if (Origen == null) Origen = new SimpleStringProperty(this, "Inicio");
         return Origen; 
    }

    public String getFin() {
        return Fin.get();
    }

    public void setFin(String Fin) {
        FinProperty().set(Fin);
    }
    
    public StringProperty FinProperty() { 
         if (Fin == null) Fin = new SimpleStringProperty(this, "Fin");
         return Fin; 
    }

    public String getEntrega() {
        return Entrega.get();
    }

    public void setEntrega(String Entrega) {
        EntregaProperty().set(Entrega);
    }
    
    public StringProperty EntregaProperty() { 
         if (Entrega == null) Entrega = new SimpleStringProperty(this, "Entrega");
         return Entrega; 
    }

    public long getDistancia() {
        return Integer.parseInt(Distancia.get());
    }
    
    public void setDistancia(long Distancia) {
        DistanciaProperty().set(Distancia+"");
    }

    public StringProperty DistanciaProperty() { 
         if (Distancia == null) Distancia = new SimpleStringProperty(this, "Distancia");
         return Distancia; 
    }
    
    public String toString(){
        return this.getOrigen()+" + "+this.getFin();
    }
    
    @Override
    public int compareTo(Ruta objeto){
        return this.getOrigen().compareTo(objeto.getOrigen());
    }
}
