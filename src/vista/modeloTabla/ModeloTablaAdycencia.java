/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.modeloTabla;

import controlador.TDA.grafos.Adycencia;
import controlador.TDA.grafos.GradoEtiquetadoNoDirigido;
import controlador.TDA.listas.LinkedList;
import javax.swing.table.AbstractTableModel;
import modelo.Estacion; 

/**
 *
 * @author Usuario
 */
public class ModeloTablaAdycencia extends AbstractTableModel{
    private GradoEtiquetadoNoDirigido <Estacion> grafo;

    public GradoEtiquetadoNoDirigido<Estacion> getGrafo() {
        return grafo;
    }

    public void setGrafo(GradoEtiquetadoNoDirigido<Estacion> grafo) {
        this.grafo = grafo;
    }

    
    
    @Override
    public int getRowCount() {
        return grafo.nro_vertices();
    }

    @Override
    public int getColumnCount() {
        return grafo.nro_vertices()+1;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        if (i1 == 0) {
            return grafo.obtenerEtiqueta(i+1).toString();
            
        } else {
            String valor = "-**-";
            try {
                    Estacion o = grafo.obtenerEtiqueta(i+1);
                    Estacion d = grafo.obtenerEtiqueta(i1);
                    if (grafo.existeAristaE(o, d)) {
                        valor = grafo.peso_arista((i+1), i1).toString();
                    } 
                    return valor;
                
            } catch (Exception e) {
                return valor;
            }
        }
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Estaciones";
            
        } else {
            return grafo.obtenerEtiqueta(column).toString();
        }
//        return super.getColumnName(column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    
    
}
