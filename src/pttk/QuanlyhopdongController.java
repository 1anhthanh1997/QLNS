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
import Model.Quanlyhopdonginfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;;

public class QuanlyhopdongController implements Initializable {
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	boolean test;
	@FXML
	private TextField jtfID;

	@FXML
	private TextField jtfname;
	@FXML
	private TextField jtfID2;
	@FXML
	private TextField jtftype;
	@FXML
	private TextField jtfdateofsigning;
	@FXML
	private TextField jtfexpirationdate;
	@FXML
	private TextField jtfsalary;
	@FXML
	private TextField jtfsearch;
	
	@FXML
	private TableColumn<Quanlyhopdonginfo, String> colID;
	@FXML
	private TableColumn<Quanlyhopdonginfo, String> colName;
	@FXML
	private TableColumn<Quanlyhopdonginfo, String> colID2;
	@FXML
	private TableColumn<Quanlyhopdonginfo, String> colType;
	@FXML
	private TableColumn<Quanlyhopdonginfo, String> colDateofsigning;
	@FXML
	private TableColumn<Quanlyhopdonginfo, String> colExpirationdate;
	@FXML
	private TableColumn<Quanlyhopdonginfo, String> colSalary;
	
	@FXML
	private TableView<Quanlyhopdonginfo> tableView3;
	private Quanlyhopdonginfo hopdong;
	private ObservableList<Quanlyhopdonginfo> data=FXCollections.observableArrayList(); 
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
		String name=jtfname.getText();
		String ID2=jtfID2.getText();
		String type=jtftype.getText();
		String dateofsigning=jtfdateofsigning.getText();
		String expirationdate=jtfexpirationdate.getText();
		String salary=jtfsalary.getText();
		
		test=true;
		if(ID.equals(null)||ID.equals("")||name.equals(null)||name.equals("")||	ID2.equals(null)||ID2.equals("")||type.equals(null)||type.equals("")||dateofsigning.equals(null)||
				dateofsigning.equals("")||expirationdate.equals(null)||expirationdate.equals("")||salary.equals(null)||salary.equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
			test =false;
		}
		if(test) {
			int xacnhan=JOptionPane.showConfirmDialog(null,"Bạn thực sự muốn thêm hợp đồng?","Xác nhận",JOptionPane.YES_NO_OPTION);
			if(xacnhan==JOptionPane.YES_OPTION) {
		try {
		pst=con.prepareStatement("insert into quanlyhopdong(ID,name,ID2,type,dateofsigning,expirationdate,salary)values(?,?,?,?,?,?,?)" );
		pst.setString(1, ID);
		pst.setString(2, name);
		pst.setString(3, ID2);
		pst.setString(4, type);
		pst.setString(5, dateofsigning);
		pst.setString(6, expirationdate);
		pst.setString(7, salary);
		
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
		if(position.equals("Quản lý nhân sự")) {
		//tableView.getItems().clear();
		//con=dba.DBConnection.myConnection();
		   String sqlCommand = "update  quanlynhansu set name=?,ID2=?,type=?,dateofsigning=?,expirationdate=?,salary=?  where ID=?";
	               
		   PreparedStatement pst = null;
		   	 
			String ID=jtfID.getText();
			String name=jtfname.getText();
			String ID2=jtfID2.getText();
			String type=jtftype.getText();
			String dateofsigning=jtfdateofsigning.getText();
			String expirationdate=jtfexpirationdate.getText();
			String salary=jtfsalary.getText();
		   test=true;
		   if(ID.equals(null)||ID.equals("")||name.equals(null)||name.equals("")||	ID2.equals(null)||ID2.equals("")||type.equals(null)||type.equals("")||dateofsigning.equals(null)||
					dateofsigning.equals("")||expirationdate.equals(null)||expirationdate.equals("")||salary.equals(null)||salary.equals("")) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
				test =false;
			}
			if(test) {
				int xacnhan=JOptionPane.showConfirmDialog(null,"Bạn thực sự muốn sửa thông tin?","Xác nhận",JOptionPane.YES_NO_OPTION);
				if(xacnhan==JOptionPane.YES_OPTION) {
	try {
	       pst = con.prepareStatement(sqlCommand);
	       pst.setString(7, ID);
			pst.setString(1, name);
			pst.setString(2, ID2);
			pst.setString(3, type);
			pst.setString(4, dateofsigning);
			pst.setString(5, expirationdate);
			pst.setString(6, salary);
			
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
		
    	tableView3.getItems().clear();
        try {
            //Connection conn = dc.Connect();
           // data = FXCollections.observableArrayList();
            // Execute query and store result in a resultset
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM quanlyhopdong");
            while (rs.next()) {
                //get string from db,whichever way 
                data.add(new Quanlyhopdonginfo(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
        
        //Set cell value factory to tableview.
        //NB.PropertyValue Factory must be the same with the one set in model class.
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colID2.setCellValueFactory(new PropertyValueFactory<>("ID2"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDateofsigning.setCellValueFactory(new PropertyValueFactory<>("dateofsigning"));
        colExpirationdate.setCellValueFactory(new PropertyValueFactory<>("expirationdate"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
       
        
        
        tableView3.setItems(null);
        tableView3.setItems(data);
        jtfID.setText("");
       
   	   	jtfname.setText("");
   	    jtfID2.setText("");
   	    jtftype.setText("");
   	    jtfdateofsigning.setText("");
   	    jtfexpirationdate.setText("");
   	    jtfsalary.setText("");
   	   
    }
	@FXML
	private void delete(ActionEvent event)throws SQLException{
			//tableView.getItems().clear();
		int xacnhan=JOptionPane.showConfirmDialog(null,"Bạn thực sự muốn xóa hợp đồng?","Xác nhận",JOptionPane.YES_NO_OPTION);
		if(xacnhan==JOptionPane.YES_OPTION) {
		if(position.equals("Quản lý nhân sự")) {		
		   String sqlCommand = "delete from  quanlyhopdong where ID=? ";
	               
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
            hopdong= tableView3.getSelectionModel().getSelectedItem();
            jtfID.setText(hopdong.getID());
             
            jtfname.setText(hopdong.getName());
            jtfID2.setText(hopdong.getID2());
            jtftype.setText(hopdong.getType());
            jtfdateofsigning.setText(hopdong.getDateofsigning());
            jtfexpirationdate.setText(hopdong.getExpirationdate());
            jtfsalary.setText(hopdong.getSalary());
            
            
      jtfID.setDisable(true);
    }
	 private void search() {
			jtfsearch.setOnKeyReleased(e->{
	    		if (jtfsearch.getText().equals("")) {
	    			loadDataFromDatabase();
	    		} else {
	    			data.clear();
		    		String sql = "select * from quanlyhopdong where Name LIKE '%" + jtfsearch.getText() + "%'";		
		    		try {
						pst = con.prepareStatement(sql);
						ResultSet rs = pst.executeQuery();
						while (rs.next()) {
							 data.add(new Quanlyhopdonginfo(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
						}
						tableView3.setItems(data);
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
