package com.lifeordered.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventories")
data class Inventory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val currentQty: Int, // Current stock amount
    val safeQty: Int, // Minimum threshold for warning state
    val unit: String = "袋", // E.g., 袋 (bags), 卷 (rolls), 盒 (boxes)
    val category: String = "居家",
    val iconPath: String = "",
    val isArchived: Boolean = false,
    val isDeleted: Int = 0,
    val deletedAt: Long? = null,
    val shelfLifeDays: Int? = null, // Shelf life in days
    val purchaseDate: Long? = null, // Purchase date timestamp
    val expiryDate: Long? = null // Computed expiry date
) {
    /**
     * Determines whether stock warning alert is active
     */
    fun isLow(): Boolean = currentQty <= safeQty
}
