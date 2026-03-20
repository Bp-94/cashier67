public class GameP implements Observer {
    private Customer presentCustomer;
    private double dept;
    public static final Goods goodsList[] = {
        new Goods("Alcohol", "", 250),
        new Goods("A", "", 500),
        new Goods("B", "", 10000)
    };

    public void setDept(double dept) {
        this.dept = dept;
    }
    public double getDept() {
        return dept;
    }

    @Override
    public void update(String message) {
        if (message.equals("CustomerLeft")) {
            presentCustomer = new Customer();
        }
        try {
            double playerInput = Double.parseDouble(message);
            if (presentCustomer.checkPrice(playerInput)) {
                this.setDept(this.getDept() + presentCustomer.getPayment());
                presentCustomer.leave();
            }
        }catch (NumberFormatException e) {}
    }
}