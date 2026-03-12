import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JPanel implements MouseListener {

    private Image bg;

    private String expression = "";
    private String result = "";

    int mouseX;
    int mouseY;

    // ขนาดต้นแบบของรูป
    int baseWidth = 1537;
    int baseHeight = 795;

    // --------------------
    // ปุ่ม calculator (พิกัดอ้างอิงจาก 1920x1080)
    // --------------------

    Rectangle btn7 = new Rectangle(1063,302,50,45);
    Rectangle btn8 = new Rectangle(1119,303,50,43);
    Rectangle btn9 = new Rectangle(840,200,60,60);
    Rectangle btnDiv = new Rectangle(900,200,60,60);

    Rectangle btn4 = new Rectangle(720,260,60,60);
    Rectangle btn5 = new Rectangle(780,260,60,60);
    Rectangle btn6 = new Rectangle(840,260,60,60);
    Rectangle btnMul = new Rectangle(900,260,60,60);

    Rectangle btn1 = new Rectangle(720,320,60,60);
    Rectangle btn2 = new Rectangle(780,320,60,60);
    Rectangle btn3 = new Rectangle(840,320,60,60);
    Rectangle btnSub = new Rectangle(900,320,60,60);

    Rectangle btn0 = new Rectangle(720,380,60,60);
    Rectangle btnDot = new Rectangle(780,380,60,60);
    Rectangle btnEqual = new Rectangle(840,380,60,60);
    Rectangle btnAdd = new Rectangle(900,380,60,60);

    Rectangle clearBtn = new Rectangle(940,120,40,40);

    // --------------------

    public Calculator() {

        bg = new ImageIcon("bg.jpg").getImage();

        addMouseListener(this);

    }

    // scale rectangle
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

    // --------------------
    // วาดหน้าจอ
    // --------------------

    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(bg,0,0,getWidth(),getHeight(),this);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial",Font.BOLD,24));

        g2.drawString(expression,740,150);
        g2.drawString(result,740,470);

        // DEBUG rectangle
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

        g2.draw(scale(clearBtn));

        g2.setColor(Color.YELLOW);
        g2.drawString("Mouse : "+mouseX+" , "+mouseY,20,30);
    }

    // --------------------
    // ตรวจคลิก
    // --------------------

    public void click(int x,int y){

        if(btn7.contains(x,y)) expression+="7";
        else if(btn8.contains(x,y)) expression+="8";
        else if(btn9.contains(x,y)) expression+="9";

        else if(btn4.contains(x,y)) expression+="4";
        else if(btn5.contains(x,y)) expression+="5";
        else if(btn6.contains(x,y)) expression+="6";

        else if(btn1.contains(x,y)) expression+="1";
        else if(btn2.contains(x,y)) expression+="2";
        else if(btn3.contains(x,y)) expression+="3";

        else if(btn0.contains(x,y)) expression+="0";
        else if(btnDot.contains(x,y)) expression+=".";

        else if(btnAdd.contains(x,y)) expression+="+";
        else if(btnSub.contains(x,y)) expression+="-";
        else if(btnMul.contains(x,y)) expression+="*";
        else if(btnDiv.contains(x,y)) expression+="/";

        else if(btnEqual.contains(x,y)) calculate();

        else if(clearBtn.contains(x,y)){
            expression="";
            result="";
        }

    }

    // --------------------
    // คำนวณ
    // --------------------

    public void calculate(){

        try{

            if(expression.contains("+")){

                String[] part = expression.split("\\+");

                double a = Double.parseDouble(part[0]);
                double b = Double.parseDouble(part[1]);

                result=""+(a+b);
            }

            else if(expression.contains("-")){

                String[] part = expression.split("-");

                double a = Double.parseDouble(part[0]);
                double b = Double.parseDouble(part[1]);

                result=""+(a-b);
            }

            else if(expression.contains("*")){

                String[] part = expression.split("\\*");

                double a = Double.parseDouble(part[0]);
                double b = Double.parseDouble(part[1]);

                result=""+(a*b);
            }

            else if(expression.contains("/")){

                String[] part = expression.split("/");

                double a = Double.parseDouble(part[0]);
                double b = Double.parseDouble(part[1]);

                result=""+(a/b);
            }

        }

        catch(Exception e){

            result="error";

        }

    }

    // --------------------
    // mouse
    // --------------------

    public void mousePressed(MouseEvent e){

        mouseX=e.getX();
        mouseY=e.getY();

        double scaleX=getWidth()/(double)baseWidth;
        double scaleY=getHeight()/(double)baseHeight;

        int mx=(int)(mouseX/scaleX);
        int my=(int)(mouseY/scaleY);

        click(mx,my);

        repaint();
    }

    public void mouseClicked(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
}