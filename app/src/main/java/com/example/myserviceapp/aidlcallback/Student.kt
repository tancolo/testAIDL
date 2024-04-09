package com.example.myserviceapp.aidlcallback

import android.os.Parcel
import android.os.Parcelable

data class Student(
    private var name: String,
    private var age: Int,
    private var score: Float
) : Parcelable {
    constructor() : this("NULL NAME", 0, 0.0F)
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeFloat(score)
    }

    fun readFromParcel(parcel: Parcel) {
        this.name = parcel.readString() ?: "NULL NAME"
        this.age = parcel.readInt()
        this.score = parcel.readFloat()
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

    fun getScore() = score

    fun setScore(score: Float) {
        this.score = score
    }

    override fun toString(): String {
        return "[name: $name, age: $age, score: $score]"
    }
}