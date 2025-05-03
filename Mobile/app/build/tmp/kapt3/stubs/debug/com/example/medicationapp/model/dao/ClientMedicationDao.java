package com.example.medicationapp.model.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u001c\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\b2\u0006\u0010\u0015\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0018"}, d2 = {"Lcom/example/medicationapp/model/dao/ClientMedicationDao;", "", "deleteClientMedication", "", "medication", "Lcom/example/medicationapp/model/ClientMedication;", "(Lcom/example/medicationapp/model/ClientMedication;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllClientMedications", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getClientMedicationById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getClientsForMedication", "medicationId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getClientsMedicationById", "clientMedicationId", "getMedicationsForClient", "clientId", "insertClientMedication", "updateClientMedication", "app_debug"})
@androidx.room.Dao()
public abstract interface ClientMedicationDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertClientMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.ClientMedication medication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateClientMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.ClientMedication medication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteClientMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.ClientMedication medication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM client_medications WHERE clientId = :clientId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMedicationsForClient(long clientId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.ClientMedication>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM client_medications WHERE medicationId = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getClientMedicationById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.medicationapp.model.ClientMedication> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM client_medications")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllClientMedications(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.ClientMedication>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM client_medications WHERE medicationId = :medicationId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getClientsForMedication(int medicationId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.ClientMedication>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM client_medications WHERE clientMedicationId = :clientMedicationId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getClientsMedicationById(int clientMedicationId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.medicationapp.model.ClientMedication> $completion);
}