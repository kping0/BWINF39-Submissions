import java.util.*;
import java.io.*;

public class Solver
{
    public static final boolean DEBUG = false; //Debug Output (REALLY SLOW excecution)
    
    private ArrayList<Triangle> list;
    private ArrayList<Triangle> solution;
    private int depth;
    
    /*
     * Compare1() Überprüft ob die Bedingungen, die von einem Dreieck t erfüllt werden müssen, damit es 
     * das nächste Dreieck in der Lösung s wird, erfüllt sind. 
     * Wenn die benötigten Bedingungen erfüllt sind, dann ist der Rückgabe wert TRUE, sonst FALSE
	 *
     * Hier stehen die Zahlen 0-8 für die Buchstaben A-I
     */
    public boolean compare1(ArrayList<Triangle> s, Triangle t) throws Exception
    {
        if(DEBUG){
            System.out.println("\n\n Comparing Solution with size = " + s.size() + "with Triangle");
            t.printSides(s.size());
        }
        switch(s.size())
        {
            case 0: // Position A
                return true;
            case 1: // Position B
                return true;
            case 2: // Position C
                if( s.get(0).s3 == -1 * t.s2 && s.get(1).s2 == -1 * t.s1 ) return true;
                break;
            case 3: // Position D
                if( s.get(2).s3 == -1 * t.s1 ) return true;
                break;
            case 4: // Position E
                return true;
            case 5: // Position F
                if( s.get(4).s2 == -1 * t.s1 && s.get(1).s3 == -1 * t.s2 ) return true;
                break;
            case 6: // Position G
                if( s.get(5).s3 == -1 * t.s1 ) return true;
                break;
            case 7: // Position H
                if( s.get(6).s2 == -1 * t.s1 && s.get(3).s3 == -1 * t.s2 ) return true;
                break;
            case 8: // Position I
                if(s.get(7).s3 == -1 * t.s1) return true;
                break;
            default: // Kommen mit der Position nicht klar
                throw new Exception("compare() called with s.size() == " + s.size() + " ,wtf?");
        }
        return false;
    }

    /*
     * Erstellt ein "Deep-Copy"(Keine Referenzen bzw. Pointer einzelner Elemente) von einem Triangle Array
     */
    public ArrayList<Triangle> cloneArray(ArrayList<Triangle> src)
    {
        ArrayList<Triangle>ret = new ArrayList<Triangle>();
        for(int i = 0; i < src.size(); i++)
        {
            Triangle tmp = src.get(i);
            ret.add(new Triangle(tmp.s1,tmp.s2,tmp.s3));
        }
        return ret;
    }

    /*
     * Implementierung vom Backtracking-Algorithmus
     */
    public boolean solve() throws Exception
    {
        for(int i = 0; i < list.size(); i++) /* Hier gehen wir den Übergebenen Pool durch */
        {
            for(int x = 0; x < 3; x++) /* Da jedes Dreieck drei Seiten hat, daher 3x gedreht werden muss */
            {
                if(compare1(solution, list.get(i))) /* Schauen, ob das Jetzige Dreieck die Bedingungen erfüllt */
                {
                	/* Wenn wir hier ankommen, haben wir ein passendes Dreieck für die Position gefunden */
                    if(DEBUG)System.out.println("Match( i = " + i + " )  in DEPTH = " + depth + ", SPAWNING NEXTNODE");

                    /* Hier klonen wir den Pool und die Lösung */
                    ArrayList<Triangle> l2 = cloneArray(list); 
                    l2.remove(i);
                
                    solution.add(list.get(i));
                    ArrayList<Triangle> s2 = cloneArray(solution);
                
                	/* Erstellen ein neues Objekt Solver und übergeben die geklonten Listen */
                    Solver nextNode = new Solver(l2,s2,depth+1);
                    if(nextNode.solve())
                    {
                    	/* Das letzte erstellte Objekt hat die Lösung gefunden und ausgedruckt */
                        return true;
                    } else 
                    {
                    	/*
                    	 * Solver Objekt nextNode hat alle Möglichkeiten ausprobiert, und keine Lösung gefunden
						 * daher müssen wir das jetzige Dreieck wieder aus der Liste entfernen.
                    	 */
                        if(DEBUG)System.out.println("spawned Node Exhausted all Options, on " + depth + " with i = " + i + " , TRYING OTHER");
                        solution.remove(solution.size()-1);
                    }
                }
                list.get(i).rotate(); //Dreieck drehen, damit alle Möglichkeiten durchprobiert werden
            }
        }
        if(list.size() == 0)
        {
        	/* 
        	 * Wir haben die Lösung gefunden!
        	 */
            System.out.println("Solved Puzzle!");
            for(int z = 0; z < solution.size(); z++){
                solution.get(z).printSides(z);
             }
             System.out.println("(Siehe Anordnung_Dreieck.png)");
            return true; //Kaskadiert rekursiv zurück
        }

        /*
         * Wenn wir an dieser Stelle ankommen, haben wir alle Möglichkeiten für die jetzige Position
         * durchprobiert, und haben keine Lösung gefunden, daher müssen wir Backtracken
         */
        if(DEBUG)System.out.println("Exhausted all options on level " + depth + ", BACKTRACKING");
        return false; 
    }
    

    /*
     * Konstruktor, welcher den Pool List, und die BISHERIGE Lösung übergeben bekommt (+ DEBUG Parameter depth) 
     */
    public Solver(ArrayList<Triangle> l, ArrayList<Triangle> s, int d)
    {
        list = l;
        solution = s;
        depth = d;
    }
}