import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.text.*;

public class App extends JPanel implements MouseListener,ActionListener, MouseMotionListener, Obserable {

    private List<Observer> observers = new ArrayList<>();

    Image bg = new ImageIcon("bg.jpg").getImage();

    private static JTextField txt = new JTextField();
    private static JTextField txtAns =  new JTextField();
    public Observer Game;
    int mouseX;
    int mouseY;

    int baseWidth = 1537;
    int baseHeight = 795;

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
    private JTextField currentFocus;
    
    public App(GameP game) {
        this.add(game);
        addMouseListener(this);
        txt.setPreferredSize(new Dimension(197, 33));
        txtAns.setPreferredSize(new Dimension(200,33));
        txt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currentFocus = txt; // ถ้าคลิกช่องคำนวณ ให้พิมพ์ลงช่องนี้
            }
        });

        
        
        txtAns.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currentFocus = txtAns; // ถ้าคลิกช่องส่งคำตอบ ให้พิมพ์ลงช่องนี้
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
            }
            
    public static void main(String[] args){
        
        JFrame frame = new JFrame("CASHIER");
        Font myFont = new Font("Serif", Font.PLAIN, 20);
        
        frame.setSize(1920,1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        App panel = new App(new GameP(null, 0));
        panel.setLayout(null);
        txt.setFont(myFont);
        txtAns.setFont(myFont);
        txt.setOpaque(false);                // ทำให้พื้นหลังโปร่งใส
        txt.setBackground(new Color(0,0,0,0)); // ตั้งค่าสีพื้นหลังเป็นใส (Alpha = 0)
        txt.setBorder(null);                 // เอาเส้นขอบสี่เหลี่ยมออก
        txtAns.setOpaque(false);                // ทำให้พื้นหลังโปร่งใส
        txtAns.setBackground(new Color(0,0,0,0)); // ตั้งค่าสีพื้นหลังเป็นใส (Alpha = 0)
        txtAns.setBorder(null);                 // เอาเส้นขอบสี่เหลี่ยมออก
        txt.setBounds(1051, 175,202,40);
        txt.setCaretColor(new Color(0,0,0,0));
        txtAns.setBounds(967, 620, 345, 42);
        txt.setEditable(false);
        // txt.setOpaque(false);
        // txt.setBorder(null);
        panel.add(txtAns);
        panel.add(txt);
        frame.add(panel);
        
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
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
    
    protected void paintComponent(Graphics g){
        
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.drawImage(bg,0,0,getWidth(),getHeight(),this);
        
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
    int x = e.getX();
    int y = e.getY();
    double num1, num2;
    String op;
    if (currentFocus == null){currentFocus = txt;}
    if (btn7.contains(x, y)) {
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
                txtAns.setText("asdpookokfawofjawfpawepoewfon");
                this.notifyObserver(txtAns.getText()); // ส่งคำตอบไปให้ Game ตรวจสอบ
            } else if (btnDot.contains(x, y)) {
                currentFocus.setText(currentFocus.getText()+".");
            }
        }
        
        public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    
    @Override
    public void mouseDragged(MouseEvent e) {}
    
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
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