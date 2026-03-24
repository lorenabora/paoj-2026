package com.pao.laboratory05.playlist;

import java.util.Arrays;

public class Playlist {
    private String name;
    private Song[] songs;
//    Playlist(String name) — constructor
    Playlist(String name){
        this.name=name;
        this.songs=new Song[0];
    }

    public String getName(){ return this.name;}

//    void addSong(Song song) — adaugă cu pattern-ul de resize (System.arraycopy)
    public void addSong(Song song){
        Song[] newSongs = new Song[songs.length + 1];
        System.arraycopy(songs, 0, newSongs, 0, this.songs.length);
        newSongs[newSongs.length-1]=song;
        songs = newSongs;
    }
//    void printSortedByTitle() — clonează array-ul, Arrays.sort(copy), afișează
    public void printSortedByTitle(){
        Song[] copy = this.songs.clone();
        Arrays.sort(copy);
        System.out.println(Arrays.toString(copy).replace(", S", "\nS"));
    }
//    void printSortedByDuration() — clonează, Arrays.sort(copy, new SongDurationComparator()), afișează
    public void printSortedByDuration(){
        Song[] copy=this.songs.clone();
        Arrays.sort(copy, new SongDurationComparator());
        System.out.println(Arrays.toString(copy).replace(", S", "\nS"));
    }
//    int getTotalDuration() — suma durationSeconds din toate song-urile
    public int getTotalDuration(){
        int sum=0;
        for(int i=0; i<this.songs.length; i++){
            sum+=songs[i].durationSeconds();
        }
        return sum;
    }
}
