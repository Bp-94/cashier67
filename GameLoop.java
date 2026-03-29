public class GameLoop implements Runnable {

    private Game game;
    private boolean running;

    public GameLoop(Game game) {
        this.game = game;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            game.updateGame(); 

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopLoop() {
        running = false;
    }
}