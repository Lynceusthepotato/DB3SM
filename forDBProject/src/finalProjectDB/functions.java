package finalProjectDB;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class functions {

    mainUI mUI;
    visibilityManager vm;

    public functions(mainUI mainUI, visibilityManager visibilityManager) {
        mUI = mainUI;
        vm = visibilityManager;
    }

    public void insertIntoTable() {
        try{
            conDB.connection = DriverManager.getConnection(conDB.url,conDB.user,conDB.password);
            String q = ("insert into product(productID, name, price, description, stock) values (?,?,?,?,?)");
            PreparedStatement pst = conDB.connection.prepareStatement(q);
            pst.setString(1, mUI.productIDTextField.getText());
            pst.setString(2, mUI.nameTextField.getText());
            pst.setString(3, mUI.priceTextField.getText());
            pst.setString(4, mUI.descriptionTextField.getText());
            pst.setString(5, mUI.stockTextField.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Inserted successfully!");

        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void sortComboChange(){
        try{
            String inside = mUI.sortCombo.getSelectedItem().toString();
            if (inside.equals("ID")) {
                inside = "productID";
            }
            conDB.connection = DriverManager.getConnection(conDB.url,conDB.user,conDB.password);
            String q = ("select * from product order by " + inside);
            PreparedStatement pst = conDB.connection.prepareStatement(q);
            conDB.result = pst.executeQuery();
            mUI.productTable.setModel(DbUtils.resultSetToTableModel(conDB.result));
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void orderComboChange(){
        try{
            String inside = mUI.sortCombo.getSelectedItem().toString();
            if (inside.equals("ID")) {
                inside = "productID";
            }
            String insideOrder = mUI.orderCombo.getSelectedItem().toString();
            if (insideOrder.equals("Ascending")){
                insideOrder = "ASC";
            } else {
                insideOrder = "DESC";
            }
            conDB.connection = DriverManager.getConnection(conDB.url,conDB.user,conDB.password);
            String q = ("select * from product order by " + inside + " " + insideOrder);
            PreparedStatement pst = conDB.connection.prepareStatement(q);
            conDB.result = pst.executeQuery();
            mUI.productTable.setModel(DbUtils.resultSetToTableModel(conDB.result));
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void searchByName() {
        try {
            String q = "";
            String inside = mUI.searchName.getText();
            conDB.connection = DriverManager.getConnection(conDB.url, conDB.user, conDB.password);
            if(inside.equals(" ") || inside.equals("")) {
                q = ("select * from product");
            } else {
                q = ("select * from product where name=" + " '" + inside + "' ");
            }
            PreparedStatement pst = conDB.connection.prepareStatement(q);
            conDB.result = pst.executeQuery();
            mUI.productTable.setModel(DbUtils.resultSetToTableModel(conDB.result));
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void loginCheck() {
        try {
            conDB.connection = DriverManager.getConnection(conDB.url, conDB.user, conDB.password);
            String inside = mUI.staffNameTextField.getText();
            String insidePass = mUI.passwordTextField.getText();
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
                        vm.visibilityAtMenu();
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
            pst.setString(2, mUI.registerNameTF.getText());
            pst.setString(3, mUI.emailTF.getText());
            String gender = "";
            if (mUI.maleRadioButton.isSelected()) {
                gender = "Male";
            } else if (mUI.femaleRadioButton.isSelected()) {
                gender = "Female";
            } else if (mUI.otherRadioButton.isSelected()) {
                gender = "Other";
            }
            pst.setString(4, mUI.ageTF.getText());
            pst.setString(5, gender);
            pst.setString(6, mUI.addressTF.getText());
            pst.setString(7, mUI.telephoneTF.getText());
            pst.setString(8, mUI.passwordTF.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have successfully registered!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

}

