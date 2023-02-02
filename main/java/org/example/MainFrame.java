package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainFrame extends DbConnector {

    public static JFrame settingsWindow;
    public static JFrame addWindow;
    public static JFrame editWindow;
    public static JFrame deleteWindow;

    //Main Elements
    public JPanel panel;
    private JButton tabMag;
    private JButton zamProdButton;
    private JPanel menu;
    private JButton editBtn;
    private JPanel toolbar;
    private JButton addBtn;
    private static DefaultTableModel tableModel;
    public JTable table1;
    private JButton Refresh;
    private JButton settingsBtn;
    private JButton LogoutBtn;
    private JButton deleteBtn;
    private JTextField kodTextField;
    private JTextField textField2;
    private JTextField textField3;
    private JButton searchBtn;
    private JButton ImportBtn;
    private JButton ExportBtn;
    String currentSQL = Settings.ScriptSQL[0];

    public MainFrame(){
        TableRefresh();

        Refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table1.setModel(tableModel);
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addWindow = new JFrame("Dodaj wiersz");
                addWindow.setContentPane(new AddRow().panel);
                addWindow.pack();
                addWindow.setVisible(true);

                //table1.setModel(tableModel);
            }
        });

        tabMag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSQL = Settings.ScriptSQL[0];

                TableRefresh();
            }
        });

        zamProdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSQL = Settings.ScriptSQL[1];
                TableRefresh();
            }
        });

        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsWindow = new JFrame("Ustawienia");
                settingsWindow.setContentPane(new Settings().panel);
                settingsWindow.pack();
                settingsWindow.setVisible(true);
            }
        });
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editWindow = new JFrame("Edytuj wiersz");
                editWindow.setContentPane(new EditRow().panel);
                editWindow.pack();
                editWindow.setVisible(true);
            }
        });
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteWindow = new JFrame("usuń dane");
                deleteWindow.setContentPane(new DeleteData().panel);
                deleteWindow.pack();
                deleteWindow.setVisible(true);
            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(kodTextField.getText().equals("") && textField2.getText().equals("") && textField3.getText().equals("")) return;
                String tempSQL = currentSQL;
                String where1 = !kodTextField.getText().equals("") ? " WHERE '" + kodTextField.getText() + "' = ID " : " WHERE ";
                where1 += !kodTextField.getText().equals("") && !textField2.getText().equals("") ? "AND " : "";
                String where2 = !textField2.getText().equals("") ? "'" + textField2.getText() + "' = Magazyn.Ilosc " : "";
                where2 += !textField2.getText().equals("") && !textField3.getText().equals("") ? "AND " : "";
                String where3 = !textField3.getText().equals("") ? "'" + textField3.getText() + "' = Magazyn.Miejsce " : "";

                currentSQL = currentSQL + where1 + where2 + where3;
                System.out.println(currentSQL);
                TableRefresh();
                currentSQL = tempSQL;
            }
        });
        LogoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame = new JFrame("Zaloguj sie");
                Main.frame.setContentPane(new Login().panel);
                Main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Main.frame.pack();
                //frame.setBounds(100, 100, 450, 300);
                Main.frame.setBounds(100, 100, 800, 600);
                Main.frame.setVisible(true);

                Login.mainFrame.dispose();
            }
        });
    }

    public void TableRefresh() {
        Connection conn = connect();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            // przygotowanie zapytania do bazy danych
            String sql = currentSQL;
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();

            tableModel = new DefaultTableModel();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(rs.getMetaData().getColumnName(i));
            }

            while(rs.next()){
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(rowData);

            }
            table1.setModel(tableModel);

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            Disconnect(conn, stmt);
        }
    }
}
