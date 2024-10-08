package event.addEvent;

public class event {

	
	private int id;
	private String title;
	private String type;
	private String location;
	private String number;
	private String description;
	private String imageUrl; // New field for image URL

	public event(int id, String title, String type, String location, String number, String description,
			String imageUrl) {
		super();
		this.id = id;
		this.title = title;
		this.type = type;
		this.location = location;
		this.number = number;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public event(String title, String type, String location, String number, String description,
			String imageUrl) {
		super();
		this.title = title;
		this.type = type;
		this.location = location;
		this.number = number;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
}
