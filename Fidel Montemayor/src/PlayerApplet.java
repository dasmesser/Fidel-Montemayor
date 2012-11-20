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

    public void init(URL url){

        setLayout(new BorderLayout());

        //String mediaFile=getParameter("FILE");

        try{

            //URL mediaURL=new URL(getDocumentBase(), mediaFile);

            player=Manager.createRealizedPlayer(url);

            if(player.getVisualComponent()!=null)
                add("Center", player.getVisualComponent());

            if(player.getControlPanelComponent()!=null)
                add("South", player.getControlPanelComponent());

        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println("Got exception "+e);
        }

    }

    public void start(){

        player.start();

    }

    public void stop(){

        player.stop();

        player.deallocate();

    }

    public void destroy(){

        player.close();

    }
}