/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.grafos.exeption.VerticeOfSizeExecption;
import controlador.TDA.listas.LinkedList;
import controlador.util.Utilidades;
import java.io.File;

/**
 *
 * @author sebastian
 */
public class GrafoDirigido extends Grafo {
    private Integer nro_vertices;
    private Integer nro_aristas;
    private LinkedList<Adycencia> listaAdycente [];

    public GrafoDirigido(Integer nro_vertices) {
        this.nro_vertices = nro_vertices;
        nro_aristas = 0;
        listaAdycente = new LinkedList[nro_vertices + 1];
        for(int i = 1; i <= nro_vertices; i++){
            listaAdycente[i] = new LinkedList<>();
        }
    }

    public LinkedList<Adycencia>[] getListaAdycente() {
        return listaAdycente;
    }
    
    
    public void setNro_aristas(Integer num) {
        this.nro_aristas = num;
    }
    @Override
    public Integer nro_vertices() {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         return this.nro_vertices;
    }

    @Override
    public Integer nro_aristas() {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         return this.nro_aristas;
    }

    @Override
    public Boolean existe_arista(Integer a, Integer b) throws Exception {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         Boolean band = false;
         if(a.intValue() <= nro_vertices.intValue() &&  b.intValue() <= nro_vertices.intValue()) {
             LinkedList<Adycencia> lista = listaAdycente[a];
             for(int i = 0; i < lista.getSize(); i++) {
                 Adycencia aux = lista.get(i);
                 if(aux.getD().intValue() == b.intValue()){
                     band = true;
                     break;
                 }
             }
         } else {
             throw new VerticeOfSizeExecption();
         }
         return band;
    }

    @Override
    public Double peso_arista(Integer a, Integer b) throws Exception {
        Double peso = Double.NaN;
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if(existe_arista(a, b)) {
            LinkedList<Adycencia> lista = listaAdycente[a];
             for(int i = 0; i < lista.getSize(); i++) {
                 Adycencia aux = lista.get(i);
                 if(aux.getD().intValue() == b.intValue()){
                     peso = aux.getPeso();
                     break;
                 }
             }
        }
        return peso;
    }

    @Override
    public void insertar(Integer a, Integer b)  throws Exception  {
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        insertar(a, b, Double.NaN);
    }

    @Override
    public void insertar(Integer a, Integer b, Double peso) throws Exception {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         if(a.intValue() <= nro_vertices.intValue() &&  b.intValue() <= nro_vertices.intValue()) {
             if(!existe_arista(a, b)) {
                 nro_aristas++;
                 Adycencia aux = new Adycencia();
                 aux.setPeso(peso);
                 aux.setD(b);
                 listaAdycente[a].add(aux);
             }
         } else 
             throw new VerticeOfSizeExecption();
    }

    @Override
    public LinkedList<Adycencia> adycentes(Integer a) {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         return listaAdycente[a];
    }
    
    public static void main(String[] args) {
//        try {
//            
//             GrafoDirigido grafo = new GrafoDirigido(10);
//        grafo.insertar(1, 2);
//        grafo.insertar(2, 3);
//        grafo.insertar(3, 4);
//
//        DibujarGrafo dibujarGrafo = new DibujarGrafo();
//        dibujarGrafo.crearArchivo(grafo);
//
//        // Abrir el navegador predeterminado (Windows)
//            String os = Utilidades.getOS();
//            String dir = Utilidades.getDirProject();
//            System.out.println(Utilidades.getOS());
//            if (os.equalsIgnoreCase("Windows 11")) {
//                Utilidades.abrirNavegadorPredeterminadoWindows(dir +File.separatorChar + "d3"+File.separatorChar+"grafo.html");
//            }
//        } catch (Exception e) {
//        }
//    
    
}

}
