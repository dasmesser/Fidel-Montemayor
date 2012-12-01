
import java.io.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 To change this template, choose Tools | Templates and open the template in
 the editor.
 */
/**

 @author jorge
 */
public class DBManager{

    public static void init(){
        File video=new File(".\\Archivos del programa\\Videos");
        File definicion=new File(".\\Archivos del programa\\Definiciones");
        File imagen=new File(".\\Archivos del programa\\Imagenes");

        FileFilter filterVid=new FileFilter(){
            @Override
            public boolean accept(File pathname){
                String aux=pathname.getName();
                if(aux.endsWith(".avi"))
                    return true;
                return false;
            }
        };
        File[] videoArch=video.listFiles(filterVid);
        Arrays.sort(videoArch);
        
        
        FileFilter filterDef=new FileFilter(){
            @Override
            public boolean accept(File pathname){
                String aux=pathname.getName();
                if(aux.endsWith(".txt"))
                    return true;
                return false;
            }
        };      
        File[] definicionArch=definicion.listFiles(filterDef);
        Arrays.sort(definicionArch);

        
        FileFilter filterImg=new FileFilter(){
            @Override
            public boolean accept(File pathname){
                String aux=pathname.getName();
                if(aux.endsWith(".jpeg")||aux.endsWith(".jpg"))
                    return true;
                return false;
            }
        };

        File[] imagenArch=imagen.listFiles(filterImg);
        Arrays.sort(imagenArch);
        
        /*for(int i=0;i<videoArch.length;i++)
            System.out.println(videoArch[i].getName());

        System.out.println(new File(frontalArch[0].getName()).compareTo(lateralArch[0]));
        Arrays.binarySearch(imagenArch, imagen);*/
        
        try{
            fileCreator(videoArch, definicionArch, imagenArch);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static File fileCreator(File[] videoArch, File[] definicionArch, File[] imagenArch) throws IOException{
        File archivo=new File(".\\Archivos del programa\\Base de datos.txt");

        if(archivo.createNewFile())
            System.out.println("Archivo creado");
        else//Esto no debería pasar
            System.out.println("archivo no creado");

        BufferedWriter bw=new BufferedWriter(new FileWriter(archivo));

        String aux=fix(videoArch[0].getName());
        int c=1;

        for(int i=1; i<videoArch.length; i++){
            if(derives(aux, videoArch[i].getName())){
                c++;
                if(i!=videoArch.length-1) 
                    continue;
                else{
                    i++;
                    write(i, c, bw, videoArch, definicionArch, imagenArch);
                    break;
                }
            }
            write(i, c, bw, videoArch, definicionArch, imagenArch);
            c=1;
            aux=fix(videoArch[i].getName());
            if(i==videoArch.length-1){
                i++;
                write(i, c, bw, videoArch, definicionArch, imagenArch);
            }
        }

        bw.close();
        return archivo;
    }

    public static boolean derives(String s1, String s2){
        return s2.startsWith(s1+"_");
    }

    public static String fix(String s1){
        return s1.substring(0, s1.lastIndexOf("."));
    }
    
    public static String fix(String s1, char c){
        return s1.substring(0,s1.lastIndexOf(c));
    }
    
    public static void write(int i, int c, BufferedWriter bw, File[] videoArch, File[] definicionArch, File[] imagenArch){
        try{
            String res;
            bw.write(fix(videoArch[i-c].getName())+" "+(res=validate(videoArch[i-c], definicionArch))+" "+validate(videoArch[i-c], imagenArch));
            if(res.equals("OK"))
                bw.write("\t\t\t"+new BufferedReader(new FileReader(new File(".\\Archivos del programa\\Definiciones\\"+fix(videoArch[i-c].getName())+".txt"))).readLine());
            bw.newLine();
            for(c--; c>0; c--){
                bw.write("\t"+fix(videoArch[i-c].getName())+" "+validate(videoArch[i-c], definicionArch)+" "+validate(videoArch[i-c], imagenArch));
                bw.newLine();
            }

        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public static String validate(File source, File[] target){
        String name=fix(source.getName());
        if(name.contains("_"))  name=name.substring(0, name.lastIndexOf("_"));
        
        int i1=0, i2=target.length-1;
                
        while(i1<=i2){
            if(name.compareTo(fix(target[(i1+i2)/2].getName()))==0){
                return "OK";
            }
            else if(name.compareTo(fix(target[(i1+i2)/2].getName()))>0)  i1=(i1+i2)/2+1;
            else //if(name.compareTo(target[(i1+i2)/2].getName())<0)
                i2=(i1+i2)/2-1;
        }
        
        return "NO";
    }
}