/*
Cosmografia
Panaderia
Lecheria
*/
import ch.rakudave.suggest.JSuggestField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.*;

public class Diccionario extends JFrame{

    JLabel imagen;
    GridBagConstraints cImagen;
    
    JLabel descripcion;
    GridBagConstraints cDescripcion;
    
    JSuggestField jsf;
    GridBagConstraints cJsf;
    
    Reproductor video;
    GridBagConstraints cVideo;

    public Diccionario(File archivo){
        super("Diccionario Guerrero de Lengua de Señas Mexicanas");

        setContentPane(new MyPanel(".\\Archivos del programa\\Fondo.jpg", true));

        this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setLayout(new GridBagLayout());

        //Inicio aleatorio
        int num=(((int)(Math.random()*10))+1);
        BufferedReader br=null;
        String data="",dataaux;
        try{
            br=new BufferedReader(new FileReader(archivo));
            do{
                dataaux=br.readLine();
                num--;
                if(!dataaux.startsWith("\t")){
                    data=dataaux;
                }
            }while(num>0);
            System.out.println(data);
        }
        catch(IOException ax){
            ax.printStackTrace();
        }
        finally{
            if(br!=null){
                try{
                    br.close();
                }
                catch(IOException ax){
                }
            }
        }
        //Inicio aleatorio
        
        //Imagen
        cImagen=new GridBagConstraints();
        try{
        Image img=ImageIO.read(new URL("file:.\\Archivos del programa\\Imagenes\\"+data.substring(0, data.indexOf(" "))+".jpeg"));
        img=img.getScaledInstance(300, 250, 0);
        imagen=new JLabel();
        imagen.setIcon(new ImageIcon(img));

        cImagen.fill=GridBagConstraints.NONE; //OCUPARA TODO EL ESPACIO DE LA CELDA
        cImagen.gridx=0; //PRIMER BOTON DE LA DERECHA
        cImagen.gridy=0; //PRIMER BOTON DE ARRIBA
        cImagen.insets=new Insets(10, 10, 10, 10);  //ESPACIADO
        cImagen.weighty=2; //CRECER CON LA VENTANA EN Y
        cImagen.weightx=2; //CRECER CON LA VENTANA EN X
        cImagen.ipadx=0; //CELDAS PROPORCIONLES EN X
        cImagen.ipady=0; //CELDAS PROPORCIONLES EN Y

        this.add(imagen, cImagen);
        }catch(IOException ax){
            ax.printStackTrace();
        }
        //Imagen

        //Descripcion
        String desc=data.substring(data.lastIndexOf("\t"),data.length());
        String respar="";
        String res="";
        int i=0;
        while(i<desc.length()){ //Generación del texto de la descripción
            int lim,lio;

            if(i+50>desc.length()){
                lim=desc.length()-i;
                respar=desc.substring(i,i+lim);
                lio=desc.length()-i;
            }
            else{
                lim=50;
                respar=desc.substring(i,i+lim);
                lio=respar.lastIndexOf(" ");
            }
            
            if(lio==0 || lio==-1)
                respar=respar.substring(0,lim);
            else
                respar=respar.substring(0,lio);
            res+="<br>"+respar;
            i+=respar.length();
        }
        
        //Dividir el String
        
        descripcion=new JLabel("<html>Descripcion:"+res+"</html>");
        descripcion.setVerticalAlignment(SwingConstants.TOP);
        descripcion.setForeground(Color.black);
        descripcion.setBackground(Color.white);
        descripcion.setOpaque(true);
        
        cDescripcion=new GridBagConstraints();
        
        cDescripcion.fill=GridBagConstraints.BOTH;
        cDescripcion.insets=new Insets(10, 10, 10, 10);
        cDescripcion.weighty=2;
        cDescripcion.weightx=2;
        cDescripcion.ipadx=0;
        cDescripcion.ipady=0;
        cDescripcion.gridx=0;
        cDescripcion.gridy=1;

        this.add(descripcion, cDescripcion);
        //Descripcion

        //Buscador
        Vector<String> vec=new Vector<>();
        BufferedReader reader=null;
        try{
            reader=new BufferedReader(new FileReader(archivo));
            String strLine;
            while((strLine=reader.readLine())!=null){
                if(!(strLine.startsWith("\t"))){
                    vec.add(strLine.substring(0, strLine.indexOf(" ")));
                    System.out.println(strLine);
                }
            }
        }
        catch(IOException ax){
            ax.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{ 
                    reader.close();
                }
                catch(IOException ax){}
            }
                
        }
        cJsf=new GridBagConstraints();
        cJsf.weighty=2;
        cJsf.weightx=2;
        cJsf.ipadx=0;
        cJsf.ipady=0;
        cJsf.insets=new Insets(0, 10, 0, 10);
        cJsf.fill=GridBagConstraints.HORIZONTAL;
        cJsf.gridx=0;
        cJsf.gridy=2;

        jsf=new JSuggestField(this, vec);
        jsf.addActionListener(new Refresh());

        this.add(jsf, cJsf);
        //Buscador

        /* //Soporte de las ubicaciones sigientes
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
         //Soporte de las ubicaciones sigientes */

        
        //Video
        //JButton videoB=new JButton("Video");
        try{
            video=new Reproductor(new URL("file:.\\Archivos del programa\\Videos\\"+data.substring(0, data.indexOf(" "))+".avi"));
            /*Player p=Manager.createRealizedPlayer(new URL("file:.\\Archivos del programa\\Videos\\"+data.substring(0, data.indexOf(" "))+".avi")); //Agregar URL
            Component video=p.getVisualComponent();
            Component control=p.getControlPanelComponent();*/
        }
        catch(MalformedURLException ax){
            ax.printStackTrace();
        }
        /*catch(CannotRealizeException ex){
            ex.printStackTrace();
        }
        catch(IOException ix){
            ix.printStackTrace();
        }*/

        /*
        cImagen.fill=GridBagConstraints.NONE; //OCUPARA TODO EL ESPACIO DE LA CELDA
        cImagen.gridx=0; //PRIMER BOTON DE LA DERECHA
        cImagen.gridy=0; //PRIMER BOTON DE ARRIBA
        cImagen.insets=new Insets(10, 10, 10, 10);  //ESPACIADO
        cImagen.weighty=2; //CRECER CON LA VENTANA EN Y
        cImagen.weightx=2; //CRECER CON LA VENTANA EN X
        cImagen.ipadx=0; //CELDAS PROPORCIONLES EN X
        cImagen.ipady=0; //CELDAS PROPORCIONLES EN Y
        */
        cVideo=new GridBagConstraints();
        cVideo.fill=GridBagConstraints.NONE;
        cVideo.insets=new Insets(10, 10, 10, 10);
        cVideo.gridx=1;
        cVideo.gridy=0;
        cVideo.fill=GridBagConstraints.BOTH;
        cVideo.gridheight=3;
        cVideo.gridwidth=3;
        cVideo.weighty=2; 
        cVideo.weightx=2; 
        cVideo.ipadx=0; 
        cVideo.ipady=0; 

        this.add(video, cVideo);
        //Video
        

        //Variantes
        JButton variantes=new JButton("Variantes");

        cImagen.gridx=0;
        cImagen.gridy=3;
        cImagen.gridwidth=4;
        cImagen.gridheight=1;

        this.add(variantes, cImagen);
        //Variantes
    }

    public class Refresh implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println(e.getActionCommand());
        }
    }
}