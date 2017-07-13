package com.hqf.demo.aidldemo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangqianfang on 2017/7/13.
 * email huangqianfanghn@gmail.com
 */

public class Book  implements Parcelable{
	public int bookId;
	public int bookName;

	protected Book(Parcel in) {
		bookId = in.readInt();
		bookName = in.readInt();
	}

	public static final Creator<Book> CREATOR = new Creator<Book>() {
		@Override
		public Book createFromParcel(Parcel in) {
			return new Book(in);
		}

		@Override
		public Book[] newArray(int size) {
			return new Book[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(bookId);
		dest.writeInt(bookName);
	}
}
