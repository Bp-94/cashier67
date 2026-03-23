import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
public class Game extends JFrame implements Observer {
    
    private Customer currentcustomer;
    private Customer presentCustomer;
    private LevelCanvas levelCanvas;
    private double dept;
    public static final Goods goodsList[] = {
        new Goods("Alcohol",250),
        new Goods("A",500),
        new Goods("B", 10000)
    };
    public Game(){
        
        JLabel BackGround,Customer,TableAndCalculator,PresentLevel;
        
        setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        levelCanvas = new LevelCanvas(this);
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
                System.out.println("next level");
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
            presentCustomer = new Customer(this);
        }
        try {
            double playerInput = Double.parseDouble(message);
            if (presentCustomer.checkPrice(playerInput)) {
                this.setDept(this.getDept() + presentCustomer.getPayment());
                presentCustomer.leave();
            }
        }catch (NumberFormatException e) {}
    }
    // public static void main(String[] args){
    //     Game currentGame = new Game();
    //     currentGame.isGameEnd(0, 0, currentGame);
    // }
}
