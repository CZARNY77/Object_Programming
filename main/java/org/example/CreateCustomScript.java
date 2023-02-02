package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateCustomScript extends Settings {
    public JPanel panel1;
    private JButton okBtn;
    private JTextArea textArea1;
    private JButton ResetBtn;
    private JButton BackBtn;

    public CreateCustomScript() {

        if(customT[0] == customTable.Magazyn)   textArea1.setText(ScriptSQL[0]);
        else if(customT[0] == customTable.Zamówienia_prod)   textArea1.setText(ScriptSQL[1]);

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(customT[0] == customTable.Magazyn)   ScriptSQL[0] = textArea1.getText();
                else if(customT[0] == customTable.Zamówienia_prod)   ScriptSQL[1] = textArea1.getText();

                createCustomScript.dispose();
            }
        });
        ResetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(customT[0] == customTable.Magazyn)   textArea1.setText("SELECT * FROM " + textField1.getText());
                else if(customT[0] == customTable.Zamówienia_prod)   textArea1.setText("SELECT * FROM " + textField2.getText());

            }
        });
        BackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCustomScript.dispose();
            }
        });
    }

}
