//This class is responsible for the titlescreen.

import java.awt.Color;
import java.awt.Font;

public class TitleScreen {

    public TitleScreen() {

    }

    public void start() {
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(0, 1.0);
        StdDraw.setYscale(0, 1.0);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.picture(0.5, 0.5, "images/Background.jpg", 1.7, 1.2);
        // sets background as a picture
        // original image source:
        // https://www.wallpaperflare.com/meteors-spaceship-tie-fighter-star-wars-tie-interceptor-wallpaper-uopnl

        StdDraw.setPenColor(Color.GREEN);
        StdDraw.setFont(new Font("Broadway", Font.BOLD, 40));
        StdDraw.text(0.5, 0.70, "Imperial Fighter");

        StdDraw.setPenColor(Color.CYAN);
        StdDraw.setFont(new Font("Broadway", Font.BOLD, 20));
        StdDraw.text(0.5, 0.6, "Blast the Rebel Scum!");
        StdDraw.text(0.5, 0.55, "FOR THE EMPIRE!");

        StdDraw.setPenColor(Color.red);
        StdDraw.text(0.5, 0.45, "Press SPACE to Begin:");

        StdDraw.setFont(new Font("Broadway", Font.BOLD, 18));
        StdDraw.text(0.5, 0.40, "Controls of Game:");
        StdDraw.text(0.5, 0.34, "Shoot (SPACE)");
        StdDraw.text(0.5, 0.28, "Rotate Left (Left arrow), Rotate Right (Right arrow)");
        StdDraw.text(0.5, 0.23, "Move: Left (A), Right (D)");
        StdDraw.text(0.5, 0.18, "Quit Game (Q), Exit (ESC)");
        StdDraw.text(0.5, 0.13, "There are endless waves, try to score high!");
        StdDraw.show();
    }
}
