public class Goods {
    private String name;
//    private String imagePath;
    private int price;
//    public Goods(String name, String imagePath, int price) {
    public Goods(String name, int price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
//    public String getImagePath() {
//        return imagePath;
//    }
//    public void setImagePath(String imagePath) {
//        this.imagePath = imagePath;
//    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
