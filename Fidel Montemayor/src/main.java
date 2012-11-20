import java.io.*;

import javax.swing.*;

public class main{

    public static void main(String[] args){

        File archivo=new File(".\\Archivos del programa\\Base de datos.txt");
        if(!(archivo.exists()))
            DBManager.init();
         
        Diccionario d=new Diccionario(archivo);
        d.setVisible(true);
        d.pack();
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
    }
}