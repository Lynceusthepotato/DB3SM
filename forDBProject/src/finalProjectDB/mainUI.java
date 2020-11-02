package finalProjectDB;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.util.*;

public class mainUI {

    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel productPanel;
    private JPanel registerPanel;
    private JPanel loginMenu;
    
    private JLabel supermarketLabel;
    private JButton productButton;
    private JTable productTable;
    private JButton backButton;
    private JComboBox sortCombo;
    private JComboBox orderCombo;
    private JTextField searchName;
    private JTextField productIDTextField;
    private JTextField nameTextField;
    private JTextField priceTextField;
    private JTextField descriptionTextField;
    private JButton saveInputButton;
    private JTextField stockTextField;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField staffNameTextField;
    private JTextField passwordTextField;
    private JTextField registerNameTF;
    private JTextField emailTF;
    private JTextField ageTF;
    private JTextField telephoneTF;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JRadioButton otherRadioButton;
    private JButton backButton1;
    private JButton signUpButton;
    private JTextField addressTF;
    private JTextField passwordTF;
    private JButton backButtonMM;

    public mainUI() {
        // Data catch from SQL
        fetch();

        // Data to start
        createSortCombo();
        createOrderCombo();
        visibilityatStart();

        sortCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortComboChange();
            }
        });

        orderCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderComboChange();
            }
        });

        // Main menu
        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visibilityAtProduct();
            }
        });

        backButtonMM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visibilityatStart();
            }
        });

        // Product menu
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        });

        searchName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByName();
            }
        });

        saveInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertIntoTable();
                fetch();
            }
        });

        // Stuff
        productTable.getTableHeader().setBackground(Color.WHITE);

        // This is to make the table value in the center

        //DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        //centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        //productTable.getColumn("productID").setCellRenderer(centerRenderer);
        //productTable.getColumn("price").setCellRenderer(centerRenderer);
        //productTable.getColumn("stock").setCellRenderer(centerRenderer);

        // Login menu

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginCheck();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visibilitytoRegister();
            }
        });

        // Register menu

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toRegister();
            }
        });


        backButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               visibilityatStart();
            }
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void fetch(){
        try{
            conDB.connection = DriverManager.getConnection(conDB.url,conDB.user,conDB.password);
            String q = "Select * from Supermarket.product";
            conDB.statement = conDB.connection.prepareStatement(q);
            conDB.result = conDB.statement.executeQuery(q);
            productTable.setModel(DbUtils.resultSetToTableModel(conDB.result));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void visibilityatStart() {
        // Show
        loginMenu.setVisible(true);

        // Hide
        menuPanel.setVisible(false);
        registerPanel.setVisible(false);
        productPanel.setVisible(false);

    }

    public void visibilitytoRegister(){
        // Show
        registerPanel.setVisible(true);

        // Hide
        loginMenu.setVisible(false);
        menuPanel.setVisible(false);
        productPanel.setVisible(false);
    }

    public void visibilityAtProduct() {
        // Show
        productPanel.setVisible(true);

        // Hide
        loginMenu.setVisible(false);
        menuPanel.setVisible(false);
        registerPanel.setVisible(false);
    }

    public void createSortCombo() {
        sortCombo.setModel(new DefaultComboBoxModel(new String[] {"ID", "Name", "Price", "Stock"}));
    }

    public void createOrderCombo() {
        orderCombo.setModel(new DefaultComboBoxModel(new String[] {"Ascending", "Descending"}));
    }

    public void insertIntoTable() {
        try{
            conDB.connection = DriverManager.getConnection(conDB.url,conDB.user,conDB.password);
            String q = ("insert into product(productID, name, price, description, stock) values (?,?,?,?,?)");
            PreparedStatement pst = conDB.connection.prepareStatement(q);
            pst.setString(1, productIDTextField.getText());
            pst.setString(2, nameTextField.getText());
            pst.setString(3, priceTextField.getText());
            pst.setString(4, descriptionTextField.getText());
            pst.setString(5, stockTextField.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Inserted successfully!");

        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void sortComboChange(){
        try{
            String inside = sortCombo.getSelectedItem().toString();
            if (inside.equals("ID")) {
                inside = "productID";
            }
            conDB.connection = DriverManager.getConnection(conDB.url,conDB.user,conDB.password);
            String q = ("select * from product order by " + inside);
            PreparedStatement pst = conDB.connection.prepareStatement(q);
            conDB.result = pst.executeQuery();
            productTable.setModel(DbUtils.resultSetToTableModel(conDB.result));
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void orderComboChange(){
        try{
            String inside = sortCombo.getSelectedItem().toString();
            if (inside.equals("ID")) {
                inside = "productID";
            }
            String insideOrder = orderCombo.getSelectedItem().toString();
            if (insideOrder.equals("Ascending")){
                insideOrder = "ASC";
            } else {
                insideOrder = "DESC";
            }
            conDB.connection = DriverManager.getConnection(conDB.url,conDB.user,conDB.password);
            String q = ("select * from product order by " + inside + " " + insideOrder);
            PreparedStatement pst = conDB.connection.prepareStatement(q);
            conDB.result = pst.executeQuery();
            productTable.setModel(DbUtils.resultSetToTableModel(conDB.result));
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void searchByName() {
        try {
            String q = "";
            String inside = searchName.getText();
            conDB.connection = DriverManager.getConnection(conDB.url, conDB.user, conDB.password);
            if(inside.equals(" ") || inside.equals("")) {
                q = ("select * from product");
            } else {
                q = ("select * from product where name=" + " '" + inside + "' ");
            }
            PreparedStatement pst = conDB.connection.prepareStatement(q);
            conDB.result = pst.executeQuery();
            productTable.setModel(DbUtils.resultSetToTableModel(conDB.result));
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void loginCheck() {
        try {
            conDB.connection = DriverManager.getConnection(conDB.url, conDB.user, conDB.password);
            String inside = staffNameTextField.getText();
            String insidePass = passwordTextField.getText();
            String q = "select * from staff where name=" + " '" + inside + "' ";
            String qpass = "select password from staff where name=" + " '" + inside + "' ";
            PreparedStatement pst2 = conDB.connection.prepareStatement(qpass);
            conDB.result = pst2.executeQuery();
            if (conDB.result.next()){
                String pass = conDB.result.getString("password");
                if (pass.contains(insidePass)){
                    PreparedStatement pst = conDB.connection.prepareStatement(q);
                    conDB.result = pst.executeQuery();
                    if (conDB.result.next()){
                        JOptionPane.showMessageDialog(null, "Welcome back!");
                        menuPanel.setVisible(true);
                        loginMenu.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Your input is incorrect");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Your input is incorrect");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Your input is incorrect");
            }

        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Your input is incorrect");
        }
    }

    public void toRegister() {
        try {
            conDB.connection = DriverManager.getConnection(conDB.url, conDB.user, conDB.password);
            String q = ("insert into staff(staffID, name, email, age, gender, address, telephone) values (?,?,?,?,?,?,?,?)");

            String findID = ("Select max(staffID) as staffID from staff");
            PreparedStatement pst2 = conDB.connection.prepareStatement(findID);
            conDB.result = pst2.executeQuery();

            int bigNum = 1;
            if (conDB.result.next()){
                bigNum  = conDB.result.getInt("staffID");
                bigNum += 1;
            }

            PreparedStatement pst = conDB.connection.prepareStatement(q);

            pst.setString(1, String.valueOf(bigNum));
            pst.setString(2, registerNameTF.getText());
            pst.setString(3, emailTF.getText());
            String gender = "";
            if (maleRadioButton.isSelected()) {
                gender = "Male";
            } else if (femaleRadioButton.isSelected()) {
                gender = "Female";
            } else if (otherRadioButton.isSelected()) {
                gender = "Other";
            }
            pst.setString(4, ageTF.getText());
            pst.setString(5, gender);
            pst.setString(6, addressTF.getText());
            pst.setString(7, telephoneTF.getText());
            pst.setString(8, passwordTF.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have successfully registered!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
