package Listas;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Lista<Tipo extends Comparable<Tipo>> implements Iterable<Tipo> {
    private int N;          // tamaño de la lista
    private Nodo inicio;     // inicio de la lista
    private Nodo fin;     // final de la lista
    
    private class Nodo {
        private Tipo item;
        private Nodo prox;
    }

    public Lista() {
        inicio = null;
        fin = null;
        N = 0;
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    public int tamanno() {
        return N;
    }

    public void insertarAlInicio(Tipo item) {
        Nodo inicioAnterior = inicio;
        //Nodo finAnterior = fin;
        if(N==0){
            inicio = fin = new Nodo();
        }
        else{
            inicio = new Nodo();
        }
            
        inicio.item = item;
        inicio.prox = inicioAnterior;
        N++;
    }

    public Tipo eliminarAlInicio() {
        if (estaVacia()) throw new NoSuchElementException("Lista Vacía");
        Tipo item = inicio.item;        // guarda item a devolver
        if(N==1){
            fin = null;
        }
        inicio = inicio.prox;           // borra el primer nodo
        N--;
        return item;                    // devuelve el item guardado
    }
    public void insertarAlFinal(Tipo item){
        Nodo finAnterior = fin;
        if(N==0){
            fin=inicio= new Nodo();
        }
        else{
            fin= new Nodo();
            finAnterior.prox = fin;
        }
        fin.item = item;
        fin.prox = null;
        
        N++;
    }
    public Tipo eliminarAlFinal() {
        if (estaVacia()) throw new NoSuchElementException("Lista Vacía");
        Tipo borrado;
        if(N==1){
            borrado=inicio.item;
            inicio=fin=null;
        }
        else{
            Nodo penultimo=null;
            for(Nodo x=inicio;x!=fin;x=x.prox){
                penultimo=x;
            }
            borrado = fin.item;
            penultimo.prox=null;
            fin=penultimo;
        }
        N--;
        return borrado;
    }
    //Devuelve el primer item pero no lo elimina
    public Tipo primero() {
        if (estaVacia()) throw new NoSuchElementException("Lista vacía");
        return inicio.item;
    }
    
    public Tipo ultimo() {
        if (estaVacia()) throw new NoSuchElementException("Lista vacía");
        return fin.item;
    }
    
    public Tipo buscar(int pos){
        if (estaVacia()){
            throw new NoSuchElementException("Lista vacía");
        }
        else{
            int i = 0; 
            Nodo nodo = inicio;
            while(i<N){
                if(i==pos)
                    return nodo.item;
                nodo = nodo.prox;
                i++;
            }
            return nodo.item;
        }
    }
    
    public boolean existe(Tipo objeto){
        if(estaVacia())throw new NoSuchElementException("Lista vacía");
        else{
            Nodo nodo = inicio;
            Nodo sgte = inicio.prox;
            if(objeto.compareTo(nodo.item)==0)
                return true;
            else{
                int i = 0;
                while(i<N){
                    if(objeto.compareTo(nodo.item)==0){
                        return true;
                    }
                    nodo = nodo.prox;
                    i++;
                }	
            }
        }
        return false;
    }
    
    public Tipo buscarValor(Tipo objeto){
        if(estaVacia())throw new NoSuchElementException("Lista vacía");
        else{
            Nodo nodo = inicio;
            if(objeto.compareTo(inicio.item)==0)
                return inicio.item;
            else{
                int i=0;
                while(i<N){
                    if(objeto.compareTo(nodo.item)==0){
                        return nodo.item;
                    }
                    i++;
                    nodo = nodo.prox;
                }	
            }
            return null;
        }
    }
    
    public void eliminarObjeto(Tipo objeto){
        if (estaVacia())throw new NoSuchElementException("Lista vacía");
        else{
            Nodo nodo = inicio;
            Nodo sgte = inicio.prox;
            if(objeto.compareTo(nodo.item)==0)
                inicio = sgte;
            else{
                boolean encontrado = false;
                while(sgte != null && !encontrado){
                    if(objeto.compareTo(sgte.item)==0){
                        nodo.prox = sgte.prox;
                        encontrado = true;
                    }
                    nodo = nodo.prox;
                    sgte = sgte.prox;
                }
                if(!encontrado){
                    System.out.println("Elemento no existe\n");
                }	
            }
        }
    }

    //Devuelve una representación de cadena de esta lista
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Tipo item : this)
            s.append(item + " ");
        return s.toString();
    }
       
    public Iterator<Tipo> iterator()  { 
        return new IteradorInicioAlFinal();  
    }

    private class IteradorInicioAlFinal implements Iterator<Tipo> {
        private Nodo actual = inicio;
        
        public boolean hasNext() {
            return actual != null;                     
        }
        
        public void remove(){ throw new UnsupportedOperationException();  }

        public Tipo next() {
            if (!hasNext()) throw new NoSuchElementException();
            Tipo item = actual.item;
            actual = actual.prox; 
            return item;
        }
    }
    
    
    //Solucion a la Pregunta 2
    public void insertar(Tipo item){
        //Nodo finAnterior = fin;
        if(N==0){
            fin=inicio= new Nodo();
            fin.item = item;
            fin.prox = null;
        }
        else if(N==1){
            if(item.compareTo(inicio.item)<0){
                inicio = new Nodo();
                inicio.item = item;
                inicio.prox = fin;
            }
            else{
                fin = new Nodo();
                fin.item = item;
                fin.prox = null;
                inicio.prox = fin;
            }
        }
        else{
            if(item.compareTo(inicio.item)<0){
                Nodo inicioAnterior = inicio;
                inicio = new Nodo();
                inicio.item = item;
                inicio.prox = inicioAnterior;
            }
            else if(item.compareTo(fin.item)>=0){
                Nodo finAnterior = fin;
                fin = new Nodo();
                fin.item = item;
                fin.prox = null;
                finAnterior.prox = fin;
            }
            else
            {
                Nodo previo = null;
//                Nodo siguiente = null;
                Nodo actual = inicio;
                while(item.compareTo(actual.item)>=0){
                    previo = actual;
                    actual = actual.prox;
//                    siguiente = actual.prox;
                }
                Nodo nuevo = new Nodo();
                nuevo.item = item;
                previo.prox = nuevo;
                nuevo.prox = actual;
            }
        }
        N++;
    }

    // comprobar invariantes internas
    public boolean revisar() {
        if (N == 0) {
            if (inicio != null) return false;
            if (fin != null) return false;
        }
        else if (N == 1) {
            if (inicio == null)      return false;
            if (fin == null)      return false;
            if (inicio.prox != null) return false;
            if (fin.prox != null) return false;
        }
        else {
            if (inicio.prox == null) return false;
            if (fin == null) return false;
            if (fin.prox != null) return false;
        }

        // revisar consistencia interna de variable N
        int numeroDeNodos = 0;
        for (Nodo x = inicio; x != null; x = x.prox) {
            numeroDeNodos++;
        }
        if (numeroDeNodos != N) return false;

        return true;
    }
}



