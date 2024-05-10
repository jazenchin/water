package com.lhr.water.room

import androidx.room.*
import com.lhr.water.room.SqlModel.Companion.INVENTORY_TABLE_NAME

@Dao
interface InventoryDao {
    @Query("SELECT * FROM $INVENTORY_TABLE_NAME")
    fun getAll(): List<InventoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNewForm(inventoryEntity: InventoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(inventoryEntity: InventoryEntity)

    @Query("DELETE FROM $INVENTORY_TABLE_NAME")
    fun clearTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInventoryEntities(targetEntities: List<InventoryEntity>)
}