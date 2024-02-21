/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.grafo.estacion;

import controlador.TDA.grafos.Adycencia;
import controlador.TDA.grafos.GradoEtiquetadoNoDirigido;
import controlador.TDA.listas.LinkedList;
import controlador.TDA.listas.Nodo;
import controlador.TDA.listas.Queque;
import controlador.TDA.listas.exception.VacioException;
import controlador.dao.DataAccessObject;
import java.io.FileReader;
import java.io.FileWriter;
import modelo.Estacion;

/**
 *
 * @author Usuario
 */
public class EstacionDao extends DataAccessObject<Estacion> {
    public EstacionDao() {
        super(Estacion.class);
    }
    
    private Estacion estacion = new Estacion();
    private LinkedList<Estacion> estaciones = new LinkedList<>();
    private Integer index = -1;
    private GradoEtiquetadoNoDirigido<Estacion> grafoEstacion;
    
    public GradoEtiquetadoNoDirigido<Estacion> getGrafoEstacion() throws VacioException {
        if (grafoEstacion == null) {
            LinkedList<Estacion> lista = getEstaciones();
            Integer size = lista.getSize();
            if (size > 0) {
                grafoEstacion = new GradoEtiquetadoNoDirigido(size,Estacion.class);
                for (int i = 0; i < size; i++) {
                    grafoEstacion.etiquetarVertice((i+1), lista.get(i));
                }
            }
        }
        return grafoEstacion;
    }

    public Estacion getEstacion() {
        if(estacion == null)
            estacion = new Estacion();
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    public LinkedList<Estacion> getEstaciones() {
        if(estaciones.isEmpty())
            estaciones = listAll();
        return estaciones;
    }

    public void setEstaciones(LinkedList<Estacion> estaciones) {
        this.estaciones = estaciones;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
    private Estacion getEstacion(int vertice) {
        return grafoEstacion.obtenerEtiqueta(vertice);
    }
    
    public Boolean save() {
        estacion.setId(generated_id());
        return save(estacion);
    }
    
    public Boolean update(Integer index) {
        //escuela.setId(generated_id());
        return update(estacion, buscarIndex(estacion.getId()));
    }
    
    public Integer buscarIndex (Integer id) {
        Integer index = 0;
        if (listAll().isEmpty()) {
            Estacion [] estaciones = listAll().toArray();
            for (int i = 0; i < estaciones.length; i++) {
                if (id.intValue() == estaciones[i].getId().intValue()) {
                    index = 1;
                    break;
                } 
            }
        }
        return index;
    }
    
    public void guardarGrafo() throws Exception{
        if (grafoEstacion != null) {
            getXstream().alias(grafoEstacion.getClass().getName(), GradoEtiquetadoNoDirigido.class);
            getXstream().toXML(grafoEstacion, new FileWriter("data/grafo.js"));
        } else new NullPointerException("Grafo vacio");
        
    }
    
    public void cargarGrafo() throws Exception{
        grafoEstacion = (GradoEtiquetadoNoDirigido<Estacion>) getXstream().fromXML(new FileReader("data/grafo.js"));
        estaciones.clear();
        for (int i = 1; i < grafoEstacion.nro_vertices(); i++) {
            estaciones.add(grafoEstacion.obtenerEtiqueta(i));
        }
    }
    
    private LinkedList<Adycencia> obtenerAdyacentesGE(Estacion vertice) throws Exception {
        return grafoEstacion.adycentesE(vertice);
    }
    
    public boolean existeCaminoBFS(Estacion cuentaOrigen, Estacion cuentaDestino) throws Exception {
        GradoEtiquetadoNoDirigido<Estacion> grafo = getGrafoEstacion();
        if (grafo.obtenerCodigoE(cuentaOrigen) == null || grafo.obtenerCodigoE(cuentaDestino) == null) {
            return false;
        }
        Integer cuentaOrigenInt = grafo.obtenerCodigoE(cuentaOrigen);
        Integer cuentaDestinoInt = grafo.obtenerCodigoE(cuentaDestino);
        // Búsqueda en amplitud para encontrar una conexión entre las cuentas
        Queque<Integer> queue = new Queque<>(grafo.nro_vertices());
        LinkedList<Integer> visitados = new LinkedList<>();
        queue.queque(cuentaOrigenInt);
        visitados.add(cuentaOrigenInt);
        while (queue.getSize() != 0) {
            Integer nodoActual = queue.dequeque();
            if (nodoActual.equals(cuentaDestinoInt)) {
                return true;
            }
            LinkedList<Adycencia> adyacentes = obtenerAdyacentesGE(getEstacion(nodoActual));
            Nodo<Adycencia> aux = adyacentes.getCabecera();
            while (aux != null) {
                Integer vecino = aux.getData().getD();
                if (!visitados.contiene(vecino)) {
                    queue.queque(vecino);
                    visitados.add(vecino);
                }
                aux = aux.getNext();
            }
        }
        return false;
    }
    
    
    
}
