/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.TDA.grafos.DibujarGrafo;
import controlador.TDA.listas.LinkedList;
import controlador.TDA.listas.exception.VacioException;
import controlador.grafo.estacion.EstacionDao;
import controlador.util.Utilidades;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Estacion;
import vista.modeloTabla.ModeloTablaAdycencia;

/**
 *
 * @author Usuario
 */
public class FrmMapa extends javax.swing.JFrame {

    private EstacionDao ed = new EstacionDao();
    private ModeloTablaAdycencia mt = new ModeloTablaAdycencia();

    /**
     * Creates new form FrmMapa
     */
    public FrmMapa() {
        initComponents();
        limpiar();
    }

    private void guardarGrafo() {
        int i = JOptionPane.showConfirmDialog(null, "Esta de acuerdo con guardar el grafo?", "pregunta", JOptionPane.OK_CANCEL_OPTION);
        if (i == JOptionPane.OK_OPTION) {
            try {
                ed.guardarGrafo();
                JOptionPane.showMessageDialog(null, "GRAFO GUARDADO", "OK", JOptionPane.OK_OPTION);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, " no se pudo guardar");
            }
        }

    }

    private void limpiar() {
        try {
            UtilesEstacionVista.cargarComboEstacion(cbxOrigen);
            UtilesEstacionVista.cargarComboEstacion(cbxDestino);
            cargarTabla();
        } catch (Exception e) {
        }
    }

    private void cargarTabla() {
        try {
            mt.setGrafo(ed.getGrafoEstacion());
            mt.fireTableDataChanged();
            tblTabla.setModel(mt);
            tblTabla.updateUI();
        } catch (Exception e) {
        }

    }

    private void cargarGrafo() {

        int i = JOptionPane.showConfirmDialog(null, "Esta de acuerdo con cargar el grafo?", "pregunta", JOptionPane.OK_CANCEL_OPTION);
        if (i == JOptionPane.OK_OPTION) {
            try {
                ed.cargarGrafo();
                limpiar();
                JOptionPane.showMessageDialog(null, "GRAFO CARGADO", "OK", JOptionPane.OK_OPTION);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, " no se pudo guardar");
            }
        }
    }
    
    private void ejecutarMetodos() {
        int inicio = cbxOrigen.getSelectedIndex()+1 ;
        int fina_l = cbxDestino.getSelectedIndex()+1;
        
        try {
            if ("floyd".equals(cbxCamino.getSelectedItem().toString().toLowerCase())) {
                JOptionPane.showMessageDialog(null, ed.getGrafoEstacion().rutaCortaFloyd(inicio,fina_l));
            } else if ("dijkstra".equals(cbxCamino.getSelectedItem().toString().toLowerCase())) {
                ed.getGrafoEstacion().dijkstra(inicio);
            } else {
                throw new UnsupportedOperationException("Método de rutas no reconocido");
            }
        } catch (Exception ex) {
            Logger.getLogger(FrmMapa.class.getName()).log(Level.SEVERE, null, ex);

        }

        
    }
    
    

    private void mostrarMapa() {
        try {
            UtilesEstacionVista.crearMapaEstacion(ed.getGrafoEstacion());
            String os = Utilidades.getOS();
            String dir = Utilidades.getDirProject();
            if (os.equalsIgnoreCase("Windows 11")) {
                Utilidades.abrirNavegadorPredeterminadoWindows(dir + File.separatorChar + "mapas" + File.separatorChar + "index.html");

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo abrir");
            }
        } catch (Exception e) {
        }
    }

    private void mostrarGrafo() {
        try {
            DibujarGrafo dg = new DibujarGrafo();
            dg.crearArchivo(ed.getGrafoEstacion());
            System.out.println(Utilidades.getOS());
            String os = Utilidades.getOS();
            String dir = Utilidades.getDirProject();
            if (os.equalsIgnoreCase("Windows 11")) {
                Utilidades.abrirNavegadorPredeterminadoWindows(dir + File.separatorChar + "d3" + File.separatorChar + "grafo.html");

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo abrir");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo abrir");
        }
    }

    private void adycencia() {
        try {
            Integer posO = cbxOrigen.getSelectedIndex();
            Integer posD = cbxDestino.getSelectedIndex();
            if (posO != posD) {
                System.out.println(ed.getEstaciones().get(posO).hashCode());
                Double peso = UtilesEstacionVista.distanciaEstaciones(UtilesEstacionVista.getComboEstacion(cbxOrigen), UtilesEstacionVista.getComboEstacion(cbxDestino));
                System.out.println("PESO " + peso);
                ed.getGrafoEstacion().insertarAristaE(ed.getEstaciones().get(posO), ed.getEstaciones().get(posD), peso);
                JOptionPane.showMessageDialog(null, "Adycencia agregada ", "ok", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se puede agregar la misma adycencia ", "Error", JOptionPane.ERROR_MESSAGE);

            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            JOptionPane.showMessageDialog(null, "No se puede agregar la misma adycencia ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void camino() throws Exception {
        if (ed.getGrafoEstacion() != null) {
            Integer posO = cbxOrigen.getSelectedIndex() + 1;
            Integer posD = cbxDestino.getSelectedIndex() + 1;
            HashMap<String, LinkedList> mapa = ed.getGrafoEstacion().camino(posO, posD);
            System.out.println(mapa);
            if (mapa.isEmpty()) {
                JOptionPane.showMessageDialog(null, " NO EXISTE CAMINO ", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                LinkedList<Integer> caminos = mapa.get("camino");
                for (int i = 0; i < caminos.getSize(); i++) {
                    Integer v = caminos.get(i);
                    System.out.println(ed.getGrafoEstacion().obtenerEtiqueta(v));

                }
//                System.out.println(mapa.get("camino").print());
            }
        } else {
            JOptionPane.showMessageDialog(null, "GRAFO NULO ", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void busqueda() {
        int inicio = cbxOrigen.getSelectedIndex() + 1;
        int fina_l = cbxDestino.getSelectedIndex() + 1;
        
        try {
            if ("anchura".equals(cbxBusqueda.getSelectedItem().toString().toLowerCase())) {
                ed.getGrafoEstacion().anchura(inicio);
            } else if ("profundidad".equals(cbxBusqueda.getSelectedItem().toString().toLowerCase())) {
                ed.getGrafoEstacion().profundidad(inicio);
            } else {
                throw new UnsupportedOperationException("Método de ordenamiento no reconocido");
            }
        } catch (Exception ex) {
            Logger.getLogger(FrmMapa.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void adyacenciaRandom() {
        try {
            Integer posO = cbxOrigen.getSelectedIndex();
            if (posO != -1) { // Verificar si se seleccionó un índice válido en cbxOrigen
                Random rand = new Random();
                int posD1 = rand.nextInt(cbxDestino.getItemCount()); // Obtener un índice aleatorio para el primer destino
                int posD2 = rand.nextInt(cbxDestino.getItemCount()); // Obtener un índice aleatorio para el segundo destino

                // Evitar que posD1 y posD2 sean iguales a posO
                while (posD1 == posO || posD2 == posO || posD1 == posD2) {
                    posD1 = rand.nextInt(cbxDestino.getItemCount());
                    posD2 = rand.nextInt(cbxDestino.getItemCount());
                }

                LinkedList<Estacion> estaciones = ed.listAll();
                Estacion estacionesD1 = estaciones.get(posD1);
                Estacion estacionesD2 = estaciones.get(posD2);

                Double peso1 = UtilesEstacionVista.distanciaEstaciones(UtilesEstacionVista.getComboEstacion(cbxOrigen), estacionesD1);
                Double peso2 = UtilesEstacionVista.distanciaEstaciones(UtilesEstacionVista.getComboEstacion(cbxOrigen), estacionesD2);

                ed.getGrafoEstacion().insertarAristaE(ed.getEstaciones().get(posO), ed.getEstaciones().get(posD1), peso1);
                ed.getGrafoEstacion().insertarAristaE(ed.getEstaciones().get(posO), ed.getEstaciones().get(posD2), peso2);

                JOptionPane.showMessageDialog(null, "Adyacencia agregada", "OK", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor selecciona un origen válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelHeader1 = new org.edisoncor.gui.label.LabelHeader();
        buttonColoredAction1 = new org.edisoncor.gui.button.ButtonColoredAction();
        buttonColoredAction2 = new org.edisoncor.gui.button.ButtonColoredAction();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxOrigen = new javax.swing.JComboBox<>();
        cbxDestino = new javax.swing.JComboBox<>();
        btnAdycencia = new org.edisoncor.gui.button.ButtonColoredAction();
        cbxBusqueda = new javax.swing.JComboBox<>();
        btnBusqueda = new org.edisoncor.gui.button.ButtonColoredAction();
        btnBusqueda1 = new org.edisoncor.gui.button.ButtonColoredAction();
        cbxCamino = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTabla = new javax.swing.JTable();
        btnCargar = new org.edisoncor.gui.button.ButtonColoredAction();
        btnGuardar1 = new org.edisoncor.gui.button.ButtonColoredAction();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        labelHeader1.setText("Ubicaciones de Escuelas");

        buttonColoredAction1.setBackground(new java.awt.Color(51, 51, 255));
        buttonColoredAction1.setText("VER MAPA");
        buttonColoredAction1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonColoredAction1ActionPerformed(evt);
            }
        });

        buttonColoredAction2.setBackground(new java.awt.Color(51, 51, 255));
        buttonColoredAction2.setText("VER GRAFO");
        buttonColoredAction2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonColoredAction2ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Construir grafos"));

        jLabel1.setText("Origen");

        jLabel2.setText("Destino");

        cbxOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAdycencia.setBackground(new java.awt.Color(51, 51, 255));
        btnAdycencia.setText("Adycencia");
        btnAdycencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdycenciaActionPerformed(evt);
            }
        });

        cbxBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Anchura", "Profundidad" }));

        btnBusqueda.setBackground(new java.awt.Color(51, 51, 255));
        btnBusqueda.setText("Camino mas corto");
        btnBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusquedaActionPerformed(evt);
            }
        });

        btnBusqueda1.setBackground(new java.awt.Color(51, 51, 255));
        btnBusqueda1.setText("Busqueda");
        btnBusqueda1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusqueda1ActionPerformed(evt);
            }
        });

        cbxCamino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Floyd", "Dijkstra" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbxDestino, 0, 183, Short.MAX_VALUE)
                            .addComponent(cbxOrigen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .addComponent(cbxCamino, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBusqueda1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbxBusqueda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdycencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cbxOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btnAdycencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCamino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBusqueda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblTabla.setBackground(new java.awt.Color(204, 204, 204));
        tblTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblTabla);

        btnCargar.setBackground(new java.awt.Color(255, 102, 51));
        btnCargar.setText("Cargar");
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });

        btnGuardar1.setBackground(new java.awt.Color(255, 102, 51));
        btnGuardar1.setText("Guardar");
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCargar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(70, 70, 70)
                    .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(475, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(btnCargar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(211, Short.MAX_VALUE)
                    .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(2, 2, 2)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(buttonColoredAction1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonColoredAction2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelHeader1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(labelHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonColoredAction1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonColoredAction2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonColoredAction2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonColoredAction2ActionPerformed
        mostrarGrafo();
    }//GEN-LAST:event_buttonColoredAction2ActionPerformed

    private void buttonColoredAction1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonColoredAction1ActionPerformed
        mostrarMapa();
    }//GEN-LAST:event_buttonColoredAction1ActionPerformed

    private void btnAdycenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdycenciaActionPerformed
        adyacenciaRandom();
    }//GEN-LAST:event_btnAdycenciaActionPerformed

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
        cargarGrafo();
    }//GEN-LAST:event_btnCargarActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        guardarGrafo();
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void btnBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusquedaActionPerformed
        ejecutarMetodos();
    }//GEN-LAST:event_btnBusquedaActionPerformed

    private void btnBusqueda1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusqueda1ActionPerformed
        busqueda();
    }//GEN-LAST:event_btnBusqueda1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMapa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMapa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMapa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMapa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMapa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonColoredAction btnAdycencia;
    private org.edisoncor.gui.button.ButtonColoredAction btnBusqueda;
    private org.edisoncor.gui.button.ButtonColoredAction btnBusqueda1;
    private org.edisoncor.gui.button.ButtonColoredAction btnCargar;
    private org.edisoncor.gui.button.ButtonColoredAction btnGuardar1;
    private org.edisoncor.gui.button.ButtonColoredAction buttonColoredAction1;
    private org.edisoncor.gui.button.ButtonColoredAction buttonColoredAction2;
    private javax.swing.JComboBox<String> cbxBusqueda;
    private javax.swing.JComboBox<String> cbxCamino;
    private javax.swing.JComboBox<String> cbxDestino;
    private javax.swing.JComboBox<String> cbxOrigen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private org.edisoncor.gui.label.LabelHeader labelHeader1;
    private javax.swing.JTable tblTabla;
    // End of variables declaration//GEN-END:variables
}
