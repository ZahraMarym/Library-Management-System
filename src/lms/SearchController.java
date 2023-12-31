/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package lms;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Zahra Maryam
 */
public class SearchController implements Initializable {

    @FXML
    private TableView<Books> tableVIew4;
    @FXML
    private TableColumn<Books, String> Col_name;
    @FXML
    private TableColumn<Books, String> COl_Author;
    @FXML
    private TableColumn<Books, String> Col_Category;
    @FXML
    private TableColumn<Books, String> Col_Edition;
    @FXML
    private Button BackB;
    @FXML
    private TextField TFSearch;
    @FXML
    private Button SearchB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showTable();
            TFSearch.setText("Enter Name");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }    

    @FXML
    private void HandleBackAction(ActionEvent event) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("User.fxml"));
            Stage stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene1 = new Scene(root2 );
            stage1.setScene(scene1);
            stage1.show();
    }
    private void executeQuerey(String querey) {
       Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(querey);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }   
    
    private void showTable() throws SQLException{
        ObservableList<Books> oblist = getList();
        Col_name.setCellValueFactory(new PropertyValueFactory<> ("Name"));
        COl_Author.setCellValueFactory(new PropertyValueFactory<> ("Author"));
        Col_Category.setCellValueFactory(new PropertyValueFactory<> ("Category"));
        Col_Edition.setCellValueFactory(new PropertyValueFactory<> ("Edition"));
        tableVIew4.setItems(oblist);
    }
    private ObservableList<Books> getList() throws SQLException{
            ObservableList<Books> oblist = FXCollections.observableArrayList();
            Connection con = getConnection();
            String querey = "SELECT * FROM lms.books;";
            Statement ps;
            ResultSet rs;
            ps = con.createStatement();
            rs = ps.executeQuery(querey);
            Books mc ;
            while(rs.next()){
               
                    mc = new Books(rs.getString("Name"),rs.getString("Author"), rs.getString("Category"),rs.getString("Edition"));
                    oblist.add(mc);
                } 
            return oblist;
        }
    private Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","Zahra@115");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
    

    @FXML
    private void HandleSearchButton(ActionEvent event) throws SQLException {
        Connection con = getConnection();
        ObservableList<Books> oblist = FXCollections.observableArrayList();
        String querey = "SELECT * FROM lms.books WHERE name = '"+TFSearch.getText()+"';"; 
            executeQuerey(querey);
            Statement ps;
            ResultSet rs;
            ps = con.createStatement();
            rs = ps.executeQuery(querey);
            Books mc ;
            while(rs.next()){
               
                    mc = new Books(rs.getString("Name"),rs.getString("Author"), rs.getString("Category"),rs.getString("Edition"));
                    oblist.add(mc);
                } 
            tableVIew4.setItems(oblist);
    }
}
