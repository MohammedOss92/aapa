package com.sarrawi.img.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ImgsModel(

    @SerializedName("id")
    val id: Int,

    @SerializedName("ID_Type_id")
    val ID_Type: Int,

    @SerializedName("new_img")
    val new_img: String,

    @SerializedName("image_url")
    var image_url: String,

    var is_fav:Boolean = false
): Parcelable {

    // تنفيذ الدالة describeContents
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest?.writeInt(id)
        dest?.writeInt(ID_Type)
        dest?.writeString(new_img)
        dest?.writeString(image_url)
        dest?.writeByte(if (is_fav) 1 else 0)
    }

    // تنفيذ الدالة writeToParcel لحفظ بيانات الكائن في Parcel
//    override fun writeToParcel(dest: Parcel?, flags: Int) {
//        dest?.writeInt(id)
//        dest?.writeInt(ID_Type)
//        dest?.writeString(new_img)
//        dest?.writeString(image_url)
//        dest?.writeByte(if (is_fav) 1 else 0)
//    }

    // تعيين companion object لتحسين إنشاء الكائن من الـ Parcel
    companion object CREATOR : Parcelable.Creator<ImgsModel> {
        override fun createFromParcel(parcel: Parcel): ImgsModel {
            return ImgsModel(parcel)
        }

        override fun newArray(size: Int): Array<ImgsModel?> {
            return arrayOfNulls(size)
        }
    }

    // إنشاء الكائن من الـ Parcel
    private constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        ID_Type = parcel.readInt(),
        new_img = parcel.readString() ?: "",
        image_url = parcel.readString() ?: "",
        is_fav = parcel.readByte() != 0.toByte()
    )

}
