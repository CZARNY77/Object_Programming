package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

import org.apache.commons.csv.*;

enum customTable {
    Puste,
    Magazyn,
    Zamówienia_prod

}

public class Settings{

    protected static JFrame createCustomScript;
    protected static String[] ScriptSQL = new String[]{"", ""};
    protected static customTable[] customT = {customTable.Puste};
    //Settings elements
    protected JPanel panel;
    protected JTextField textField1;
    private JButton customScriptBtn1;
    private JPanel SetMagPanel;
    private JPanel SetZamPanel;
    protected JTextField textField2;
    private JButton customScriptBtn2;
    private JButton OKBtn;
    private JButton backBtn;

    public Settings() {

        String fileName = "CustomScript.csv";
        File file = new File(fileName);

        if (Files.exists(file.toPath())) {
            try (CSVParser parser = new CSVParser(Files.newBufferedReader(Paths.get(fileName)), CSVFormat.DEFAULT.withFirstRecordAsHeader());) {
                JTextField[] textFields = new JTextField[]{textField1, textField2};
                int i = 0;
                for (CSVRecord record : parser) {
                    textFields[i].setText(record.get("Nazwa"));
                    //ScriptSQL[i] = record.get("Skrypt");
                    i += 1;
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.settingsWindow.dispose();
            }
        });
        customScriptBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customT[0] = customTable.Magazyn;
                RunSettingsWindow();
            }
        });
        customScriptBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customT[0] = customTable.Zamówienia_prod;
                RunSettingsWindow();
            }
        });
        OKBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try (CSVPrinter printer = new CSVPrinter(Files.newBufferedWriter(Paths.get(fileName)), CSVFormat.DEFAULT);) {
                    printer.printRecord("Nazwa", "Utworzony", "Skrypt");
                    printer.printRecord(textField1.getText(), "tak", ScriptSQL[0]);
                    printer.printRecord(textField2.getText(), "tak", ScriptSQL[1]);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                /*try (CSVPrinter printer = new CSVPrinter(Files.newBufferedWriter(Paths.get(fileName)), CSVFormat.DEFAULT);) {
                    printer.printRecord("Magazyn", "nie", "Brak");
                    printer.printRecord("Zamówienia_prod", "nie", "Brak");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                try (CSVParser parser = new CSVParser(Files.newBufferedReader(Paths.get(fileName)), CSVFormat.DEFAULT.withFirstRecordAsHeader());) {
                    for (CSVRecord record : parser) {
                        System.out.println(record.get("Nazwa"));
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }*/
                MainFrame.settingsWindow.dispose();
            }
        });
    }

    void RunSettingsWindow(){
        createCustomScript = new JFrame("Utwórz własny skrypt");
        createCustomScript.setContentPane(new CreateCustomScript().panel1);
        createCustomScript.pack();
        createCustomScript.setBounds(400, 400, 800, 600);
        createCustomScript.setVisible(true);
    }

    public static void ReadScripts(String fileName){

        File file = new File(fileName);
        if (Files.exists(file.toPath())) {
            try (CSVParser parser = new CSVParser(Files.newBufferedReader(Paths.get(fileName)), CSVFormat.DEFAULT.withFirstRecordAsHeader());) {
                int i = 0;
                for (CSVRecord record : parser) {
                    ScriptSQL[i] = record.get("Skrypt");
                    i += 1;
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
