import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Customer implements Obserable, ActionListener {
    private ArrayList<Observer> observers = new ArrayList<>();
    private ArrayList<Goods> goodsToBuy;
    private int countWrong;
    private boolean haveAlcohol;
    private boolean haveCoupon;
    private double finalPrice;
    private double payment;
//    private String CustomerImage;
    
    public Customer(Game game) {
        Random random = new Random();
        goodsToBuy = new ArrayList<>();
        int amountToBuy = random.nextInt(10)+1;
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
//            add ภาพ ID_Card บนโต๊ะ
//            add ActionListener ให้ ID_Card
        }else {
            haveCoupon = random.nextDouble() < 0.3;
            if (haveCoupon) {
//                add ภาพ Coupon บนโต๊ะ
            }
        }
        countWrong = 0;
        this.add(game);
//        int imageIndex = random.nextInt(???);
//        CustomerImage = ("" + imageIndex);
    }
    public boolean checkPrice(double playerInput) {
        if (Math.abs(playerInput - finalPrice) < 0.01) { //double มีโอกาส ค่าไม่เท่ากันทั้งที่ควรเท่า เลยใช้ต้องวิธีนี้เทียบ double
            return true;
        }
        countWrong += 1;
        if (countWrong == 1) {
            payment *= 0.75;
//            ปลี่ยนภาพลูกค้า
        }else if (countWrong == 2) {
            payment *= 0.5;
//            ปลี่ยนภาพลูกค้า
        }else {
            this.leave();
        }
        return false;
    }
    public void leave() {
        //ภาพลูกค้าเลื่อนออก 
        this.notifyObserver("CustomerLeft");
    }
    public void showID_Card() {
        
    }
    public void showCoupon() {
        
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
    public void actionPerformed(ActionEvent ae) {
//        if (ae.getSource().equals(ID_Card)) {
//            this.showID_Card();
//        }else if (ae.getSource().equals(Coupon)) {
//            this.showCoupon();
//        }
    }
}
