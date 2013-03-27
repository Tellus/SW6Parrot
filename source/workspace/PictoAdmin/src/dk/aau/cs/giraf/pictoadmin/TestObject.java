package dk.aau.cs.giraf.pictoadmin;

import android.os.Parcel;
import android.os.Parcelable;

public class TestObject implements Parcelable  {
	String name;
	
	public TestObject(String pName){
		this.name = pName;
	}

	public TestObject(Parcel in)
	{
		String[] data= new String[1];
		 
		in.readStringArray(data);
		this.name= data[0];
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[]{this.name});
	}
	
	public static final Parcelable.Creator<TestObject> CREATOR= new Parcelable.Creator<TestObject>() {
		 
		@Override
		public TestObject createFromParcel(Parcel source) {
		return new TestObject(source);  //using parcelable constructor
		}
		 
		@Override
		public TestObject[] newArray(int size) {
		return new TestObject[size];
		}
		};
}
