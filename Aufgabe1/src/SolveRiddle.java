import java.util.*;
import java.io.*;

public class SolveRiddle{
    private ArrayList<String> blanks;
    private ArrayList<String> words;
    ArrayList<String> solution;
	
    public boolean compare(String blank, String word)
    {
        
         /* 
          * Wir überprüfen, ob die Lücke und das Wort gleich lang sind (ohne berücksichtigung von Sonderzeichen)
          */
        int blank_length = blank.length();
        int word_length = word.length();
        char blank_last = blank.charAt(blank_length-1);
        if(blank_last == '!' || blank_last == ',' || blank_last == ':' || blank_last == '.' || blank_last == '?')blank_length--; 
        if(word_length != blank_length)return false;
        /*
         * Übereinstimmung von Lücke und Wort überprüfen
         */
        for(int i = 0; i < word_length; i++){
            if( (blank.charAt(i) == '_') || (blank.charAt(i) == word.charAt(i)) ){} //wildcard or match
            else
            {
                return false;
            }
        }
        return true;
    }
    

    /* Methode, die ein DeepCopy von einem StringArray machen */
    public ArrayList<String> cloneArray(ArrayList<String>src){
        ArrayList<String>ret = new ArrayList<String>();
        for(int i = 0; i < src.size(); i++){
            ret.add(src.get(i));
        }
        return ret;
    }

    /*
     * Wenn ein Sonderzeichen am Ende von x vorhanden ist,
     * gebe es zurück (Für das Anhängen von Sonderzeichen bei dem Hinzufügen von Lösungen in solve()
     */
    private String checkForSpecial(String x)
    {
        char r = x.charAt(x.length()-1);
        if(r == '!' || r == ',' || r == ':' || r == '.' || r == '?')return Character.toString(r);
        return "";
    }

    public boolean solve(){
        for(int i = 0; i < words.size(); i++) /* Hier gehen wir durch jedes Wort im Array einmal durch */
        {
            /* Vergleich von der jetzigen Lücke mit dem Wort */
            if(compare(blanks.get(0),words.get(i)))
            { 
                
                /* wir klonen die Arrays und entfernen die Lücke und das passende Wort vom pool*/
                ArrayList<String> nBlanks = cloneArray(blanks); 
                nBlanks.remove(0);
                ArrayList<String> nWords = cloneArray(words);
                nWords.remove(i);
                
                /* neues Objekt wird erstellt, wobei die geklonten Arrays uebergeben werden */
                SolveRiddle nriddle = new SolveRiddle(nBlanks,nWords,solution);

                /* aufruf von solve() */
                if(nriddle.solve())
				{

                    /* 
                     * Wenn wir diesen Punkt erreicht haben, hat der letze aufruf von solve() TRUE zurueckgegeben, und wir 
                     * koennen unsere Loesung hinzufuegen
                     */

                    /* 
                     * Hier fügen wir noch, falls es ein Sonderzeichen am Ende der Lücke gab,
                     * dieses an mithilfe von der Methode checkForSpecial(), da die compare() funktion dieses Ignoriert hat
                     */
                    solution.add(words.get(i) + checkForSpecial(blanks.get(0))); 
                    return true;
                }
            }

        }
        /* 
         * Überprüfen, ob wir die Lösung haben (wenn keine Lücken mehr übrig sind)
         */
        if(blanks.size() == 0)
		{
                System.out.println("Puzzle Solved!");

                /* 
                 * Mit diesem TRUE wird die kaskade ausgelöst, wodurch alle vorherigen SolveRiddle Objekte 
                 * ihre lösung in das Lösungsarray hinzufügen
                 */
                return true;
        }
        /* 
         * Wenn wir in diesem SolveRiddle Objekt kein passendes Wort für die Lücke gefunden haben,
         * und wir noch nicht am Ende sind, dann müssen wir Backtracken, und andere Wörter für die Vorherige Lücke ausprobieren
         * daher FALSE
         */
        return false;
    }
    
    public SolveRiddle(ArrayList<String>b,ArrayList<String>w,ArrayList<String>s)
    {
        blanks = b;
        words = w;
        solution = s;
    }
    
}