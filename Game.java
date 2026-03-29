import javax.swing.*;

public class Game extends JFrame implements Observer {

    private static final int PLAYING = 0;
    private static final int TRANSITION = 1;
    private int gameState;

    private boolean runningMinigame = true;
    private GameLoop loop;
    private LevelCanvas levelCanvas;
    private Sound bgm = new Sound();
    private MinigameScheduler mgScheduler;

    private Customer presentCustomer,leavingCustomer;
    private int time,level;
    private double debt;

    public static final Goods goodsList[] = {
        new Goods("Alcohol", "Goods/เหล้า.png",120),
        new Goods("Orange", "Goods/ส้ม.png",46),
        new Goods("Apple", "Goods/แอปเปิล.png", 49),
        new Goods("Water", "Goods/น้ำเปล่า.png", 50),
        new Goods("Bannana", "Goods/กล้วย.png", 44),
        new Goods("Chocolate", "Goods/ขนมช็อคโกแลต.png", 20),
        new Goods("Pockey", "Goods/ขนมป้อกกี้.png", 25),
        new Goods("Jelly", "Goods/ขนมเยลลี่.png", 35),
        new Goods("egg", "Goods/ไข่.png", 60),
        new Goods("Figure", "Goods/ฟิกเกอร์จารแบงค์.png", 670)
        
    };
    public Game(double debt,int time,int level){
        this.debt = debt;
        this.time = time;
        this.level = level;

        bgm.play("bgm.wav");
        gameState = PLAYING;
        presentCustomer = new Customer(this);

        setSize(1980,1020);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        levelCanvas = new LevelCanvas(this);
        levelCanvas.setCurrentCustomer(presentCustomer);
        add(levelCanvas);
        
        loop = new GameLoop(this);
        new Thread(loop).start();
        
        mgScheduler = new MinigameScheduler(this);
        mgScheduler.start(); 

        setVisible(true);
    }
    
    
    
    
    
    @Override
    public void update(String message) {
        if (message.equals("CustomerLeft")) {
            Customer newCustomer = new Customer(this);
            levelCanvas.setLeavingCustomer(presentCustomer);
            presentCustomer = newCustomer;
            levelCanvas.setCurrentCustomer(presentCustomer);
            return;
        }
        else if (message.equals("ไม่ขาย:ผิด") || message.equals("ขาย:ผิด")){
            int newTime = levelCanvas.getTimer().getTotalSeconds() - 10;
            if (newTime < 0) newTime = 0;
            levelCanvas.getTimer().setTime(newTime);
            getLevelCanvas().triggerMinusAnim();
            leavingCustomer = presentCustomer;
            presentCustomer.leave();
        }
        else if(message.equals("ไม่ขาย:ถูก")){
            leavingCustomer = presentCustomer;
            presentCustomer.leave();
        }
        try {
            double playerInput = Double.parseDouble(message);
            Customer c = presentCustomer;
            if (c.getX() == c.getTargetX()){
                if (c.checkPrice(playerInput)) {
                    System.out.println("triggercheckprice");
                    this.setdebt(this.getDebt() - c.getPayment());
                    leavingCustomer = c;
                    c.leave();
                }
                else if (!c.isLeaving() && c.getAge() < 20 && c.haveAlcohol()){
                    getLevelCanvas().triggerMinusAnim();
                    System.out.println("trigger");
                    int newTime = levelCanvas.getTimer().getTotalSeconds() - 10;
                    if (newTime < 0) newTime = 0;
                    levelCanvas.getTimer().setTime(newTime);
                    leavingCustomer = c;
                    c.leave();
                }
                else{
                    levelCanvas.repaint();
                }
            }
        }catch (NumberFormatException e) {}
        
    }
    public void nextLevel() {
        dispose();
        
        level++;
        debt = 1000;
        int time;
        
        if (level == 2) time = 140;
        else if (level == 3) time = 130;
        else time = 120;
        
        new Game(debt,time,level);
    }
    public void updateGame() {
        if (gameState != PLAYING) return;
        
        if (levelCanvas.getTimer().getTotalSeconds() <= 0) {
            loseGame();
        }
        
        if (debt < 0) {
            winGame();
        }
    }
    public void winGame() {
        // เอาไว้กันไม่ให้เข้าเมธอด win รัวๆๆ
        if (gameState != PLAYING) {
            return;
        }
        getLevelCanvas().getTxt().setText("");
        getLevelCanvas().getTxtAns().setText("");
        gameState = TRANSITION;
        stopAll();
        levelCanvas.showEndDialog(true);
        if(level < 5){
            nextLevel();
        }
        else{
            dispose();
            new HomeScreen();
        }
    }
    public void loseGame() {
        // เอาไว้กันไม่ให้เข้าเมธอด lose รัวๆๆ
        if (gameState != PLAYING) {
            return;
        }
        getLevelCanvas().getTxt().setText("");
        getLevelCanvas().getTxtAns().setText("");
        gameState = TRANSITION;
        stopAll();
        levelCanvas.showEndDialog(false);
        dispose();
        new HomeScreen();
    }

    private void stopAll() {
        runningMinigame = false;
        bgm.stop();
        mgScheduler.stop();
        loop.stopLoop();
        levelCanvas.getTimer().stopTime();
    }
    public Goods[] getGoodsList() {
    return goodsList;
    }
    public LevelCanvas getLevelCanvas(){
        return levelCanvas;
    }
    public int getLevel(){
        return level;
    }
    public Customer getCustomer(){
        return presentCustomer;
    }
    public double getDebt() {
        return debt;
    }
    public void setdebt(double debt) {
        this.debt = debt;
    }
    public Customer getLeavingCustomer(){
        return leavingCustomer;
    }
    public int getTime(){
        return time;
    }
    public boolean isMinigameActive() {
        return mgScheduler != null && mgScheduler.isActive();
    }
    public MinigameScheduler getMgScheduler() {
        return mgScheduler;
    }
    public boolean isRunningMinigame() {
        return runningMinigame;
}
}
