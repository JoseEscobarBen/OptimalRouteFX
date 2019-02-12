package Recorrido;

import Clases.Paradero;
import Clases.Ruta;
import java.util.ArrayList;
import java.util.List;

public class MiRecorrido {
    
    private ArrayList<Ruta> ruta= new ArrayList();
    private long miMatriz[][]; //La matriz que recibirá
    private int n;
    private List<Paradero> paraderos;
    
    public MiRecorrido(List paraderos, long R[][]){
        this.paraderos = paraderos;
        cambiarMiMatriz(R);
    }
    
    public void cambiarMiMatriz(long R[][]){
        n = R.length;
        miMatriz = new long[n][n];
        miMatriz = R;
        vaciarArrayList();
        buscarMiRuta();
    }
    
    public ArrayList<Ruta> getCamino() {
        return ruta;
    }
    
    private void vaciarArrayList(){
        int n = ruta.size();
        for(int i = 0; i < n; i++)
            ruta.remove(0);
    }
    
//    private void buscarMiRuta(){
//        int nivel = 1;
//        int irHacia[] = new int [n + 1];
//        int Respuesta[] = new int [n + 1];
//        long sumaRecorrido = 0, minRecorrido = -1;
//        boolean salir = false;
//        inicializarVector(irHacia);
//        Respuesta[n] = Respuesta[0] = 0;
//        do{
//            generar(nivel, irHacia);
//            sumaRecorrido += miMatriz[irHacia[nivel]][irHacia[nivel - 1]];
//            if(crterio(nivel, irHacia)){
//                if(solucion(nivel)){
//                    sumaRecorrido += miMatriz[0][irHacia[nivel]];
//                    if(sumaRecorrido < minRecorrido || minRecorrido == -1){
//                        minRecorrido = sumaRecorrido;
//                        for(int i = 1; i <= n; i ++)
//                            Respuesta[i] = irHacia[i];
//                    }
//                    sumaRecorrido -= miMatriz[0][irHacia[nivel]];
//                    sumaRecorrido -= miMatriz[irHacia[nivel]][irHacia[nivel-1]];
//                    irHacia[nivel] = 0;
//                    nivel--;
//                    if(masHermanos(nivel, irHacia))
//                        sumaRecorrido -= miMatriz[irHacia[nivel]][irHacia[nivel - 1]];
//                    while (!masHermanos(nivel, irHacia) && !salir) {
//                        irHacia[nivel] = 0;
//                        nivel--;
//                        if(nivel <= 0){
//                            salir = true;
//                            nivel++;
//                        }
//                    }
//                }
//                else{
//                    if(nivel <= 0){
//                        salir = true;
//                        nivel++;
//                    }
//                    nivel++;
//                }
//            }
//            else {
//                if(masHermanos(nivel, irHacia))
//                    sumaRecorrido -= miMatriz[irHacia[nivel]][irHacia[nivel - 1]];
//                while (!masHermanos(nivel, irHacia) && !salir) {
//                    irHacia[nivel] = 0;
//                    nivel--;
//                    if(nivel <= 0){
//                        salir = true;
//                        nivel++;
//                    }
//                }
//            }
//        }while(salir == false);
//        for(int i = 1; i <= n; i ++){
//            Ruta nuevaRuta = new Ruta(); 
//            Paradero auxiliar1 = paraderos.get(Respuesta[i - 1]);
//            Paradero auxiliar2 = paraderos.get(Respuesta[i]);
//            nuevaRuta.setOrigen(auxiliar1.getNombre());
//            nuevaRuta.setFin(auxiliar2.getNombre());
//            nuevaRuta.setDistancia(miMatriz[Respuesta[i]][Respuesta[i - 1]]);
//            nuevaRuta.setEntrega(auxiliar2.getEntrega());
//            ruta.add(nuevaRuta);
//        }
//    }
         
    public void buscarMiRuta(){
        long distancia = 0; //Acumula la distancia entre los dos nodos
        int Respuesta[] = new int [n + 1];
        Respuesta[n] = Respuesta[0] = 0; //El punto de inicio y final parten del Nodo 0
        int cont = 0; //Contabiliza la cantidad de operaciones
        int posActual; //Ubicará el Nodo que estaré
        while(cont != n - 1){
            posActual = Respuesta[cont];    //Ubica el Nodo donde me ubico
            distancia = 0; //Inicializa el valor de la distancia entre Nodo
            for(int j = 1; j < n; j++){
                if(j != posActual){ //Evita usar que el Nodo Actual vaya al mismo Nodo Actual
                    if(miMatriz[j][posActual] < distancia || distancia == 0){ //Uso la distancia más corta que encuentre
                        if(!existeDato(j, cont + 1, Respuesta)){ //Verificio si ese dato nuevo ya lo registre anteriormente
                            Respuesta [cont + 1] = j; //Guarda esa dirección
                            distancia = miMatriz[j][posActual]; //Lo guardo como la nueva distancia mínima que se comparará
                        }
                    }
                }
            }
            cont++;
        }
        //De los datos guardados de posiciones, se guarda la informacion en la clase Paradero
        for(int i = 1; i <= n; i ++){
            Ruta nuevaRuta = new Ruta(); 
            Paradero auxiliar1 = paraderos.get(Respuesta[i - 1]);
            Paradero auxiliar2 = paraderos.get(Respuesta[i]);
            nuevaRuta.setOrigen(auxiliar1.getNombre());
            nuevaRuta.setFin(auxiliar2.getNombre());
            nuevaRuta.setDistancia(miMatriz[Respuesta[i]][Respuesta[i - 1]]);
            nuevaRuta.setEntrega(auxiliar2.getEntrega());
            ruta.add(nuevaRuta);
        }
    }
    
    private boolean solucion(int nivel){
        if(nivel == n - 1)
            return true;
        return false;
    }
    
    private void generar(int nivel, int irHacia[]){
        irHacia[nivel] += 1;
    }
    
    private boolean crterio(int nivel, int irHacia[]){
        if(irHacia[nivel] < n){
            if(!existeDato(irHacia[nivel], nivel, irHacia))
                return true;
        }
        return false;
    }
    
    private boolean masHermanos(int nivel, int irHacia[]){
        return irHacia[nivel] < n - 1;
    }
     
    private void inicializarVector(int irHacia[]){
        for(int i = 0; i < n; i++){
            irHacia[i] = 0;
        }
    }
    
    private boolean existeDato(int j, int k, int irHacia[]){
        for(int i = 1; i < k; i++){
            if(irHacia[i] == j)
                return true;
        }
        return false;
    }
}