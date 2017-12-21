package Model;

public class Quanlyhopdonginfo {
	private final String ID;
	private final String name;
	private final String ID2;
	private final String type;
	private final String dateofsigning;
	private final String expirationdate;
	private final String salary;
	public String getID() {
		return ID;
	}
	public String getName() {
		return name;
	}
	public String getID2() {
		return ID2;
	}
	public String getType() {
		return type;
	}
	public String getDateofsigning() {
		return dateofsigning;
	}
	public String getExpirationdate() {
		return expirationdate;
	}
	public String getSalary() {
		return salary;
	}
	public Quanlyhopdonginfo(String iD, String name, String iD2, String type, String dateofsigning,
			String expirationdate, String salary) {
		super();
		ID = iD;
		this.name = name;
		ID2 = iD2;
		this.type = type;
		this.dateofsigning = dateofsigning;
		this.expirationdate = expirationdate;
		this.salary = salary;
	}

}
