import java.io.*;
import java.util.*;

public class RoundLiga
{
    public static final boolean DEBUG = false; //debug var
    
    public ArrayList<Player> players; /* Spieler-Array */
    public ArrayList<Game> games = new ArrayList<Game>(); /* Array, wo alle moeglichen Spiele gespeichert werden */
    

    /* 
     * Konstruktor - Initialisiert das Array Games 
     */
    public RoundLiga(ArrayList<Player> players) 
    {
        this.players = players;
        
        /* Array mit allen moeglichen Spieler-Kombinationen erstellen */
        ArrayList<Integer[]> combinations = permutations(players.size()); 

        /* Fuer jede von diesen Kombinationen ein Spiel zwischen den Spielen erstellen */
        for(int i = 0; i < combinations.size(); i++){
            Integer[] tmp = combinations.get(i);
            games.add( new Game(players.get(tmp[0]-1) , players.get(tmp[1]-1)) ); // (Die -1 ist noetig, da das Players Array bei 0 anfängt, d.h. das [1,2] ein Spiel zwischen Spieler 0 und Spieler 1 erstellt)
        }
    }

    /* 
     * Methode, die beliebig viele Runden Simuliert, ruft die Methode playRound() auf 
     * - Gibt ein Array zurück, welches die Anzahl von Gewinnen pro Spieler enthaelt 
     */
    public ArrayList<Integer> playNRounds(int n)
    {
        System.out.println("Games per Round: nCr(" + players.size() + ", 2) = " + (fact(players.size())/ (fact(2)*fact(players.size()-2))) + ", with " + n + " rounds"); /* INFO Nachicht */
        
        ArrayList<Integer> r = new ArrayList<Integer>(Collections.nCopies(players.size(),0));  /* Gewinn-Anzahl-Array */
        
        /*
         * n-mal eine Runde spielen, und den Gewinner notieren
         */
        for(int i = 0; i < n; i++){

        	/* Runde Spielen */
            int winner = playRound(); 

            /* Der index von r entspricht dem Spieler, und der Wert von r[index] der Anzahl der Gewinne */
            r.set(winner, r.get(winner) + 1); 
        }
        return r; /* Array mit der Anzahl der Gewinne zurueckgeben */
    }

    /* Spielt eine Runde */
    private int playRound()
    {

    	/* Alle Wins zuruecksetzen */
        clearWins();
        
        /* Alle Games einmal durchfuhren */
        for(int i = 0; i < games.size(); i++){
            games.get(i).runGame(); //run all games
        }
        
        /* Den Sieger ermitteln (der mit den meisten wins) */
        int winner = 0;
        for(int i = 1; i < players.size(); i++){
            if(players.get(winner).wins < players.get(i).wins)winner = i;
        }
        return winner; /* winner zurueckgeben */
    }


    /* Setzt die temporaeren Gewinnzaehler von allen Spielen zurueck */
    private void clearWins()
    {
        for(int i = 0; i < players.size(); i++)players.get(i).wins = 0;
        if(DEBUG)System.out.println("----CLEARING SCORES ----");
    }

    /* Methode, um eine Beliebige Zahl zu Faktorisieren */
    private static long fact(int i) 
    {
            if(i <= 1) {
                    return 1;
            }
           return i * fact(i - 1);
    }
    
    /* 
     * Methode, welche alle Mögliche Kombinationen von 1 bis zur uebergebenen Zahl (count) ermittelt und diese 
     * in einem Array zurueckgibt - wird benutzt um Alle gegen Alle einmal spielen zu lassen
     */
    private ArrayList<Integer[]> permutations(int count)
    {
    	/* Pool erstellen mit allen Zahlen */
        ArrayList<Integer> pool = new ArrayList<Integer>();
        for(int i = 1; i <= count; i++)
        {
            pool.add(i);
        }

        /* Alle Kombinationen durchgehen, und diese in das Array r einfuegen */
        ArrayList<Integer[]> r = new ArrayList<Integer[]>();
        for(int i = 1; i < count; i++)
        {
            for(int x = 1; x < pool.size(); x++)
            {
                Integer tmp[] = {i, pool.get(x)};
                r.add(tmp);
            }
            pool.remove(0);
        }
        return r;
    }
}
