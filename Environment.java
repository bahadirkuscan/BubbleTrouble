/*
This class is never used as a specific object. Therefore, every data field and method are static.
This class keeps the general game constants in its data fields, initializes the game by calling other class constructors
in its constructor, checks the interactions between game elements and moves/updates them according to the game rules with
the help of its methods. Also, this class implements the main game loop and replaying/quitting function of the program.
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Environment {
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 500;
    public static final double SCALE_X1 = 0.0;
    public static final double SCALE_X2 = 16.0;
    public static final double SCALE_Y1 = -1.0;
    public static final double SCALE_Y2 = 9.0;
    private static final int PAUSE_DURATION = 15;
    private static boolean game_over_status;
    private static Arrow arrow; // these data fields and environment methods are all static since a particular environment object won't be in use, only the class is needed
    private static Bar bar;
    private static Player player;
    private static ArrayList<Ball> balls;
    private static String result;
    private static double start_time;


    Environment(){ // this constructor initializes basically everything of the game
        StdDraw.setCanvasSize(CANVAS_WIDTH,CANVAS_HEIGHT);
        StdDraw.setXscale(SCALE_X1,SCALE_X2);
        StdDraw.setYscale(SCALE_Y1,SCALE_Y2);
        game_over_status = false;
        balls = new ArrayList<>();
        balls.add(new Ball(0));
        balls.add(new Ball(1));
        balls.add(new Ball(2));
        arrow = new Arrow();
        bar = new Bar();
        player = new Player(8.0);
        result = "lose";
        start_time = System.currentTimeMillis();
    }

    private static void drawCanvas(){ // method used to clear, draw and show the current state of the game
        StdDraw.clear();
        StdDraw.picture(SCALE_X2 / 2,SCALE_Y2 / 2,"background.png", SCALE_X2, SCALE_Y2);

        if (Arrow.is_activated){
            StdDraw.picture(arrow.x_coordinate, Arrow.Y_COORDINATE, "arrow.png", Arrow.ARROW_WIDTH, arrow.height_scale);
        }

        StdDraw.picture(SCALE_X2 / 2,-0.5,"bar.png", Bar.BAR_IMAGE_SCALE_X, Bar.BAR_IMAGE_SCALE_Y);

        StdDraw.setPenColor(bar.bar_red, bar.bar_green, bar.bar_blue);
        StdDraw.filledRectangle(bar.bar_scaleX / 2, -0.5, bar.bar_scaleX / 2, Bar.BAR_SCALE_Y / 2);

        StdDraw.picture(player.x_coordinate, Player.Y_COORDINATE, "player_back.png", Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);

        for (Ball ball : balls){
            StdDraw.picture(ball.x_coordinate, ball.y_coordinate, "ball.png", ball.radius * 2, ball.radius * 2);
        }
        StdDraw.show();
    }

    private static void gameOverScreen(String status){ // method used for drawing a special canvas after the game is over
        // all those drawings before the if body are in common for both cases
        StdDraw.clear();
        StdDraw.picture(8.0,-0.5,"bar.png", Bar.BAR_IMAGE_SCALE_X, Bar.BAR_IMAGE_SCALE_Y);
        StdDraw.filledRectangle(bar.bar_scaleX / 2, -0.5, bar.bar_scaleX / 2, Bar.BAR_SCALE_Y / 2);
        StdDraw.picture(8.0,4.5,"background.png",16.0,9.0);
        StdDraw.picture(player.x_coordinate, Player.Y_COORDINATE, "player_back.png", Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        StdDraw.picture(8.0,10/2.18,"game_screen.png",16/3.8,10.0/4);
        StdDraw.setPenColor(Color.black);
        StdDraw.setFont(new Font("Helvetica",Font.ITALIC,15));
        StdDraw.text(8.0,10/2.3,"To Replay Click “Y”");
        StdDraw.text(8.0,10/2.6,"To Quit Click “N”");
        StdDraw.setFont(new Font("Helvetica",Font.BOLD,30));

        if (status.equals("win")){
            StdDraw.text(8.0,10/2.0,"YOU WON!");
        }
        else {
            StdDraw.text(8.0,10/2.0,"GAME OVER!");
        }
        StdDraw.show();
    }

    private static void applyChanges(double time_passed){ // method used for checking collisions, implementing their consequences and updating every object that move or change over time
        double player_left_border = player.x_coordinate - Player.PLAYER_WIDTH / 2;
        double player_right_border = player.x_coordinate + Player.PLAYER_WIDTH / 2;
        double player_top_border = Player.Y_COORDINATE + Player.PLAYER_HEIGHT / 2;

        //BAR
        Bar.barChange(bar,time_passed);
        if (bar.bar_scaleX <= 0.0){
            bar.bar_scaleX = 0.0; // negative scale causes problems
            game_over_status = true;
        }

        //BALL
        ArrayList<Ball> balls_modified = new ArrayList<>();
        balls_modified.addAll(balls); // a copy of the array list is needed because list needs to be modified while looping through it
        for (Ball ball : balls){ // loop for checking collisions and updating objects according to their consequences
            // Player-Ball collision checking
            // ball hits top-left corner of player
            if (Math.pow(player_left_border - ball.x_coordinate , 2) + Math.pow(player_top_border - ball.y_coordinate , 2) <= Math.pow(ball.radius, 2)){
                game_over_status = true;
                break;
            }

            // ball hits top-right corner of player
            else if (Math.pow(player_right_border - ball.x_coordinate , 2) + Math.pow(player_top_border - ball.y_coordinate , 2) <= Math.pow(ball.radius, 2)){
                game_over_status = true;
                break;
            }

            // ball hits player from left
            else if (ball.y_coordinate < player_top_border && ball.x_coordinate + ball.radius >= player_left_border && ball.x_coordinate + ball.radius <= player_right_border){
                game_over_status = true;
                break;
            }

            // ball hits player from top
            else if (player_left_border < ball.x_coordinate && ball.x_coordinate < player_right_border  && ball.y_coordinate - ball.radius <= player_top_border){
                game_over_status = true;
                break;
            }

            // ball hits player from right
            else if (ball.y_coordinate < player_top_border && ball.x_coordinate - ball.radius <= player_right_border && ball.x_coordinate - ball.radius >= player_left_border){
                game_over_status = true;
                break;
            }


            //Ball-Wall collision checking
            if (ball.x_coordinate - ball.radius + ball.velocity_x < 0.0 || ball.x_coordinate + ball.radius + ball.velocity_x > 16.0){
                ball.velocity_x *= -1;
            }

            //Ball-Floor collision checking
            if (ball.y_coordinate - ball.radius + ball.velocity_y < 0.0){
                ball.velocity_y = Math.sqrt(2 * -1 * Ball.GRAVITY * ball.maximum_height); // ball loses speed and goes back to its original speed after hitting the ground in the original game for some reason
            }

            //Ball-Arrow collision checking
            if (Arrow.is_activated){
                //Arrow tip hits the ball
                if (Math.pow(arrow.x_coordinate - ball.x_coordinate , 2) + Math.pow(arrow.arrow_tip_y_coordinate - ball.y_coordinate , 2) <= Math.pow(ball.radius, 2)){
                    balls_modified.remove(ball);
                    if (ball.level > 0){
                        balls_modified.add(new Ball(ball.x_coordinate, ball.y_coordinate, ball.velocity_x, ball.level - 1));
                        balls_modified.add(new Ball(ball.x_coordinate, ball.y_coordinate, ball.velocity_x * -1, ball.level - 1));
                    }
                    arrow = new Arrow(); // arrow is reset
                }

                //Ball hits arrow body
                if (arrow.x_coordinate < ball.x_coordinate + ball.radius && arrow.x_coordinate > ball.x_coordinate - ball.radius && arrow.arrow_tip_y_coordinate >= ball.y_coordinate - ball.radius) {
                    balls_modified.remove(ball);
                    if (ball.level > 0) {
                        balls_modified.add(new Ball(ball.x_coordinate, ball.y_coordinate, ball.velocity_x, ball.level - 1));
                        balls_modified.add(new Ball(ball.x_coordinate, ball.y_coordinate, ball.velocity_x * -1, ball.level - 1));
                    }
                    arrow = new Arrow(); // arrow is reset
                }
            }
        }
        balls = (ArrayList) balls_modified.clone(); // actual ball list is updated for movement loop

        //Moving the balls
        for (Ball ball : balls){
            Ball.moveBall(ball);
        }

        //ARROW
        if (!Arrow.is_activated && StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){ // player shoots an arrow if there isn't one already
            arrow = new Arrow(player.x_coordinate);
        }
        if (Arrow.is_activated){
            if (arrow.height_scale >= Arrow.MAXIMUM_HEIGHT_SCALE){ // arrow hits the ceiling
                arrow = new Arrow(); // arrow is reset
            }
            else {
                Arrow.moveArrow(arrow); // arrow moves
            }
        }

        //PLAYER
        // move the player if there is no collision with the wall
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && player_left_border - 0.04 >= 0.0){
            Player.movePlayer(player,"left");
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && player_right_border + 0.04 <= 16.0){
            Player.movePlayer(player, "right");
        }
    }

    private static int replay_actions(){ // method used for taking input to replay or quit the game
        if (StdDraw.isKeyPressed(KeyEvent.VK_Y)){
            return 0;
        }
        else if (StdDraw.isKeyPressed(KeyEvent.VK_N)){
            return 1;
        }
        else{
            return 2; // method is used in an infinite loop so I return an arbitrary number while waiting for an input from the user
        }
    }

    public static void game(){ // method used for starting, continuing, restarting or quitting the game
        while (!game_over_status){  // main loop for drawing and updating the game
            drawCanvas();
            applyChanges(System.currentTimeMillis() - start_time);
            if (balls.isEmpty()){ // all balls are popped
                game_over_status = true;
                result = "win";
                break;
            }
            StdDraw.pause(Environment.PAUSE_DURATION);
        }

        gameOverScreen(result);

        while (true){ // loop for waiting for input
            int input = replay_actions();
            if (input == 0){
                break; // we go back to the infinite loop in the client code by breaking from this loop
            }
            else if (input == 1){
                System.exit(0); // program finishes directly as player chooses not to replay
            }
        }
    }

}
