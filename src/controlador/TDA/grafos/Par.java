/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

/**
 *
 * @author Usuario
 */
public class Par <I, D> {
    private final I izquierda;
    private final D derecha;

    public Par(I izquierda, D derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    public I getIzquierda() {
        return izquierda;
    }

    public D getDerecha() {
        return derecha;
    }
}
