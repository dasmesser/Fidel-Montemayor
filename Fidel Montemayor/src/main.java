
import ch.rakudave.suggest.JSuggestField;
import java.awt.Frame;
import java.io.*;
import java.util.Vector;
import javax.swing.*;

public class main{

    public static void main(String[] args){

        File archivo=new File(".\\Archivos del programa\\Base de datos.txt");
        if(!(archivo.exists()))
            DBManager.init(); /* JOptionPane.showMessageDialog(null,"Iniciando la configuración inicial");
         JFileChooser fc=new JFileChooser();
         fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
         int respuesta;
         do{
         respuesta=fc.showOpenDialog(null);

         if(respuesta==JFileChooser.APPROVE_OPTION){
         //JOptionPane.showMessageDialog(null,"Bien hecho muchacho");
         try{
         archivo=DBManager.creator(fc.getSelectedFile());
         }
         catch(IOException ax){
         ax.printStackTrace();
         JOptionPane.showMessageDialog(null, "El archivo no se ha podido crear");
         }
         }

         if(respuesta==JFileChooser.ERROR_OPTION){
         if(JOptionPane.showConfirmDialog(new Frame(),"Ocurrió un error abriendo la carpeta.\n¿Desea intentar cargarla de nuevo?",
         "Error",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE)
         ==JOptionPane.YES_OPTION){
         continue;
         }
         else{
         return;
         }
         }

         if(respuesta==JFileChooser.CANCEL_OPTION){
         if(JOptionPane.showConfirmDialog(new JInternalFrame(),"¿Desea cerrar la configuración inicial?",
         "Error",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)
         ==JOptionPane.YES_OPTION){
         return;
         }
         else{
         continue;
         }
         }

         }while(!(respuesta==JFileChooser.APPROVE_OPTION));

         JOptionPane.showMessageDialog(null,"El programa deberá reiniciar para adquirir\nlas configuraciones actuales.");
         return; */
     
        //El ya existe
        System.out.println("YEA!");
            Diccionario d=new Diccionario(archivo);
            d.setVisible(true);
            d.pack();
            d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
    }
}