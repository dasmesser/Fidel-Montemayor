
import ch.rakudave.suggest.JSuggestField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Diccionario extends JFrame{

    JLabel imagen;
    GridBagConstraints cImagen;
    JLabel descripcion;
    GridBagConstraints cDescripcion;
    JSuggestField jsf;
    GridBagConstraints cJsf;
    PlayerApplet video;
    GridBagConstraints cVideo;
    ArrayList<JButton> variaciones;
    GridBagConstraints cVariaciones;
    Diccionario dic;
    MyPanel panel;
    Vector<String> vec;

    public Diccionario(File archivo){
        super("Fullscreen");
        dic=this;

        /* Toolkit tk = Toolkit.getDefaultToolkit();
         int xSize = ((int) tk.getScreenSize().getWidth());
         int ySize = ((int) tk.getScreenSize().getHeight());
         this.setSize(xSize,ySize); */

        BufferedReader br=null;

        try{

            this.setTitle("Diccionario Guerrero de Lengua de Señas Mexicanas");

            setContentPane(panel=new MyPanel(".\\Archivos del programa\\Fondo.jpg", false));

            this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            this.setLayout(new GridBagLayout());

            //Inicio aleatorio
            int num=(((int) (Math.random()*10))+1);

            String data="", dataaux;

            br=new BufferedReader(new FileReader(archivo));
            do{
                dataaux=br.readLine();
                num--;
                if(!dataaux.startsWith("\t"))
                    data=dataaux;
            }while(num>0);

            //Inicio aleatorio

            //Imagen
            cImagen=new GridBagConstraints();
            setImg(data);

            cImagen.fill=GridBagConstraints.NONE; //OCUPARA TODO EL ESPACIO DE LA CELDA
            cImagen.gridx=0; //PRIMER BOTON DE LA DERECHA
            cImagen.gridy=0; //PRIMER BOTON DE ARRIBA
            cImagen.insets=new Insets(10, 10, 10, 10);  //ESPACIADO
            cImagen.weighty=2; //CRECER CON LA VENTANA EN Y
            cImagen.weightx=2; //CRECER CON LA VENTANA EN X
            cImagen.ipadx=0; //CELDAS PROPORCIONLES EN X
            cImagen.ipady=0; //CELDAS PROPORCIONLES EN Y

            this.add(imagen, cImagen);
            //Imagen

            //Descripcion

            //Dividir el String

            descripcion=new JLabel();
            descripcion.setText(setDesc(data));
            descripcion.setVerticalAlignment(SwingConstants.TOP);
            descripcion.setForeground(Color.black);
            descripcion.setBackground(Color.white);
            descripcion.setOpaque(true);
            descripcion.setSize(new Dimension(800, 800));

            cDescripcion=new GridBagConstraints();

            cDescripcion.fill=GridBagConstraints.HORIZONTAL;
            cDescripcion.insets=new Insets(10, 10, 10, 10);
            cDescripcion.weighty=0;
            cDescripcion.weightx=0;
            cDescripcion.ipadx=0;
            cDescripcion.ipady=0;
            cDescripcion.gridx=0;
            cDescripcion.gridy=1;

            this.add(descripcion, cDescripcion);
            //Descripcion

            //Buscador
            vec=new Vector<String>();
            BufferedReader reader=null;
            try{
                reader=new BufferedReader(new FileReader(archivo));
                String strLine;
                while((strLine=reader.readLine())!=null)
                    if(!(strLine.startsWith("\t")))
                        vec.add(strLine.substring(0, strLine.indexOf(" ")));
            }
            catch(IOException ax){
            }
            finally{
                if(reader!=null)
                    try{
                        reader.close();
                    }
                    catch(IOException ax){
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


            //Video
            try{
                video=new PlayerApplet();
                video.init(new URL("file:.\\Archivos del programa\\Videos\\"+data.substring(0, data.indexOf(" "))+".avi"), true);
                video.setBounds(new Rectangle(new Dimension(350, 350)));
            }
            catch(MalformedURLException ax){
            }

            cVideo=new GridBagConstraints();
            cVideo.fill=GridBagConstraints.BOTH;
            cVideo.insets=new Insets(10, 10, 10, 10);
            cVideo.gridx=1;
            cVideo.gridy=0;
            cVideo.gridheight=3;
            cVideo.gridwidth=3;
            cVideo.weighty=2;
            cVideo.weightx=2;
            cVideo.ipadx=0;
            cVideo.ipady=0;

            this.add(video, cVideo);
            //Video


            //Variantes
            variaciones=new ArrayList<JButton>();

            cVariaciones=new GridBagConstraints();
            cVariaciones.fill=GridBagConstraints.NONE;
            cVariaciones.insets=new Insets(10, 10, 10, 10);
            cVariaciones.weighty=2;
            cVariaciones.weightx=2;
            cVariaciones.ipadx=0;
            cVariaciones.ipady=0;


            setVar(data, archivo);
            //Variantes


            //FIN
        }
        catch(IOException ax){
        }
        finally{
            if(br!=null)
                try{
                    br.close();
                }
                catch(IOException ax){
                }
        }
    }

    public void setImg(String data){
        Image img=null;
        try{
            img=ImageIO.read(new URL("file:.\\Archivos del programa\\Imagenes\\"+data.substring(0, data.indexOf(" "))+".jpeg"));
            img=img.getScaledInstance(300, 250, 0);
            imagen=new JLabel();
            imagen.setIcon(new ImageIcon(img));
        }
        catch(IOException ax){
        }
    }

    public String setDesc(String data){
        String desc=data.substring(data.lastIndexOf("\t"), data.length());
        String respar="";
        String res="";
        int i=0;
        while(i<desc.length()){ //Generación del texto de la descripción
            int lim, lio;

            if(i+65>desc.length()){
                lim=desc.length()-i;
                respar=desc.substring(i, i+lim);
                lio=desc.length()-i;
            }
            else{
                lim=65;
                respar=desc.substring(i, i+lim);
                lio=respar.lastIndexOf(" ");
            }

            if(lio==0||lio==-1)
                respar=respar.substring(0, lim);
            else
                respar=respar.substring(0, lio);
            res+="<br>"+respar;
            i+=respar.length();
        }
        return "<html>Descripcion:"+res+"</html>";
    }

    public void setVar(String data, File archivo){

        for(JButton jb : variaciones)
            panel.remove(jb);

        variaciones.clear();

        variaciones=new ArrayList<JButton>();
        if(!data.contains("_")){
            BufferedReader read=null;
            try{
                read=new BufferedReader(new FileReader(archivo));
                while(!(read.readLine()).startsWith(data));


                String dataaux="";
                int con=0;
                while((dataaux=read.readLine()).contains(data.substring(0, data.indexOf(" "))+"_")){
                    JButton jb=new JButton();
                    jb.setName(dataaux.substring(1, dataaux.indexOf(" ")));
                    jb.setText("Variacion "+((char) (97+con)));

                    Change c;
                    jb.addActionListener((c=new Change()));
                    c.name=jb.getName();
                    
                    variaciones.add(jb);

                    cVariaciones.gridx=con%4;
                    cVariaciones.gridy=con/4+4;
                    this.add(variaciones.get(con++), cVariaciones);
                }
            }
            catch(Exception ax){
            }
            finally{
                if(read!=null)
                    try{
                        read.close();
                    }
                    catch(IOException ex){
                    }
            }
        }
        else{
            JButton jb=new JButton();
            jb.setName(data.substring(1, data.indexOf(" ")));
            jb.setText("Variacion Global");
            variaciones.add(jb);
            
            cVariaciones.gridx=0;
            cVariaciones.gridy=4;
            
            this.add(jb,cVariaciones);
        }
    }

    public class Refresh implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            String data;
            if(!(data=e.getActionCommand()).equals("")){
                setImg(data+" ");//Imagen setteada

                BufferedReader br=null;
                try{
                    br=new BufferedReader(new FileReader(".\\Archivos del programa\\Base de datos.txt"));

                    String dataaux="";
                    do{
                        dataaux=br.readLine();
                    }while(!dataaux.startsWith(data));

                    descripcion.setText(setDesc(dataaux));//Descripcion setteada

                    dic.remove(video);
                    video=null;
                    video=new PlayerApplet();
                    video.init(new URL("file:.\\Archivos del programa\\Videos\\"+dataaux.substring(0, dataaux.indexOf(" "))+".avi"), true);//Video setteado
                    dic.add(video, cVideo);
                    setVar(data+" ", new File(".\\Archivos del programa\\Base de datos.txt"));//Variables setteadas
                }
                catch(Exception ax){
                    ax.printStackTrace();
                }
                finally{
                    if(br!=null)
                        try{
                            br.close();
                        }
                        catch(IOException ex){
                        }
                }
            }
        }
    }
    
    public class Change implements ActionListener{

        String name;
        
        @Override
        public void actionPerformed(ActionEvent e){
                    dic.remove(video);
                    video=null;
                    video=new PlayerApplet();
                    try{
                    video.init(new URL("file:.\\Archivos del programa\\Videos\\"+name+".avi"), true);//Video setteado
                    }catch(Exception ax){
                        ax.printStackTrace();
                    }
                    dic.add(video, cVideo);           
                    setVar(name+" ", new File(".\\Archivos del programa\\Base de datos.txt"));
        }
        
    }
}