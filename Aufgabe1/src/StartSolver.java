import java.util.*;
import java.io.*;

public class StartSolver
{
	private ArrayList<String> luecken = new ArrayList<String>();
    private ArrayList<String> woerter = new ArrayList<String>();
    private ArrayList<String> loesung = new ArrayList<String>();
    private Scanner input = new Scanner(System.in, "utf-8");
	
	/* String aufteilen nach leerzeichen und Array a anhängen */
	public void tokenizeAndAddToArray(String str, ArrayList<String> a)
	{
        String[] tokens = str.split(" ");
        for(String token : tokens)a.add(token);
    }
	
	/* Input von Stdin einlesen */
    public String readInput(String prepend) throws IOException
    {
        System.out.println(prepend);
        return input.nextLine();
    }
	
	/* Scanner Objekt von Datei öffnen */
	public Scanner getFileScannerHandle(String filename)
	{
		Scanner reader = null;
		try
		{
            reader = new Scanner(new FileInputStream(filename), "utf-8");
        }
        catch(FileNotFoundException e) 
        {
            System.out.println("Cannot open File, maybe doesnt exist?");
            System.exit(1);
        }
		return reader;
	}
	
	/* Konstruktor, von main() aufgerufen, der den Löseprozess startet */
	public StartSolver() throws IOException
	{
        Scanner reader = getFileScannerHandle(readInput("Bitte Dateinamen des Raetsels eingeben:"));
		
		tokenizeAndAddToArray(reader.nextLine(),luecken);
		tokenizeAndAddToArray(reader.nextLine(),woerter);
		
		SolveRiddle rid = new SolveRiddle(luecken,woerter,loesung);

        if(rid.solve())
		{
			/* 
			 * Wichtig! Wir müssen das Lösungsarray Invertieren, 
			 * da die SolveRiddle Objekte von LetzeLücke -> ErsteLücke die Lösungen anfügen
			 */
			Collections.reverse(loesung);
			for(String x : loesung)System.out.print(x + " ");
		} else {
			System.out.println("Keine Loesung gefunden!");
		}
	}
	
}