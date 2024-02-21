/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.util;

import java.awt.Desktop;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author sebastian
 */
public class Utilidades {

    public static Field getField(Class clazz, String attribte) {
        Field[] fields = clazz.getFields();
        Field resp = null;
        //System.out.println(fields.length);
        for (Field f : fields) {
            if (attribte.equalsIgnoreCase(f.getName())) {
                resp = f;
            }
        }
        Field[] fields1 = clazz.getDeclaredFields();
        // System.out.println(fields1.length);
        for (Field f : fields1) {
            if (attribte.equalsIgnoreCase(f.getName())) {
                resp = f;
            }
        }
        return resp;
    }

    public static Object getData(Object clas, String attribte) throws InvocationTargetException, IllegalAccessException {
        Class clazz = clas.getClass();
        Field f = getField(clazz, attribte);
        Object obj = null;
        if (f != null) {
            String auxAttribute = "get"+capitalize(attribte);
            Method method = null;
            for (Method m : clazz.getMethods()) {                
                if(m.getName().equalsIgnoreCase(auxAttribute)){
                    method = m;
                    break;
                }                    
            }
            for (Method m : clazz.getDeclaredMethods()) {
                if(m.getName().equalsIgnoreCase(auxAttribute)){
                    method = m;
                    break;
                }
            }
            if(method != null) {
                obj = method.invoke(clas);
            }
        }
        return obj;
    }

    public static String capitalize(String cnd){
        char [] caracteres = cnd.toCharArray();
        String aux = String.valueOf(caracteres[0]).toUpperCase();
        caracteres[0] = aux.charAt(0);
        return new String(caracteres);
    }
    
    public static String getDirProject() {
        return System.getProperty("user.dir");
    }
    
    public static String getOS() {
        return System.getProperty("os.name");
    }
    
    public static void abrirNavegadorPredeterminadoWindows (String url) throws Exception {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
//Desktop desktop = Desktop.getDesktop();
//        desktop.browse(new URI(url));
    }
    
     public static void copiarArchivo(File origen, File destino) throws Exception{
        Files.copy(origen.toPath(), (destino).toPath(),StandardCopyOption.REPLACE_EXISTING);
    }
     
    public static String extension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }
}
