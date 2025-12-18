/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hospital.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author ITACHI
 */
public class MainDashboard extends javax.swing.JFrame {
    private JPanel sidebar;
    private JDesktopPane desktop;
    private JPanel homePanel;
    
    public MainDashboard() {
        initComponents();
        setTitle("Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Sidebar ---
        sidebar = new JPanel();
        sidebar.setBackground(new Color(25, 118, 210));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setLayout(new GridLayout(8, 1, 0, 10));

        JLabel logo = new JLabel("<html><center><b style='font-size:14px;color:white;'>🏥 HOSPITAL NAME </b></center></html>", SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(200, 60));
        sidebar.add(logo);
        
        //Dashboard button
        addSidebarButton("Dashboard");
        
        //users button
        addSidebarButton("Users");
        
        //Patients button
        sidebar.add(createDropdownMenu(
        "Patients",
        new String[]{"Patient Registration", "Patient List"},
        new Runnable[]{
        () -> openModule(new PatientRegistration()),
        () -> openModule(new PatientList())
        }
        ));
        
        //Departments button
        addSidebarButton("Departments");
        
        //Doctors button
        sidebar.add(createDropdownMenu(
        "Doctors",
        new String[]{"Doctor Entry", "Doctor List"},
        new Runnable[]{
            () -> openModule(new DoctorEntry()),
            () -> openModule(new DoctorList())
        }
        ));
               
        //Rooms button
        sidebar.add(createDropdownMenu(
         "Rooms",
        new String[]{"Room Registration", "Room List"},
        new Runnable[]{
        () -> openModule(new RoomManagement()),
        () -> openModule(new RoomList())
        }
        ));
        
        //Service button
        addSidebarButton("Service");
        
        //Billings button
        addSidebarButton("Billing");              
               
        //Logout button
        addSidebarButton("Logout");

        
        // --- Desktop Pane (main area) ---
        desktop = new JDesktopPane();
        desktop.setBackground(new Color(245, 247, 250));

        add(sidebar, BorderLayout.WEST);
        add(desktop, BorderLayout.CENTER);
        showHomeScreen();
    }
    private JPanel createDropdownMenu(String title, String[] items, Runnable[] actions) {

    JPanel container = new JPanel(new BorderLayout());
    container.setBackground(new Color(25, 118, 210));

    JButton mainBtn = new JButton("  " + title + " ▾");
    mainBtn.setFocusPainted(false);
    mainBtn.setBackground(new Color(25, 118, 210));
    mainBtn.setForeground(Color.WHITE);
    mainBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainBtn.setBorderPainted(false);
    mainBtn.setHorizontalAlignment(SwingConstants.LEFT);

    JPanel submenu = new JPanel(new GridLayout(items.length, 1));
    submenu.setBackground(new Color(21, 101, 192));
    submenu.setVisible(false);

    for (int i = 0; i < items.length; i++) {
        JButton subBtn = new JButton("     • " + items[i]);
        subBtn.setFocusPainted(false);
        subBtn.setBackground(new Color(21, 101, 192));
        subBtn.setForeground(Color.WHITE);
        subBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subBtn.setBorderPainted(false);
        subBtn.setHorizontalAlignment(SwingConstants.LEFT);

        int index = i;
        subBtn.addActionListener(e -> actions[index].run());

        submenu.add(subBtn);
    }

    mainBtn.addActionListener(e -> submenu.setVisible(!submenu.isVisible()));

    container.add(mainBtn, BorderLayout.NORTH);
    container.add(submenu, BorderLayout.CENTER);

    return container;
}

    private void addSidebarButton(String text) {
        JButton btn = new JButton("  " + text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(25, 118, 210));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(21, 101, 192));
            }

            public void mouseExited(MouseEvent evt) {
                btn.setBackground(new Color(25, 118, 210));
            }
        });

        // Action events
        btn.addActionListener(e -> {
            switch (text) {
                case "Dashboard" -> showHomeScreen();
                case "Users" -> openInternalFrame(new NewUser());
                case "Patients" -> openInternalFrame(new PatientRegistration());
                case "Departments" -> openInternalFrame(new DepartmentEntry());
                case "Doctors" -> openInternalFrame(new DoctorEntry());
                case "Rooms" -> openInternalFrame(new RoomManagement());
                case "Service" -> openInternalFrame(new ServiceEntry());
                case "Billing" -> openInternalFrame(new Billing());                
                case "Logout" -> logout();
                default -> JOptionPane.showMessageDialog(this, "Coming soon!");
            }
        });

        sidebar.add(btn);
    }
    private void showHomeScreen() {
        desktop.removeAll();
        desktop.repaint();
        
        // Create dashboard internal frame
        JInternalFrame dashboardFrame = new JInternalFrame(
            "Dashboard", false, false, false, false
        );
        dashboardFrame.setBorder(null);
        dashboardFrame.setVisible(true);
        dashboardFrame.setLayout(new BorderLayout());

        homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(2, 3, 40, 40));
        homePanel.setBackground(new Color(245, 247, 250));
        homePanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        addHomeCard("Users", "user.png", () -> openInternalFrame(new NewUser()));
        addHomeCard("Patients", "patients.png", () -> openModule(new PatientRegistration()));
        addHomeCard("Departments", "departments.png", () -> openModule(new DepartmentEntry()));
        
        addHomeCard("Doctors", "doctors.png", () -> openModule(new DoctorEntry()));
        addHomeCard("Rooms", "rooms.png", () -> openModule(new RoomManagement()));
        addHomeCard("Services", "Service.png", () -> openInternalFrame(new ServiceEntry()));
        addHomeCard("Billing", "billing.png", () -> openInternalFrame(new Billing()));
        
        
        dashboardFrame.add(homePanel, BorderLayout.CENTER);
        desktop.add(dashboardFrame);

        try {
        dashboardFrame.setMaximum(true);
        } catch (Exception ignored) {}

        
        //desktop.add(wrapper);
        //desktop.add(homePanel);
        //homePanel.setBounds(0, 0, desktop.getWidth(), desktop.getHeight());
        desktop.revalidate();
        desktop.repaint();
    }

    private void addHomeCard(String title, String iconName, Runnable onClick) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel("", SwingConstants.CENTER);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/hospital/views/resources/" + iconName));

            Image scaled = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            iconLabel.setText("🩺");
        }

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(33, 33, 33));

        card.add(iconLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(232, 240, 254));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
            }
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }
        });

        //((JPanel) desktop.getComponents()[0]).add(card);
        homePanel.add(card);
    }

    private void openInternalFrame(JInternalFrame frame) {
        desktop.removeAll();
        desktop.repaint();
        frame.setVisible(true);
        desktop.add(frame);
        try {
            frame.setMaximum(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Confirm Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new Login().setVisible(true); // adjust to your login form name
        }
    }

private void openModule(JInternalFrame frame) {
    desktop.removeAll();
    desktop.repaint();

    frame.setVisible(true);
    frame.setClosable(false);
    frame.setMaximizable(true);
    frame.setIconifiable(false);
    frame.setResizable(true);

    desktop.add(frame);
    try {
        frame.setMaximum(true);
    } catch (Exception ignored) {}
}


    private void openWindow(JFrame frame) {
        frame.setVisible(true);
        dispose();
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
         SwingUtilities.invokeLater(() -> new MainDashboard().setVisible(true));

        /* Create and display the form 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainDashboard().setVisible(true);
            }
        });*/
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
