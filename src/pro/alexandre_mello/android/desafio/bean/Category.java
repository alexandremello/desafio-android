package pro.alexandre_mello.android.desafio.bean;

public class Category {
	private int id;
	private String description;
	private String image_url;

	public Category(int id, String description, String image_url) {
		super();
		this.id = id;
		this.description = description;
		this.image_url = image_url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	@Override
	public String toString() {
		return description;
	}
}
