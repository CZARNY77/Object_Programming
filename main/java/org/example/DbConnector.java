package org.example;
import javax.swing.*;
import java.sql.*;

public class DbConnector implements DatabaseOperations{

    private static String URL = "jdbc:sqlserver://localhost;encrypt=true;databaseName=Magazyn;integratedSecurity=true;trustServerCertificate=true";

    public Connection connect() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL);
            //System.out.println("Połączono");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
    public void Disconnect(Connection conn){
        try{
            if(conn != null)    conn.close(); //!conn.isClosed()
        } catch (SQLException e) {
            System.err.println("Błąd podczas rozłączania się z bazą danych: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void Disconnect(Connection conn, PreparedStatement stmt){
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas rozłączania się z bazą danych: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //metody dodaj wiersz, edytuj wiersz, usuń wiersz

    public boolean DataVerification(String code, String amount, String location){
        boolean accept = true;

        if(code.equals("") || amount.equals("") || location.equals("") ){
            JOptionPane.showMessageDialog(null, "Nie wprowadziłeś wszystkich danych");
            return false;
        }
        else {
            try{
                Integer.parseInt(code);
                if (Integer.parseInt(code)<=0){
                    JOptionPane.showMessageDialog(null, "Kod nie może być na minusie");
                    return false;
                }
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null, "Wprowadzono zły Kod");
                return false;
            }
            try{
                Integer.parseInt(amount);
                if (Integer.parseInt(amount)<0){
                    JOptionPane.showMessageDialog(null, "Ilość nie może być na minusie");
                    return false;
                }
            }catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null, "Nwprowadzono złą ilość");
                return false;
            }
        }
        return true;
    }
}
