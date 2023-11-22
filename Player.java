/*
In this class main character's attributes (some of them are constant some of them are variable) and movement are specified.
Constructor and the movement method are called in the Environment class.
 */

public class Player {
    public double x_coordinate;
    public final static double PLAYER_HEIGHT = 9.0/8.0;
    public final static double Y_COORDINATE = PLAYER_HEIGHT / 2;
    public final static double PLAYER_WIDTH = PLAYER_HEIGHT * 27.0/37.0;

    Player(double x_coordinate){ // for initializing player
        this.x_coordinate = x_coordinate;
    }

    public static void movePlayer(Player player, String direction){
        if (direction.equals("right")){
            player.x_coordinate = player.x_coordinate + 0.08;
        }
        else{
            player.x_coordinate = player.x_coordinate - 0.08;
        }
    }

}
