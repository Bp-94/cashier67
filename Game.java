import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
public class Game extends JFrame implements Observer {
    private boolean transitioning = false;
    private Font customFont;

    private static final int PLAYING = 0;
    private static final int WIN = 1;
    private static final int LOSING = 2;
    private static final int TRANSITION = 3;

    GameLoop loop;

    private int time;

    private int gameState;

    private Customer presentCustomer,leavingCustomer;

    private LevelCanvas levelCanvas;

    private int level;

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
        new Goods("egg", "Goods/ไข่.png", 60)
        
    };
    public Game(double debt,int time,int level){
        this.debt = debt;
        this.time = time;
        this.level = level;
        presentCustomer = new Customer(this);
        gameState = PLAYING;
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        levelCanvas = new LevelCanvas(this);
        levelCanvas.setCurrentCustomer(presentCustomer);
        levelCanvas.setBounds(0,0,1920,1080);
        // Customer = new JLabel(new ImageIcon("Assets/CustomerBank.png"));
        // customer.setBounds(0,0,192)
        // PresentLevel = new JLabel(new ImageIcon("Assets/Level 1.png"));
        // TableAndCalculator = new JLabel(new ImageIcon("Assets/Calculator_Table_ListGoods.png"));
        // TableAndCalculator.setBounds(0,0,1920,1080);



        
        
        add(levelCanvas);
        

        // add(TableAndCalculator);
        // setComponentZOrder(PresentLevel, 3);
        // setComponentZOrder(Customer, 3);
       
        // setComponentZOrder(TableAndCalculator, 0);
        loop = new GameLoop(this);
        new Thread(loop).start();
        setVisible(true);
        System.out.println(getWidth() + " " + getHeight());
    }
    
    public Goods[] getGoodsList() {
    return goodsList;
    }
    // public void setGoodsLists(Goods[] goodsList){
    //     this.goodsList = goodsList;
    // }
    public LevelCanvas getLevelCanvas(){
        return levelCanvas;
    }
    public int getLevel(){
        return level;
    }
    public Customer getCustomer(){
        return presentCustomer;
    }
    public void setdebt(double debt) {
        this.debt = debt;
    }
    public double getDebt() {
        return debt;
    }
    public void CaluclulateMoney(){

    }
    public boolean isWinOrLose(int debt){
        if (debt <= 0){
            return true;
        }
        else{
            return false;
        }
    }

    
    @Override
    public void update(String message) {
        if (message.equals("CustomerLeft")) {

            
            Customer newCustomer = new Customer(this);

        // บอก Canvas ว่าคนเก่าคือ leaving
            levelCanvas.setLeavingCustomer(presentCustomer);

            // เปลี่ยน current
            presentCustomer = newCustomer;

            // ส่งให้ canvas
            levelCanvas.setCurrentCustomer(presentCustomer);
        }
        else if (message.equals("correct")){

        }
        try {
            double playerInput = Double.parseDouble(message);
            // if (presentCustomer.getAge() < 20){
                
            // }
            if (presentCustomer.getX() == presentCustomer.getTargetX()){

                if (presentCustomer.checkPrice(playerInput)) {
                    this.setdebt(this.getDebt() - presentCustomer.getPayment());
                    leavingCustomer = presentCustomer;
                    presentCustomer.leave();
    
                }
                else{
                    levelCanvas.repaint();
                }
            }
        }catch (NumberFormatException e) {}
        
    }
    public void nextLevel() {
        levelCanvas.getAninmation().stopAnimation();
        loop.stopLoop();
        levelCanvas.getTimer().stopTime();
        dispose();
        level++;

        debt = 100;

        int time;
        
        if (level == 2) time = 140;
        else if (level == 3) time = 130;
        else time = 120;

        new Game(100,time,level);

        // gameState = PLAYING;

        // levelCanvas.getTimer().start();
    }
    public void updateGame() {
        if (gameState != PLAYING) return;

        if (levelCanvas.getTimer().getTotalSeconds() == 0) {
            loseGame();
        }

        if (debt < 0) {
            winGame();
        }
    }
    public void winGame() {
        if (gameState != PLAYING) {
        return;
        }

        if (transitioning == true) {
            return;
        } else {
            transitioning = true;
        }

        gameState = TRANSITION;

        levelCanvas.getTimer().stopTime();

        levelCanvas.showEndDialog(true);
        nextLevel();
    }
    public Customer getLeavingCustomer(){
        return leavingCustomer;
    }
    public int getTime(){
        return time;
    }

    public void loseGame() {
        if (gameState != PLAYING) {
            return;
        }

        if (transitioning == true) {
            return;
        } else {
            transitioning = true;
        }

        gameState = TRANSITION;

        levelCanvas.getTimer().stopTime();

        levelCanvas.showEndDialog(false);

        nextLevel();
    }

    // public static void main(String[] args){
    //     Game currentGame = new Game();
    //     currentGame.isGameEnd(0, 0, currentGame);
    // }
}
