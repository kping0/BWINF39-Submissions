/* Klasse, um einen Spieler zu Simulieren */
public class Player
{
    public static final boolean DEBUG = false;
    public int wins = 0; /* Temporärer Win-Counter */
    public int strength = 0; /* Spielerstaerke */
    public int identifier = 0; /* Einzigartiger Identifier */
    
    public Player(int strength, int wins, int identifier) /* Konstruktor */
    {
        this.strength = strength;
        this.wins = wins;
        this.identifier = identifier;
        if(DEBUG)System.out.println("Created Team with Strength: " + strength + " and wins " + wins);
    }

    public void addWin() /* wins = wins + 1 */
    {
        wins++;
        if(DEBUG)System.out.println("Added Win to Team with Strength: " + strength + " now at " + wins);
    }
}
