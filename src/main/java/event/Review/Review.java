package event.Review;

public class Review {

    private int id;
    private String event;
    private int rating;
    private String review;
    private String name;
    private String email;
    
    
	public Review(int id, String event, int rating, String review, String name, String email) {
		super();
		this.id = id;
		this.event = event;
		this.rating = rating;
		this.review = review;
		this.name = name;
		this.email = email;
	}


	public Review(String event, int rating, String review, String name, String email) {
		super();
		this.event = event;
		this.rating = rating;
		this.review = review;
		this.name = name;
		this.email = email;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getEvent() {
		return event;
	}


	public void setEvent(String event) {
		this.event = event;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}


	public String getReview() {
		return review;
	}


	public void setReview(String review) {
		this.review = review;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
    
    
    

}
