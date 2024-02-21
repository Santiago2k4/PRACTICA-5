/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.grafos.exeption.EtiquetaExecption;
import controlador.TDA.listas.LinkedList;
import controlador.TDA.listas.Queque;
import controlador.TDA.listas.Stack;
import controlador.TDA.listas.exception.LlenoException;
import controlador.TDA.listas.exception.VacioException;
import java.util.HashMap;
import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Estacion;

/**
 *
 * @author sebastian
 */
public class GrafoEtiquetadoDirigido<E> extends GrafoDirigido {

    protected E etiqueta[];
    protected HashMap<E, Integer> dicVertices;
    private Class<E> clazz;
    public Double[][] distancias;
    private Integer[][] nodosIntermedios;

    public GrafoEtiquetadoDirigido(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices);
        this.clazz = clazz;
        etiqueta = (E[]) Array.newInstance(clazz, nro_vertices + 1);
        dicVertices = new HashMap<>(nro_vertices);
        
    }

    public Boolean existeAristaE(E o, E d) throws Exception {
        if (estaEtiquetado()) {
            return existe_arista(obtenerCodigoE(o), obtenerCodigoE(d));
        } else {
            throw new EtiquetaExecption();
        }
    }

    public void insertarAristaE(E o, E d, Double peso) throws Exception {
        if (estaEtiquetado()) {
            insertar(obtenerCodigoE(o), obtenerCodigoE(d), peso);
        } else {
            throw new EtiquetaExecption();
        }
    }

    public void insertarAristaE(E o, E d) throws Exception {

        if (estaEtiquetado()) {
            insertar(obtenerCodigoE(o), obtenerCodigoE(d), Double.NaN);
        } else {
            throw new EtiquetaExecption();
        }
    }

    public LinkedList<Adycencia> adycentesE(E o) throws Exception {
        if (estaEtiquetado()) {
            return adycentes(obtenerCodigoE(o));
        } else {
            throw new EtiquetaExecption();
        }
    }

    public void etiquetarVertice(Integer vertice, E dato) {
        etiqueta[vertice] = dato;
        dicVertices.put(dato, vertice);
    }

    public Boolean estaEtiquetado() {
        Boolean band = true;
        for (int i = 1; i < etiqueta.length; i++) {
            E dato = etiqueta[i];
            if (dato == null) {
                band = false;
                break;
            }
        }
        return band;
    }

    public Integer obtenerCodigoE(E etiqueta) {
        return dicVertices.get(etiqueta);
    }

    public E obtenerEtiqueta(Integer i) {
        return etiqueta[i];
    }
    
    
     public String profundidad(Integer inicio) throws LlenoException, VacioException, Exception {
    
    StringBuilder resultado = new StringBuilder();
    Queque<Integer> cola = new Queque<>(nro_vertices());
    boolean[] visitado = new boolean[nro_vertices() + 1];

   
    visitado[inicio] = true;
    cola.queque(inicio);

    
    HashMap<Integer, E> etiquetas = new HashMap<>();
    for (int i = 1; i <= nro_vertices(); i++) {
        etiquetas.put(i, obtenerEtiqueta(i));
    }
    long tiempoInicio = System.nanoTime();
    while (cola.getSize() != 0) {
        
        Integer actual = cola.dequeque();
        
        resultado.append(etiquetas.get(actual));
        LinkedList<Adycencia> adyacentes = adycentes(actual);

        
        boolean primerAdyacente = true;
        for (Adycencia adyacente : adyacentes.toArray()) {
            Integer vecino = adyacente.getD();
            if (!visitado[vecino]) {
                visitado[vecino] = true;
                cola.queque(vecino);
                if (!primerAdyacente) {
                    resultado.append(" >>> ");
                }
                primerAdyacente = false;
            }
        }

        if (cola.getSize() != 0) {
            resultado.append(" >>> ");
        }
    }

    long tiempoFin = System.nanoTime();
    long tiempoTotal = tiempoFin - tiempoInicio;
    resultado.append("\nTiempo de la Ejecucion del metodo de profundidad ").append(tiempoTotal).append("ns");
    JOptionPane.showMessageDialog(null, resultado);

    return resultado.toString();
}
    
    public String anchura(Integer inicio) throws LlenoException, VacioException, Exception {
    
    StringBuilder resultado = new StringBuilder();
    Queque<Integer> cola = new Queque<>(nro_vertices());
    boolean[] visitado = new boolean[nro_vertices() + 1];

    visitado[inicio] = true;
    cola.queque(inicio);

    HashMap<Integer, E> etiquetas = new HashMap<>();
    for (int i = 1; i <= nro_vertices(); i++) {
        etiquetas.put(i, obtenerEtiqueta(i));
    }
    long tiempoInicio = System.nanoTime();
    while (cola.getSize() != 0) {
        Integer actual = cola.dequeque();
        resultado.append(etiquetas.get(actual));

        LinkedList<Adycencia> adyacentes = adycentes(actual);

        // Iteramos sobre los vértices adyacentes
        for (Adycencia adyacente : adyacentes.toArray()) {
            Integer vecino = adyacente.getD();
            if (!visitado[vecino]) { // Si el vecino no ha sido visitado, lo marcamos como visitado y lo agregamos a la cola
                visitado[vecino] = true;
                cola.queque(vecino);
            }
        }

        // Si hay más nodos en la cola, agregamos " >>> " al resultado
        if (cola.getSize() != 0) {
            resultado.append(" >>> ");
        }
    }

    long tiempoFin = System.nanoTime();
    long tiempoTotal = tiempoFin - tiempoInicio;
    resultado.append("\nTiempo de la Ejecucion del metodo de anchura ").append(tiempoTotal).append("ns");
    JOptionPane.showMessageDialog(null, resultado);

    // Retorna el resultado como una cadena de texto
    return resultado.toString();
}
    
    
        //FLOYD
    public void algoritmoFloyd() throws Exception {
        LinkedList<E> nombresVertices = new LinkedList<>();
        for (int i = 1; i <= nro_vertices(); i++) {
            nombresVertices.add(obtenerEtiqueta(i));
        }

        nodosIntermedios = new Integer[nro_vertices() + 1][nro_vertices() + 1];
        distancias = new Double[nro_vertices() + 1][nro_vertices() + 1];

        for (int i = 1; i <= nro_vertices(); i++) {
            for (int j = 1; j <= nro_vertices(); j++) {
                if (i == j) {
                    distancias[i][j] = 0.0;
                } else if (existe_arista(i, j)) {
                    distancias[i][j] = peso_arista(i, j);
                    nodosIntermedios[i][j] = i; 
                    distancias[j][i] = distancias[i][j];
                    nodosIntermedios[j][i] = j;
                } else {
                    distancias[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        for (int k = 1; k <= nro_vertices(); k++) {
            for (int i = 1; i <= nro_vertices(); i++) {
                for (int j = 1; j <= nro_vertices(); j++) {
                    if (distancias[i][k] != Double.POSITIVE_INFINITY && distancias[k][j] != Double.POSITIVE_INFINITY
                            && distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        nodosIntermedios[i][j] = nodosIntermedios[k][j];
                        distancias[j][i] = distancias[i][j];
                        nodosIntermedios[j][i] = nodosIntermedios[i][j];
                    }
                }
            }
        }
    }

    public String rutaCortaFloyd(Integer origen, Integer destino) throws Exception {
        long tiempoInicio = System.nanoTime();
        algoritmoFloyd();
        
        if (distancias[origen][destino] == Double.POSITIVE_INFINITY) {
            return "No existe ruta de [" + obtenerEtiqueta(origen) + " a " + obtenerEtiqueta(destino) + "]";
        }

        StringBuilder rutaMasCorta = new StringBuilder();
        rutaMasCorta.append("La ruta más corta desde ").append(obtenerEtiqueta(origen)).append(" hasta ").append(obtenerEtiqueta(destino)).append(" es de:\n");

        java.util.Stack<Integer> ruta = new java.util.Stack<>();
        ruta.push(destino);
        int intermedio = nodosIntermedios[origen][destino];

        while (intermedio != origen) {
            ruta.push(intermedio);
            intermedio = nodosIntermedios[origen][intermedio];
        }

        ruta.push(origen);

        rutaMasCorta.append(obtenerEtiqueta(ruta.pop()));
        while (!ruta.isEmpty()) {
            rutaMasCorta.append(" -> ").append(obtenerEtiqueta(ruta.pop()));
        }
        long tiempoFin = System.nanoTime();
        long tiempoTotal = tiempoFin - tiempoInicio;

        rutaMasCorta.append("\nPeso total: ").append(distancias[origen][destino]);
        rutaMasCorta.append("\nTiempo de la Ejecucion del metodo de Floyd ").append(tiempoTotal).append("ns");
        return rutaMasCorta.toString();
    }
    
    public HashMap<Integer, Double> dijkstra(Integer origen) {
    long tiempoInicio = System.nanoTime();
        // Inicializar un mapa para almacenar las distancias más cortas desde el origen hasta cada vértice
    HashMap<Integer, Double> distancias = new HashMap<>();

    // Inicializar un mapa para almacenar los predecesores de cada vértice en la ruta más corta desde el origen
    HashMap<Integer, Integer> predecesores = new HashMap<>();

    // Inicializar un conjunto para almacenar los vértices visitados
    HashSet<Integer> visitados = new HashSet<>();

    // Inicializar una cola de prioridad para almacenar los vértices pendientes y sus distancias
    PriorityQueue<Par<Integer, Double>> colaPrioridad = new PriorityQueue<>(Comparator.comparingDouble(Par::getDerecha));

    // Inicializar todas las distancias con infinito y el origen con distancia 0
    for (int i = 1; i <= nro_vertices(); i++) {
        distancias.put(i, Double.POSITIVE_INFINITY);
    }
    distancias.put(origen, 0.0);

    // Agregar el origen a la cola de prioridad
    colaPrioridad.offer(new Par<>(origen, 0.0));

    // Iterar hasta que no queden vértices pendientes
    while (!colaPrioridad.isEmpty()) {
        // Obtener el vértice con la distancia más corta de la cola de prioridad
        int actual = colaPrioridad.poll().getIzquierda();

        // Si el vértice actual ya fue visitado, continuar con el siguiente
        if (visitados.contains(actual)) {
            continue;
        }

        // Marcar el vértice actual como visitado
        visitados.add(actual);

        // Obtener las adyacencias del vértice actual
        LinkedList<Adycencia> adyacencias = adycentes(actual);

        // Iterar sobre las adyacencias
        for (Adycencia adyacente : adyacencias.toArray()) {
            int vecino = adyacente.getD();
            double peso = adyacente.getPeso();
            
            if (!visitados.contains(vecino) && distancias.get(actual) + peso < distancias.get(vecino)) {
                distancias.put(vecino, distancias.get(actual) + peso);
                predecesores.put(vecino, actual);
                colaPrioridad.offer(new Par<>(vecino, distancias.get(vecino)));
            }
        }
    }
    long tiempoFin = System.nanoTime();
    long tiempoTotal = tiempoFin - tiempoInicio;

    StringBuilder mensaje = new StringBuilder();
mensaje.append("Rutas más cortas desde el origen (").append(origen).append("):\n");
for (int i = 1; i <= nro_vertices(); i++) {
    if (i != origen) {
        mensaje.append("Ruta hasta ").append(i).append(": ").append(obtenerRuta(predecesores, origen, i))
            .append(", Distancia: ").append(distancias.get(i)).append("\n");
    }
    
}
mensaje.append("\nTiempo de la Ejecucion del metodo de Dijkstra ").append(tiempoTotal).append("ns");

JOptionPane.showMessageDialog(null, mensaje.toString(), "Rutas más cortas", JOptionPane.INFORMATION_MESSAGE);

    return distancias;
}

private String obtenerRuta(HashMap<Integer, Integer> predecesores, int origen, int destino) {
    LinkedList<Integer> ruta = new LinkedList<>();
    int actual = destino;
    while (actual != origen && predecesores.containsKey(actual)) {
        ruta.addFirst(actual);
        actual = predecesores.get(actual);
    }
    ruta.addFirst(origen);

    StringBuilder rutaString = new StringBuilder();
    for (int nodo : ruta.toArray()) {
        rutaString.append(nodo).append(" -> ");
    }
    rutaString.delete(rutaString.length() - 4, rutaString.length()); // Eliminar la flecha y espacio extra al final
    return rutaString.toString();
    }

    public HashMap camino2() throws Exception {

        HashMap sendero = new HashMap();

        
        if (esta_conectado()) {
            
            HashSet<Integer> visitados = new HashSet<>();

            
            LinkedList<Integer> vertices = new LinkedList<>();
            for (int i = 1; i <= nro_vertices(); i++) {
                vertices.add(i);
            }

            
            for (Integer origen : vertices.toArray()) {
               
                if (!visitados.contains(origen)) {
                    // Obtener el resultado del algoritmo de Dijkstra desde el vértice actual
                    HashMap<Integer, Double> distancias = dijkstra(origen);

                    // Iterar sobre los vértices para reconstruir el camino más corto desde el vértice actual hasta cada uno de ellos
                    for (Integer destino : vertices.toArray()) {
                        // Si el destino actual es diferente al origen, reconstruir el camino
                        if (!destino.equals(origen)) {
                            // Reconstruir el camino desde el vértice actual hasta el destino
                            int actual = destino;
                            LinkedList<Integer> camino = new LinkedList<>();
                            camino.addFirst(actual); // Agregar el destino al inicio del camino

                            // Iterar hacia atrás desde el destino hasta el origen siguiendo las distancias mínimas
                            while (actual != origen) {
                                double distanciaMinima = Double.POSITIVE_INFINITY;
                                int siguiente = -1;

                                // Obtener las adyacencias del vértice actual
                                LinkedList<Adycencia> adyacencias = adycentes(actual);

                                // Iterar sobre las adyacencias para encontrar el siguiente vértice en el camino
                                for (Adycencia adyacente : adyacencias.toArray()) {
                                    int vecino = adyacente.getD();
                                    double peso = adyacente.getPeso();

                                    // Verificar si la distancia desde el origen hasta el vecino es menor que la distancia mínima actual
                                    if (distancias.containsKey(vecino) && distancias.get(vecino) + peso == distancias.get(actual)) {
                                        siguiente = vecino;
                                        distanciaMinima = distancias.get(vecino);
                                        break;
                                    }
                                }

                                // Agregar el siguiente vértice al inicio del camino
                                if (siguiente != -1) {
                                    camino.addFirst(siguiente);
                                    actual = siguiente;
                                } else {
                                    // No se encontró un siguiente vértice válido, salir del bucle
                                    break;
                                }
                            }

                            // Agregar el camino reconstruido al HashMap
                            sendero.put(destino, camino);

                            // Marcar todos los vértices en el camino como visitados
//                        visitados.addAll(camino);
                        }
                    }
                }
            }
        } else {
            // Manejar el caso en el que el grafo no está conectado
        }

        return sendero;
    }
    

    @Override
    public String toString() {
        StringBuilder grafo = new StringBuilder("GRAFOS ETIQUETADOS \n");
        try {
            for (int i = 1; i <= nro_vertices(); i++) {
                grafo.append("Vertice ").append(obtenerEtiqueta(i)).append("\n");
                if (!adycentes(i).isEmpty()) {
                    Adycencia[] lista = adycentes(i).toArray();
                    for (int j = 0; j < lista.length; j++) {
                        Adycencia a = lista[j];
                        grafo.append("Adycente ").append(obtenerEtiqueta(a.getD())).
                                append(" -Peso- ").append(a.getPeso()).append("\n");
                    }
                }

            }
        } catch (Exception e) {
        }
        return grafo.toString();
//return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public static void main(String[] args) {
        try {
        // Crear un grafo etiquetado dirigido con 5 vértices
        GrafoEtiquetadoDirigido<String> grafo = new GrafoEtiquetadoDirigido(5, String.class);

        // Etiquetar los vértices
        grafo.etiquetarVertice(1, "hola");
        grafo.etiquetarVertice(2, "ws");
        grafo.etiquetarVertice(3, "dsd");
        grafo.etiquetarVertice(4, "saas");
        grafo.etiquetarVertice(5, "sdas");

        // Insertar aristas con pesos
        grafo.insertarAristaE("hola", "ws", 3.0);
        grafo.insertarAristaE("ws", "dsd", 4.0);
        grafo.insertarAristaE( "dsd","saas", 5.0);
        grafo.insertarAristaE( "saas","sdas", 7.0);
        grafo.insertarAristaE( "sdas","hola", 7.0);

        // Calcula las distancias mínimas utilizando el algoritmo de Floyd
        grafo.algoritmoFloyd();

        // Calcular el camino más corto
        int origen = 1;
        int destino = 2;
        String camino = grafo.rutaCortaFloyd(destino, origen);
        System.out.println(camino);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    

}
