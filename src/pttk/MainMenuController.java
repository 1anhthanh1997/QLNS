package pttk;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javax.swing.JOptionPane;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController implements Initializable{
	private String position;
	private Connection con;
	private ResultSet rs;
	private String username;
	@FXML
	private Label lbname;
	private String fullname;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		con=dba.DBConnection.myConnection();
		getusername();
		getposition();
		fullname=getname();
		lbname.setText(fullname);
		
	}
	private void getusername() {
		try {
			rs=con.createStatement().executeQuery("Select ID from idhientai");
			while(rs.next()) {
				username=rs.getString(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getposition() {
		
		  try {
	            //Connection conn = dc.Connect();
	           // data = FXCollections.observableArrayList();
	            // Execute query and store result in a resultset
	            ResultSet rs = con.createStatement().executeQuery("SELECT position FROM dangnhap where ID LIKE '%" + username + "%'");
	            while (rs.next()) {
	                //get string from db,whichever way 
	                position= rs.getString(1);
	            }

	        } catch (SQLException ex) {
	            System.err.println("Error"+ex);
	        }
	        
	}
   @FXML
   private void quantrihethong(ActionEvent event) throws Exception{
	   if(position.equals("Quản trị hệ thống")) {
	   ((Node) (event.getSource())).getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Quantrihethong.fxml"));
		Scene scene = new Scene(root,700,696);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	   }
	   else {
		   JOptionPane.showMessageDialog(null, "Chỉ có quản trị hệ thống mới được truy cập chức năng này!");
	   }
	
   }
   @FXML
   private void quanlynhansu(ActionEvent event) throws Exception{
	   
	   ((Node) (event.getSource())).getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Thongtinnhanvien.fxml"));
		Scene scene = new Scene(root,1100,696);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	  
   }
   @FXML
   private void quanlyhopdong(ActionEvent event) throws Exception{
	  
	   ((Node) (event.getSource())).getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Quanlyhopdong.fxml"));
		Scene scene = new Scene(root,700,696);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	 
   }
   @FXML
   private void trangcanhan(ActionEvent event) throws Exception{
	  
	   ((Node) (event.getSource())).getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Trangcanhan.fxml"));
		Scene scene = new Scene(root,700,696);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	 
   }
   private String getname() {
	   String name=null;
	   try {
			rs=con.createStatement().executeQuery("Select name from dangnhap where ID LIKE '%" + username + "%'");
			while(rs.next()) {
				name=rs.getString(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return name;
   }
   @FXML
   private void changepass(ActionEvent event) throws Exception{
	  
	   ((Node) (event.getSource())).getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Doimatkhau.fxml"));
		Scene scene = new Scene(root,400,320);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	 
   }
   @FXML
   private void logout(ActionEvent event) throws Exception{
	  
	   ((Node) (event.getSource())).getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Dangnhap.fxml"));
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	 
   }
}
