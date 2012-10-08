/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorge
 */
/** * @(#)reproductor.java
* * * @author jesus mar * @version 1.00 2009/4/7 */
import java.awt.BorderLayout;

import java.awt.Component;

import java.io.*;

import javax.media.CannotRealizeException;

import javax.media.Manager;

import javax.media.NoPlayerException;

import javax.media.Player;

import javax.swing.JPanel;

import java.net.URL;

public class NewClass1 extends JPanel{

public NewClass1(URL mediaURL) {

setLayout(new BorderLayout());

Manager.setHint(Manager.LIGHTWEIGHT_RENDERER,true);

try{

Player mediaPlayer=Manager.createRealizedPlayer(mediaURL);

Component video= mediaPlayer.getVisualComponent(); Component

controls=mediaPlayer.getControlPanelComponent();

if(video!= null){ add(video,BorderLayout.CENTER); }

if(controls!=null){ add(controls,BorderLayout.SOUTH); }

mediaPlayer.start();

}catch(NoPlayerException noplayerexception){

System.out.println("no hay archivo"); }

catch(CannotRealizeException noreleace){

System.out.println("archivo desconocido"); }

catch(IOException es){

System.out.println("Error al leer la fuente");
}
}
}