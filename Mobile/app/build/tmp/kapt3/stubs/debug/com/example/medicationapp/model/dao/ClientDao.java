package com.example.medicationapp.model.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\r2\u0006\u0010\u0006\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0012\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\u0013\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\u0014"}, d2 = {"Lcom/example/medicationapp/model/dao/ClientDao;", "", "assignClientToCarer", "", "clientId", "", "carerId", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteClient", "client", "Lcom/example/medicationapp/model/Client;", "(Lcom/example/medicationapp/model/Client;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllClients", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getClientById", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getClientsForCarer", "insertClient", "updateClient", "app_debug"})
@androidx.room.Dao()
public abstract interface ClientDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertClient(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Client client, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM clients")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllClients(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.Client>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM clients WHERE carerId = :carerId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getClientsForCarer(long carerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.Client>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM clients WHERE clientId = :clientId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getClientById(long clientId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.medicationapp.model.Client> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteClient(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Client client, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateClient(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Client client, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE clients SET carerId = :carerId WHERE clientId = :clientId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object assignClientToCarer(long clientId, long carerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}