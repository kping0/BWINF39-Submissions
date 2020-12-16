import java.util.*;
import java.io.*;

public class Triangle
{
    public int s1, s2, s3; //Alle Kanten des Dreiecks
    private static char[] position_characters = {'A','B','C','D','E','F','G','H','I'};

    /* Methode, um das Dreieck zu drehen */
    public void rotate() 
    {
        int tmp = s3;
        s3 = s2;
        s2 = s1;
        s1 = tmp;
    }
    
    /* Konstruktor */
    public Triangle(int x, int y, int z)
    {
        s1 = x;
        s2 = y;
        s3 = z;
    }

    /* Methode um Lösung auszudrucken */
    public void printSides(int z){
        System.out.println("Dreieck an Position " + position_characters[z] + " hat die Kanten s1 = "+ s1+ " ; s2 = " + s2 + " ; s3 = " + s3);
    }
}