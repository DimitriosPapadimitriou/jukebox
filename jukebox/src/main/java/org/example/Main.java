package org.example;

import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import gr.hua.dit.oop2.musicplayer.PlayerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.System.exit;

public class Main {

    static Player p = PlayerFactory.getPlayer();

    public static void main(String[] args) throws IOException {

        String songName = args[0];
        String mode = "order";

        if(!(args.length == 1)){
            mode = args[1];
        }


        if(checkFileType(songName) == 1){
            playSong(songName,checkMode(mode));

        }else if(checkFileType(songName) == 2){
            ArrayList<String> m3uSongs = m3uSongs(songName);
            playSong(m3uSongs, checkMode(mode));
        }else{
            exit(1);
        }

    }


    public static int checkFileType(String songName){
        int check;
        if(songName.endsWith(".mp3")){
            check = 1;
        }else if(songName.endsWith(".m3u")){
            check = 2;
        }else{
            System.err.println("Not a valid file. Choose either a .mp3 or .m3u file.");
            check = 3;
        }
        return check;
    }


    public static int checkMode(String mode){
        int check;
        if(mode.equals("loop")){
            check = 1;
        }else if(mode.equals("order") || mode.isBlank()){
            check = 2;
        }else if(mode.equals("random")){
            check = 3;
        }else{
            System.out.println("Not a valid mode.");
            System.out.println("Will execute in order mode.");
            check = 2;
        }
        return check;
    }

    public static ArrayList<String> m3uSongs(String songName) throws IOException {

        File m3uFile = new File(songName);

        if(!(m3uFile.isAbsolute())){
            songName = "..\\.\\" + songName;
            m3uFile = new File(songName);
        }
        Reader reader = new FileReader(m3uFile);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        ArrayList<String> playlist = new ArrayList<>() ;


        while (line!=null){

            if (!(line.startsWith("#") || line.isBlank())){

                File songFile= new File(line);
                if(!(songFile.isAbsolute())){
                    line = "..\\.\\" + line;
                    playlist.add(line);

                }else {
                    int i = line.indexOf('-');
                    playlist.add(line.substring(0, i - 1) + "\\" + line.substring(i + 2));

                }
            }

            line = bufferedReader.readLine();

        }

        return playlist;

    }


    public static void playSong(String songName, int mode){

        switch (mode){
            case 1:
                while(true){
                    callPlayer(songName);
                }
            case 2,3:
                callPlayer(songName);
                p.close();
                exit(0);
        }

    }

    private static void callPlayer(String songName) {
        File songFile = new File(songName);

        if((!songFile.isAbsolute())){
            songName = "..\\.\\"+ songName;
            songFile = new File(songName);
        }

        try {
            InputStream song = new FileInputStream(songFile);
            p.play(song);
        }catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (PlayerException e) {
            System.err.println("Something's wrong with the player: " + e.getMessage());
        }
    }

    private static void playPlaylist(ArrayList<String> mp3){
        for(int i=0;i < mp3.size();i++) {
            File songFile = new File(mp3.get(i));

            try {
                InputStream song = new FileInputStream(songFile);
                p.play(song);

            } catch (FileNotFoundException e) {
                System.err.println("File not found");
            } catch (PlayerException e) {
                System.err.println("Something's wrong with the player: " + e.getMessage());
            }

        }
    }

    public static void playSong(ArrayList<String> playlist, int mode){
        switch(mode){
            case 1:
                while(true){
                    playPlaylist(playlist);
                }
            case 2:
                playPlaylist(playlist);
                p.close();
                exit(0);
            case 3:
                playPlaylist(createRandom(playlist));
                p.close();
                exit(0);
        }
    }


    private static ArrayList<String> createRandom(ArrayList<String> playlist){
        Collections.shuffle(playlist);
        return playlist;
    }
}