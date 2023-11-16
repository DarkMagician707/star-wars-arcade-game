//This class is responsible for the endscreen.

import java.awt.Color;
import java.awt.Font;

public class EndGame {
    public EndGame() {

    }

    public void enable() {

        StdDraw.setXscale(0, 1.0);
        StdDraw.setYscale(0, 1.0);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledSquare(0.0, 0.0, 1.0);   // Drawing a filled square with a Black Background

        // text 1

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Broadway", Font.BOLD, 30));
        StdDraw.text(0.5, 0.55, "The Game Has Ended!");
        StdDraw.text(0.5, 0.5, "Press (E) to return to Menu");
    }
}
