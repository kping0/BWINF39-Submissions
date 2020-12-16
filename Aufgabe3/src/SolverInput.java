import java.io.*;
import java.util.*;

public class SolverInput
{
    public static final int n = 5000000; //Anzahl der Runden (Am besten eine große Zahl)
    private Scanner stdin = new Scanner(System.in); //Scanner to get File name from user
    ArrayList<Player> players = new ArrayList<Player>(); // Array um Spieler und ihre Staerke festzuhalten

    public String readInput(String prepend) throws IOException //Input von STDIN einlesen
    {
        System.out.println(prepend);
        return stdin.nextLine();
    }
	
	public Scanner getFileScannerHandle(String filename) //Open File handle
	{
		Scanner reader = null;
		try
		{
            reader = new Scanner(new FileInputStream(filename), "utf-8");
        }
        catch(FileNotFoundException e) 
        {
            System.out.println("Cannot open File, maybe doesnt exist?");
            System.exit(1); //wenn wir die Datei nicht oeffenen koennen, dann programm terminieren
        }
		return reader;
	}
    
 	/* 
 	 * Konstruktor, liest eine Datei mit den Spielerstaerken ein, und erstellt aus diesem Spieler, die in das Player Array eingefuegt werden
 	 */
    public SolverInput() throws Exception 
    {
		Scanner reader = getFileScannerHandle(readInput("Bitte Spielstaerke-Datei name angeben:"));
		int player_count = reader.nextInt();
        System.out.println("Anzahl der Spieler: " + player_count);
        
        for(int i = 0; i < player_count; i++)
        {
            players.add(new Player(reader.nextInt(),0,i));
        }
    }
    
    /* Ausfuehrung */
    public void run()
    {
        
    	/* RoundLiga N-mal spielen */
        RoundLiga x = new RoundLiga(players);
        ArrayList<Integer> liga_result = x.playNRounds(n);
       
        /* K.O. N-mal spielen */ 
        RoundKO y = new RoundKO(players);
        ArrayList<Integer> ko_result = y.playNRounds(n);


        int best_player = 0;
        for(int i = 0; i < players.size(); i++)
        {
        	/* Merken des Allgemein Besten Spielers (hoechste Spielstaerke) */
            if(players.get(best_player).strength < players.get(i).strength)best_player = i;

            /* Ausgeben, wie jeder Spieler Abgeschnitten hat in RoundLiga und K.O. */ 
            System.out.println("Spieler " + players.get(i).identifier +" mit Spielstaerke: " + players.get(i).strength + " hat bei RoundLiga " + liga_result.get(i) +  "/" + n + " ("  + (float) liga_result.get(i) / n * 100 +  "%) Spielen gewonnen, bei K.O. " + ko_result.get(i) + "/" + n + " ("+ (float) ko_result.get(i) / n * 100 + "%) Spielen gewonnen(" + (float)liga_result.get(i)/n*100 +" vs. " + (float)ko_result.get(i)/n*100 + ")");
        }

        /* Empfehlung an Tobi geben, welche Variante mit einer hoeheren Warscheinlichkeit den Besten Spieler hervorbringt */
        if(liga_result.get(best_player) > ko_result.get(best_player)){
            System.out.println("In dieser Aufstellung gewinnt der beste Spieler (Spielstaerke: " + players.get(best_player).strength + " ) mit einer hoeheren Warscheinlichkeit in einem RoundLiga Spiel");
        } else {
            System.out.println("In dieser Aufstellung gewinnt der beste Spieler (Spielstaerke: " + players.get(best_player).strength + " ) mit einer hoeheren Warscheinlichkeit in einem K.O. Spiel");
        }
        
    }
}
