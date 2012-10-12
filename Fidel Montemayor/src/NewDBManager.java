
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
public class NewDBManager{

    public static void init(){
        File frontal=new File(".\\Archivos del programa\\Frontal");
        File lateral=new File(".\\Archivos del programa\\Lateral");
        File imagen=new File(".\\Archivos del programa\\Imagenes");

        FileFilter filterVid=new FileFilter(){

            @Override
            public boolean accept(File pathname){
                String aux=pathname.getName();
                if(aux.endsWith(".mov")) //Agregar algún manejador de archivos de imagen faltantes
                    return true;
                return false;
            }
        };

        File[] frontalArch=frontal.listFiles(filterVid);
        File[] lateralArch=lateral.listFiles(filterVid);

        Arrays.sort(frontalArch);
        Arrays.sort(lateralArch);

        FileFilter filterImg=new FileFilter(){

            @Override
            public boolean accept(File pathname){
                String aux=pathname.getName();
                if(aux.endsWith(".jpg")) //Agregar algún manejador de archivos de imagen faltantes
                    return true;
                return false;
            }
        };

        File[] imagenArch=imagen.listFiles(filterImg);
        Arrays.sort(imagenArch);
        
        for(int i=0;i<frontalArch.length;i++){
            System.out.println(frontalArch[i].getName());
        }

        //System.out.println("compare "+fix(frontalArch[0].getName()).compareTo(fix(imagenArch[0].getName())));
        //System.out.println(frontalArch[0].getName()+" "+imagenArch[0].getName());

        System.out.println(new File(frontalArch[0].getName()).compareTo(lateralArch[0]));
        Arrays.binarySearch(imagenArch, imagen);
        
        try{
            fileCreator(frontalArch, lateralArch, imagenArch);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static File fileCreator(File[] frontalArch, File[] lateralArch, File[] imagenArch) throws IOException{
        File archivo=new File(".\\Archivos del programa\\Base de datos.txt");

        if(archivo.createNewFile())
            System.out.println("Archivo creado");
        else//Esto no debería pasar
            System.out.println("archivo no creado");

        BufferedWriter bw=new BufferedWriter(new FileWriter(archivo));

        String aux=fix(frontalArch[0].getName());
        int c=1;

        for(int i=1; i<frontalArch.length; i++){
            if(derives(aux, frontalArch[i].getName())){
                c++;
                if(i!=frontalArch.length-1) 
                    continue;
                else{
                    i++;
                    write(i, c, bw, frontalArch, lateralArch, imagenArch);
                    break;
                }
            }
            write(i, c, bw, frontalArch, lateralArch, imagenArch);//No arreglado
            c=1;
            aux=fix(frontalArch[i].getName());
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
    
    public static void write(int i, int c, BufferedWriter bw, File[] frontalArch, File[] lateralArch, File[] imagenArch){
        try{
            bw.write(fix(frontalArch[i-c].getName())+" "+validateVid(frontalArch[i-c], lateralArch, i-c)+" "+validateImg(frontalArch[i-c], imagenArch));
            bw.newLine();
            for(c--; c>0; c--){
                bw.write("\t"+fix(frontalArch[i-c].getName())+" "+validateVid(frontalArch[i-c], lateralArch, i-c)+" "+validateImg(frontalArch[i-c], imagenArch));
                bw.newLine();
            }

        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /*
     public static void write(int i, int c, File [] archivosV, BufferedWriter
     bw, File [] archivosI){ try{
     bw.write(validate(archivosV[i-c],archivosI)+"
     "+fix(archivosV[i-c].getName())+" "+(c-1)); bw.newLine();
     for(c--;c>0;c--){ bw.write("\t"+validate(archivosV[i-c],archivosI)+"
     "+fix(archivosV[i-c].getName())); bw.newLine(); } } catch(IOException
     ax){ ax.printStackTrace(); }
     }
     */
    public static String validateVid(File source, File[] target, int c){
        int i=1, e=0;
        String name=fix(source.getName());
        try{
            while(true){
                if(name.equals(fix(target[c].getName())))
                    return "OK";
                c=c+(++e*(i));
                i*=-1;
            }
        }
        catch(IndexOutOfBoundsException ax){
            i*=-1;
            try{
                while(true){
                    if(name.equals(fix(target[c].getName())))
                        return "OK";
                    c=c+(++e*(i));
                }
            }
            catch(IndexOutOfBoundsException ex){
                return "NO";
            }
        }
    }
    
    public static String validateImg(File source, File[] target){
        String name=fix(source.getName());
        if(name.contains("_"))  name=name.substring(0, name.lastIndexOf("_"));
        
        int i1=0, i2=target.length-1;
                
        while(i1<=i2){
            System.out.println("Comparing "+name+" with "+fix(target[(i1+i2)/2].getName())+" "+name.compareTo(fix(target[(i1+i2)/2].getName())));
            if(name.compareTo(fix(target[(i1+i2)/2].getName()))==0){
                System.out.println("");
                return "OK";
            }
            else if(name.compareTo(fix(target[(i1+i2)/2].getName()))>0)  i1=(i1+i2)/2+1;
            else //if(name.compareTo(target[(i1+i2)/2].getName())<0)
                i2=(i1+i2)/2-1;
        }
        System.out.println("");
        
        /*while(ok1 && ok2){
            try{
            if(name.equals(fix(target[c].getName())))
                return "OK";
            else{
                if(name.compareTo(target[c].getName())<0){
                    c--;
                    ok1=false;
                }
                else{
                    c++;
                    ok2=false;
                }
            }
            }
            catch(IndexOutOfBoundsException ax){
                break;
            }
        }*/
        return "NO";
    }
}