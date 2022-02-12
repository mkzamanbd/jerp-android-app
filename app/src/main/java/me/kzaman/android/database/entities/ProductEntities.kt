package me.kzaman.android.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_list")
data class ProductEntities(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "prod_id") val productId: String,
    @ColumnInfo(name = "prod_class") val productClass: String,
    @ColumnInfo(name = "base_tp") val baseTp: Double = 0.0,
    @ColumnInfo(name = "base_qty") val baseQty: Int = 0,
    @ColumnInfo(name = "base_vat") val baseVat: Double = 0.0,
    @ColumnInfo(name = "prod_name") val productName: String,
    @ColumnInfo(name = "display_name") val displayName: String? = null,
    @ColumnInfo(name = "prod_code") val productCode: String,
    @ColumnInfo(name = "display_code") val displayCode: String? = null,
    @ColumnInfo(name = "search_keywords") val searchKey: String? = null,
    @ColumnInfo(name = "com_pack_size") val packSize: String? = null,
    @ColumnInfo(name = "com_pack_desc") val comPackDesc: String? = null,
    @ColumnInfo(name = "prod_offer_description") val offerDescription: String? = null,
    @ColumnInfo(name = "mtp") val mtp: Double = 0.0,
    @ColumnInfo(name = "offer") val offer: String? = null,
    @ColumnInfo(name = "offer_type") val offerType: String? = null,
    @ColumnInfo(name = "start_date") val startDate: String? = null,
    @ColumnInfo(name = "valid_until") val validUntil: String,
    @ColumnInfo(name = "min_qty") val minimumQty: Int = 0,
    @ColumnInfo(name = "elements") val elementInfo: String? = null,
)