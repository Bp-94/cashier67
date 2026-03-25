import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
public class Game extends JFrame implements Observer {

    private Font customFont;



    private Customer presentCustomer,leavingCustomer;

    private LevelCanvas levelCanvas;

    private int level;

    private double dept ;

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
    public Game(){
        dept = 1200;
        presentCustomer = new Customer(this);
        JLabel BackGround,Customer,TableAndCalculator,PresentLevel;
        
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
    public void setDept(double dept) {
        this.dept = dept;
    }
    public double getDept() {
        return dept;
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

    // เมธอดเช็คว่า จบเกมยังงง
    public void isGameEnd(int time,int debt,Game currentGame){
        if (time == 0){
            boolean result = currentGame.isWinOrLose(debt);
            if (result){
                ;
            }
            else{
                System.out.println("return to home");
            }
        }
        else{
            
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
                    this.setDept(this.getDept() - presentCustomer.getPayment());
                    leavingCustomer = presentCustomer;
                    presentCustomer.leave();
    
                }
                else{
                    levelCanvas.repaint();
                }
            }
        }catch (NumberFormatException e) {}
        
    }
    public Customer getLeavingCustomer(){
        return leavingCustomer;
    }
    // public static void main(String[] args){
    //     Game currentGame = new Game();
    //     currentGame.isGameEnd(0, 0, currentGame);
    // }
}
