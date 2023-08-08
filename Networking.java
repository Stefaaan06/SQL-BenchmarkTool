import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Random;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*
 * Handles the Networking / performing of actions
 * @version 1.0.0 - release
 * @author Stefan
 */
public class Networking {
    public static JLabel connectionLabel;
    public static void connectToDB(String DB_URL, String DB_USER, String DB_PASSWORD) throws SQLException{

        JFrame frame = new JFrame("Database Connection");
        frame.setLayout(new BorderLayout());
        Dimension d = new Dimension(500, 100);
        frame.setPreferredSize(d);
        connectionLabel = new JLabel("Count: 0");
        frame.add(connectionLabel, BorderLayout.CENTER);
        connectionLabel.setText("Trying to connect...");
        centreWindow(frame);

        frame.pack();
        frame.setVisible(true);

        OracleDataSource ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setUser(DB_USER);
        ods.setPassword(DB_PASSWORD);

        new Thread(() -> {
            try (OracleConnection connection = (OracleConnection)
                    ods.getConnection()) {
                connectionLabel.setText("Connection successful");
            }catch (Exception e){
                connectionLabel.setText("Connection failed with error: " + e);
            }
        }).start();
    }

    public static JLabel countLabel;
    public static void writeRandom (int num, String DB_URL, String DB_USER, String DB_PASSWORD) throws SQLException {
        JFrame frame = new JFrame("Write " + num + " rows to Table1");
        frame.setLayout(new BorderLayout());
        Dimension d = new Dimension(500, 100);
        frame.setPreferredSize(d);
        countLabel = new JLabel("Connecting...");
        frame.add(countLabel, BorderLayout.CENTER);
        centreWindow(frame);

        frame.pack();
        frame.setVisible(true);

        OracleDataSource ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setUser(DB_USER);
        ods.setPassword(DB_PASSWORD);

        new Thread(() -> {
            try (OracleConnection connection = (OracleConnection)
                    ods.getConnection()) {
                Stopwatch.start();
                int ageRandom;
                String nameRandom = "";
                String emailRandom = "";
                Random rand = new Random();
                int count = 0;
                for(int i = num; i >= 1; i--){
                    ageRandom = rand.nextInt(100);

                    nameRandom = "";
                    for(int y = rand.nextInt(2,10); y > 0; y--){
                        nameRandom += (char) rand.nextInt(65,122);
                    }

                    emailRandom = "";
                    for(int y = rand.nextInt(2,10); y > 0; y--){
                        emailRandom += (char) rand.nextInt(65,122);
                    }
                    emailRandom += "@gmail.com";

                    String query = "INSERT INTO TEST_TABLE1 (name, email, age) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, nameRandom);
                    pstmt.setString(2, emailRandom);
                    pstmt.setInt(3, ageRandom);
                    pstmt.executeUpdate();
                    pstmt.close();
                    count++;
                    updateCountLabel(count);
                }
                double time = Stopwatch.getElapsedTimeInSeconds();
                Statement statement = ((OracleConnection) ods.getConnection()).createStatement();
                statement.executeQuery("commit");
                countLabel.setText("Write Completed, finished in " + time + " seconds.");
                connection.close();
            }catch (Exception e){
                countLabel.setText("Failed to connect to Database with error: " + e);
            }
        }).start();
    }


    public static void instertTomKyte(int num, String DB_URL, String DB_USER, String DB_PASSWORD) throws SQLException {
        JFrame frame = new JFrame("Write " + num + " rows to Table1");
        frame.setLayout(new BorderLayout());
        Dimension d = new Dimension(500, 100);
        frame.setPreferredSize(d);
        countLabel = new JLabel("Connecting...");
        frame.add(countLabel, BorderLayout.CENTER);
        centreWindow(frame);

        frame.pack();
        frame.setVisible(true);

        OracleDataSource ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setUser(DB_USER);
        ods.setPassword(DB_PASSWORD);

        new Thread(() -> {
            try (OracleConnection connection = (OracleConnection)
                    ods.getConnection()) {
                Stopwatch.start();

                String insertSQL = "INSERT /*+ append */ INTO TEST_TABLE2 " +
                        "SELECT ROWNUM, OWNER, OBJECT_NAME, SUBOBJECT_NAME, " +
                        "OBJECT_ID, DATA_OBJECT_ID, OBJECT_TYPE, CREATED, LAST_DDL_TIME, " +
                        "TIMESTAMP, STATUS, TEMPORARY, GENERATED, SECONDARY " +
                        "FROM all_objects a WHERE ROWNUM <= ?";

                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
                preparedStatement.setInt(1, num);
                preparedStatement.executeUpdate();
                double time = Stopwatch.getElapsedTimeInSeconds();
                countLabel.setText("Write Completed, finished in " + time + " seconds.");
                connection.close();
            }catch (Exception e){
                countLabel.setText("Failed to connect to Database with error: " + e);
            }
        }).start();

    }

    private static void updateCountLabel(int count) {
            SwingUtilities.invokeLater(() -> countLabel.setText("Count: " + count));
    }

    public static void deleteAll (String DB_URL, String DB_USER, String DB_PASSWORD) throws SQLException {

        JFrame frame = new JFrame("Delete all rows from Table1.");
        frame.setLayout(new BorderLayout());
        Dimension d = new Dimension(500, 100);
        frame.setPreferredSize(d);
        connectionLabel = new JLabel("Connecting...");
        frame.add(connectionLabel, BorderLayout.CENTER);
        centreWindow(frame);

        frame.pack();
        frame.setVisible(true);

        OracleDataSource ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setUser(DB_USER);
        ods.setPassword(DB_PASSWORD);

        new Thread(() -> {
            try (OracleConnection connection = (OracleConnection)ods.getConnection()) {
                connectionLabel.setText("Deleting everything...");
                Stopwatch.start();
                Statement statement = ((OracleConnection) ods.getConnection()).createStatement();
                statement.executeQuery("TRUNCATE TABLE TEST_TABLE1");
                double time = Stopwatch.getElapsedTimeInSeconds();
                connectionLabel.setText("All rows removed successfully in " + time + " Seconds.");
                statement.executeQuery("commit");
            }catch (Exception e){
                connectionLabel.setText("Failed to connect to Database with error: " + e);
                System.out.println(e);
            }
        }).start();
    }

    public static void deleteAll2 (String DB_URL, String DB_USER, String DB_PASSWORD) throws SQLException {

        JFrame frame = new JFrame("Delete all rows from Table2.");
        frame.setLayout(new BorderLayout());
        Dimension d = new Dimension(500, 100);
        frame.setPreferredSize(d);
        connectionLabel = new JLabel("Connecting...");
        frame.add(connectionLabel, BorderLayout.CENTER);
        centreWindow(frame);

        frame.pack();
        frame.setVisible(true);

        OracleDataSource ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setUser(DB_USER);
        ods.setPassword(DB_PASSWORD);

        new Thread(() -> {
            try (OracleConnection connection = (OracleConnection)ods.getConnection()) {
                connectionLabel.setText("Deleting everything...");
                Stopwatch.start();
                Statement statement = ((OracleConnection) ods.getConnection()).createStatement();
                statement.executeQuery("TRUNCATE TABLE TEST_TABLE2");
                double time = Stopwatch.getElapsedTimeInSeconds();
                connectionLabel.setText("All rows removed successfully in " + time + " Seconds.");
                statement.executeQuery("commit");
            }catch (Exception e){
                connectionLabel.setText("Failed to connect to Database with error: " + e);
                System.out.println(e);
            }
        }).start();
    }


    public static void displayAll(String DB_URL, String DB_USER, String DB_PASSWORD) throws SQLException{

        new Thread(() -> {
            try {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String query = "SELECT * FROM TEST_TABLE1 FETCH FIRST 100 ROWS ONLY";
                ResultSet resultSet = connection.createStatement().executeQuery(query);


                String[] columnNames = {"Name", "Email", "Age", "UID"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                while (resultSet.next()) {
                    String column1Value = resultSet.getString("Name");
                    String column2Value = resultSet.getString("Email");
                    Integer column3Value = resultSet.getInt("Age");
                    Integer column4Value = resultSet.getInt("ID");

                    Object[] rowData = {column1Value, column2Value, column3Value, column4Value};

                    tableModel.addRow(rowData);
                }

                JTable table = new JTable(tableModel);

                JScrollPane scrollPane = new JScrollPane(table);

                JButton reloadButton = new JButton("Reload");

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(reloadButton);


                JFrame frame = new JFrame("Data display Table1");
                frame.setLayout(new BorderLayout());
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.add(buttonPanel, BorderLayout.SOUTH);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                resultSet.close();
                connection.close();

                reloadButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            displayAll(DB_URL, DB_USER, DB_PASSWORD);
                            frame.dispose();
                        }catch (Exception error){
                            JOptionPane.showMessageDialog(null,"Failed to connect to Database with error: " + error);
                        }
                    }
                });

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Failed to connect to Database with error: " + e);
            }
        }).start();
    }

    public static void displayAll2(String DB_URL, String DB_USER, String DB_PASSWORD) throws SQLException{

        new Thread(() -> {
            try {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String query = "SELECT * FROM TEST_TABLE2 FETCH FIRST 100 ROWS ONLY";
                ResultSet resultSet = connection.createStatement().executeQuery(query);


                String[] columnNames = {"ID", "OWNER", "OBJECT_NAME", "SUBOBJECT_NAME", "OBJECT_ID","DATA_OBJECT_ID", "OBJECT_TYPE", "CREATED", "LAST_DLL_TIME", "TIMESTAMP", "STATUS", "TEMPORARY", "GENERATED", "SECONDARY"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                int column1, column5 ,column6;
                Date column8, column9;
                String column2,column3,column4, column7, column10, column11, column12, column13, column14;

                while (resultSet.next()) {
                    column1 = resultSet.getInt(1);
                    if (resultSet.wasNull()) {
                        column1 = 0;
                    }
                    column2 = resultSet.getString(2);
                    column3 = resultSet.getString(3);
                    column4 = resultSet.getString(4);
                    if (resultSet.wasNull()) {
                        column4 = "null";
                    }
                    column5 = resultSet.getInt(5);
                    column6 = resultSet.getInt(6);
                    if (resultSet.wasNull()) {
                        column6 = 0;
                    }
                    column7 = resultSet.getString(7);
                    if (resultSet.wasNull()) {
                        column7 = "null";
                    }
                    column8 = resultSet.getDate(8);
                    column9 = resultSet.getDate(9);
                    column10 = resultSet.getString(10);
                    if (resultSet.wasNull()) {
                        column10 = "null";
                    }
                    column11 = resultSet.getString(11);
                    if (resultSet.wasNull()) {
                        column11 = "null";
                    }
                    column12 = resultSet.getString(12);
                    if (resultSet.wasNull()) {
                        column12 = "null";
                    }
                    column13 = resultSet.getString(13);
                    if (resultSet.wasNull()) {
                        column13 = "null";
                    }
                    column14 = resultSet.getString(14);
                    if (resultSet.wasNull()) {
                        column14 = "null";
                    }

                    Object[] rowData = {column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14};
                    tableModel.addRow(rowData);
                }

                JTable table = new JTable(tableModel);

                JScrollPane scrollPane = new JScrollPane(table);

                JButton reloadButton = new JButton("Reload");

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(reloadButton);


                JFrame frame = new JFrame("Data display Table2");
                frame.setLayout(new BorderLayout());
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.add(buttonPanel, BorderLayout.SOUTH);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                resultSet.close();
                connection.close();

                reloadButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            displayAll2(DB_URL, DB_USER, DB_PASSWORD);
                            frame.dispose();
                        }catch (Exception error){
                            JOptionPane.showMessageDialog(null,"Failed to connect to Database with error: " + error);
                        }
                    }
                });

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Failed to connect to Database with error: " + e);
            }
        }).start();
    }


    public static void replaceAllRandom (String DB_URL, String DB_USER, String DB_PASSWORD) throws SQLException {;
        JFrame frame = new JFrame("Update all rows Table1");
        frame.setLayout(new BorderLayout());
        Dimension d = new Dimension(500, 100);
        frame.setPreferredSize(d);
        countLabel = new JLabel("Connecting...");
        frame.add(countLabel, BorderLayout.CENTER);
        centreWindow(frame);

        frame.pack();
        frame.setVisible(true);

        OracleDataSource ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setUser(DB_USER);
        ods.setPassword(DB_PASSWORD);

        new Thread(() -> {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                Stopwatch.start();
                String selectQuery = "SELECT * FROM TEST_TABLE1";
                String updateQuery = "UPDATE TEST_TABLE1 SET name = ?, email = ?, age = ? WHERE id = ?";

                int ageRandom;
                String nameRandom = "";
                String emailRandom = "";
                Random rand = new Random();
                int count = 0;

                try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                     ResultSet resultSet = selectStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");

                        ageRandom = rand.nextInt(100);

                        nameRandom = "";
                        for(int y = rand.nextInt(2,10); y > 0; y--){
                            nameRandom += (char) rand.nextInt(65,122);
                        }

                        emailRandom = "";
                        for(int y = rand.nextInt(2,10); y > 0; y--){
                            emailRandom += (char) rand.nextInt(65,122);
                        }
                        emailRandom += "@gmail.com";


                        // Update the row in the table
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setString(1, nameRandom);
                            updateStatement.setString(2, emailRandom);
                            updateStatement.setInt(3, ageRandom);
                            updateStatement.setInt(4, id);
                            updateStatement.executeUpdate();
                        }
                        count++;
                        updateCountLabel(count);
                    }

                    double time = Stopwatch.getElapsedTimeInSeconds();
                    countLabel.setText("All rows updated successfully in " + time + " Seconds.");
                    Statement statement = ((OracleConnection) ods.getConnection()).createStatement();
                    statement.executeQuery("commit");
                    connection.close();
                }
            } catch (SQLException e) {
                countLabel.setText("Failed to connect to Database with error: " + e);
            }
        }).start();
    }

    public static void replaceAllRandom2 (String DB_URL, String DB_USER, String DB_PASSWORD) throws SQLException {;
        JFrame frame = new JFrame("Update all rows Table2");
        frame.setLayout(new BorderLayout());
        Dimension d = new Dimension(500, 100);
        frame.setPreferredSize(d);
        countLabel = new JLabel("Connecting...");
        frame.add(countLabel, BorderLayout.CENTER);
        centreWindow(frame);

        frame.pack();
        frame.setVisible(true);

        OracleDataSource ods = new OracleDataSource();
        ods.setURL(DB_URL);
        ods.setUser(DB_USER);
        ods.setPassword(DB_PASSWORD);

        new Thread(() -> {
            try (OracleConnection connection = (OracleConnection)ods.getConnection()) {
                Statement statement = ((OracleConnection) ods.getConnection()).createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM TEST_TABLE2");
                int rowCount = 0;
                if (resultSet.next()) {
                    rowCount = resultSet.getInt(1); // Get the count from the first column
                    System.out.println("Number of rows: " + rowCount);
                }
                statement.executeQuery("TRUNCATE TABLE TEST_TABLE2");
                instertTomKyte(rowCount, DB_URL, DB_USER, DB_PASSWORD);

                statement.close();
                resultSet.close();
                connection.close();
                frame.dispose();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,"Failed to connect to Database with error: " + e);
                frame.dispose();
            }
        }).start();
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public static void createTables(String DB_URL, String DB_USER, String DB_PASSWORD){

        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            // Get database metadata
            DatabaseMetaData metaData = connection.getMetaData();

            // Check if the table exists
            ResultSet tables = metaData.getTables(null, null, "TEST_TABLE1", null);
            boolean table1Exists = tables.next();

            if (table1Exists) {
                JOptionPane.showMessageDialog(null,"Table exists in the database.");
            } else {
                JOptionPane.showMessageDialog(null,"Table does not exist in the database.");
                statement.executeQuery("\n" +
                        "  CREATE TABLE \"SYSTEM\".\"TEST_TABLE1\" \n" +
                        "   (\t\"NAME\" VARCHAR2(20 BYTE) NOT NULL ENABLE, \n" +
                        "\t\"EMAIL\" VARCHAR2(20 BYTE) NOT NULL ENABLE, \n" +
                        "\t\"AGE\" NUMBER(*,0) NOT NULL ENABLE, \n" +
                        "\t\"ID\" NUMBER GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  NOT NULL ENABLE\n" +
                        "   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 \n" +
                        " NOCOMPRESS LOGGING\n" +
                        "  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645\n" +
                        "  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1\n" +
                        "  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)\n" +
                        "  TABLESPACE \"SYSTEM\" \n" +
                        "\n");
            }

            ResultSet tables2 = metaData.getTables(null, null, "TEST_TABLE2", null);
            boolean table2Exists = tables2.next();

            if (table2Exists) {
                JOptionPane.showMessageDialog(null,"Table exists in the database.");
            } else {
                JOptionPane.showMessageDialog(null,"Table does not exist in the database.");
                statement.executeQuery("  CREATE TABLE \"SYSTEM\".\"TEST_TABLE2\" \n" +
                        "   (\t\"ID\" NUMBER, \n" +
                        "\t\"OWNER\" VARCHAR2(128 BYTE) NOT NULL ENABLE, \n" +
                        "\t\"OBJECT_NAME\" VARCHAR2(128 BYTE) NOT NULL ENABLE, \n" +
                        "\t\"SUBOBJECT_NAME\" VARCHAR2(128 BYTE), \n" +
                        "\t\"OBJECT_ID\" NUMBER NOT NULL ENABLE, \n" +
                        "\t\"DATA_OBJECT_ID\" NUMBER, \n" +
                        "\t\"OBJECT_TYPE\" VARCHAR2(23 BYTE), \n" +
                        "\t\"CREATED\" DATE NOT NULL ENABLE, \n" +
                        "\t\"LAST_DDL_TIME\" DATE NOT NULL ENABLE, \n" +
                        "\t\"TIMESTAMP\" VARCHAR2(19 BYTE), \n" +
                        "\t\"STATUS\" VARCHAR2(7 BYTE), \n" +
                        "\t\"TEMPORARY\" VARCHAR2(1 BYTE), \n" +
                        "\t\"GENERATED\" VARCHAR2(1 BYTE), \n" +
                        "\t\"SECONDARY\" VARCHAR2(1 BYTE)\n" +
                        "   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 \n" +
                        " NOCOMPRESS NOLOGGING\n" +
                        "  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645\n" +
                        "  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1\n" +
                        "  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)\n" +
                        "  TABLESPACE \"SYSTEM\"");
            }

            // Close resources
            tables.close();
            connection.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed with error" + e);
        }
    }
}
