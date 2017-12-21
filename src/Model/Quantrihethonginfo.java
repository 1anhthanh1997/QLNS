package Model;

public class Quantrihethonginfo {
	private final String ID;
	private final String password;
	private final String name;
	private final String position;
	public String getID() {
		return ID;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public String getPosition() {
		return position;
	}
	public Quantrihethonginfo(String ID, String password, String name, String position) {
		super();
		this.ID = ID;
		this.password = password;
		this.name = name;
		this.position = position;
	}
	
	
}
