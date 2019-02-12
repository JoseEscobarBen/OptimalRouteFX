
package Clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Paradero implements Comparable<Paradero>{
    private SimpleStringProperty Paradero = new SimpleStringProperty();
    private SimpleStringProperty Entrega = new SimpleStringProperty();

    public Paradero(){}
    
    public String getNombre() {
        return Paradero.get();
    }

    public void setNombre(String nombre) {
        ParaderoProperty().set(nombre);
    }
    
    public StringProperty ParaderoProperty() { 
         if (Paradero == null) Paradero = new SimpleStringProperty(this, "Paradero");
         return Paradero; 
    }

    public String getEntrega() {
        return Entrega.get();
    }

    public void setEntrega(String entrega) {
        EntregaProperty().set(entrega);
    }
    
    public StringProperty EntregaProperty() { 
         if (Entrega == null) Entrega = new SimpleStringProperty(this, "Entrega");
         return Entrega; 
    } 
    
    @Override
    public int compareTo(Paradero objeto){
        return this.getNombre().compareTo(objeto.getNombre());
    }
    
    public String toString(){
        return " "+this.getNombre()+" "+this.getEntrega();
    }
}
