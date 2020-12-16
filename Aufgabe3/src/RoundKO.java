import java.util.*;

public class RoundKO
{
    public static final boolean DEBUG = false; //debug var
    
    public ArrayList<Player> players;
    public ArrayList<Game> games = new ArrayList<Game>();
    Random rand = new Random();
    
    public RoundKO(ArrayList<Player> players)
    {
        this.players = players;
    }

    public ArrayList<Integer> playNRounds(int n)
    {
        ArrayList<Integer> r = new ArrayList<Integer>(Collections.nCopies(players.size(),0));
        for(int i = 0; i < n; i++)
        {
            RoundKO node = new RoundKO(players);
            node.initFirstRound();
            int winner = node.playRound();
            r.set(winner, r.get(winner) + 1);
        }
        return r;
    }

    private void initFirstRound()
    {
         ArrayList<Integer[]> combinations = permutations(players.size());
        for(int i = 0; i < combinations.size(); i++)
        {
            Integer[] tmp = combinations.get(i);
            games.add( new Game(players.get(tmp[0]-1) , players.get(tmp[1]-1)) );
        }
    }
	
	private void initNthRound()
	{
        for(int i = 0; i < players.size(); i = i + 2)
        {
            games.add( new Game(players.get(i), players.get(i+1)) );
        }
    }

    private int playRound()
    {
        if(players.size() == 2)
        {
            if(DEBUG)System.out.println("We have reached the Final Stage");
            Game finale = new Game(players.get(0), players.get(1));
            return finale.runGame().identifier;
        }
        ArrayList<Player>firstRoundWinners = new ArrayList<Player>();
        for(int i = 0; i < games.size(); i++){
            firstRoundWinners.add(games.get(i).runGame()); //run all games, add winners
        }
        if(DEBUG)System.out.println("Descending, round complete");
        RoundKO nextRound = new RoundKO(firstRoundWinners);
        nextRound.initNthRound();
        return nextRound.playRound();
    }
    
    private ArrayList<Integer[]> permutations(int count)
    {
        assert count%2 == 0 : "Invalid number of Players, must be a multiple of 2";
        
        ArrayList<Integer> pool = new ArrayList<Integer>();
        for(int i = 1; i <= count; i++)
        {
            pool.add(i);
        }
        
        ArrayList<Integer[]> r = new ArrayList<Integer[]>();
        while(pool.size() != 0)
        {
            int z = rand.nextInt(pool.size());
            int y = pool.get(z);
            pool.remove(z);
            
            z = rand.nextInt(pool.size());
            int x = pool.get(z);
            pool.remove(z);
            
            Integer[] tmp = { x,y };
            r.add(tmp);
        }
        return r;
    }
}