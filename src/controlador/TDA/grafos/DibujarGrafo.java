/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.listas.LinkedList;
import controlador.TDA.listas.exception.VacioException;
import java.io.FileWriter;
import vista.UtilesEstacionVista;

/**
 *
 * @author Usuario
 */
public class DibujarGrafo {
    String URL = "d3/grafo.js";
    
//    public void crearArchivo(GradoEtiquetadoNoDirigido g) throws VacioException {
//        String verticesData = "";
//        String adyecenciaData = "";
//
//        for (int i = 1; i <= g.nro_vertices(); i++) {
//            verticesData += "{ id: " + i + ", label: \""+g.obtenerEtiqueta(i)+ "\" },\n";
//        }
//        for (int i = 1; i <= g.nro_vertices(); i++) {
//            LinkedList<Adycencia> adycentes = g.adycentes(i);
//            for (int j = 0; j < adycentes.getSize(); j++) {
//                Adycencia a = adycentes.get(j);
//                adyecenciaData += "{ from: " + i + ", to: " + a.getD() + " },\n";
//            }
//        }
//
//        String finalArchivo = "var nodes = new vis.DataSet([\n" + verticesData + "]);\n\n" +
//                "var edges = new vis.DataSet([\n" + adyecenciaData + "]);\n\n" +
//                "var container = document.getElementById(\"mynetwork\");\n" +
//                "var data = {\n" +
//                "  nodes: nodes,\n" +
//                "  edges: edges,\n" +
//                "};\n" +
//                "var options = {};\n" +
//                "var network = new vis.Network(container, data, options);";
//        
//        try {
//            FileWriter file = new FileWriter(URL);
//            file.write(finalArchivo);
//            file.close();
//        } catch (Exception e) {
//        }
//        
//        
//        
//        for (int i = 1; i <= g.nro_vertices(); i++) {
//            LinkedList<Adycencia> ad = g.adycentes(i);
//            if (!ad.isEmpty()) {
//                Adycencia[] a = ad.toArray();
//                for (Adycencia an : a) {
//                    int verticeDestino = an.getD();
//                    if (i < verticeDestino) {
//                        Double n = an.getPeso();
//                        contenido.append("{ from: ").append(i).append(", to: ").append(verticeDestino).append(",value: 3").append(", label: \"").append(n).append("\" },");
//                    }
//
//            }
//    }
//}
    
    public void crearArchivo (GrafoEtiquetadoDirigido g){
        StringBuilder valor = new StringBuilder("var nodes = new vis.DataSet([\n");
        
        for(int i=1; i <= g.nro_vertices(); i++){
            String etq = g.obtenerEtiqueta(i).toString();
            
            valor.append("\t{ id: ").append(i).append(", label: '").append("").append(etq).append("' },\n");
        }
        valor.append("]);");
        
        try {
            valor.append("\nvar edges = new vis.DataSet([\n");
            for(int i=1; i <= g.nro_vertices(); i++){
                if(!g.adycentes(i).isEmpty()){
                    Adycencia[] lista = g.adycentes(i).toArray();
                    for(int j = 0; j < lista.length; j++){
                        Adycencia ady = lista[j];
                        if(g.existe_arista(i, ady.getD())){
                            valor.append("\t{ from: ").append(i).append(", to: ").append(ady.getD()).append(", label: '").append(UtilesEstacionVista.redondear(ady.getPeso())).append("'},\n");
                        }
                    }
                }
            }
            valor.append("]);");
            String data = valor.toString();
            String finalArchivo = "\nvar container = document.getElementById(\"mynetwork\");\n"
                + "      var data = {\n"
                + "        nodes: nodes,\n"
                + "        edges: edges,\n"
                + "      };\n"
                + "      var options = {};\n"
                + "      var network = new vis.Network(container, data, options);\n"
                + "";
            
            FileWriter file = new FileWriter(URL);
            file.write(data+"\n" + finalArchivo);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}
