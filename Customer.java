import java.util.*;
public class Customer {
    private ArrayList<Goods> goodsToBuy;
    private int countWrong;
    private boolean haveAlcohol;
    private boolean haveCoupon;
    private int discount;
//    private String CustomerImage;
    public Customer() {
        Random random = new Random();
        goodsToBuy = new ArrayList<Goods>();
        int amountToBuy = random.nextInt(10);
        haveAlcohol = false;
        for (int i = 0; i < amountToBuy; i++) {
            int randomGoods = random.nextInt(Game.goodsList.length);
            Goods g = Game.goodsList[randomGoods];
            if (g.getName().equals("Alcohol")) {
                haveAlcohol = true;
            }
            goodsToBuy.add(g);
        }
        if (haveAlcohol) {
//            แสดงภาพ ID_card กดแล้วให้ New ID_card
        }else {
            haveCoupon = random.nextDouble() < 0.3;
            if (haveCoupon) {
                
            }
        }
        countWrong = 0;
//        int imageIndex = random.nextInt(???);
//        CustomerImage = ("" + imageIndex);
    }
//    public void checkPrice(double playerInput) {
//        double sum = 0;
//        for (Goods g : goodsToBuy) {
//            sum += g.getPrice();
//        }
//        if (((haveCoupon) && ((sum - sum*(ส่วนลดคูปอง)) == sum)) || ((!haveCoupon) && (playerInput == sum))) {
//            
//        }else {
//            countWrong += 1;
//            if (countWrong == 1) {
//                
//            }else if (countWrong == 2) {
//                
//            }else {
//                
//            }
//        }
//    }
}
