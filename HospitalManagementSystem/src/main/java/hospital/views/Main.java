/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital.views;

/**
 *
 * @author ITACHI
 */
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame {
    public Main() {
        setTitle("Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new JLabel("Welcome to Hospital Management System!"));
    }
}

