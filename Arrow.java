/*
In this class the attributes and movement of the arrow that player shoots are specified.
There are 2 constructors - one for initializing/resetting and one for activating the arrow in the desired position.
Constructor and movement method are called in the Environment class.
 */
public class Arrow {
    public static boolean is_activated;
    public double x_coordinate;
    public double height_scale;
    public double arrow_tip_y_coordinate; // arrow tip is needed for hit-box
    public final static double Y_COORDINATE = 0.0;
    public final static double ARROW_WIDTH = Player.PLAYER_WIDTH / 3;
    private final static double INITIAL_HEIGHT_SCALE = 2.0;
    public final static double MAXIMUM_HEIGHT_SCALE = 18.0;

    Arrow(){  // this constructor is used to initialize or reset the arrow to inactive state
        is_activated = false;
        height_scale = INITIAL_HEIGHT_SCALE;
        arrow_tip_y_coordinate = 1;
    }

    Arrow(double x_coordinate){  // this constructor is used when the player shoots an arrow
        this.x_coordinate = x_coordinate;
        height_scale = INITIAL_HEIGHT_SCALE;
        arrow_tip_y_coordinate = 1;
        is_activated = true;
    }

    public static void moveArrow(Arrow arrow){ // arrow moves by increasing image scale, so the tip coordinate should go half the speed of scale increment
        arrow.height_scale += 0.36;
        arrow.arrow_tip_y_coordinate += 0.18;
    }

}
