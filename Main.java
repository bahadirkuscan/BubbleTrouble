/*
This class is the client code. In this class, the game is initialized and run by calling Environment constructor and method in
an infinite loop which is used for replaying the game.
 */
public class Main {
    public static void main(String[] args){
        StdDraw.enableDoubleBuffering();
        while (true){   // replaying loop that goes on infinitely until player chooses to quit in environment's game() method
            new Environment(); // all the game elements are initialized in this constructor
            Environment.game(); // game starts
        }
    }
}