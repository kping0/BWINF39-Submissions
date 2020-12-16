import java.io.*;
import java.util.*;

public class SolverInput
{
    private ArrayList<Triangle> list = new ArrayList<Triangle>();
    private Scanner input = new Scanner(System.in);
    
    /* Input aus STDIN einlesen mit Vortext und als String zurückgeben */
    public String readInput(String prepend) throws IOException
    {
        System.out.println(prepend);
        return input.nextLine();
    }
    
    /* Dokument nach Dateinamen öffnen und Scanner zurückgeben */
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
	
	/* Konstruktor, liest Datei ein und startet den Lösungsprozess */
    public SolverInput() throws Exception
    {
        Scanner reader = getFileScannerHandle(readInput("Bitte Dateinamen fuer Aufgabe A2 eingeben:"));
        System.out.println("Anzahl der Figuren, die Vorkommen: " + reader.nextInt());
        if(reader.nextInt() != 9){
            System.out.println("Puzzle Solver only works with 9 Figures!");
            System.exit(1);
        }
        for(int i = 0; i<9; i++){
            list.add(new Triangle(reader.nextInt(), reader.nextInt(), reader.nextInt())); //Dreiecke aus Datei auslesen und in Liste einfügen
        }
        /* Problem Lösen */
        Solver s = new Solver(list,new ArrayList<Triangle>(), 0);
        if(!s.solve()){
            System.out.println("No solution found...");
        }
    }
}