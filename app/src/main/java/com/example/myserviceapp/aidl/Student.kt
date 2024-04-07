package com.example.myserviceapp.aidl

import android.os.Parcel
import android.os.Parcelable

data class Student(private var name: String, private var age: Int) : Parcelable {
    constructor() : this("", 0)
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
    }

    fun readFromParcel(parcel: Parcel) {
        this.name = parcel.readString() ?: ""
        this.age = parcel.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }

    fun getName() = name
    fun setName(name: String) {
        this.name = name
    }

    fun getAge() = age
    fun setAge(age: Int) {
        this.age = age
    }
}
