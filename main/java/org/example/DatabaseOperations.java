package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;

public interface DatabaseOperations {

    public Connection connect();
    public void Disconnect(Connection conn);
    public void Disconnect(Connection conn, PreparedStatement stmt);
    public boolean DataVerification(String code, String amount, String location);
}
