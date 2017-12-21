package Model;

public class QuanlynhansuInfo {
	private String ID;
	private String gender;
	private String name;
	private String address;
	private String dateofbirth;
	private String phonenumber;
	private String email;
	private String department;
	private String CMT;
	private String position;
	public String getID() {
		return ID;
	}
	public String getGender() {
		return gender;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public String getEmail() {
		return email;
	}
	public String getDepartment() {
		return department;
	}
	public String getCMT() {
		return CMT;
	}
	public String getPosition() {
		return position;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setCMT(String cMT) {
		CMT = cMT;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public QuanlynhansuInfo(String iD, String gender, String name, String address, String dateofbirth,
			String phonenumber, String email, String department, String cMT, String position) {
		super();
		ID = iD;
		this.gender = gender;
		this.name = name;
		this.address = address;
		this.dateofbirth = dateofbirth;
		this.phonenumber = phonenumber;
		this.email = email;
		this.department = department;
		CMT = cMT;
		this.position = position;
	}
	

}
