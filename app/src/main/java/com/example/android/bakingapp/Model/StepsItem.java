package com.example.android.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StepsItem implements Parcelable {

	@SerializedName("videoURL")
	private String videoURL;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("shortDescription")
	private String shortDescription;

	@SerializedName("thumbnailURL")
	private String thumbnailURL;

	public void setVideoURL(String videoURL){
		this.videoURL = videoURL;
	}

	public String getVideoURL(){
		return videoURL;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setShortDescription(String shortDescription){
		this.shortDescription = shortDescription;
	}

	public String getShortDescription(){
		return shortDescription;
	}

	public void setThumbnailURL(String thumbnailURL){
		this.thumbnailURL = thumbnailURL;
	}

	public String getThumbnailURL(){
		return thumbnailURL;
	}

	protected StepsItem(Parcel in) {
		videoURL = in.readString();
		description = in.readString();
		id = in.readInt();
		shortDescription = in.readString();
		thumbnailURL = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(videoURL);
		dest.writeString(description);
		dest.writeInt(id);
		dest.writeString(shortDescription);
		dest.writeString(thumbnailURL);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<StepsItem> CREATOR = new Parcelable.Creator<StepsItem>() {
		@Override
		public StepsItem createFromParcel(Parcel in) {
			return new StepsItem(in);
		}

		@Override
		public StepsItem[] newArray(int size) {
			return new StepsItem[size];
		}
	};
}