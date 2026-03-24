package com.pao.laboratory05.biblioteca;

import com.pao.laboratory05.playlist.Song;
import com.pao.laboratory05.playlist.SongDurationComparator;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {
//    Constructor privat, getInstance() cu Holder intern (pattern din Lab 01)
//    Câmp: private Carte[] carti (inițializat new Carte[0])
    private Carte[] carti;
    private BibliotecaService(){
        this.carti=new Carte[0];
    }

    // Holder intern — JVM garantează că se inițializează o singură dată
    private static class Holder {
        private static final BibliotecaService INSTANCE = new BibliotecaService();
    }

    // Punct unic de acces la instanță
    public static BibliotecaService getInstance() {
        return Holder.INSTANCE;
    }
//    void addCarte(Carte carte) — resize + adaugă + printează confirmare
    public void addCarte(Carte carte){
        Carte[] newBooks = new Carte[carti.length + 1];
        System.arraycopy(carti, 0, newBooks, 0, this.carti.length);
        newBooks[newBooks.length-1]=carte;
        this.carti = newBooks;
    }
//    void listSortedByRating() — clonează, Arrays.sort(copy) (natural = Comparable), afișează
//    void listSortedBy(Comparator<Carte> comparator) — clonează, Arrays.sort(copy, comparator), afișează
    public void listSortedByRating(){
    Carte[] copy = this.carti.clone();
    Arrays.sort(copy);
    System.out.println(Arrays.toString(copy).replace(", C", "\nC"));
}
    public void listSortedBy(Comparator<Carte> comparator){
        Carte[] copy=this.carti.clone();
        Arrays.sort(copy, comparator);
        System.out.println(Arrays.toString(copy).replace(", C", "\nC"));
    }
}
