package com.dtt.testintent.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

	private String name;
	private int age;
	
	public void setName(String name){
		this.name = name;
	}
	public void setAge(int age){
		this.age = age;
	}
	public String getName(){
		return name;
	}
	public int getAge(){
		return age;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flag) {
		dest.writeString(name);
		dest.writeInt(age);
	}

	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override
		public Person createFromParcel(Parcel source) {
			Person person = new Person();
			person.name = source.readString();
			person.age = source.readInt();
			return person;
		}

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	
	};
}
