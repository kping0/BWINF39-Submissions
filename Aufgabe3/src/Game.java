import java.util.*;

/*
 * Klasse, welche ein Spiel zwischen zwei Spielern simuliert
 */
public class Game
{
    public static final boolean DEBUG = false;
    Player t1, t2;

    /* Konstruktor */
    public Game(Player t1, Player t2)
    {
        this.t1 = t1;
        this.t2 = t2;
    }

    /* Zufallszahl wuerfeln */
    private int getRandomNumber(int min, int max)
    {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /* Methode, die einen Gewinner zufällig ermittelt (Urnenprinzip) */
    private Player chooseWinner()
    {    
        if(t1.strength >= getRandomNumber(1, t1.strength + t2.strength))return t1;
        return t2;
    } 

    /* 
     * Spiel Simulieren
     */
    public Player runGame()
    {
        Player winner = chooseWinner(); /* Gewinner auslosen */
        
        if(DEBUG)System.out.println("Game beween strength " + t1.strength + " and " + t2.strength + ", WINNER " + winner.strength);

        winner.addWin(); /* einen win hinzufuegen - wichtig fuer liga */
        return winner; /* gewinner zurueckgeben - wichtig fuer K.O. */
    }
}
