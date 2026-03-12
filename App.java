import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class App {
    public static void main(String[] args) {

        JFrame frame = new JFrame("My Game");
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Calculator panel = new Calculator();

        frame.add(panel);
        frame.setVisible(true);
    }
}