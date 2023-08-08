import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Manages GUI of the benchmark application
 * @version 1.0.0 - release
 * @author Stefan
 */
public class BenchmarkGUI {


    //connection valuables
    static String DB_URL= "";
    static String DB_USER = "";
    static String DB_PASSWORD = "";
    //save file valuable
    static String filename = "valueSaves.txt";


    //buttons, frames, text fields etc
    public static JFrame frame;
    public static JTextArea connection = new JTextArea();
    public static JTextField URLField;
    public static JTextField UsernameField;
    public static JTextField PasswordField;
    public static JButton connectButton;
    public static JButton insertButton1;
    public static JButton insertButton2;
    public static JButton updateButton1;
    public static JButton updateButton2;
    public static JButton deleteButton;
    public static JButton deleteButton2;
    public static JButton saveButton;
    public static JButton loadButton;
    public static JButton fetchRecordsButton;
    public static JButton generateTables;
    public static JButton fetchRecordsButton2;
    public static JPanel inputPanel;
    public static JPanel buttonPanel;
    public static JLabel label1 = new JLabel();

    /*
     * Creation of the GUI as well as listening for inputs
     */
    public static void initialize() {

        // Create and configure Swing components
        frame = new JFrame("Database Benchmark");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        inputPanel = new JPanel(new GridLayout(4, 2));
        URLField = new JTextField(40);
        UsernameField = new JTextField(40);
        PasswordField = new JTextField(40);
        inputPanel.add(new JLabel("Database URL:"));
        inputPanel.add(URLField);
        inputPanel.add(new JLabel("User:"));
        inputPanel.add(UsernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(PasswordField);
        inputPanel.add(saveButton = new JButton("Save Values"));
        inputPanel.add(loadButton = new JButton("Fetch Values"));


        URLField.setText(DB_URL);
        UsernameField.setText(DB_USER);
        PasswordField.setText(DB_PASSWORD);

        connectButton = new JButton("Test Connection");
        insertButton1 = new JButton("Insert Random - Stefan");
        insertButton2 = new JButton("Insert Random - Tom Kyte");
        updateButton1 = new JButton("Update Random - Stefan");
        updateButton2 = new JButton("Update Random - Tom Kyte");
        fetchRecordsButton = new JButton("Fetch Records - Stefan");
        fetchRecordsButton2 = new JButton("Fetch Records - Tom Kyte");
        deleteButton = new JButton("Delete all - Stefan");
        deleteButton2 = new JButton("Delete all - Tom Kyte");
        generateTables = new JButton("Generate Tables");

        //add elements
        buttonPanel = new JPanel(new GridLayout(2, 8));
        buttonPanel.add(connectButton);
        buttonPanel.add(insertButton1);
        buttonPanel.add(updateButton1);
        buttonPanel.add(fetchRecordsButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(generateTables);
        buttonPanel.add(insertButton2);
        buttonPanel.add(updateButton2);
        buttonPanel.add(fetchRecordsButton2);
        buttonPanel.add(deleteButton2);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        //display & center frame
        Networking.centreWindow(frame);
        frame.pack();
        frame.setVisible(true);


        //button for saving the URL, Username & password
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB_URL = URLField.getText();
                DB_USER = UsernameField.getText();
                DB_PASSWORD = PasswordField.getText();
                SaveAndLoad.writeSave(new save(DB_URL, DB_USER, DB_PASSWORD), filename);
                System.out.println("saving");
            }
        });

        //button for loading the URL, Username & password
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save Save = SaveAndLoad.fetchSave(filename);
                System.out.println(Save);

                DB_URL = Save.DB_URL;
                DB_USER = Save.DB_USER;
                DB_PASSWORD = Save.DB_PASSWORD;

                URLField.setText(DB_URL);
                UsernameField.setText(DB_USER);
                PasswordField.setText(DB_PASSWORD);

                frame.revalidate();
                frame.repaint();
            }
        });

        //test connection to server
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB_URL = URLField.getText();
                DB_USER = UsernameField.getText();
                DB_PASSWORD = PasswordField.getText();

                try{
                    Networking.connectToDB(DB_URL, DB_USER, DB_PASSWORD);
                }catch (Exception E){
                    JOptionPane.showMessageDialog(null ,"Error with connecting to Database with error: " + E);
                }
            }
        });

        //insert Method 1
        insertButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB_URL = URLField.getText();
                DB_USER = UsernameField.getText();
                DB_PASSWORD = PasswordField.getText();

                //create UI
                JFrame frame = new JFrame("Insert Stefan");
                frame.setSize(500, 150); // Set the desired size of the window.

                frame.setLocationRelativeTo(null);

                JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                inputPanel.add(new JLabel("How many Rows do you want generated:"));
                JTextField inputField = new JTextField(10);
                inputPanel.add(inputField);

                JButton startInput1 = new JButton("Start");
                inputPanel.add(startInput1);

                inputPanel.add(label1);

                frame.add(inputPanel);
                frame.setVisible(true);

                //start insert button
                startInput1.addActionListener(new ActionListener() {
                    @Override

                    public void actionPerformed(ActionEvent e) {
                        DB_URL = URLField.getText();
                        DB_USER = UsernameField.getText();
                        DB_PASSWORD = PasswordField.getText();

                        String countString = inputField.getText();  //get number of rows to insert
                        try {
                            int countInt = Integer.parseInt(countString);
                            inputPanel.revalidate();
                            inputPanel.repaint();
                            Networking.writeRandom(countInt, DB_URL, DB_USER, DB_PASSWORD);
                        } catch (Exception ex) {
                            label1.setText("Please insert a number");
                        }
                        // Repaint the panel to reflect the changes.
                        inputPanel.revalidate();
                        inputPanel.repaint();
                    }
                });
            }
        });

        //insert Method 1
        insertButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB_URL = URLField.getText();
                DB_USER = UsernameField.getText();
                DB_PASSWORD = PasswordField.getText();

                //create UI
                JFrame frame = new JFrame("Insert Tom Kyte");
                frame.setSize(500, 150); // Set the desired size of the window.

                frame.setLocationRelativeTo(null);

                JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                inputPanel.add(new JLabel("How many Rows do you want generated:"));
                JTextField inputField = new JTextField(10);
                inputPanel.add(inputField);

                JButton startInput1 = new JButton("Start");
                inputPanel.add(startInput1);

                inputPanel.add(label1);

                frame.add(inputPanel);
                frame.setVisible(true);

                //start insert button
                startInput1.addActionListener(new ActionListener() {
                    @Override

                    public void actionPerformed(ActionEvent e) {
                        DB_URL = URLField.getText();
                        DB_USER = UsernameField.getText();
                        DB_PASSWORD = PasswordField.getText();

                        String countString = inputField.getText();  //get number of rows to insert
                        try {
                            int countInt = Integer.parseInt(countString);
                            inputPanel.revalidate();
                            inputPanel.repaint();
                            Networking.instertTomKyte(countInt, DB_URL, DB_USER, DB_PASSWORD);
                        } catch (Exception ex) {
                            label1.setText("Please insert a number");
                        }
                        // Repaint the panel to reflect the changes.
                        inputPanel.revalidate();
                        inputPanel.repaint();
                    }
                });
            }
        });


        //delete all button for Table1
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB_URL = URLField.getText();
                DB_USER = UsernameField.getText();
                DB_PASSWORD = PasswordField.getText();
                try{
                    Networking.deleteAll(DB_URL, DB_USER, DB_PASSWORD);
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null ,"Error with connecting to Database with error: " + exception);
                }
            }
        });

        //delete all button for Table2
        deleteButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB_URL = URLField.getText();
                DB_USER = UsernameField.getText();
                DB_PASSWORD = PasswordField.getText();
                try{
                    Networking.deleteAll2(DB_URL, DB_USER, DB_PASSWORD);
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null ,"Error with connecting to Database with error: " + exception);
                }
            }
        });

        //fetch records button for table1
        fetchRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB_URL = URLField.getText();
                DB_USER = UsernameField.getText();
                DB_PASSWORD = PasswordField.getText();
                try{
                    Networking.displayAll(DB_URL, DB_USER, DB_PASSWORD);
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null ,"Error with connecting to Database with error: " + exception);
                }
            }
        });

        //fetch records button for table2
        fetchRecordsButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB_URL = URLField.getText();
                DB_USER = UsernameField.getText();
                DB_PASSWORD = PasswordField.getText();
                try{
                    Networking.displayAll2(DB_URL, DB_USER, DB_PASSWORD);
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null ,"Error with connecting to Database with error: " + exception);
                }
            }
        });

        //update button for table 1
        updateButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB_URL = URLField.getText();
                DB_USER = UsernameField.getText();
                DB_PASSWORD = PasswordField.getText();
                try{
                    Networking.replaceAllRandom(DB_URL, DB_USER, DB_PASSWORD);
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null ,"Error with connecting to Database with error: " + exception);
                }
            }
        });

        //update button for table 2
        updateButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB_URL = URLField.getText();
                DB_USER = UsernameField.getText();
                DB_PASSWORD = PasswordField.getText();
                try{
                    Networking.replaceAllRandom2(DB_URL, DB_USER, DB_PASSWORD);
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null ,"Error with connecting to Database with error: " + exception);
                }
            }
        });

        //generate tables button
        generateTables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DB_URL = URLField.getText();
                DB_USER = UsernameField.getText();
                DB_PASSWORD = PasswordField.getText();
                try{
                    Networking.createTables(DB_URL, DB_USER, DB_PASSWORD);
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null ,"Error with connecting to Database with error: " + exception);
                }
            }
        });
    }
}