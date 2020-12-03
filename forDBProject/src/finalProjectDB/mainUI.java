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
    // Panel
    public JPanel mainPanel;
    public JPanel menuPanel;
    public JPanel productPanel;
    public JPanel registerPanel;
    public JPanel loginMenu;

    // TextField
    public JTextField productIDTextField;
    public JTextField nameTextField;
    public JTextField priceTextField;
    public JTextField descriptionTextField;
    public JTextField staffNameTextField;
    public JTextField passwordTextField;
    public JTextField registerNameTF;
    public JTextField emailTF;
    public JTextField ageTF;
    public JTextField telephoneTF;
    public JTextField addressTF;
    public JTextField passwordTF;
    public JTextField stockTextField;
    public JTextField searchName;

    // Button
    private JButton productButton;
    private JButton saveInputButton;
    private JButton backButton;
    private JButton backButton1;
    private JButton signUpButton;
    private JButton backButtonMM;
    private JButton loginButton;
    private JButton registerButton;

    // Label
    private JLabel supermarketLabel;

    // Table
    public JTable productTable;

    // ComboBox
    public JComboBox sortCombo;
    public JComboBox orderCombo;

    // RadioButton
    public JRadioButton maleRadioButton;
    public JRadioButton femaleRadioButton;
    public JRadioButton otherRadioButton;


    visibilityManager vm = new visibilityManager(this);
    functions f = new functions(this, vm);


    public mainUI() {
        // Data catch from SQL
        fetch();

        // Data to start
        createSortCombo();
        createOrderCombo();
        vm.visibilityatStart();

        sortCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.sortComboChange();
            }
        });

        orderCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.orderComboChange();
            }
        });

        // Main menu
        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.visibilityAtProduct();
            }
        });

        backButtonMM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.visibilityatStart();
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
                f.searchByName();
            }
        });

        saveInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.insertIntoTable();
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
                f.loginCheck();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.visibilitytoRegister();
            }
        });

        // Register menu

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.toRegister();
            }
        });


        backButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.visibilityatStart();
            }
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void fetch() {
        try {
            conDB.connection = DriverManager.getConnection(conDB.url, conDB.user, conDB.password);
            String q = "Select * from Supermarket.product";
            conDB.statement = conDB.connection.prepareStatement(q);
            conDB.result = conDB.statement.executeQuery(q);
            productTable.setModel(DbUtils.resultSetToTableModel(conDB.result));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void createSortCombo() {
        sortCombo.setModel(new DefaultComboBoxModel(new String[]{"ID", "Name", "Price", "Stock"}));
    }

    public void createOrderCombo() {
        orderCombo.setModel(new DefaultComboBoxModel(new String[]{"Ascending", "Descending"}));
    }
}