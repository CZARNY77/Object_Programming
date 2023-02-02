package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditRow extends DbConnector{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton BackBtn;
    private JButton okBtn;
    public JPanel panel;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;

    public EditRow() {

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!DataVerification(textField1.getText(), textField2.getText(), textField3.getText()))  return;
                if(!DataVerification(textField4.getText(), textField5.getText(), textField6.getText()))  return;

                Connection conn = connect();
                PreparedStatement stmt = null;

                try{
                    String query = "UPDATE Magazyn SET ID_Produktu = ?, Ilosc = ?, Miejsce = ? WHERE ID_Produktu = ? AND Ilosc = ? AND Miejsce = ?";
                    stmt= conn.prepareStatement(query);
                    stmt.setInt(1, Integer.parseInt(textField4.getText()));
                    stmt.setInt(2, Integer.parseInt(textField5.getText()));
                    stmt.setString(3, textField6.getText());
                    stmt.setInt(4, Integer.parseInt(textField1.getText()));
                    stmt.setInt(5, Integer.parseInt(textField2.getText()));
                    stmt.setString(6, textField3.getText());

                    int count = stmt.executeUpdate();
                    System.out.println("Zaktualizowano " + count + " wierszy");

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas modyfikacji danych!!");
                    throw new RuntimeException(ex);
                }

                Disconnect(conn, stmt);
                MainFrame.editWindow.dispose();
            }
        });

        BackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.editWindow.dispose();
            }
        });
    }
}
