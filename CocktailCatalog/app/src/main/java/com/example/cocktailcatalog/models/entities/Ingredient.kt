package com.example.cocktailcatalog.models.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
        tableName = "ingredient_table",
        foreignKeys = [
                ForeignKey(
                        entity = LocalDrink::class,
                        parentColumns = ["id"],
                        childColumns = ["drink_id"],
                        onDelete = ForeignKey.CASCADE
                )
        ]
)
data class Ingredient(
        @Expose
        @SerializedName("idIngredient")
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        @Expose
        @SerializedName("strIngredient")
        var name: String,
        @Expose(serialize = false, deserialize = false)
        var measure: String,
        var drink_id: Long
): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readLong()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeString(name)
                parcel.writeString(measure)
                parcel.writeLong(drink_id)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Ingredient> {
                override fun createFromParcel(parcel: Parcel): Ingredient {
                        return Ingredient(parcel)
                }

                override fun newArray(size: Int): Array<Ingredient?> {
                        return arrayOfNulls(size)
                }
        }
}


class IngredientNamesList : ArrayList<String>() {

}