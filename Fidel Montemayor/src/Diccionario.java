import ch.rakudave.suggest.JSuggestField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.*;

public class Diccionario extends JFrame{
    
    Image imagen;           GridBagConstraints cImagen;
    TextArea descripcion;   GridBagConstraints cDescripcion;
    JSuggestField jsf;      GridBagConstraints cJsf;
    
      
    public Diccionario(File archivo){
        super("Diccionario Guerrero de Lengua de Señas Mexicanas");
        
        setContentPane(new MyPanel(".\\Archivos del programa\\Fondo.jpg", true));
        
        this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setLayout(new GridBagLayout());
        
        //Inicio aleatorio
        int num=((int)Math.random()*500+1);
        BufferedReader br=null;
        String data;
        try{
            br=new BufferedReader(new FileReader(archivo));
            while(num!=0){
                num--;
                br.readLine();
            }
            data=br.readLine();
        }
        catch(IOException ax){
            ax.printStackTrace();
        }
        finally{
            if(br!=null)
                try{
                    br.close();
                }
                catch(IOException ax){}
        }
        //Inicio aleatorio
        
        //Imagen
        cImagen = new GridBagConstraints();
        JButton imagen=new JButton("Imagen");
        
        cImagen.fill=GridBagConstraints.NONE; //OCUPARA TODO EL ESPACIO DE LA CELDA
        cImagen.gridx=0; //PRIMER BOTON DE LA DERECHA
        cImagen.gridy=0; //PRIMER BOTON DE ARRIBA
        cImagen.insets = new Insets(10,10,10,10);  //ESPACIADO
        cImagen.weighty=2; //CRECER CON LA VENTANA EN Y
        cImagen.weightx=2; //CRECER CON LA VENTANA EN X
        cImagen.ipadx=0; //CELDAS PROPORCIONLES EN X
        cImagen.ipady=0; //CELDAS PROPORCIONLES EN Y
        
        this.add(imagen,cImagen);
        //Imagen
        
        //Descripcion
        JButton descripcion=new JButton("Descripcion");
        cDescripcion=new GridBagConstraints();
        cDescripcion.fill=GridBagConstraints.BOTH;
        cDescripcion.insets = new Insets(10,10,10,10); 
        cDescripcion.weighty=2; 
        cDescripcion.weightx=2; 
        cDescripcion.ipadx=0; 
        cDescripcion.ipady=0;
        cDescripcion.gridx=0;
        cDescripcion.gridy=1;
        
        this.add(descripcion,cDescripcion);
        //Descripcion
        
        //Buscador
        Vector <String> vec=new Vector <>();
            BufferedReader reader;
            try{
                reader=new BufferedReader(new FileReader(".\\Archivos del programa\\Base de datos.txt"));
                String strLine;
                while((strLine=reader.readLine())!=null){
                    if(!(strLine.startsWith("\n"))){
                        vec.add(strLine);
                    }
                }
            }
            catch(IOException ax){
                ax.printStackTrace();
            }
        cJsf=new GridBagConstraints();
        cJsf.weighty=2;
        cJsf.weightx=2;
        cJsf.ipadx=0;
        cJsf.ipady=0;
        cJsf.insets = new Insets(0,10,0,10);
        cJsf.fill=GridBagConstraints.HORIZONTAL;
        cJsf.gridx=0;
        cJsf.gridy=2;
        
        JSuggestField jsf=new JSuggestField(this, vec);     
        jsf.addActionListener(new Refresh());
        
        this.add(jsf,cJsf);
        //Buscador
        
        /*//Soporte de las ubicaciones sigientes
        cImagen.gridx=0;
        cImagen.gridy=4;
        this.add(new JButton("1"),cImagen);
        
        cImagen.gridx=1;
        cImagen.gridy=4;
        this.add(new JButton("2"),cImagen);
        
        cImagen.gridx=2;
        cImagen.gridy=4;
        this.add(new JButton("3"),cImagen);
        
        cImagen.gridx=3;
        cImagen.gridy=4;
        this.add(new JButton("4"),cImagen);
        //Soporte de las ubicaciones sigientes*/
        
        //Video
        JButton videoB=new JButton("Video");
        try{
            Player p=Manager.createRealizedPlayer(new URL("")); //Agregar URL
            Component video=p.getVisualComponent();
            Component control=p.getControlPanelComponent();
        }
        catch(NoPlayerException ax){
            ax.printStackTrace();
        }
        catch(CannotRealizeException ex){
            ex.printStackTrace();
        }
        catch(IOException ix){
            ix.printStackTrace();
        }
        
        cImagen.insets = new Insets(10,10,10,10);
        cImagen.gridx=1;
        cImagen.gridy=0;
        cImagen.fill=GridBagConstraints.BOTH;
        cImagen.gridheight=3;
        cImagen.gridwidth=3;
        
        this.add(videoB,cImagen);
        //Video
        
        //Variantes
        JButton variantes=new JButton("Variantes");
        
        cImagen.gridx=0;
        cImagen.gridy=3;
        cImagen.gridwidth=4;
        cImagen.gridheight=1;
        
        this.add(variantes,cImagen);
        //Variantes
    }
    
    public class Refresh implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println(e.getActionCommand());
        }
        
        
    }
}