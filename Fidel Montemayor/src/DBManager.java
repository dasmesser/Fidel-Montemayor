
import java.io.*;
import java.util.*;

/**
 *
 * @author jorge
 */
public class DBManager {  //Deprecated

    public static File creator(File target) throws IOException{
        FileFilter filter=new FileFilter(){
            @Override
            public boolean accept(File pathname) {
                String aux=pathname.getName();
                if(aux.endsWith(".mov")){ //Agregar algún manejador de archivos de imagen faltantes
                    return true;
                }
                return false;
            }  
        };
        
        File[] archivosV=target.listFiles(filter);
        
        Arrays.sort(archivosV);
        
        FileFilter filter2=new FileFilter(){
            @Override
            public boolean accept(File pathname) {
                String aux=pathname.getName();
                if(aux.endsWith(".mov")){ //Agregar algún manejador de archivos de imagen faltantes
                    return true;
                }
                return false;
            }  
        };
        
        File [] archivosI=target.listFiles(filter2);
        
        return fileCreator(archivosV,archivosI);
    }
    
    public static File fileCreator(File [] archivosV,File [] archivosI)throws IOException{
        File archivo=new File(".\\Archivos del programa\\Base de datos.txt");
        if(archivo.createNewFile()){
            System.out.println("Archivo creado");
        }
        else{
            System.out.println("archivo no creado");
        }
        BufferedWriter bw=new BufferedWriter(new FileWriter(archivo));
        
        String aux=fix(archivosV[0].getName());
        int c=1;
        
        for(int i=1;i<archivosV.length;i++){
            if(derives(aux,archivosV[i].getName())){
                c++;
                continue;
            }
            write(i,c,archivosV,bw,archivosI);
            c=1;
            aux=fix(archivosV[i].getName());
        }
        
        bw.close();
        
        return archivo;
    }
    
    public static boolean derives(String s1, String s2){
        if(s2.startsWith(s1)){
            return true;
        }
        return false;
    }
    
    public static String fix(String s1){
        return s1.substring(0,s1.lastIndexOf("."));
    }
    
    public static void write(int i, int c, File [] archivosV, BufferedWriter bw, File [] archivosI){
        try{
            bw.write(validate(archivosV[i-c],archivosI)+" "+fix(archivosV[i-c].getName())+" "+(c-1));
            bw.newLine();
            for(c--;c>0;c--){
                bw.write("\t"+validate(archivosV[i-c],archivosI)+" "+fix(archivosV[i-c].getName()));
                bw.newLine();
            }
        }
        catch(IOException ax){
            ax.printStackTrace();
        }
    }
    
    public static String validate(File archivoV, File [] archivosI){
        for(int i=0;i<archivosI.length;i++){
            if(fix(archivoV.getName()).equals(fix(archivosI[i].getName()))){
                return "OK";
            }
        }
        return "NO";
    }
}