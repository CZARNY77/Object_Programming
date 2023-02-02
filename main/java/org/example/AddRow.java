package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddRow extends DbConnector{
    public JPanel panel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton BackBtn;
    private JButton okBtn;

    public AddRow() {
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!DataVerification(textField1.getText(), textField2.getText(), textField3.getText()))  return;

                Connection conn = connect();
                PreparedStatement stmt = null;

                try {
                    String query = "INSERT INTO Magazyn (ID_Produktu, Ilosc, Miejsce) VALUES (?, ?, ?)";
                    stmt= conn.prepareStatement(query);
                    stmt.setInt(1, Integer.parseInt(textField1.getText()));
                    stmt.setInt(2, Integer.parseInt(textField1.getText()));
                    stmt.setString(3, textField2.getText());

                    int rowsInserted = stmt.executeUpdate();
                    if(rowsInserted<=0) JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas wprowadzania danych");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas wprowadzania danych. Kod albo Miejsce nie istnieje!!");
                    throw new RuntimeException(ex);
                }


                Disconnect(conn, stmt);
                MainFrame.addWindow.dispose();
            }
        });
        BackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.addWindow.dispose();
            }
        });
    }

}
