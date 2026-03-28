import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Customer implements Obserable, Runnable {
    private ArrayList<Observer> observers = new ArrayList<>();
    
    private ArrayList<Goods> goodsToBuy;
    
    private int amountToBuy, age, countWrong, targetX, x;
    private double finalPrice, payment,discount;
    private String imagePath;
    private boolean haveAlcohol, haveCoupon, leaving,Enter;
    
    private Game game;
    private Random random;


    
    public Customer(Game game) {
        this.game = game;
        this.add(game); //notifyObserver
        random = new Random();

        int imageIndex = random.nextInt(6) + 1;
        imagePath = "CustomerImage/Customer" + imageIndex + ".png";
        x = -600;
        
        goodsToBuy = new ArrayList<>();
        if (game.getLevel() <= 3) {
            amountToBuy = random.nextInt(7) + 1;
        }else {
            amountToBuy = random.nextInt(3) + 1;
        }
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
            int ages[] = {14,18,19,21,25};
            age = ages[random.nextInt(ages.length)];
        }else {
            haveCoupon = random.nextDouble() < 0.2; //มีโอกาส 30%
            if (haveCoupon) {
                int discounts[] = {25, 50};
                discount = discounts[random.nextInt(discounts.length)];
                finalPrice -= finalPrice * (discount / 100.0);
            }
        }
        System.out.println(age);
        finalPrice = Math.floor(finalPrice);
        payment = finalPrice;
        countWrong = 0;
        Enter = false;
        leaving = false;
        Thread thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run() {
        targetX = -200;
        Enter = true;
        // เดินเข้า
        while (Enter &&x < targetX) {
            x += 4;
            // game.getLevelCanvas().repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Enter = false;
        //รอ leave()
        while (!leaving) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // เดินออก
        while (x < game.getWidth()) {
            x += 8;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean checkPrice(double playerInput) {
        if (Math.abs(playerInput - finalPrice) < 0.01) { //double มีโอกาส ค่าไม่เท่ากันทั้งที่ควรเท่า เลยใช้วิธีนี้เทีบ double
            return true;
        }
        countWrong += 1;
        if (countWrong == 1) {
            payment *= 0.75;
        }else if (countWrong == 2) {
            payment *= 0.5;
        }else {
            this.leave();
            
            System.out.println("CustomerLeft triggered");
            
        }
        return false;
    }
    
    public void leave() {
        System.out.println("LEAVE CALLED: " + this);
        if (leaving) return;
        leaving = true;
        System.out.println("CustomerLeft triggered");
        this.notifyObserver("CustomerLeft");
    }
    public boolean isLeaving(){
        return leaving;
    }
    
    //Setter&Getter
    public double getPayment() {
        return payment;
    }
    public int getCountWrong() {
        return countWrong;
    }
    public int getTargetX() {
        return targetX;
    }
    public int getX() {
        return x;
    }
    public int getAge() {
        return age;
    }
    public double getDiscount(){
        return discount;
    }
    public int getAmountToBuy() {
        return amountToBuy;
    }
    public ArrayList<Goods> getGoodsToBuy() {
        return goodsToBuy;
    }
    public String getimagePath() {
        return imagePath;
    }
    public boolean getEnter(){
        return Enter;
    }
    public boolean haveAlcohol() {
        return haveAlcohol;
    }
    public boolean haveCoupon() {
        return haveCoupon;
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

}