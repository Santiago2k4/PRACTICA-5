/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.modeloTabla;

import controlador.TDA.listas.LinkedList;
import controlador.TDA.listas.exception.VacioException;
import javax.swing.table.AbstractTableModel;
import modelo.Estacion;

/**
 *
 * @author Usuario
 */
public class ModeloTablaEstacion extends AbstractTableModel {
    private LinkedList<Estacion> estaciones = new LinkedList<>();

    public LinkedList<Estacion> getEstaciones() {
        return estaciones;
    }

    public void setEstaciones(LinkedList<Estacion> estaciones) {
        this.estaciones = estaciones;
    }
    
    public Estacion getEstacion(int fila) throws VacioException {
    if (fila >= 0 && fila < estaciones.getSize()) {
        return estaciones.get(fila);
    }
    return null;
}

    
    
    @Override
    public int getRowCount() {
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return estaciones.getSize();
    }

    @Override
    public int getColumnCount() {
        return 4; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getValueAt(int i, int i1) {
        //Nodo<Llanta> nodo
        Estacion estacion = null;
        
        try {
            estacion = estaciones.get(i);
        } catch (Exception e) {
        }
        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        switch (i1) {
            case 0:
                return (estacion != null) ? estacion.getId() : "";
            case 1:
                return (estacion != null) ? estacion.getNombre(): "";
            case 2:
                return (estacion != null) ? estacion.getLongitud(): "";
            case 3:
                return (estacion != null) ? +estacion.getLatitud(): "";
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
         switch (column) {
            case 0:
                return "Nro";
            case 1:
                return "Nombre";
            case 2:
                return "Longitud";
            case 3:
                return "Latitud";
            default:
                return null;
        }
    }
    
    
    
}
