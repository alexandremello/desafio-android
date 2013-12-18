package pro.alexandre_mello.android.desafio.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
	private int id;
	private String description;
	private String image_url;

	public Category(int id, String description, String image_url) {
		super();
		this.id = id;
		this.description = description;
		this.image_url = image_url;
	}
	
	public Category(Parcel in) {
		readFromParcel(in);
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

	// 99.9% of the time you can just ignore this
	@Override
	public int describeContents() {
		return 0;
	}

	// write your object's data to the passed-in Parcel
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeString(description);
		out.writeString(image_url);
	}

	public void readFromParcel(Parcel in) {
		id = in.readInt();
		description = in.readString();
		image_url = in.readString();
	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
		public Category createFromParcel(Parcel in) {
			return new Category(in);
		}

		public Category[] newArray(int size) {
			return new Category[size];
		}
	};
}
