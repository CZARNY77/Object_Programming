package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteData extends DbConnector{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton BackBtn;
    private JButton okBtn;
    public JPanel panel;

    public DeleteData() {
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!DataVerification(textField1.getText(), textField2.getText(), textField3.getText()))  return;

                Connection conn = connect();
                PreparedStatement stmt = null;

                try{
                    String query = "DELETE FROM Magazyn WHERE ID_Produktu = ? AND Ilosc = ? AND Miejsce = ?";
                    stmt = conn.prepareStatement(query);
                    stmt.setInt(1, Integer.parseInt(textField1.getText()));
                    stmt.setInt(2, Integer.parseInt(textField2.getText()));
                    stmt.setString(3, textField3.getText());

                    int rowsDeleted = stmt.executeUpdate();
                    System.out.println("Usunięto " + rowsDeleted + " rekordów.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas usuwania danych!!");
                    throw new RuntimeException(ex);
                }

                Disconnect(conn, stmt);
                MainFrame.deleteWindow.dispose();
            }
        });
        BackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.deleteWindow.dispose();
            }
        });
    }
}
