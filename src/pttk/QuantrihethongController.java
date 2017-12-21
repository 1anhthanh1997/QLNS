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
import Model.Quantrihethonginfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
public class QuantrihethongController implements Initializable {
	private Connection con;
	private PreparedStatement pst;
	boolean test;
	@FXML
	private TextField jtfID;
	@FXML
	private TextField jtfpassword;
	@FXML
	private TextField jtfpassword2;
	@FXML
	private TextField jtfname;
	@FXML
	private TextField jtfposition;
	@FXML
	private TextField jtfsearch;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colID;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colPassword;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colName;
	@FXML
	private TableColumn<Quantrihethonginfo, String> colPosition;
	@FXML
	private TableView<Quantrihethonginfo> tableView;
	private Quantrihethonginfo quantri;
	private ObservableList<Quantrihethonginfo> data=FXCollections.observableArrayList(); 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		con=dba.DBConnection.myConnection();
		loadDataFromDatabase();
		search();
	}
	@FXML
	private void register(ActionEvent event) throws SQLException {
		
		String ID=jtfID.getText();
		String password=jtfpassword.getText();
		String password2=jtfpassword2.getText();
		String name=jtfname.getText();
		String position=jtfposition.getText();
		test=true;
		if (!(password.equals(password2))) {
			test=false;
			jtfpassword2.setText("");
			JOptionPane.showMessageDialog(null,"Nhập lại mật khẩu không đúng!");
			
		}
		else if(ID.equals("")||ID.equals(null)||password.equals("")||password.equals(null)||password2.equals("")||password2.equals(null)||name.equals("")||name.equals(null)||position.equals("")||position.equals(null)) {
			test=false;
			JOptionPane.showMessageDialog(null, "Bạn phải nhập đầy đủ thông tin để có thể đăng kí!");
		}
		if(test) {
			int xacnhan=JOptionPane.showConfirmDialog(null,"Bạn thực sự muốn đăng kí tài khoản?","Xác nhận",JOptionPane.YES_NO_OPTION);
			if(xacnhan==JOptionPane.YES_OPTION) {
		try {
		pst=con.prepareStatement("insert into dangnhap(ID,password,name,position)values(?,?,?,?)" );
		pst.setString(1, ID);
		pst.setString(2, password);
		pst.setString(3, name);
		pst.setString(4, position);
		 if (pst.executeUpdate() > 0) {
             JOptionPane.showMessageDialog(null, "Đăng kí thành công!");
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
	}
	@FXML
	private void edit(ActionEvent event)throws SQLException{
		//tableView.getItems().clear();
		//con=dba.DBConnection.myConnection();
		   String sqlCommand = "update  dangnhap set password=?,name=?,position=?  where ID=?";
	               
		   PreparedStatement pst = null;
		   String ID=jtfID.getText();
		   String password=jtfpassword.getText();
		   System.out.println(password);
		   String password2=jtfpassword2.getText();
		   System.out.println(password2);
		   String name=jtfname.getText();
		   String position=jtfposition.getText();
		   test=true;
			if (!(password.equals(password2))) {
				test=false;
				jtfpassword2.setText("");
				JOptionPane.showMessageDialog(null,"Nhập lại mật khẩu không đúng!");
				
			}
			else if(ID.equals("")||ID.equals(null)||password.equals("")||password.equals(null)||password2.equals("")||password2.equals(null)||name.equals("")||name.equals(null)||position.equals("")||position.equals(null)) {
				test=false;
				JOptionPane.showMessageDialog(null, "Bạn phải nhập đầy đủ thông tin để có thể đăng kí!");
			}
			if(test) {
				int xacnhan=JOptionPane.showConfirmDialog(null,"Bạn thực sự muốn sửa thông tin tài khoản?","Xác nhận",JOptionPane.YES_NO_OPTION);
				if(xacnhan==JOptionPane.YES_OPTION) {
	try {
	       pst = con.prepareStatement(sqlCommand);
	       pst.setString(1, password);
	       pst.setString(2, name);
	       pst.setString(3, position);
	       pst.setString(4, ID);
	       if (pst.executeUpdate() > 0) {
	               JOptionPane.showMessageDialog(null, "Sửa thành công!");
	       } else {
	    	   JOptionPane.showMessageDialog(null, "Bạn phải chọn ID cần sửa trước!");
	       }
	       
	       loadDataFromDatabase();
	} catch (SQLException e) {
	       System.out.println("update error \n" + e.toString());
	} 
			}
			}

	}
	@FXML
    private void loadDataFromDatabase() {
    	tableView.getItems().clear();
        try {
            //Connection conn = dc.Connect();
           // data = FXCollections.observableArrayList();
            // Execute query and store result in a resultset
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM dangnhap");
            while (rs.next()) {
                //get string from db,whichever way 
                data.add(new Quantrihethonginfo(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4)));
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }
        
        //Set cell value factory to tableview.
        //NB.PropertyValue Factory must be the same with the one set in model class.
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        
        
        tableView.setItems(null);
        tableView.setItems(data);
        jtfID.setText("");
        jtfpassword.setText("");
   	   jtfpassword2.setText("");
   	   jtfname.setText("");
   	   jtfposition.setText("");
   	 
    }
	@FXML
	private void delete(ActionEvent event)throws SQLException{
			//tableView.getItems().clear();
		int xacnhan=JOptionPane.showConfirmDialog(null,"Bạn thực sự muốn xóa tài khoản?","Xác nhận",JOptionPane.YES_NO_OPTION);
		if(xacnhan==JOptionPane.YES_OPTION) {
		   String sqlCommand = "delete from  dangnhap where ID=? ";
	               
		   PreparedStatement pst = null;
		   
		   String ID=jtfID.getText();
	      
	  	  
	  	  
	try {
	       pst = con.prepareStatement(sqlCommand);
	     
	       pst.setString(1,ID);
	      
	       if (pst.executeUpdate() > 0) {
	           JOptionPane.showMessageDialog(null, "Xóa thành công!");
	   } else {
		   JOptionPane.showMessageDialog(null, "Bạn phải chọn ID cần xóa trước!");
	   }
	   
	       loadDataFromDatabase();
	} catch (SQLException e) {
	       System.out.println("update error \n" + e.toString());
	}
		}

	}
	@FXML
    public void tableClick(MouseEvent e) {
        //if (MouseButton.PRIMARY == e.getButton()) {
            quantri= tableView.getSelectionModel().getSelectedItem();
            jtfID.setText(quantri.getID());
            jtfpassword.setText(quantri.getPassword());
            jtfpassword2.setText(quantri.getPassword());
            jtfname.setText(quantri.getName());
            jtfposition.setText(quantri.getPosition());
           
            
      jtfID.setDisable(true);
    }
	 private void search() {
			jtfsearch.setOnKeyReleased(e->{
	    		if (jtfsearch.getText().equals("")) {
	    			loadDataFromDatabase();
	    		} else {
	    			data.clear();
		    		String sql = "select * from dangnhap where Name LIKE '%" + jtfsearch.getText() + "%'";		
		    		try {
						pst = con.prepareStatement(sql);
						ResultSet rs = pst.executeQuery();
						while (rs.next()) {
							data.add(new Quantrihethonginfo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
						}
						tableView.setItems(data);
					} catch (SQLException e1) {
						Logger.getLogger(QuantrihethongController.class.getName()).log(Level.SEVERE, null, e1);
					}
	    		}
	    	});
	    }
	 @FXML
	    private void makenew(ActionEvent event){
	    	jtfID.setDisable(false);
	    	loadDataFromDatabase();
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
	


