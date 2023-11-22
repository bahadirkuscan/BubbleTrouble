/*
This class specifies the initial states of the time bar and the way that the bar will shrink and change color over time.
The initial state and the change method together determine the total game duration which is near 40000 ms.
Constructor and bar change method are called in the Environment class.
 */
public class Bar {
    public static final double BAR_IMAGE_SCALE_X = 16.0;
    public static final double BAR_IMAGE_SCALE_Y = 1.0;
    public static final double BAR_SCALE_Y = 0.5;
    public double bar_scaleX;
    public int bar_red;
    public int bar_green;
    public int bar_blue;

    Bar(){ // for initializing bar
        bar_scaleX = 16.0;
        bar_red = 225;
        bar_green = 225;
        bar_blue = 0;
    }

    public static void barChange(Bar bar, double time_passed){
        bar.bar_scaleX -= 0.012;
        if (bar.bar_green > 0) {
            bar.bar_green = 225 - (int) time_passed / 178; // bar goes from yellow to red as time proceeds. every 178 ms have a different green value from 225 to 0 in a 40000 ms game.
        }
    }
}
