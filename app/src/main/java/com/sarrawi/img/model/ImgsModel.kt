package com.sarrawi.img.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class ImgsModel(
    @SerializedName("id")
    var id: Int,

    @SerializedName("ID_Type_id")
    val ID_Type: Int,

    @SerializedName("new_img")
    val new_img: Int,

    @SerializedName("image_url")
    var image_url: String,

    var is_fav: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(ID_Type)
        parcel.writeInt(new_img)
        parcel.writeString(image_url)
        parcel.writeByte(if (is_fav) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImgsModel> {
        override fun createFromParcel(parcel: Parcel): ImgsModel {
            return ImgsModel(parcel)
        }

        override fun newArray(size: Int): Array<ImgsModel?> {
            return arrayOfNulls(size)
        }
    }
}
