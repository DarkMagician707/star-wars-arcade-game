public class Main {
    public static int LOOP_COUNT = 0;

    public static void main(String[] args) {
        while (true) {
            loopMusic();
            TitleScreen title = new TitleScreen();
            title.start();
            boolean check = true;
            while (check) {
                if (StdDraw.isKeyPressed(32)) { // When SPACE is pressed
                    Game game = new Game();
                    game.play();
                    check = false;
                }
                if (StdDraw.isKeyPressed(27)) { // when ESC is pressed, close program
                    System.exit(0);
                }
            }
            EndGame end = new EndGame();
            end.enable();
            while (!StdDraw.isKeyPressed(69)) {
                StdDraw.show();
            }
            StdDraw.clear();
        }
    }

    // loops music and ensures the loop can only be called once when application is called to avoid
    // multiple loops
    // original sound file source:
    // https://www.thesoundarchive.com/play-wav-files.asp?sound=starwars/imperial_march.wav
    public static void loopMusic() {
        if (LOOP_COUNT == 0) {
            StdAudio.loopInBackground("audio/Imperial March.wav");
            LOOP_COUNT++;
        }
    }
}
