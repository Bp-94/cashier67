import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Customer implements Obserable, Runnable, MouseListener {
    private ArrayList<Observer> observers = new ArrayList<>();
    private ArrayList<Goods> goodsToBuy;
    private int age, countWrong;
    private boolean haveAlcohol, haveCoupon, leaving;
    private double discount, finalPrice, payment;
    private Game game;
    private Random random;
    private Rectangle id_card, coupon, closeCoupon, sell, notSell;
    
    public Customer(Game game) {
        this.game = game;
        this.add(game); //notifyObserver
        random = new Random();
        goodsToBuy = new ArrayList<>();
        int amountToBuy = random.nextInt(10) + 1;
        haveAlcohol = false;
        finalPrice = 0;
        for (int i = 0; i < amountToBuy; i++) {
            int randomGoods = random.nextInt(Game.goodsList.length);
            Goods g = Game.goodsList[randomGoods];
            if (g.getName().equals("Alcohol")) {
                haveAlcohol = true;
            }
            goodsToBuy.add(g);
            finalPrice += g.getPrice();
        }
        if (haveAlcohol) {
            int ages[] = {18, 19, 21, 25};
            age = ages[random.nextInt(ages.length)];
            game.add(new JLabel(new ImageIcon("CustomerImage/mini_ID.png")));
            //id_card = new Rectangle(,,,,)
        }else {
            haveCoupon = random.nextDouble() < 0.3; //มีโอกาส 30%
            if (haveCoupon) {
                int discounts[] = {25, 50};
                discount = discounts[random.nextInt(discounts.length)];
                finalPrice -= finalPrice * (discount / 100);
                game.add(new JLabel(new ImageIcon("CustomerImage/miniCoupon.png")));
                //coupon = new Rectangle(,,,,)
            }
        }
        countWrong = 0;
        leaving = false;
        Thread thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run() {
        //// SOS ////
        int imageIndex = random.nextInt(6) + 1;
        ImageIcon icon = new ImageIcon("CustomerImage/Customer" + imageIndex + ".png");
        int originalW = icon.getIconWidth();
        int originalH = icon.getIconHeight();
        int newH = game.getHeight() * 2360 / 1080; //จากภาพต้นฉบับที่ฉากหลังสูง 1080 ลูกค้าสูง 2360
        int newW = originalW * newH / originalH;
        Image newImg = icon.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        JLabel customer = new JLabel(new ImageIcon(newImg));
        int y = game.getWidth() - newW - 200;
        customer.setBounds(-newW, y, newW, newH);
        game.add(customer);
        game.repaint();
        int targetX = game.getWidth() / 100;
        ////
        // เดินเข้า
        while (customer.getX() < targetX) {
            customer.setLocation(customer.getX() + 5, y);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //รอ leave()
        while (!leaving) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // เดินออก
        while (customer.getX() < game.getWidth()) {
            customer.setLocation(customer.getX() + 5, y);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        game.remove(customer);
        game.repaint();
    }
    
    public boolean checkPrice(double playerInput) {
        if (Math.abs(playerInput - finalPrice) < 0.01) { //double มีโอกาส ค่าไม่เท่ากันทั้งที่ควรเท่า เลยใช้วิธีนี้เทีบ double
            return true;
        }
        countWrong += 1;
        if (countWrong == 1) {
            payment *= 0.75;
//            ใส่ภาพเครื่องหมาย
        }else if (countWrong == 2) {
            payment *= 0.5;
//            ใส่ภาพเครื่องหมาย
        }else {
            this.leave();
        }
        return false;
    }
    public void leave() {
        leaving = true;
        this.notifyObserver("CustomerLeft");
    }
    public void showID_Card() {
        JDialog dialog = new JDialog(game);
        JLabel show = new JLabel(new ImageIcon("ID" + age + ".png"));
        
    }
    public void showCoupon() {
         JDialog dialog = new JDialog(game);
         JLabel show = new JLabel(new ImageIcon("Coupon" + discount + ".png"));
         
    }
    
    //Setter&Getter
    public double getPayment() {
        return payment;
    }
    public int getCountWrong() {
        return countWrong;
    }
    //
    
    //Obserable
    @Override
    public void add(Observer observer) {
        observers.add(observer); 
    } 
    @Override
    public void remove(Observer observer) {
        observers.remove(observer); 
    } 
    @Override
    public void notifyObserver(String message) {
        for (Observer observer : observers){
            observer.update(message); } 
    }
    //

    @Override
    public void mouseClicked(MouseEvent e) {
//        if (ae.getSource().equals(ID_Card)) {
//            this.showID_Card();
//        }else if (ae.getSource().equals(Coupon)) {
//            this.showCoupon();
//        }
//        ปุ่มไม่ขายเหล้าให้
                
    }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
