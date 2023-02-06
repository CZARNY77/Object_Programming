package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login  extends DbConnector{
    public static JFrame mainFrame;
    public JPanel panel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton logInButton;

    public Login() {

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (login(username, password)) {
                    Main.frame.dispose();
                    Settings.ReadScripts("CustomScript.csv");
                    mainFrame = new JFrame("Magazyn");
                    mainFrame.setContentPane( new MainFrame().panel);
                    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mainFrame.pack();
                    mainFrame.setBounds(100, 100, 800, 600);
                    mainFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(panel, "Błędny login lub hasło!");
                }
            }
        });
    }

    //można to logowanie wywalić do interfejsu
    public boolean login(String username, String password) {
        Connection conn = connect();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean success = false;

        try {

            // przygotowanie zapytania do bazy danych
            String sql = "SELECT * FROM Pracownicy WHERE Login = ? AND Haslo = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // jeśli zapytanie zwróci co najmniej jeden wiersz, znaczy to, że podane dane logowania są prawidłowe
                success = true;
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            Disconnect(conn, stmt);
        }

        return success;
    }

}
