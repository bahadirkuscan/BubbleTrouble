/*
This class specifies the attributes and movements of balls on different levels.
There are 2 constructors - one used for creating the balls in the beginning of the game and one for creating
new balls that appear after the division of the upper-level ball.
Constructors and movement method are called in the Environment class.
 */
public class Ball {
    public int level;
    public double x_coordinate;
    public double y_coordinate;
    public double velocity_x;
    public double velocity_y;
    public double maximum_height;
    public double radius;
    public static final double GRAVITY = -0.00024 * Environment.SCALE_Y2;

    Ball(int level){   // for initializing balls
        maximum_height = Player.PLAYER_HEIGHT * 1.4 * Math.pow(1.75,level);
        this.level = level;
        radius = Environment.SCALE_Y2 * 0.0175 * Math.pow(2 , level);

        if (level == 1){
            x_coordinate = 16.0/3;
            y_coordinate = 2.0;
            velocity_x = -0.032;
            velocity_y = Math.sqrt(2 * -1 * GRAVITY * maximum_height);
        }

        else{
            x_coordinate = 4.0;
            y_coordinate = 2.0;
            velocity_x = 0.032;
            velocity_y = Math.sqrt(2* -1 * GRAVITY * maximum_height);
        }

    }

    Ball(double x_coordinate, double y_coordinate, double velocity_x, int level){    // for new balls after division
        maximum_height = Player.PLAYER_HEIGHT * 1.4 * Math.pow(1.75,level);
        velocity_y = Math.sqrt(2* -1 * GRAVITY * maximum_height);
        radius = Environment.SCALE_Y2 * 0.0175 * Math.pow(2 , level);
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
        this.velocity_x = velocity_x;
        this.level = level;
    }

    public static void moveBall(Ball ball){
        ball.x_coordinate += ball.velocity_x;
        ball.y_coordinate += ball.velocity_y;
        ball.velocity_y += GRAVITY;

    }
}
