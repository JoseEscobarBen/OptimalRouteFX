
package auxiliarbeta;

//librerias de clases
import Clases.Bus;
import Clases.IndicacionRuta;
import Clases.Paradero;
import Clases.Ruta;
import Listas.Lista;
import Recorrido.MiRecorrido;

//manejo apis google
import MapsGoogle.Route;
import MapsGoogle.StaticMaps;

//manajeador de javaFX
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

//manejo de listas 
import java.util.ArrayList;
import java.util.List;

//manejador de elementos de javaFX
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

//manejo de imagenes
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

//mensajes de alerta
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

//Manejo del autocompletador text field
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class ImplementacionFXMLDocumentController implements Initializable {

    @FXML private JFXButton crearBusBT;
    @FXML private JFXButton buscarBusBT;
    @FXML private JFXButton limpiarBT;
    @FXML private JFXButton ingresarParaderosBT;
    @FXML private JFXButton eliminarBusBT;
    @FXML private JFXButton añadirBT;
    @FXML private JFXButton modificarBT;
    @FXML private JFXButton eliminarBT;
    @FXML private JFXButton guardarBT;
    
    @FXML private TextField NumBusTF;
    @FXML private TextField ParaderoInicialTF;
    @FXML private TextField ParaderoTF;
    @FXML private TextField EntregaTF;
    @FXML private TextField tramoTF;
    
    @FXML private ImageView mapImagen;
    
    @FXML private JFXSlider zoomSlider;
    
    @FXML private TableView<Paradero> Tabla1;
    @FXML private TableColumn paraderoCL;
    @FXML private TableColumn entregaCL;
    ObservableList<Paradero> paraderosTabla;
    
    @FXML private TableView<Bus> Tabla2;
    @FXML private TableColumn busCL;
    @FXML private TableColumn recorridoCL;
    ObservableList<Bus> busesTabla;
    
    @FXML private TableView<Ruta> Tabla3;
    @FXML private TableColumn origenCL;
    @FXML private TableColumn finCL;
    @FXML private TableColumn distanciaCL;
    @FXML private TableColumn entrega2CL;
    ObservableList<Ruta> rutasTabla;
    
    @FXML private TableView<IndicacionRuta> Tabla4;
    @FXML private TableColumn tramoCL;
    ObservableList<IndicacionRuta> indicacionesRuta;
    
    private Lista listaBus = new Lista();
    private Bus bus = new Bus();
    
    private Bus busSeleccionado = new Bus();
    private Paradero paraderoSeleccionado = new Paradero();
    private Ruta rutaSeleccionado = new Ruta();
    private IndicacionRuta indicacionRutaSeleccionado = new IndicacionRuta();
    
    Set<String> posiblesNombres = new HashSet<>();
    private AutoCompletionBinding<String> autoComplete;
    
    public void inicializarTabla1(){
        paraderoCL.setCellValueFactory(
                new PropertyValueFactory<Paradero,String>("Paradero"));
        entregaCL.setCellValueFactory(
                new PropertyValueFactory<Paradero,String>("Entrega"));
        paraderosTabla = FXCollections.observableArrayList();
        Tabla1.setItems(paraderosTabla); 
    }
    
    public void inicializarTabla2(){
        busCL.setCellValueFactory(
                new PropertyValueFactory<Bus,String>("Bus"));
        recorridoCL.setCellValueFactory(
                new PropertyValueFactory<Bus,String>("Recorrido"));
        busesTabla = FXCollections.observableArrayList();
        Tabla2.setItems(busesTabla); 
    }
    
    public void inicializarTabla3(){
        List auxiliar = busSeleccionado.getRuta();
        origenCL.setCellValueFactory(
                new PropertyValueFactory<Ruta,String>("Origen"));
        finCL.setCellValueFactory(
                new PropertyValueFactory<Ruta,String>("Fin"));
        distanciaCL.setCellValueFactory(
                new PropertyValueFactory<Ruta,String>("Distancia"));
        entrega2CL.setCellValueFactory(
                new PropertyValueFactory<Ruta,String>("Entrega"));
        rutasTabla = FXCollections.observableArrayList(auxiliar);
        Tabla3.setItems(rutasTabla);
    }
    
    public void inicializarTabla4(String inicio, String fin) throws MalformedURLException, UnsupportedEncodingException{
        Route ObjRout=new Route();
        String[][] resultado=ObjRout.getRoute(inicio, fin, null, Boolean.TRUE, Route.mode.driving, Route.avoids.nothing);
        List<IndicacionRuta> auxiliar = new ArrayList<IndicacionRuta>();
        for(int i=0; i<resultado.length-1;i++){
            String todo = String.valueOf(resultado[i][1])+" "+String.valueOf(resultado[i][2]);
            auxiliar.add(new IndicacionRuta(todo));
        }
        tramoCL.setCellValueFactory(
                new PropertyValueFactory<IndicacionRuta,String>("Tramo"));
        indicacionesRuta = FXCollections.observableArrayList(auxiliar);
        Tabla4.setItems(indicacionesRuta);
    }
    
    public void iniciarMenu(){
        ingresarParaderosBT.setDisable(true);
//        eliminarBusBT.setDisable(true);
        añadirBT.setDisable(true);
        modificarBT.setDisable(true);
        eliminarBT.setDisable(true);
        guardarBT.setDisable(true);
        limpiarBT.setDisable(true);
        ParaderoInicialTF.setDisable(true);
        ParaderoTF.setDisable(true);
        EntregaTF.setDisable(true);
        tramoTF.setDisable(true);
    }
    
    private void crearMapa(int escala) throws MalformedURLException, UnsupportedEncodingException, FileNotFoundException{
        if(escala == -1)
            escala = 14;
        StaticMaps ObjStaticMaps=new StaticMaps();
        if(!ParaderoInicialTF.getText().isEmpty()){
            Image mapaGoogle = ObjStaticMaps.getStaticMap(
                    ParaderoInicialTF.getText(),escala,400,400,1,
                        StaticMaps.Format.png,StaticMaps.Maptype.roadmap);
            if(mapaGoogle!=null){
                mapImagen.setImage(mapaGoogle);
            }
        }
    }
    
    private void llenadoMatriz() throws MalformedURLException, UnsupportedEncodingException{
        Paradero paradero0 = new Paradero();
        paradero0.setNombre(ParaderoInicialTF.getText());
        paradero0.setEntrega("fin");
        bus.getParadero().add(0,paradero0);
        int tam = bus.getParadero().size();
        long[][] matriz = new long[tam][tam]; 
        for(int i=0;i<tam;i++){
            for(int j=0;j<tam;j++){
                matriz[i][j] = distancias(i,j);
            }
        }
        MiRecorrido auxiliar = new MiRecorrido(bus.getParadero(),matriz);
        bus.setRuta(auxiliar.getCamino());
        long distancia=0;
        for(int i=0;i<bus.getRuta().size();i++){
            Ruta ruta = (Ruta) bus.getRuta().get(i);
            distancia += ruta.getDistancia();
        }
        bus.setRecorrido(distancia);
    }
    
    private long distancias(int i, int j) throws MalformedURLException, UnsupportedEncodingException{
        Route ObjRout=new Route();
        List<Paradero> auxiliar = bus.getParadero();
        List<Paradero> auxiliar2 = bus.getParadero();
        Paradero inicio = (Paradero) auxiliar.get(i);
        Paradero fin = (Paradero) auxiliar2.get(j);
        String[][] resultado = ObjRout.getRoute(inicio.getNombre(), fin.getNombre(), null, Boolean.TRUE, Route.mode.driving, Route.avoids.nothing);
        ArrayList<Integer> recorridoTotal= ObjRout.getTotalDistance();
        long distanciaAux=0;
        for(Integer item:recorridoTotal){
            distanciaAux+=item;
        }
        return distanciaAux;
    }
    
    @FXML
    void handleActualizarAction(ActionEvent event) throws Exception{
        double escala = zoomSlider.getValue();
        crearMapa((int)escala);
    }
    
    @FXML
    void handleCrearBusAction(ActionEvent event) throws Exception{
        if(!casillaVacia(NumBusTF.getText())){
            ingresarParaderosBT.setDisable(false);
            NumBusTF.setDisable(false);
            ParaderoInicialTF.setDisable(false);
            limpiarBT.setDisable(false);
            posiblesNombres.add(NumBusTF.getText());
            if(autoComplete != null)
                autoComplete.dispose();
            autoComplete = TextFields.bindAutoCompletion(NumBusTF,posiblesNombres);
        }
    }
    
    @FXML
    void handleIngresarParaderoAction(ActionEvent event) throws Exception{
        if(!casillaVacia(ParaderoInicialTF.getText())){
            crearMapa(-1);      
            añadirBT.setDisable(false);
            modificarBT.setDisable(false);
            eliminarBT.setDisable(false);
            guardarBT.setDisable(false);
            limpiarBT.setDisable(false);
            ParaderoTF.setDisable(false);
            EntregaTF.setDisable(false);
        }
    }
    
    
    @FXML
    void handleAñadirAction(ActionEvent event) throws Exception{
        if(!casillaVacia(ParaderoTF.getText())&&!casillaVacia(EntregaTF.getText())){
            Paradero paradero = new Paradero();
            paradero.setNombre(ParaderoTF.getText());
            paradero.setEntrega(EntregaTF.getText());
            bus.getParadero().add(paradero);
            Collections.sort(bus.getParadero());
            bus.setParadero(bus.getParadero());
            paraderosTabla.add(paradero);
            ParaderoTF.setText("");
            EntregaTF.setText("");
        }
    }
    
    @FXML
    void handleModificarAction(ActionEvent event) throws Exception{
        Paradero paradero = new Paradero();
        bus.getParadero().remove(posicionTabla1Seleccionada);
        paradero.setNombre(ParaderoTF.getText());
        paradero.setEntrega(EntregaTF.getText());
        bus.getParadero().add(paradero);
        Collections.sort(bus.getParadero());
        paraderosTabla.set(posicionTabla1Seleccionada, paradero);
        ParaderoTF.setText("");
        EntregaTF.setText("");
    }
    
    @FXML
    void handleEliminarAction(ActionEvent event) throws Exception{
        ParaderoTF.setText("");
        EntregaTF.setText("");
        bus.getParadero().remove(posicionTabla1Seleccionada-1);
        Collections.sort(bus.getParadero());
        bus.setParadero(bus.getParadero());
        paraderosTabla.remove(posicionTabla1Seleccionada);
    }

    
    @FXML
    void handleGuardarAction(ActionEvent event) throws Exception{
        if(ventanaConfirmar()){
            Bus auxiliar = new Bus();
            auxiliar.setBus(NumBusTF.getText());
            auxiliar.setParaderoInicial(ParaderoInicialTF.getText());
            auxiliar.setParadero(bus.getParadero());
            listaBus.insertarAlFinal(bus);
            llenadoMatriz();
            auxiliar.setRecorrido(bus.getRecorrido());
            busesTabla.add(auxiliar);
            List auxiliar2 = new ArrayList<>();
            List auxiliar3 = new ArrayList<>();
            bus = new Bus();
            bus.setParadero(auxiliar2);
            bus.setRuta(auxiliar3);
            NumBusTF.setText("");
            ParaderoInicialTF.setText("");
            ParaderoTF.setText("");
            EntregaTF.setText("");
            tramoTF.setText("");
            Tabla1.setItems(null);
            Tabla3.setItems(null);
            Tabla4.setItems(null);
            iniciarMenu();
            inicializarTabla1();
        }
    }
    
    @FXML
    void handleLimpiarAction(ActionEvent event) {
            NumBusTF.setText("");
            ParaderoInicialTF.setText("");
            ParaderoTF.setText("");
            EntregaTF.setText("");
            tramoTF.setText("");
            Tabla1.setItems(null);
            iniciarMenu();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        autoComplete = TextFields.bindAutoCompletion(NumBusTF,posiblesNombres);
        
        this.iniciarMenu();
        this.inicializarTabla1();
        this.inicializarTabla2();
        final ObservableList<Paradero> tabla1Sel =
                Tabla1.getSelectionModel().getSelectedItems();
        final ObservableList<Bus> tabla2Sel =
                Tabla2.getSelectionModel().getSelectedItems();
        final ObservableList<Ruta> tabla3Sel =
                Tabla3.getSelectionModel().getSelectedItems();
        final ObservableList<IndicacionRuta> tabla4Sel =
                Tabla4.getSelectionModel().getSelectedItems();
        tabla1Sel.addListener(selectorTabla1);
        tabla2Sel.addListener(selectorTabla2);
        tabla3Sel.addListener(selectorTabla3);
        tabla4Sel.addListener(selectorTabla4);
    }
    
    //Manejo de tabla 1
    private int posicionTabla1Seleccionada;
    
    private final ListChangeListener<Paradero> selectorTabla1 =
        new ListChangeListener<Paradero>(){
            @Override
            public void onChanged(
                ListChangeListener.Change<? extends Paradero>c){
                    ponerTabla1Seleccionada();
            }
        };
    
    public Paradero posicionTabla1Seleccionada(){
        if(Tabla1 != null){
            List<Paradero> tabla = Tabla1.getSelectionModel().getSelectedItems();
            if(tabla.size() == 1 ){
                final Paradero paraderoSeleccionado = tabla.get(0);
                return paraderoSeleccionado;
            }
        }
        return null;
    }
    
    private void ponerTabla1Seleccionada(){
        final Paradero paraderoSelec = posicionTabla1Seleccionada();
        posicionTabla1Seleccionada = paraderosTabla.indexOf(paraderoSelec);
        if(paraderoSelec != null){
            ParaderoTF.setText(paraderoSelec.getNombre());
            EntregaTF.setText(paraderoSelec.getEntrega());
            paraderoSeleccionado.setNombre(paraderoSelec.getNombre());
            paraderoSeleccionado.setEntrega(paraderoSelec.getEntrega());
        }
    }
    
    //manejo de tabla 2
    private int posicionTabla2Seleccionada;
    
    private final ListChangeListener<Bus> selectorTabla2 =
        new ListChangeListener<Bus>(){
            @Override
            public void onChanged(
                ListChangeListener.Change<? extends Bus>c){
                    ponerTabla2Seleccionada();
            }
        };
    
    public Bus posicionTabla2Seleccionada(){
        if(Tabla2 != null){
            List<Bus> tabla = Tabla2.getSelectionModel().getSelectedItems();
            if(tabla.size() == 1 ){
                final Bus busSeleccionado = tabla.get(0);
                return busSeleccionado;
            }
        }
        return null;
    }
    
    private void ponerTabla2Seleccionada(){
        final Bus busSelec = posicionTabla2Seleccionada();
        posicionTabla2Seleccionada = busesTabla.indexOf(busSelec);
        if(busSelec != null){
            busSeleccionado = (Bus)listaBus.buscar(posicionTabla2Seleccionada);
            inicializarTabla3();
        }
    }
    
    //manejo de tabla 3
    private int posicionTabla3Seleccionada;
    
    private final ListChangeListener<Ruta> selectorTabla3 =
        new ListChangeListener<Ruta>(){
            @Override
            public void onChanged(
                ListChangeListener.Change<? extends Ruta>c){
                try {
                    ponerTabla3Seleccionada();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ImplementacionFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ImplementacionFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    
    public Ruta posicionTabla3Seleccionada(){
        if(Tabla3 != null){
            List<Ruta> tabla = Tabla3.getSelectionModel().getSelectedItems();
            if(tabla.size() == 1 ){
                final Ruta rutaSeleccionado = tabla.get(0);
                return rutaSeleccionado;
            }
        }
        return null;
    }
    
    private void ponerTabla3Seleccionada() throws MalformedURLException, UnsupportedEncodingException{
        final Ruta rutaSelec = posicionTabla3Seleccionada();
        posicionTabla3Seleccionada = rutasTabla.indexOf(rutaSelec);
        if(rutaSelec != null){
            List rutas = busSeleccionado.getRuta();
            rutaSeleccionado = (Ruta)rutas.get(posicionTabla3Seleccionada);
            inicializarTabla4(rutaSeleccionado.getOrigen(),rutaSeleccionado.getFin());
        }
    }
    
    //manejo de tabla 4
    private int posicionTabla4Seleccionada;
    
    private final ListChangeListener<IndicacionRuta> selectorTabla4 =
        new ListChangeListener<IndicacionRuta>(){
            @Override
            public void onChanged(
                ListChangeListener.Change<? extends IndicacionRuta>c){
                    ponerTabla4Seleccionada();
            }
        };
    
    public IndicacionRuta posicionTabla4Seleccionada(){
        if(Tabla1 != null){
            List<IndicacionRuta> tabla = Tabla4.getSelectionModel().getSelectedItems();
            if(tabla.size() == 1 ){
                final IndicacionRuta indicacionRutaSeleccionado = tabla.get(0);
                return indicacionRutaSeleccionado;
            }
        }
        return null;
    }
    
    private void ponerTabla4Seleccionada(){
        final IndicacionRuta indicacionRutaSelec = posicionTabla4Seleccionada();
        posicionTabla4Seleccionada = paraderosTabla.indexOf(indicacionRutaSelec);
        if(indicacionRutaSelec != null){
            tramoTF.setText(indicacionRutaSelec.getTramo());
        }
    }
    
    public boolean mensajeEliminar(){
        Alert mensajeAlerta = new Alert(AlertType.CONFIRMATION);
        mensajeAlerta.setTitle("Eliminar");
        mensajeAlerta.setHeaderText(null);
        mensajeAlerta.initStyle(StageStyle.UTILITY);
        mensajeAlerta.setContentText("¿Realmente desea eliminar la información?");
        Optional<ButtonType> respuesta = mensajeAlerta.showAndWait();
        if(respuesta.get() == ButtonType.OK)
            return true;
        return false;
    }
    
    public void ventanaMensaje(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("¡ERROR!");
        alert.setHeaderText("Dato ingresado incorrecto.");
        alert.setContentText("No se pudo registrar la información");
        alert.showAndWait();
    }
    
    public boolean ventanaConfirmar(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText("Guardar datos");
        alert.setContentText("¿Todos los datos han sido ingresados correctamente?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }
    
    private static boolean esEntero(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public boolean esDecimalFloat(String cad) {
        try {
            Float.parseFloat(cad);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    public boolean esDecimalDouble(String cad) {
        try {
            Double.parseDouble(cad);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean casillaVacia(String cadena) {
        if (cadena == null || cadena.trim().length() == 0) {
            ventanaMensaje();
            return true;
        } else {
            return false;
        }
    }
}
