package org.example;

import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import gr.hua.dit.oop2.musicplayer.PlayerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {


        Player player1 = PlayerFactory.getPlayer();
        String songName = args[0];
        File songFile = new File(songName);

        try {
            InputStream songStream = new FileInputStream(songName);

            if (songName.endsWith(".mp3") == true) {
                System.out.println("SUI");
                player1.play(songStream);
            } else if (songName.endsWith(".m3u") == true) {
                System.out.println("Messi");

            }
        }
        catch (FileNotFoundException exception){
            System.out.println("File" + " " + songName + " " + "not found");
        }
        catch (PlayerException exception){
            exception.getMessage();
        }




        //yesssiiiir

    }

    public String findAbsolutePath(File songFile){
        String path = null;
        try{
            path = songFile.getAbsolutePath();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return path;
    }

    public ArrayList<File> m3uReader(String m3uFile){
        ArrayList<File> songs = new ArrayList<>();

        InputStream in = new FileInputStream(m3uFile);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(in));

    }
}