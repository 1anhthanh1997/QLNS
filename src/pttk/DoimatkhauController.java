package pttk;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javax.swing.JOptionPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class DoimatkhauController implements Initializable{
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	@FXML
	private TextField jtfoldpassword;
	@FXML
	private TextField jtfnewpassword;
	@FXML
	private TextField jtfnewpassword2;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		con=dba.DBConnection.myConnection();
	}
	@FXML
	private void changepass(ActionEvent event){
		String oldpassword=jtfoldpassword.getText();
		String newpassword=jtfnewpassword.getText();
		String newpassword2=jtfnewpassword2.getText();
		String username=null;
		boolean test=true;
		if(oldpassword.equals(null)||oldpassword.equals("")||newpassword.equals(null)||newpassword.equals("")||newpassword2.equals(null)||newpassword2.equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
			test=false;
		}
		if(test) {
		try {
			rs=con.createStatement().executeQuery("Select ID from idhientai");
			while(rs.next()) {
				username=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pst=con.prepareStatement("Select position from dangnhap where ID=? and password=?");
			pst.setString(1, username);
			pst.setString(2, oldpassword);
			rs= pst.executeQuery();
			if(rs.next()) {
				if(!(newpassword.equals(newpassword2))) {
					JOptionPane.showMessageDialog(null, "Nhập lại mật khẩu mới không chính xác!");
					jtfnewpassword.setText("");
					jtfnewpassword2.setText("");
				}
				else {
					pst=con.prepareStatement("update dangnhap set password=? where ID=? ");
					pst.setString(1, newpassword);
					pst.setString(2, username);
					int i=pst.executeUpdate();
					if(i>0) {
						JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!");
						jtfoldpassword.setText("");
						jtfnewpassword.setText("");
						jtfnewpassword2.setText("");
					}
					else {
						JOptionPane.showMessageDialog(null,"Đã xảy ra lỗi!");
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Mật khẩu cũ không đúng!");
				jtfoldpassword.setText("");
				jtfnewpassword.setText("");
				jtfnewpassword2.setText("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	}	
	@FXML
	private void cancel(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Trangcanhan.fxml"));
		Scene scene = new Scene(root,700,696);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

}
