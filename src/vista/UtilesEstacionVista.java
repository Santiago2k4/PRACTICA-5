/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.TDA.grafos.Adycencia;
import controlador.TDA.grafos.GradoEtiquetadoNoDirigido;
import controlador.TDA.grafos.Grafo;
import controlador.TDA.grafos.GrafoEtiquetadoDirigido;
import controlador.TDA.listas.LinkedList;
import controlador.grafo.estacion.EstacionDao;
import java.io.FileWriter;
import javax.swing.JComboBox;
import modelo.Estacion;

/**
 *
 * @author Usuario
 */
public class UtilesEstacionVista {
//    public static void crearMapaEscuela(Grafo grafo) {
//        if (grafo instanceof GrafoEtiquetadoDirigido || grafo instanceof GradoEtiquetadoNoDirigido) {
//            GrafoEtiquetadoDirigido ge = (GrafoEtiquetadoDirigido) grafo;
//            String mapa = "var osmUrl = 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',\n"
//                    + "osmAttrib = '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors',\n"
//                    + "osm = L.tileLayer(osmUrl, {maxZoom: 15, attribution: osmAttrib});\n"
//                    + "\n"
//                    + "var map = L.map('map').setView([-4.036, -79.201], 15);\n"
//                    + "\n"
//                    + "            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {\n"
//                    + "                attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\n"
//                    + "            }).addTo(map); " + "\n";
//            
//            try {
//                for (int i = 1; i < ge.nro_vertices(); i++) {
//                    Escuela ec = (Escuela) ge.obtenerEtiqueta(i);
//                    mapa += "L.marker(["+ec.getLatitud()+","+ec.getLongitud()+"]).addTo(map)" + "\n";
//                    mapa += ".bindPopup('"+ ec.getNombre()+"')" + "\n";
//                    mapa += ".openPopup();"+"\n";
//                    
//                    
//                    FileWriter file = new FileWriter("mapas/mapa.js");
//                    file.write(mapa);
//                    file.close();
//                }
//            } catch (Exception e) {
//            }
//        }
//    }
    public static double redondear(double valor) {
    return Math.round(valor * 100.0) / 100.0;
}

    
    
    public static void crearMapaEstacion(Grafo grafo) {
        if (grafo instanceof GrafoEtiquetadoDirigido || grafo instanceof GrafoEtiquetadoDirigido) {
            GrafoEtiquetadoDirigido ge = (GrafoEtiquetadoDirigido) grafo;

            String mapa = "var osmUrl = 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',\n"
                    + "                    osmAttrib = '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors',\n"
                    + "                    osm = L.tileLayer(osmUrl, {maxZoom: 15, attribution: osmAttrib});\n"
                    + "\n"
                    + "            var map = L.map('map').setView([-4.036, -79.201], 15);\n"
                    + "\n"
                    + "            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {\n"
                    + "                attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\n"
                    + "            }).addTo(map);" + "\n";


            try {
                for (int i = 1; i <= ge.nro_vertices(); i++) {
                    Estacion ec = (Estacion) ge.obtenerEtiqueta(i);
                    mapa += "L.marker([" + ec.getLatitud() + "," + ec.getLongitud() + "]).addTo(map)" + "\n";
                    mapa += ".bindPopup('" + ec.getNombre() + "')" + "\n";
                    mapa += ".openPopup();" + "\n";

                    FileWriter file = new FileWriter("mapas/mapa.js");
                    file.write(mapa);
                    file.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public static void cargarComboEstacion(JComboBox combo) throws Exception {
        combo.removeAllItems();
        LinkedList<Estacion> lista = new EstacionDao().listAll();
        for (int i = 0; i < lista.getSize(); i++) {
            combo.addItem(lista.get(i));
        }
    }
    
    

    public static Estacion getComboEstacion(JComboBox combo) {
        return (Estacion) combo.getSelectedItem();
    }

    public static double distanciaEstaciones(Estacion origen, Estacion destino) {
        double lat1rad = origen.getLatitud();
        double lon1rad = origen.getLongitud();
        double lat2rad = destino.getLatitud();
        double lon2rad = destino.getLongitud();

        double difLatitud = lat1rad - lat2rad;
        double difLongitud = lon1rad - lon2rad;

        double a = Math.pow(Math.sin(difLatitud / 2), 2)
                + Math.cos(lat1rad)
                * Math.cos(lat2rad)
                * Math.pow(Math.sin(difLongitud / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double radioTierraKm = 6378.0;
        double distancia = radioTierraKm * c;

        return distancia;
    }
}
