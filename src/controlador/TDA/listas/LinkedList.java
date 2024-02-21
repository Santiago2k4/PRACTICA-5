/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.listas;

import controlador.TDA.listas.exception.VacioException;

/**
 *
 * @author sebastian
 */
public class LinkedList <E> {
    private Nodo<E> head;
    private Nodo<E> last;
    private Integer size;

    public LinkedList() {
        head = null;
        last = null;
        size = 0;
    }
    
    public Boolean isEmpty() {
        return head == null || size == 0;
    }
    //12   34    45   -->67
    //67  12   34   45
    //head = aux
    public void addFirst(E data) {
        if(isEmpty()) {
            Nodo<E> aux = new Nodo<>(data, null);
            head = aux;
            last = aux;
        } else {
            Nodo<E> headOld = head;
            Nodo<E> aux = new Nodo<>(data, headOld);
            head = aux;
        }
        size++;
    }
    protected void addLast(E data) {
        if(isEmpty()) {
            addFirst(data);
        } else {
            Nodo<E> aux = new Nodo<>(data, null);
            last.setNext(aux);
            last = aux;
            
            
            /*Nodo<E> help = head;

            while (help.getNext()!= null) {
                help = help.getNext();
            }
            help.setNext(aux);*/
            size++;
        }
        
    }
    
    public void add(E data) {
        addLast(data);
    }
    
    public void add(E data, Integer post) throws VacioException {
        if(post == 0) {
            addFirst(data);
        } else if(post.intValue() == (size.intValue())) {
            addLast(data);
        } else {
            //12   34    67    78   --->  20 [2]
            //preview  12   34
            //search   67   78
            //20   67   78
            //12   34   20   67   78
            Nodo<E> search_preview = getNode(post - 1);
            Nodo<E> search = getNode(post);
            Nodo<E> aux = new Nodo<>(data, search);
            search_preview.setNext(aux);
            size++;
        }
    }
    public Nodo getCabecera() {
        return head;
    }
    public E getFirst() throws VacioException {
        if(isEmpty()) {
            throw new VacioException("Lista vacia");
        } else {
            return head.getData();
        }
    }
    
    public E getLast() throws VacioException {
        if(isEmpty()) {
            throw new VacioException("Lista vacia");
        } else {
            return last.getData();
        }
    }
    
    public E get(Integer index) throws VacioException {
        if(isEmpty()) {
            throw new VacioException("Lista vacia");
        } else if(index.intValue() < 0 || index.intValue() >= size) {
            throw new IndexOutOfBoundsException("Fuera de rango");
        } else if(index.intValue() == 0) {
            return getFirst();
        } else if(index.intValue() == (size - 1)) {
            return getLast();
        } else {
            Nodo<E> search = getNode(index);
            return search.getData();
        }
    }
    
    private Nodo<E> getNode(Integer post) throws VacioException {
        if(isEmpty()) {
            throw new VacioException("Error, la lista esta vacia");
        } else if(post < 0 || post >= size) {
            throw  new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if(post == 0) {
            return head;
        } else if(post == (size - 1)) {
            return last;
        } else {
            //2   5   6   9  --> 2
            Nodo<E> search = head;
            Integer cont = 0;
            while(cont < post) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }
    
     public void update(E data, Integer post) throws VacioException {
        if(isEmpty()) {
            throw new VacioException("Error, la lista esta vacia");
        } else if(post < 0 || post >= size) {
            throw  new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if(post == 0) {
            head.setData(data);
        } else if(post == (size - 1)) {
            last.setData(data);
        } else {
            //2   5   6   9  --> 2
            Nodo<E> search = head;
            Integer cont = 0;
            while(cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setData(data);
        }
    }
    public boolean contiene(E dato) {
        Nodo<E> aux = head;
        while (aux != null) {
            if (aux.getData().equals(dato)) {
                return true;
            }
            aux = aux.getNext();
        }
        return false;
    }
    public String print() {
        StringBuilder sb = new StringBuilder();
        if(isEmpty()) {
            sb.append("Lista vacia");
        } else {
            Nodo<E> aux = head;
            while(aux != null){
                sb.append(aux.getData().toString()).append("\n");
                aux = aux.getNext();
            }
        }
        return sb.toString();
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    
    public E deleteFirst() throws VacioException {
        if(isEmpty()) {
            throw new VacioException("Lista vacia");
        } else {
            E element = head.getData();
            Nodo<E> aux = head.getNext();
            head = aux;
            if(size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }
    
    public E deleteLast() throws VacioException {
        if(isEmpty()) {
            throw new VacioException("Lista vacia");
        } else {
            E element = last.getData();
            Nodo<E> aux = getNode(size - 2);
            if(aux == null) {
                last = null;
                if(size == 2) {
                    last = head;
                } else {
                    head = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }
    
    public E delete (Integer post) throws VacioException {
        if(isEmpty()) {
            throw new VacioException("Error, la lista esta vacia");
        } else if(post < 0 || post >= size) {
            throw  new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if(post == 0) {
            return deleteFirst();
        } else if(post == (size - 1)) {
            return deleteLast();
        } else {
            Nodo<E> preview = getNode(post - 1);
            Nodo<E> actually = getNode(post);
            E element = preview.getData();
            Nodo<E> next = actually.getNext();
            actually = null;
            preview.setNext(next);
            size--;
            return element;
        }
    }
    
    public void clear() {
        head = null;
        last = null;
        size = 0;
    }
    
    public E[] toArray() {
        Class clazz = null;
        E[] matriz = null;
        if(this.size > 0) {
            clazz= head.getData().getClass();
            matriz = (E[])java.lang.reflect.Array.newInstance(clazz, size);
            Nodo<E> aux = head;
            for(int i = 0; i < size; i++){
                matriz[i] = aux.getData();
                aux = aux.getNext();
            }
        }
        return matriz;
    }
    
    public LinkedList<E> toList(E[] m) {
        clear();
        for(int i = 0; i < m.length;i++) {
            this.add(m[i]);
        }
        return this;
    }
    
    public static void main(String[] args) {
        LinkedList<Integer> numerics = new LinkedList<>();
        for(int i = 0; i < 2; i++) {
            Integer aux = (int)(Math.random()*1000);
            numerics.add(aux);
        }
        System.out.println(numerics.print());
        System.out.println("Tamanio de lista "+numerics.getSize());
        try {
            //numerics.add(1, numerics.getSize());
            System.out.println("-----------------");
            System.out.println(numerics.print());
            //System.out.println(numerics.getNode(1).getData().toString());
            System.out.println("Tamanio de lista "+numerics.getSize());
        //delete
            System.out.println("DELETE");
            numerics.delete(2);
            System.out.println("size "+numerics.getSize());
            System.out.println(numerics.print());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
//<palabra_reservada> <palabra_reservada> <nombre> <corchete_aper> <corchete_cie>
//public void <nombre_metodo> (){}
//64+25*+
//4     6 + 4 = 10     5     *   5 *2 = 10  10    10 + 10 = 20  
//6                   2                      10
//                    10 