package com.example.android.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class IngredientsItem implements Parcelable {

	@SerializedName("quantity")
	private double quantity;

	@SerializedName("measure")
	private String measure;

	@SerializedName("ingredient")
	private String ingredient;

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public double getQuantity(){
		return quantity;
	}

	public void setMeasure(String measure){
		this.measure = measure;
	}

	public String getMeasure(){
		return measure;
	}

	public void setIngredient(String ingredient){
		this.ingredient = ingredient;
	}

	public String getIngredient(){
		return ingredient;
	}

	protected IngredientsItem(Parcel in) {
		quantity = in.readDouble();
		measure = in.readString();
		ingredient = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(quantity);
		dest.writeString(measure);
		dest.writeString(ingredient);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<IngredientsItem> CREATOR = new Parcelable.Creator<IngredientsItem>() {
		@Override
		public IngredientsItem createFromParcel(Parcel in) {
			return new IngredientsItem(in);
		}

		@Override
		public IngredientsItem[] newArray(int size) {
			return new IngredientsItem[size];
		}
	};
}