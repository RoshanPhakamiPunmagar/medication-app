package com.example.medicationapp.model.dao


import androidx.room.*
import com.example.medicationapp.model.Client

@Dao
interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: Client): Long

    @Query("SELECT * FROM clients")
    suspend fun getAllClients(): List<Client>

    @Query("SELECT * FROM clients WHERE carerId = :carerId")
    suspend fun getClientsForCarer(carerId: Long): List<Client>

    @Query("SELECT * FROM clients WHERE clientId = :clientId")
    suspend fun getClientById(clientId: Long): Client?

    @Delete
    suspend fun deleteClient(client: Client)

    @Update
    suspend fun updateClient(client: Client)

    @Query("UPDATE clients SET carerId = :carerId WHERE clientId = :clientId")
    suspend fun assignClientToCarer(clientId: Long, carerId: Long)
}
