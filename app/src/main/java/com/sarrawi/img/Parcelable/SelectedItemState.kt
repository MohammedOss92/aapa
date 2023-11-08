package com.sarrawi.img.Parcelable

import android.os.Parcel
import android.os.Parcelable

data class SelectedItemState(val itemId: Int) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(itemId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SelectedItemState> {
        override fun createFromParcel(parcel: Parcel): SelectedItemState {
            return SelectedItemState(parcel)
        }

        override fun newArray(size: Int): Array<SelectedItemState?> {
            return arrayOfNulls(size)
        }
    }
}

