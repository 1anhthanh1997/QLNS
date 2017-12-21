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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javax.swing.JOptionPane;
import Model.QuanlynhansuInfo;
import Model.Quantrihethonginfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;;

public class QuanlynhansuController implements Initializable {
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	boolean test;
	@FXML
	private TextField jtfID;
	@FXML
	private RadioButton rdnam;
	@FXML
	private RadioButton rdnu;
	@FXML
    private ToggleGroup tggender;
	@FXML
	private TextField jtfname;
	@FXML
	private TextField jtfaddress;
	@FXML
	private TextField jtfdateofbirth;
	@FXML
	private TextField jtfphonenumber;
	@FXML
	private TextField jtfemail;
	@FXML
	private TextField jtfdepartment;
	@FXML
	private TextField jtfCMT;
	@FXML
	private TextField jtfposition;
	@FXML
	private TextField jtfsearch;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colID;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colGender;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colName;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colAddress;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colDateofbirth;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colPhonenumber;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colEmail;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colDepartment;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colCMT;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colPosition;
	@FXML
	private TableView<QuanlynhansuInfo> tableView2;
	private QuanlynhansuInfo nhansu;
	private ObservableList<QuanlynhansuInfo> data=FXCollections.observableArrayList(); 
	private String username;
	private String position;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		con=dba.DBConnection.myConnection();
		loadDataFromDatabase();
		search();
		getusername();
		getposition();
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
	private void add(ActionEvent event) throws SQLException {
		if(position.equals("Quản lý nhân sự")) {
		String ID=jtfID.getText();
		String gender = null;
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
			int xacnhan=JOptionPane.showConfirmDialog(null,"Bạn thực sự muốn thêm nhân viên?","Xác nhận",JOptionPane.YES_NO_OPTION);
			if(xacnhan==JOptionPane.YES_OPTION) {
		try {
		pst=con.prepareStatement("insert into quanlynhansu(ID,gender,name,address,dateofbirth,phonenumber,email,department,CMT,position)values(?,?,?,?,?,?,?,?,?,?)" );
		pst.setString(1, ID);
		pst.setString(2, gender);
		pst.setString(3, name);
		pst.setString(4, address);
		pst.setString(5, dateofbirth);
		pst.setString(6, phonenumber);
		pst.setString(7, email);
		pst.setString(8, department);
		pst.setString(9, CMT);
		pst.setString(10, position);
		 if (pst.executeUpdate() > 0) {
             JOptionPane.showMessageDialog(null, "Thêm thành công!");
     } else {
  	   JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi!");
     }
		 loadDataFromDatabase(); 
		 }catch (Exception e) {
			   JOptionPane.showMessageDialog(null, e); 
				// TODO: handle exception
			}
		 finally {
				pst.close();
			}
		}
		}
		}else {
			JOptionPane.showMessageDialog(null, "Chỉ có quản lý nhân sự mới được chỉnh sửa thông tin ở trang này!");
			loadDataFromDatabase();
			jtfID.setDisable(false);
		}
	}
	@FXML
	private void edit(ActionEvent event)throws SQLException{
		//tableView.getItems().clear();
		//con=dba.DBConnection.myConnection();
		if(position.equals("Quản lý nhân sự")) {
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
				int xacnhan=JOptionPane.showConfirmDialog(null,"Bạn thực sự muốn sửa thông tin?","Xác nhận",JOptionPane.YES_NO_OPTION);
				if(xacnhan==JOptionPane.YES_OPTION) {
	  	 
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
		}else {
			JOptionPane.showMessageDialog(null, "Chỉ có quản lý nhân sự mới được chỉnh sửa thông tin ở trang này!");
			loadDataFromDatabase();
			jtfID.setDisable(false);
		}
			

	}
	@FXML
    private void loadDataFromDatabase() {
    	tableView2.getItems().clear();
        try {
            //Connection conn = dc.Connect();
           // data = FXCollections.observableArrayList();
            // Execute query and store result in a resultset
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quanlynhansu");
            while (rs.next()) {
                //get string from db,whichever way 
                data.add(new QuanlynhansuInfo(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)));
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
        
        //Set cell value factory to tableview.
        //NB.PropertyValue Factory must be the same with the one set in model class.
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDateofbirth.setCellValueFactory(new PropertyValueFactory<>("dateofbirth"));
        colPhonenumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colCMT.setCellValueFactory(new PropertyValueFactory<>("CMT"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        
        
        tableView2.setItems(null);
        tableView2.setItems(data);
        jtfID.setText("");
        rdnam.setSelected(false);
        rdnu.setSelected(false);
   	   	jtfname.setText("");
   	    jtfaddress.setText("");
   	    jtfdateofbirth.setText("");
   	    jtfphonenumber.setText("");
   	    jtfemail.setText("");
   	    jtfdepartment.setText("");
   	    jtfCMT.setText("");
   	    jtfposition.setText("");
   	 
    }
	@FXML
	private void delete(ActionEvent event)throws SQLException{
			//tableView.getItems().clear();
		int xacnhan=JOptionPane.showConfirmDialog(null,"Bạn thực sự muốn xóa thông tin?","Xác nhận",JOptionPane.YES_NO_OPTION);
		if(xacnhan==JOptionPane.YES_OPTION) {
		if(position.equals("Quản lý nhân sự")) {	
		   String sqlCommand = "delete from  quanlynhansu where ID=? ";
	               
		   PreparedStatement pst = null;
		   
		   String ID=jtfID.getText();
	      
	  	  
	  	  
	try {
	       pst = con.prepareStatement(sqlCommand);
	     
	       pst.setString(1,ID);
	      
	       if (pst.executeUpdate() > 0) {
	           JOptionPane.showMessageDialog(null, "Xóa thành công!");
	   } else {
		   JOptionPane.showMessageDialog(null, "Bạn phải chọn nhân viên cần xóa trước!");
	   }
	   
	       loadDataFromDatabase();
	} catch (SQLException e) {
	       System.out.println("update error \n" + e.toString());
	}
		}
		}else {
			JOptionPane.showMessageDialog(null, "Chỉ có quản lý nhân sự mới được chỉnh sửa thông tin ở trang này!");
			loadDataFromDatabase();
			jtfID.setDisable(false);
		}
	}
	@FXML
    public void tableClick(MouseEvent e) {
        //if (MouseButton.PRIMARY == e.getButton()) {
            nhansu= tableView2.getSelectionModel().getSelectedItem();
            jtfID.setText(nhansu.getID());
            String gender=nhansu.getGender();
                    
            
            if (gender.equals("Nam")) {
                rdnam.setSelected(true);
            } else {
                rdnu.setSelected(true);
            }
            jtfaddress.setText(nhansu.getAddress());
            jtfname.setText(nhansu.getName());
            jtfdateofbirth.setText(nhansu.getDateofbirth());
            jtfphonenumber.setText(nhansu.getPhonenumber());
            jtfemail.setText(nhansu.getEmail());
            jtfdepartment.setText(nhansu.getDepartment());
            jtfCMT.setText(nhansu.getCMT());
            jtfposition.setText(nhansu.getPosition());
           
            
      jtfID.setDisable(true);
    }
	 private void search() {
			jtfsearch.setOnKeyReleased(e->{
	    		if (jtfsearch.getText().equals("")) {
	    			loadDataFromDatabase();
	    		} else {
	    			data.clear();
		    		String sql = "select * from quanlynhansu where Name LIKE '%" + jtfsearch.getText() + "%'";		
		    		try {
						pst = con.prepareStatement(sql);
						ResultSet rs = pst.executeQuery();
						while (rs.next()) {
							 data.add(new QuanlynhansuInfo(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)));
						}
						tableView2.setItems(data);
					} catch (SQLException e1) {
						Logger.getLogger(QuantrihethongController.class.getName()).log(Level.SEVERE, null, e1);
					}
	    		}
	    	});
	    }
	 @FXML
	    private void makenew(ActionEvent event){
		 if(position.equals("Quản lý nhân sự")) {
	    	jtfID.setDisable(false);
	    	loadDataFromDatabase();
		 }
		 else {
	    		JOptionPane.showMessageDialog(null, "Chỉ có quản lý nhân sự mới được chỉnh sửa thông tin ở trang này!");
	    		jtfID.setDisable(false);
		    	loadDataFromDatabase();
	    	}
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
	  private void MainMenu(ActionEvent event) throws Exception{
		  ((Node) (event.getSource())).getScene().getWindow().hide();
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
			Scene scene = new Scene(root,779,487);
			scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
	  }

}
