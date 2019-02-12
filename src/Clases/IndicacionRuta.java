
package Clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IndicacionRuta implements Comparable<IndicacionRuta>{
    private SimpleStringProperty Tramo = new SimpleStringProperty();
    private SimpleStringProperty Indicaciones = new SimpleStringProperty();

    public IndicacionRuta(){}
    
    public IndicacionRuta(String a) {
        TramoProperty().set(a);
//        IndicacionesProperty().set(b);
    }
    
    public String getTramo() {
        return Tramo.get();
    }

    public void setTramo(String tramo) {
        TramoProperty().set(tramo);
    }
    
    public StringProperty TramoProperty() { 
         if (Tramo == null) Tramo = new SimpleStringProperty(this, "Tramo");
         return Tramo; 
    }

    public String getIndicacion() {
        return Indicaciones.get();
    }

    public void setIndicaciones(String b) {
        IndicacionesProperty().set(b);
    }
    
    private StringProperty IndicacionesProperty() { 
         if (Indicaciones == null) Indicaciones = new SimpleStringProperty(this, "Indicaciones");
         return Indicaciones; 
    }
    
    public String toString(){
        return getIndicacion();
    }
    
    @Override
    public int compareTo(IndicacionRuta auxiliar){
        return 0;
    }
}
