/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**

 @author jorge
 */
import java.applet.*;

import java.awt.*;

import java.net.*;

import javax.media.*;

public class PlayerApplet extends Applet{

    Player player=null;
    boolean play;

    public void init(URL url, boolean play){

        this.play=play;
        setLayout(new BorderLayout());

        //String mediaFile=getParameter("FILE");

        try{

            //URL mediaURL=new URL(getDocumentBase(), mediaFile);

            player=Manager.createRealizedPlayer(url);

            if(player.getVisualComponent()!=null)
                add("Center", player.getVisualComponent());

            if(player.getControlPanelComponent()!=null && play){
                add("South", player.getControlPanelComponent());
                player.start();
            }
            
            if(!play){
                player.start();
                player.stop();
            }

        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println("Got exception "+e);
        }

    }

    public void start(){

        if(play) player.start();

    }

    public void stop(){

        player.stop();

        player.deallocate();

    }

    public void destroy(){

        player.close();

    }
}