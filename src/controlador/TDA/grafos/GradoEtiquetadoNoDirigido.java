/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.listas.LinkedList;
import controlador.TDA.listas.Queque;
import controlador.TDA.listas.Stack;
import controlador.TDA.listas.exception.LlenoException;
import controlador.TDA.listas.exception.VacioException;
import controlador.util.Utilidades;
import java.io.File;
import java.lang.reflect.Array;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author sebastian
 */
public class GradoEtiquetadoNoDirigido<E> extends GrafoEtiquetadoDirigido<E> {

    public GradoEtiquetadoNoDirigido(Integer nro_vertice, Class<E> clazz) {
        super(nro_vertice, clazz);
        etiqueta = (E[]) Array.newInstance(clazz, nro_vertice + 1);
    }

    @Override
    public void insertar(Integer a, Integer b, Double peso) throws Exception {
        if (a.intValue() <= nro_vertices() && b.intValue() <= nro_vertices()) {
            if (!existe_arista(a, b)) {
                Adycencia auxO = new Adycencia();
                 auxO.setPeso(peso);
                 auxO.setD(b);                 
                 Adycencia auxD = new Adycencia();
                 auxD.setPeso(peso);
                 auxD.setD(a);
                getListaAdycente()[a].add(auxO);
                getListaAdycente()[b].add(auxD);
                //nro_aristas++;
                setNro_aristas(nro_aristas()+1);
            }
        } else {
            throw new Exception();
        }
    }
    
    
//    public String dfs(Integer inicio) throws LlenoException, VacioException, Exception {
//    
//    StringBuilder resultado = new StringBuilder();
//    Queque<Integer> cola = new Queque<>(nro_vertices()); // Creamos una cola para almacenar los vértices a visitar
//    boolean[] visitado = new boolean[nro_vertices() + 1]; // Creamos un arreglo para marcar los vértices visitados
//
//    // Marcamos el vértice de inicio como visitado y lo agregamos a la cola
//    visitado[inicio] = true;
//    cola.queque(inicio);
//
//    // Mapeo de números de vértices a etiquetas
//    HashMap<Integer, E> etiquetas = new HashMap<>();
//    for (int i = 1; i <= nro_vertices(); i++) {
//        etiquetas.put(i, obtenerEtiqueta(i));
//    }
//    long tiempoInicio = System.nanoTime();
//    while (cola.getSize() != 0) {
//        // Sacamos un vértice de la cola
//        Integer actual = cola.dequeque();
//        // Imprimimos su etiqueta sin agregar " >>> " al final
//        resultado.append(etiquetas.get(actual));
//
//        // Obtenemos todos los vértices adyacentes del vértice actual
//        LinkedList<Adycencia> adyacentes = adycentes(actual);
//
//        // Iteramos sobre los vértices adyacentes
//        boolean primerAdyacente = true; // Variable para controlar la impresión de ">>>"
//        for (Adycencia adyacente : adyacentes.toArray()) {
//            Integer vecino = adyacente.getD();
//            if (!visitado[vecino]) { // Si el vecino no ha sido visitado, lo marcamos como visitado y lo agregamos a la cola
//                visitado[vecino] = true;
//                cola.queque(vecino);
//                // Si hay más nodos adyacentes, agregamos " >>> " al resultado
//                if (!primerAdyacente) {
//                    resultado.append(" >>> ");
//                }
//                primerAdyacente = false;
//            }
//        }
//
//        // Si hay más nodos en la cola, agregamos " >>> " al resultado
//        if (cola.getSize() != 0) {
//            resultado.append(" >>> ");
//        }
//    }
//
//    long tiempoFin = System.nanoTime();
//    long tiempoTotal = tiempoFin - tiempoInicio;
//    resultado.append("\nTiempo de la Ejecucion del metodo de profundidad ").append(tiempoTotal).append("ns");
//    JOptionPane.showMessageDialog(null, resultado);
//
//    // Retorna el resultado como una cadena de texto
//    return resultado.toString();
//}
//    
//    public String bfs(Integer inicio) throws LlenoException, VacioException, Exception {
//    
//    StringBuilder resultado = new StringBuilder();
//    Queque<Integer> cola = new Queque<>(nro_vertices()); // Creamos una cola para almacenar los vértices a visitar
//    boolean[] visitado = new boolean[nro_vertices() + 1]; // Creamos un arreglo para marcar los vértices visitados
//
//    // Marcamos el vértice de inicio como visitado y lo agregamos a la cola
//    visitado[inicio] = true;
//    cola.queque(inicio);
//
//    // Mapeo de números de vértices a etiquetas
//    HashMap<Integer, E> etiquetas = new HashMap<>();
//    for (int i = 1; i <= nro_vertices(); i++) {
//        etiquetas.put(i, obtenerEtiqueta(i));
//    }
//    long tiempoInicio = System.nanoTime();
//    while (cola.getSize() != 0) {
//        // Sacamos un vértice de la cola
//        Integer actual = cola.dequeque();
//        // Imprimimos su etiqueta sin agregar " >>> " al final
//        resultado.append(etiquetas.get(actual));
//
//        // Obtenemos todos los vértices adyacentes del vértice actual
//        LinkedList<Adycencia> adyacentes = adycentes(actual);
//
//        // Iteramos sobre los vértices adyacentes
//        for (Adycencia adyacente : adyacentes.toArray()) {
//            Integer vecino = adyacente.getD();
//            if (!visitado[vecino]) { // Si el vecino no ha sido visitado, lo marcamos como visitado y lo agregamos a la cola
//                visitado[vecino] = true;
//                cola.queque(vecino);
//            }
//        }
//
//        // Si hay más nodos en la cola, agregamos " >>> " al resultado
//        if (cola.getSize() != 0) {
//            resultado.append(" >>> ");
//        }
//    }
//
//    long tiempoFin = System.nanoTime();
//    long tiempoTotal = tiempoFin - tiempoInicio;
//    resultado.append("\nTiempo de la Ejecucion del metodo de profundidad ").append(tiempoTotal).append("ns");
//    JOptionPane.showMessageDialog(null, resultado);
//
//    // Retorna el resultado como una cadena de texto
//    return resultado.toString();
//}
   
    
    public static void main(String[] args) {
        try {

            GradoEtiquetadoNoDirigido grafo = new GradoEtiquetadoNoDirigido(10,GradoEtiquetadoNoDirigido.class);
            grafo.insertar(1, 2,5.0);
            grafo.insertar(2, 3,3.4);
            grafo.insertar(3, 4,4.3);
//            grafo.adycentesE(grafo);
//            grafo.etiquetarVertice(1, "sexo");
//            grafo.insertarAristaE(1, 2, 5.0);
            DibujarGrafo dibujarGrafo = new DibujarGrafo();
            dibujarGrafo.crearArchivo(grafo);

            // Abrir el navegador predeterminado (Windows)
            String os = Utilidades.getOS();
            String dir = Utilidades.getDirProject();
            System.out.println(Utilidades.getOS());
            if (os.equalsIgnoreCase("Windows 11")) {
                Utilidades.abrirNavegadorPredeterminadoWindows(dir + File.separatorChar + "d3" + File.separatorChar + "grafo.html");
            }
        } catch (Exception e) {
        }

    }
}
