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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
public class TrangcanhanController implements Initializable {
	 private PreparedStatement pst=null;
	 private Connection con= null;
	 private ResultSet rs=null;
	 @FXML
	 private TextField jtfID;	
	 @FXML
	 private TextField jtfname;	
	 @FXML
	 private TextField jtfdateofbirth;	
	 @FXML
	 private TextField jtfaddress;	
	 @FXML
	 private TextField jtfphonenumber;	
	 @FXML
	 private TextField jtfemail;	
	 @FXML
	 private TextField jtfCMT;	
	 @FXML
	 private TextField jtfdepartment;	
	 @FXML
	 private TextField jtfposition;	
	 @FXML
	 private RadioButton rdnam;
	 @FXML
	 private RadioButton rdnu;
	 @FXML
	 private ToggleGroup tggender;
	 boolean test;
	 
	 @Override
	public void initialize(URL location, ResourceBundle resources) {
		con=dba.DBConnection.myConnection();
		loadDataFromDatabase();
	}
	@FXML
    private void loadDataFromDatabase() {
		String username=null;
		try {
			rs=con.createStatement().executeQuery("Select ID from idhientai");
			while(rs.next()) {
				username=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String ID=null;
		String gender=null;
		String name=null;
		String address=null;
		String dateofbirth=null;
		String phonenumber=null;
		String email=null;
		String department=null;
		String CMT=null;
		String position=null;
		
        try {
            //Connection conn = dc.Connect();
           // data = FXCollections.observableArrayList();
            // Execute query and store result in a resultset
        	pst=con.prepareStatement("select * from dangnhap,quanlynhansu where dangnhap.ID=quanlynhansu.ID and dangnhap.ID LIKE '%" + username + "%'");
           
            rs = pst.executeQuery();
            while (rs.next()) {
                //get string from db,whichever way 
               ID=rs.getString(5);
               gender=rs.getString(6);
               name=rs.getString(7);
               address=rs.getString(8);
               dateofbirth=rs.getString(9);
               phonenumber=rs.getString(10);
               email=rs.getString(11);
               department=rs.getString(12);
               CMT=rs.getString(13);
               position=rs.getString(14);
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
        
        //Set cell value factory to tableview.
        //NB.PropertyValue Factory must be the same with the one set in model class.
             
       
        jtfID.setText(ID);
        if(gender.equals("Nam")) {
        rdnam.setSelected(true);
        }
        else {
        rdnu.setSelected(true);
        }
   	   	jtfname.setText(name);
   	    jtfaddress.setText(address);
   	    jtfdateofbirth.setText(dateofbirth);
   	    jtfphonenumber.setText(phonenumber);
   	    jtfemail.setText(email);
   	    jtfdepartment.setText(department);
   	    jtfCMT.setText(CMT);
   	    jtfposition.setText(position);
   	    jtfID.setDisable(true);
   	 
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
	@FXML
	private void edit(ActionEvent event)throws SQLException{
		//tableView.getItems().clear();
		//con=dba.DBConnection.myConnection();
		   String sqlCommand = "update  quanlynhansu set gender=?,name=?,address=?,dateofbirth=?,phonenumber=?,email=?,department=?,CMT=?,position=?  where ID=?";
	               
		   PreparedStatement pst = null;
		   String ID=jtfID.getText();
		   String gender=null;
			  if(rdnam.isSelected()) {
		  		   gender="Nam";
		  	   }
		  	   else if(rdnu.isSelected()) {
		  		   gender="Nữ";
		  	   }
			String name=jtfname.getText();
			String dateofbirth=jtfdateofbirth.getText();
			String phonenumber=jtfphonenumber.getText();
			String email=jtfemail.getText();
			String department=jtfdepartment.getText();
			String address=jtfaddress.getText();
			String CMT=jtfCMT.getText();
			String position=jtfposition.getText();
		   test=true;
		   if(ID.equals(null)||ID.equals("")||gender.equals(null)||gender.equals("")||name.equals(null)||name.equals("")||
					address.equals(null)||address.equals("")||dateofbirth.equals(null)||dateofbirth.equals("")||phonenumber.equals(null)||
					phonenumber.equals("")||email.equals(null)||email.equals("")||department.equals(null)||department.equals("")||
					CMT.equals(null)||CMT.equals("")||position.equals(null)||position.equals("")) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
				test =false;
			}
			if(test) {
	  	 
	try {
	       pst = con.prepareStatement(sqlCommand);
	       pst.setString(10, ID);
			pst.setString(1, gender);
			pst.setString(2, name);
			pst.setString(3, address);
			pst.setString(4, dateofbirth);
			pst.setString(5, phonenumber);
			pst.setString(6, email);
			pst.setString(7, department);
			pst.setString(8, CMT);
			pst.setString(9, position);
	       if (pst.executeUpdate() > 0) {
	               JOptionPane.showMessageDialog(null, "Sửa thành công!");
	       } else {
	    	   JOptionPane.showMessageDialog(null, "Bạn phải chọn nhân viên cần sửa trước!");
	       }
	       
	       loadDataFromDatabase();
	} catch (SQLException e) {
	       System.out.println("update error \n" + e.toString());
	} 
			}

	}
	@FXML
	private void changepass(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/Doimatkhau.fxml"));
		Scene scene = new Scene(root,400,320);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	private void MainMenu(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
		Scene scene = new Scene(root,779,487);
		scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}

