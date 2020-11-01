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
    private JPanel loginMenu;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField staffNameTextField;
    private JTextField passwordTextField;

    Set<String> password = new HashSet<String>();

    private void setPassword() {
        password.add("Password");
        password.add("Bruh");
        password.add("Potato");
        password.add("ImStaff");
    }

    public mainUI() {
        // Data catch from SQL
        fetch();

        // Data
        createSortCombo();
        createOrderCombo();
        setPassword(); // IDK

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
                menuPanel.setVisible(false);
                productPanel.setVisible(true);
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
            PreparedStatement pst = conDB.connection.prepareStatement(q);
            if (password.contains(insidePass)){
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

        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Your input is incorrect");
        }
    }
}
