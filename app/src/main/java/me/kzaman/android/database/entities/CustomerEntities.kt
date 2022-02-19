package me.kzaman.android.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class CustomerEntities(
    @ColumnInfo(name = "sbu_id") val sbuId: String,
    @ColumnInfo(name = "composite_key") val compositeKey: String,
    @PrimaryKey(autoGenerate = false) @NonNull
    @ColumnInfo(name = "customer_id") val customerId: String,
    @ColumnInfo(name = "ds_id") val dsId: String? = null,
    @ColumnInfo(name = "sales_area_id") val salesAreaId: String,
    @ColumnInfo(name = "area_lvl") val areaLevel: String,
    @ColumnInfo(name = "customer_type") val customerType: String,
    @ColumnInfo(name = "customer_code") val customerCode: String,
    @ColumnInfo(name = "display_code") val displayCode: String,
    @ColumnInfo(name = "customer_name") val customerName: String,
    @ColumnInfo(name = "credit_flag") val creditFlag: String,
    @ColumnInfo(name = "display_name") val displayName: String? = null,
    @ColumnInfo(name = "credit_limit") var creditLimit: String? = null,
    @ColumnInfo(name = "search_keywords") val searchKey: String,
    @ColumnInfo(name = "vat_challan_flag") val vatChallaFlag: String? = null,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "current_due") var currentDue: String = "0",
    @ColumnInfo(name = "current_advance") var currentAdvance: String = "0",
    @ColumnInfo(name = "phone") var phone: String? = null,
    @ColumnInfo(name = "email") var email: String? = null,
    @ColumnInfo(name = "address") var customerAddress: String? = null,
    @ColumnInfo(name = "photo") var photo: String? = null,
    @ColumnInfo(name = "area_display_code") val territoryCode: String,
    @ColumnInfo(name = "area_name") val territoryName: String,
    @ColumnInfo(name = "payment_mode") val paymentMode: String? = null,
    @ColumnInfo(name = "cash_due") var cashDueAmt: String? = null,
    @ColumnInfo(name = "activate_verify_date") var activateVerifyDate: String? = null,
    @ColumnInfo(name = "activate_verify_by") var activateVerifyBy: String? = null,
    @ColumnInfo(name = "hq_type") var hqType: String? = null,
)
