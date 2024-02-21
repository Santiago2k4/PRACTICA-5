/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.listas;

import controlador.TDA.listas.exception.LlenoException;
import controlador.TDA.listas.exception.VacioException;

/**
 *
 * @author sebastian
 */
public class Queque<E> {
    private QuequeOperation<E> quequeOperation;
    public Queque(Integer cant) {
        this.quequeOperation = new QuequeOperation<>(cant);
    }
    public void queque(E dato) throws LlenoException {
        quequeOperation.queque(dato);
    }
    
    public Integer getSize() {
        return this.quequeOperation.getSize();
    }
    
    public void clear() {
        this.quequeOperation.clear();
    }
    
    public Integer getTop() {
        return this.quequeOperation.getTop();
    }
    
    public void print() {
        System.out.println("Cola");
        System.out.println(quequeOperation.print());
        System.out.println("******");
    }
    //TODO.. falta push
    public E dequeque() throws VacioException {
        return quequeOperation.dequeque();
    }
}










