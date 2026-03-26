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
            game.updateGame(); // 🔥 เรียกเช็คเกมตลอด

            try {
                Thread.sleep(200); // 0.1 วิ
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopLoop() {
        running = false;
    }
}