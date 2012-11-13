
import java.awt.BorderLayout;

import java.awt.Component;

import java.io.*;

import javax.media.CannotRealizeException;

import javax.media.Manager;

import javax.media.NoPlayerException;

import javax.media.Player;

import javax.swing.JPanel;

import java.net.URL;

public class Reproductor extends JPanel{

    public Reproductor(URL mediaURL){

        setLayout(new BorderLayout());

        Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);

        try{

            Player mediaPlayer=Manager.createRealizedPlayer(mediaURL);
            Component video=mediaPlayer.getVisualComponent();
            video.setSize(WIDTH, HEIGHT);
            video.setVisible(true);
            Component controls=mediaPlayer.getControlPanelComponent();
            if(video!=null)
                add(video, BorderLayout.CENTER);

            if(controls!=null)
                add(controls, BorderLayout.SOUTH);

            mediaPlayer.start();

        }
        catch(NoPlayerException noplayerexception){

            System.out.println("no hay archivo");
        }
        catch(CannotRealizeException noreleace){

            System.out.println("archivo desconocido");
        }
        catch(IOException es){

            System.out.println("Error al leer la fuente");
        }
    }
}