package event.Package;

public class Package {

    private int id;
    private String pkgName;
    private String description;
    private double price;
    private int duration;
    private String pkgType;
    
    
	public Package(int id, String pkgName, String description, double price, int duration, String pkgType) {
		super();
		this.id = id;
		this.pkgName = pkgName;
		this.description = description;
		this.price = price;
		this.duration = duration;
		this.pkgType = pkgType;
	}


	public Package(String pkgName, String description, double price, int duration, String pkgType) {
		super();
		this.pkgName = pkgName;
		this.description = description;
		this.price = price;
		this.duration = duration;
		this.pkgType = pkgType;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getPkgName() {
		return pkgName;
	}


	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getDuration() {
		return duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}


	public String getPkgType() {
		return pkgType;
	}


	public void setPkgType(String pkgType) {
		this.pkgType = pkgType;
	}


}
