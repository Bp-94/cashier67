public class AnimationLoseMinigame implements Runnable {
    private LevelCanvas panel;
    private int minusY;
    private float alpha;

    public AnimationLoseMinigame(LevelCanvas panel) {
        this.panel = panel;
    }
    
    @Override
    public void run() {
        minusY = -200;  
        alpha = 1.0f;

        try {
            while (minusY < -40) {
                minusY += 20;
                panel.updateMinusAnim(minusY, alpha);
                Thread.sleep(15);
            }
            Thread.sleep(1000);

            while (alpha > 0.0f) {
                alpha -= 0.02f;
                if (alpha < 0.0f) {
                    alpha = 0.0f;
                }
                panel.updateMinusAnim(minusY, alpha);
                Thread.sleep(30);
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
    }

    public void play() {
        new Thread(this).start();
    }
}
