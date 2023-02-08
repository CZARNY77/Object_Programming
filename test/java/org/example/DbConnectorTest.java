package org.example;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

class DbConnectorTest {
    private DbConnector service = new DbConnector();

    @Test
    void testConnect() {
        Connection result = new DbConnector().connect();
        Assert.assertNotNull(result);
    }

    @Test
    void testDisconnect() {
        DbConnector db = new DbConnector();
        Connection con = db.connect();
        db.Disconnect(con);
        Assert.assertNotNull(con);
    }

    @Test
    void TestDataVerification() {
        Assert.assertTrue(new DbConnector().DataVerification("5", "100", "A1"));
    }

    @Test
    void testLogin() {
        Assert.assertTrue(new Login().login("jkowalski", "haslo123"));
    }

    @Test
    public void testCreateMainFrame() {
        MainFrame mainFrame = new MainFrame();
        Assert.assertNotNull(mainFrame);
    }

}