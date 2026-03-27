import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


import javax.swing.*;
import javax.swing.text.*;
public class LevelCanvas extends JPanel implements MouseListener,ActionListener, MouseMotionListener, Obserable {
    private Font customFont;
    private MyTimer timerLogic;
    private AnimationLevel AnLe;
    private List<Observer> observers = new ArrayList<>();
    private int currentY;
    private float Prongsai;
    JDialog dialog;
    private boolean dialogOpen;

    Rectangle sell,notSell,closeCoupon;

    Image bg = new ImageIcon("Asset/bg.png").getImage();
    Image table_calculator_goodslist = new ImageIcon("Asset/Calculator_Table_ListGoods.png").getImage();
    Image CustomerBank = new ImageIcon("Asset/CustomerBank.png").getImage();
    Image Level1ICon = new ImageIcon("Asset/Level1.png").getImage();
    Image Level2ICon = new ImageIcon("Asset/Level2.png").getImage();
    Image Level3ICon = new ImageIcon("Asset/Level3.png").getImage();
    Image Level4ICon = new ImageIcon("Asset/Level4.png").getImage();
    Image Level5ICon = new ImageIcon("Asset/Level5.png").getImage();
    Image Wrong1 = new ImageIcon("CustomerImage/Question.png").getImage();
    Image Wrong2 = new ImageIcon("CustomerImage/Exclamation.png").getImage();
    Image couponmini = new ImageIcon("CustomerImage/minicoupon.png").getImage(); 
    Image IDmini = new ImageIcon("CustomerImage/mini_ID.png").getImage(); 

        
    ;
    Image presentcustomerImg;
    // Image goodsList = new ImageIcon("Asset/ListGoods.png");

    private static JTextField txt = new JTextField();
    private static JTextField txtAns =  new JTextField();
    private JTextField currentFocus;
    private Customer currentCustomer;
    private Customer leavingCustomer;

    private int CountWrong;

    public Observer Game;
    int mouseX;
    int mouseY;

    int baseWidth = 1537;
    int baseHeight = 795;
    
    private Game game;
    

    Rectangle btn7 = new Rectangle(1063,302,50,45);
    Rectangle btn8 = new Rectangle(1119,303,50,43);
    Rectangle btn9 = new Rectangle(1170,303,48,43);
    Rectangle btnDiv = new Rectangle(1226,304,56,43);

    Rectangle btn4 = new Rectangle(1050,345,50,61);
    Rectangle btn5 = new Rectangle(1115,347,50,60);
    Rectangle btn6 = new Rectangle(1167,346,60,60);
    Rectangle btnMul = new Rectangle(1234,347,60,60);

    Rectangle btn1 = new Rectangle(1031,410,59,69);
    Rectangle btn2 = new Rectangle(1103,408,60,67);
    Rectangle btn3 = new Rectangle(1166,408,69,67);
    Rectangle btnSub = new Rectangle(1245,410,60,69);

    Rectangle btn0 = new Rectangle(1012,478,60,77);
    Rectangle btnDot = new Rectangle(1090,476,70,80);
    Rectangle btnEqual = new Rectangle(1163,476,83,80);
    Rectangle btnAdd = new Rectangle(1265,477,60,80);
    Rectangle btnAns = new Rectangle(1330,620,50,50);
    Rectangle txtRect = new Rectangle(1051,175,202,40);
    Rectangle ansRect = new Rectangle(967,620,345,42);
    Rectangle clearBtn = new Rectangle(1261,180,28,30);

    Rectangle customerRect = new Rectangle(0, -13, 950, 1250);
    
    Rectangle goodsListRect = new Rectangle(1050,715,335,400);

    Rectangle goodRect = new Rectangle(-200,450,225,225);

    Rectangle IconLevel1 = new Rectangle(80,50,1400,900);

    Rectangle wrongRect = new Rectangle(350,80,1200,1050); 

    Rectangle couponminiRect = new Rectangle(510,360,100,50);

    Rectangle IDRect = new Rectangle(510,360,100,50);

    // Rectangle closeCouponRect = new Rectangle(600, 622, 205, 75);
    // Rectangle notSell = new Rectangle(490, 620, 225, 75);
    // Rectangle sell = new Rectangle(830, 620, 207, 75);

    public LevelCanvas(Game game) {
        timerLogic = new MyTimer(game.getTime());
        timerLogic.start();
        // presentcustomer = game.getCustomer();
        // presentcustomerImg = new ImageIcon(presentcustomer.getimagePath()).getImage();
        // 2. สร้าง Thread แยกเพื่อให้นาฬิกาวิ่งเลนขนาน (หน้าจอจะได้ไม่ค้าง)
        this.game = game;
        // 3. สร้าง Timer ตัวเล็กๆ เพื่อสั่งให้หน้าจอ "วาดใหม่" (Repaint) ทุกๆ 0.1 วินาที
        // เพื่อให้เลขนาฬิกาที่อัปเดตใน Thread โชว์บนหน้าจอทันที
        Timer refresh = new Timer(16, e -> repaint());
        refresh.start();
        AnLe = new AnimationLevel(this);
        Thread AL = new Thread(AnLe);
        AL.start();
        
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("2005_iannnnnJPG.ttf"));
            customFont = customFont.deriveFont(54f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.add((Observer) game);
        addMouseListener(this);
        
        
        

        setOpaque(true);
        setLayout(null);

        

        txt.setFont(customFont);
        txtAns.setFont(customFont);

        txt.setOpaque(false);
        txt.setBackground(new Color(0,0,0,0));
        txt.setBorder(null);

        txtAns.setOpaque(false);
        txtAns.setBackground(new Color(0,0,0,0));
        txtAns.setBorder(null);

        txt.setBounds(1051, 175,202,40);
        txtAns.setBounds(967, 620, 345, 42);

        txt.setEditable(false);
        txt.setCaretColor(new Color(0,0,0,0));

        add(txtAns);
        add(txt);
        txt.setPreferredSize(new Dimension(197, 33));
        txtAns.setPreferredSize(new Dimension(200,33));

        txt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currentFocus = txt; // ถ้าคลิกช่องคำนวณ ให้พิมพ์ลงช่องนี้
            }
        });
        

        
        
        
        
        
        
        // ล็อกให้ txtAns พิมพ์ได้เฉพาะตัวเลข
        ((AbstractDocument) txtAns.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
                
                String current = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = current.substring(0, offset) + text + current.substring(offset + length);
                
                if (newText.matches("\\d*")) { // อนุญาตเฉพาะตัวเลข และว่างได้
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        txtAns.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            currentFocus = txtAns; // ถ้าคลิกช่องส่งคำตอบ ให้พิมพ์ลงช่องนี้
                            
                        }
                    });
            }
    public void updateState(int currentY,float Prongsai){
        this.currentY = currentY;
        this.Prongsai = Prongsai;
        repaint();
    }
    public void setCurrentCustomer(Customer c) {
        this.currentCustomer = c;
        this.presentcustomerImg = new ImageIcon(c.getimagePath()).getImage();
    }

    public void setLeavingCustomer(Customer c) {
        this.leavingCustomer = c;
    }
    public void showGoodListDialog() {
                JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), true);
                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                dialog.setSize(screen);
                dialog.setUndecorated(true);
                dialog.setBackground(new Color(0,0,0,50));
                int w = (int)(screen.width * 0.5);
                int h = (int)(w * 9.0 / 16);

                Image img = new ImageIcon("Asset/ListGoods.png").getImage();
                Image scaled = img.getScaledInstance(screen.width, screen.height, Image.SCALE_SMOOTH);
                JLabel label = new JLabel(new ImageIcon(scaled));
                dialog.add(label);
                dialog.setLocationRelativeTo(null);
                MouseAdapter closeEvent = new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    dialog.dispose();
                    }
                };

                dialog.addMouseListener(closeEvent);
                label.addMouseListener(closeEvent);
                dialog.setVisible(true);
                

            }
                
            // public static void main(String[] args){
                
            //     JFrame frame = new JFrame("CASHIER");
            //     Font myFont = new Font("Serif", Font.PLAIN, 20);
            
            //     frame.setSize(1920,1080);
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    //     Calculator panel = new Calculator(new Game());
    //     panel.setLayout(null);
    //     txt.setFont(myFont);
    //     txtAns.setFont(myFont);
    //     txt.setOpaque(false);                // ทำให้พื้นหลังโปร่งใส
    //     txt.setBackground(new Color(0,0,0,0)); // ตั้งค่าสีพื้นหลังเป็นใส (Alpha = 0)
    //     txt.setBorder(null);                 // เอาเส้นขอบสี่เหลี่ยมออก
    //     txtAns.setOpaque(false);                // ทำให้พื้นหลังโปร่งใส
    //     txtAns.setBackground(new Color(0,0,0,0)); // ตั้งค่าสีพื้นหลังเป็นใส (Alpha = 0)
    //     txtAns.setBorder(null);                 // เอาเส้นขอบสี่เหลี่ยมออก
    //     txt.setBounds(1051, 175,202,40);
    //     txt.setCaretColor(new Color(0,0,0,0));
    //     txtAns.setBounds(967, 620, 345, 42);
    //     txt.setEditable(false);
    //     // txt.setOpaque(false);
    //     // txt.setBorder(null);
    //     panel.add(txtAns);
    //     panel.add(txt);
    //     frame.add(panel);
        
    //     frame.setLocationRelativeTo(null);
    //     frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    //     frame.setVisible(true);
    // }
    public void doLayout(){
        
        super.doLayout();
        
        scale(txt, txtRect);
        scale(txtAns, ansRect);
    }
    public void scale(JTextField txt, Rectangle base){
        
        double scaleX = getWidth()/(double)baseWidth;
        double scaleY = getHeight()/(double)baseHeight;
        
        txt.setBounds(
            (int)(base.x * scaleX),
            (int)(base.y * scaleY),
            (int)(base.width * scaleX),
            (int)(base.height * scaleY)
        );
    }
    public Rectangle scale(Rectangle r){
        
        double scaleX = getWidth()/(double)baseWidth;
        double scaleY = getHeight()/(double)baseHeight;
        
        return new Rectangle(
            (int)(r.x*scaleX),
            (int)(r.y*scaleY),
            (int)(r.width*scaleX),
            (int)(r.height*scaleY)
        );
    }
    // วาดรูปลง JPanel
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Image pImg = new ImageIcon(game.getCustomer().getimagePath()).getImage();
        Rectangle w = scale(wrongRect);
        Rectangle r = scale(customerRect);
        Rectangle LI = scale(IconLevel1);
        Rectangle gd = scale(goodRect);
        Rectangle cm = scale(couponminiRect);
        Rectangle id = scale(IDRect);
        // Rectangle s = scale(sell);
        // Rectangle ns = scale(sell);

        String timeToShow ;
        
        if (currentCustomer != null) {
                CountWrong = currentCustomer.getCountWrong();
        }
        // Rectangle gl = scale(goodsListRect);
        int debtx = 500;
        int debty = 55;
        
        int timex = 1000;
        int timey = 55;
        
        int goodx = 50;
        int goody = 550;
        
        double scaleX = getWidth()/(double)baseWidth;
        double scaleY = getHeight() / (double)baseHeight;
        
        double scaledebt = Math.min(scaleX, scaleY);
        
        int dx = (int)(debtx * scaleX);
        int dy = (int)(debty * scaleY);
        
        int tx = (int)(timex * scaleX);
        int ty = (int)(timey * scaleY);
        
        int gx = (int)(goodx * scaleX);
        int gy = (int)(goody * scaleY);
        
        Graphics2D g2 = (Graphics2D) g;

        Composite oldComposite = g2.getComposite();
        
        g2.drawImage(bg,0,0,getWidth(),getHeight(),this);
        // วาดคนที่กำลังออก
        if (leavingCustomer != null) {
            Image leaveImg = new ImageIcon(leavingCustomer.getimagePath()).getImage();
            g2.drawImage(leaveImg, leavingCustomer.getX(), r.y, r.width, r.height, this);
        }

        // วาดคนปัจจุบัน
        if (currentCustomer != null) {
            g2.drawImage(presentcustomerImg, currentCustomer.getX(), r.y, r.width, r.height, this);
        }
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,Prongsai);
        
        g2.setComposite(ac);
        if (game.getLevel() == 1) {
            g2.drawImage(Level1ICon, LI.x,currentY,LI.width,LI.height,this);
        }
        else if (game.getLevel() == 2){
            g2.drawImage(Level2ICon, LI.x,currentY,LI.width,LI.height,this);
        }
        else if (game.getLevel() == 3){
            g2.drawImage(Level3ICon, LI.x,currentY,LI.width,LI.height,this);
        }
        else if (game.getLevel() == 4){
            g2.drawImage(Level4ICon, LI.x,currentY,LI.width,LI.height,this);
        }
        else if (game.getLevel() == 5){
            g2.drawImage(Level5ICon, LI.x,currentY,LI.width,LI.height,this);
        }
        g2.setComposite(oldComposite);
        g2.drawImage(table_calculator_goodslist,0,0,getWidth(),getHeight(),this);
        if (currentCustomer != null && currentCustomer.getX() == currentCustomer.getTargetX()){
            for(int i = 0;i < game.getCustomer().getAmountToBuy() ; i++){
                Goods good = game.getCustomer().getGoodsToBuy().get(i);
                Image gicon = new ImageIcon(good.getImagePath()).getImage();
                g2.drawImage(gicon,gx,gy,gd.width,gd.height,this);
                gx += 110;
            }
        }
        if(!currentCustomer.getEnter() && currentCustomer.haveCoupon()){
            g2.drawImage(couponmini,0,0,getWidth(),getHeight(),this);
        }
        else if(!currentCustomer.getEnter() && currentCustomer.haveAlcohol()){
            g2.drawImage(IDmini,0,0,getWidth(),getHeight(),this);
        }
        if (CountWrong == 1){
            g2.drawImage(Wrong1, 0,0,getWidth(),getHeight(), this);
        }
        else if(CountWrong == 2){
            g2.drawImage(Wrong2,0,0,getWidth(),getHeight() , this);
        }
        else if(CountWrong == 3){
            CountWrong = 0;
        }

        // ตั้งค่าฟอนต์และสีที่จะวาดเลขนาฬิกา
        g.setFont(customFont);
        g.setColor(Color.WHITE);
        if (timerLogic.getTimeString() == null) {
            timeToShow = "00:00";
        } else {
            timeToShow = timerLogic.getTimeString();
        }

        // วาดลงบนหน้าจอ (ตำแหน่ง x=80, y=100)
        g.drawString(timeToShow, tx, ty);

        g2.setFont(customFont.deriveFont((float)(68 * scaledebt)) );
        g2.setColor(Color.WHITE);
        
        g2.drawString(String.valueOf(game.getDebt()), dx, dy);
        g2.setColor(Color.RED);
        
        g2.draw(btn7);
        // g2.draw(scale(btn8));
        // g2.draw(scale(btn9));
        // g2.draw(scale(btnDiv));
        
        // g2.draw(scale(btn4));
        // g2.draw(scale(btn5));
        // g2.draw(scale(btn6));
        // g2.draw(scale(btnMul));
        
        // g2.draw(scale(btn1));
        // g2.draw(scale(btn2));
        // g2.draw(scale(btn3));
        // g2.draw(scale(btnSub));
        
        // g2.draw(scale(btn0));
        // g2.draw(scale(btnDot));
        // g2.draw(scale(btnEqual));
        // g2.draw(scale(btnAdd));
        // g2.draw(scale(btnAns));
        // g2.draw(scale(clearBtn));
        // g2.draw(scale(goodsListRect));
        // g2.draw(scale(goodRect));
        // g2.draw(scale(couponminiRect));
        // g2.draw(scale(IDRect));
        // g2.draw(scale(closeCouponRect));
        // g2.draw(scale(sell));
        // g2.draw(scale(notSell));
        // g2.draw(scale(wrongRect));
    }
    
    public static double calculate(String exp){
        
    String[] tokens = exp.split("(?=[-+*/])|(?<=[-+*/])");
    
    double result = Double.parseDouble(tokens[0]);
    
    for(int i = 1; i < tokens.length; i += 2){
        
        String op = tokens[i];
        double num = Double.parseDouble(tokens[i+1]);
        
        switch(op){
            case "+": result += num; break;
            case "-": result -= num; break;
            case "*": result *= num; break;
            case "/": 
            if (num == 0) throw new ArithmeticException("Division by zero");
            result /= num; 
            break;
            
        }
    }
    
    return result;
}
public void mousePressed(MouseEvent e) {
    double scaleX = getWidth() / (double)baseWidth;
    double scaleY = getHeight() / (double)baseHeight;
    
    int x = (int)(e.getX() / scaleX);
    int y = (int)(e.getY() / scaleY);
    System.out.println("Coupon? " + currentCustomer.haveCoupon());
    System.out.println("Click in rect? " + couponminiRect.contains(x,y));
    
    System.out.println("CLICK");
    double num1, num2;
    String op;
    // กดแล้วขึ้นภาพขึ้นตัวอักษร
    if (currentFocus == null){currentFocus = txt;}
    if (btn7.contains(x,y)) {
        currentFocus.setText(currentFocus.getText()+"7");
    } else if (btn8.contains(x, y)) {
        currentFocus.setText(currentFocus.getText()+"8");
    } else if (btn9.contains(x, y)) {
        currentFocus.setText(currentFocus.getText()+"9");
    } else if (btn4.contains(x, y)) {
        currentFocus.setText(currentFocus.getText()+"4");
    } else if (btn5.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"5");
            } else if (btn6.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"6");
            } else if (btn1.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"1");
            } else if (btn2.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"2");
            } else if (btn3.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"3");
            } else if (btn0.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"0");
            } else if (clearBtn.contains(x, y)) {
                txt.setText("");
            } else if (game.getLevel() < 4 && btnAdd.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"+");
            } else if (game.getLevel() < 4 && btnSub.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"-");
            } else if (game.getLevel() < 4 && btnMul.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"*");
            } else if (game.getLevel() < 4 && btnDiv.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"/");
            } else if (game.getLevel() < 4 && btnEqual.contains(x, y)) {
                double ans = calculate(currentFocus.getText());
                int floorAns = (int)Math.floor(ans); // ปัดลงเสมอ
                currentFocus.setText(String.valueOf(floorAns));
            } else if (btnAns.contains(x, y)) {
                this.notifyObserver(txtAns.getText()); // ส่งคำตอบไปให้ Game ตรวจสอบ
            } else if (btnDot.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+".");
            } else if (goodsListRect.contains(x,y)){
                showGoodListDialog();
            } 
            
            if (!dialogOpen && !currentCustomer.getEnter() && currentCustomer.haveCoupon() && couponminiRect.contains(x,y)){
                dialogOpen = true;
                showCoupon();
            } 
            if (!dialogOpen && !currentCustomer.getEnter() && currentCustomer.haveAlcohol() && IDRect.contains(x,y)){
                dialogOpen = true;
                showID_Card();
            }


        
        // ของ IDCARD
        if (notSell != null && notSell.contains(e.getPoint())) {
            dialogOpen = false;
            if (currentCustomer.getAge() > 20) {
                this.notifyObserver("ไม่ขาย:ผิด");
            }else {
                this.notifyObserver("ไม่ขาย:ถูก");
            }
            dialog.dispose();
        }else if (sell != null && sell.contains(e.getPoint())) {
            dialogOpen = false;
            if (currentCustomer.getAge() < 20) {
                this.notifyObserver("ขาย:ผิด");
            }else {
                this.notifyObserver("ขาย:ถูก");
            }
            dialog.dispose();

        }else if (closeCoupon != null && closeCoupon.contains(e.getPoint())) {
            dialogOpen = false;
            dialog.dispose();
        }
}
public void mouseClicked(MouseEvent e){
    // System.out.println(e.getX() + "  " + e.getY());
    // double scaleX = getWidth() / (double)baseWidth;
    // double scaleY = getHeight() / (double)baseHeight;

    // int x = (int)(e.getX() / scaleX);
    // int y = (int)(e.getY() / scaleY);
    
    // System.out.println("CLICK");
    // double num1, num2;
    // String op;
    // if (currentFocus == null){currentFocus = txt;}
    // if (btn7.contains(x,y)) {
    //     currentFocus.setText(currentFocus.getText()+"7");
    // } else if (btn8.contains(x, y)) {
    //     currentFocus.setText(currentFocus.getText()+"8");
    // } else if (btn9.contains(x, y)) {
    //     currentFocus.setText(currentFocus.getText()+"9");
    // } else if (btn4.contains(x, y)) {
    //     currentFocus.setText(currentFocus.getText()+"4");
    // } else if (btn5.contains(x, y)) {
    //             currentFocus.setText(currentFocus.getText()+"5");
    //         } else if (btn6.contains(x, y)) {
    //             currentFocus.setText(currentFocus.getText()+"6");
    //         } else if (btn1.contains(x, y)) {
    //             currentFocus.setText(currentFocus.getText()+"1");
    //         } else if (btn2.contains(x, y)) {
    //             currentFocus.setText(currentFocus.getText()+"2");
    //         } else if (btn3.contains(x, y)) {
    //             currentFocus.setText(currentFocus.getText()+"3");
    //         } else if (btn0.contains(x, y)) {
    //             currentFocus.setText(currentFocus.getText()+"0");
    //         } else if (clearBtn.contains(x, y)) {
    //             txtAns.setText("");
    //         } else if (game.getLevel() < 4 && btnAdd.contains(x, y)) {
    //             currentFocus.setText(currentFocus.getText()+"+");
    //         } else if (game.getLevel() < 4 && btnSub.contains(x, y)) {
    //             currentFocus.setText(currentFocus.getText()+"-");
    //         } else if (game.getLevel() < 4 && btnMul.contains(x, y)) {
    //             currentFocus.setText(currentFocus.getText()+"*");
    //         } else if (game.getLevel() < 4 && btnDiv.contains(x, y)) {
    //             currentFocus.setText(currentFocus.getText()+"/");
    //         } else if (game.getLevel() < 4 && btnEqual.contains(x, y)) {
    //             double ans = calculate(currentFocus.getText());
    //             int floorAns = (int)Math.floor(ans); // ปัดลงเสมอ
    //             currentFocus.setText(String.valueOf(floorAns));
    //         } else if (btnAns.contains(x, y)) {
    //             this.notifyObserver(txtAns.getText()); // ส่งคำตอบไปให้ Game ตรวจสอบ
    //         } else if (btnDot.contains(x, y)) {
    //             currentFocus.setText(currentFocus.getText()+".");
    //         } else if (goodsListRect.contains(x,y)){
    //             showGoodListDialog();
    //         } else if (currentCustomer.haveCoupon() && couponminiRect.contains(x,y)){
    //             currentCustomer.showCoupon();
    //         } else if (currentCustomer.haveAlcohol() && IDRect.contains(x,y)){
    //             showID_Card();
    //         }
            

        }
        
        public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    
    @Override
    public void mouseDragged(MouseEvent e) {}
    
    @Override
    public void mouseMoved(MouseEvent e) {
        double scaleX = getWidth() / (double)baseWidth;
        double scaleY = getHeight() / (double)baseHeight;

        int x = (int)(e.getX() / scaleX);
        int y = (int)(e.getY() / scaleY);
        
        if (btn7.contains(x, y) || (btn8.contains(x, y)) || (btn9.contains(x,y)) || (btn4.contains(x, y)) || (btn5.contains(x, y)) || (btn6.contains(x, y)) || (btn1.contains(x, y)) || (btn2.contains(x,y)) || (btn3.contains(x, y)) || (btn0.contains(x,y)) || (btnAdd.contains(x, y)) || (btnSub.contains(x, y)) || (btnMul.contains(x, y)) || (btnDiv.contains(x, y)) || (btnDot.contains(x, y)) || (btnEqual.contains(x, y))) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }
    @Override
    public void remove(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObserver(String message) {
        for (Observer observer : observers){
            observer.update(message);
        }
    }
    @Override
        public void add(Observer observer){
            observers.add(observer);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    public MyTimer getTimer() { 
        return timerLogic; 
    }
        public void showID_Card() {
            
        dialog = new JDialog(game, true);
        dialog.setSize(game.getWidth() - 100, game.getHeight() - 100);
        dialog.setLocationRelativeTo(game);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));
        ImageIcon icon = new ImageIcon("CustomerImage/ID" + currentCustomer.getAge() + ".png");
        Image scaled = icon.getImage().getScaledInstance(dialog.getWidth(), dialog.getHeight(), Image.SCALE_SMOOTH);
        JLabel show = new  JLabel(new ImageIcon(scaled));
        show.setOpaque(false);
        System.out.println(currentCustomer.getAge());
        notSell = scale(new Rectangle(490, 620, 225, 75));
        sell = scale(new Rectangle(830, 620, 207, 75));
        show.addMouseListener(this);
        dialog.add(show);
        dialog.setVisible(true);
        
    }
    public void showCoupon() {
        dialog = new JDialog(game, true);
        dialog.setSize(game.getWidth() - 100, game.getHeight() - 100);
        dialog.setLocationRelativeTo(game);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 100));
        ImageIcon icon = new ImageIcon("CustomerImage/Coupon" + (int) currentCustomer.getDiscount() + ".png");
        Image scaled = icon.getImage().getScaledInstance(dialog.getWidth(), dialog.getHeight(), Image.SCALE_SMOOTH);
        JLabel show = new  JLabel(new ImageIcon(scaled));
        show.setOpaque(false);
        closeCoupon = scale(new Rectangle(600, 622, 205, 75));
        show.addMouseListener(this);
        dialog.add(show);
        
        dialog.setVisible(true);
    }

    public void showEndDialog(boolean isWin) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setSize(game.getWidth(), game.getHeight());
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0,251));

        String imgPath;
        if (isWin && game.getLevel() != 5) {
            imgPath = "Asset/win.png";

        }else if(isWin && game.getLevel() == 5) {
            imgPath = "Asset/congrat.png";
            // dialog.dispose();
            // new HomeScreen();
        } 
        
        else {
            imgPath = "Asset/lose.png";
        }

        Image img = new ImageIcon(imgPath).getImage();
        JLabel label = new JLabel(new ImageIcon(img));
        dialog.add(label);

        

        // ⏱️ ปิดอัตโนมัติ 4 วิ
        new Timer(4000, e -> {
            dialog.dispose();
            
        }).start();
        
        dialog.setVisible(true);
    }
    public void setTimer(MyTimer timer) {
        this.timerLogic = timer;
    }
    public AnimationLevel getAninmation(){
        return AnLe;
    }
    
}
