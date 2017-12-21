package pttk;

import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login implements Initializable {
	private Connection con;
	private PreparedStatement pst;
	@FXML 
	private TextField jtfuser;
	@FXML
	private TextField jtfpass;
	
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		con=dba.DBConnection.myConnection();
	}
	@FXML
	private void login(ActionEvent event) throws Exception,SQLException {
		
		String ID = jtfuser.getText();
		String password = jtfpass.getText();
		boolean test = true;
		if(ID.equals(null)||ID.equals("")||password.equals(null)||password.equals("")) {
			JOptionPane.showMessageDialog(null,"Vui lòng nhập đủ tên tài khoản và mật khẩu!");
			test=false;
		
		}
		if(test) {
		try {
			pst=con.prepareStatement("Select position from dangnhap where ID=? and password=?");
			pst.setString(1, ID);
			pst.setString(2, password);
			ResultSet result= pst.executeQuery();
			if(result.next()) {
						pst=con.prepareStatement("update idhientai set ID=? where stt='1'");
						pst.setString(1, ID);
						pst.executeUpdate();
						((Node) (event.getSource())).getScene().getWindow().hide();
						Stage stage = new Stage();
						stage.setTitle("Main Menu");
						Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
						Scene scene = new Scene(root,779,487);
						scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
						stage.setScene(scene);
						stage.show();
					}
			
			else {
				JOptionPane.showMessageDialog(null , "ID hoặc password không chính xác!");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}		
	}
	

}
