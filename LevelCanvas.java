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
    Image bg = new ImageIcon("Asset/bg.png").getImage();
    Image table_calculator_goodslist = new ImageIcon("Asset/Calculator_Table_ListGoods.png").getImage();
    Image CustomerBank = new ImageIcon("Asset/CustomerBank.png").getImage();
    Image Level1ICon = new ImageIcon("Asset/Level1.png").getImage();
    Image Wrong1 = new ImageIcon("CustomerImage/สงสัย.png").getImage();
    Image Wrong2 = new ImageIcon("CustomerImage/ตกใจ.png").getImage();
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

    Rectangle customerRect = new Rectangle(-50, -13, 950, 1250);
    
    Rectangle goodsListRect = new Rectangle(1050,715,335,400);

    Rectangle goodRect = new Rectangle(206,550,150,150);

    Rectangle IconLevel1 = new Rectangle(80,50,1400,900);

    Rectangle wrongRect = new Rectangle(350,80,1200,1050); 

    public LevelCanvas(Game game) {
        timerLogic = new MyTimer(150);
        // presentcustomer = game.getCustomer();
        // presentcustomerImg = new ImageIcon(presentcustomer.getimagePath()).getImage();
        // 2. สร้าง Thread แยกเพื่อให้นาฬิกาวิ่งเลนขนาน (หน้าจอจะได้ไม่ค้าง)
        Thread t = new Thread(timerLogic);
        t.start();
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
    public void showCouponDialog() {
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

        if (currentCustomer != null) {
                CountWrong = currentCustomer.getCountWrong();
        }
        // Rectangle gl = scale(goodsListRect);
        int debtx = 500;
        int debty = 55;
        
        int timex = 1000;
        int timey = 55;
        
        int goodx = 206;
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
        g2.drawImage(Level1ICon, LI.x,currentY,LI.width,LI.height,this);

        g2.setComposite(oldComposite);
        g2.drawImage(table_calculator_goodslist,0,0,getWidth(),getHeight(),this);
        if (currentCustomer != null && currentCustomer.getX() == currentCustomer.getTargetX()){
            for(int i = 0;i < game.getCustomer().getAmountToBuy() ; i++){
                Goods good = game.getCustomer().getGoodsToBuy().get(i);
                Image gicon = new ImageIcon(good.getImagePath()).getImage();
                g2.drawImage(gicon,gx,gy,gd.width,gd.height,this);
                gx += 75;
            }
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
        String timeToShow = timerLogic.getTimeString();

        // ตั้งค่าฟอนต์และสีที่จะวาดเลขนาฬิกา
        g.setFont(customFont);
        g.setColor(Color.WHITE);
        
        // วาดลงบนหน้าจอ (ตำแหน่ง x=80, y=100)
        g.drawString(timeToShow, tx, ty);

        g2.setFont(customFont.deriveFont((float)(68 * scaledebt)) );
        g2.setColor(Color.WHITE);
        
        g2.drawString(String.valueOf(game.getDept()), dx, dy);
        g2.setColor(Color.RED);
        
        g2.draw(scale(btn7));
        g2.draw(scale(btn8));
        g2.draw(scale(btn9));
        g2.draw(scale(btnDiv));
        
        g2.draw(scale(btn4));
        g2.draw(scale(btn5));
        g2.draw(scale(btn6));
        g2.draw(scale(btnMul));
        
        g2.draw(scale(btn1));
        g2.draw(scale(btn2));
        g2.draw(scale(btn3));
        g2.draw(scale(btnSub));
        
        g2.draw(scale(btn0));
        g2.draw(scale(btnDot));
        g2.draw(scale(btnEqual));
        g2.draw(scale(btnAdd));
        g2.draw(scale(btnAns));
        g2.draw(scale(clearBtn));
        g2.draw(scale(goodsListRect));
        g2.draw(scale(goodRect));
        
        g2.draw(scale(wrongRect));
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
public void mousePressed(MouseEvent e){}
public void mouseClicked(MouseEvent e){
    System.out.println(e.getX() + "  " + e.getY());
    double scaleX = getWidth() / (double)baseWidth;
    double scaleY = getHeight() / (double)baseHeight;

    int x = (int)(e.getX() / scaleX);
    int y = (int)(e.getY() / scaleY);
    
    System.out.println("CLICK");
    double num1, num2;
    String op;
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
                currentFocus.setText("");
            } else if (btnAdd.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"+");
            } else if (btnSub.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"-");
            } else if (btnMul.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"*");
            } else if (btnDiv.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+"/");
            } else if (btnEqual.contains(x, y)) {
                double ans = calculate(currentFocus.getText());
                int floorAns = (int)Math.floor(ans); // ปัดลงเสมอ
                currentFocus.setText(String.valueOf(floorAns));
            } else if (btnAns.contains(x, y)) {
                this.notifyObserver(txtAns.getText()); // ส่งคำตอบไปให้ Game ตรวจสอบ
            } else if (btnDot.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+".");
            } else if (goodsListRect.contains(x,y)){
                showCouponDialog();
            }
            

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
}
