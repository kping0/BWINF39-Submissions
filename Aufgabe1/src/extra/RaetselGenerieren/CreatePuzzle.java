import java.util.*;
import java.io.*;

public class CreatePuzzle
{

	private ArrayList<String> luecken = new ArrayList<String>();
    private ArrayList<String> woerter = new ArrayList<String>();
    private ArrayList<String> fwoerter = new ArrayList<String>();
    Random rand = new Random();
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
	public Writer outputFile(String x) throws IOException
	{
			return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(x), "utf-8"));
	}
	public CreatePuzzle() throws IOException
	{
		Scanner reader = getFileScannerHandle(readInput("File:"));
		PrintWriter outfile = new PrintWriter(outputFile(readInput("Outfile:")));
		tokenizeAndAddToArray(reader.nextLine(), woerter);
		for(String x : woerter){
			char[] char_tmp = new char[x.length()];
			Arrays.fill(char_tmp, '_');
			int i1 = rand.nextInt(x.length()-1);
			int i2 = rand.nextInt(x.length()-1);
			char_tmp[i1] = x.charAt(i1);
			char_tmp[i2] = x.charAt(i2);
			char lc = x.charAt(x.length()-1);
			if(lc == '!' || lc == '.' || lc == '?' || lc == ',')char_tmp[x.length()-1] = lc;
			luecken.add(new String(char_tmp));
		}
		while(woerter.size() != 0)
		{
			int r = 0;
			if(woerter.size() != 1)r = rand.nextInt(woerter.size()-1);
			String x = woerter.get(r);
			char lc = x.charAt(x.length()-1);
			if(lc == '!' || lc == '.' || lc == '?' || lc == ',')
			{
				System.out.printf("Found word with special %s\n",x);
				fwoerter.add(x.substring(0, x.length()-1));
			} else
			{
				fwoerter.add(x);
			}
			woerter.remove(r);


		}
		for(String y : luecken)outfile.print(y + " ");
		outfile.println("");
		for(String y : fwoerter)outfile.print(y + " ");
		outfile.println("");
		outfile.close();
	}
	
}