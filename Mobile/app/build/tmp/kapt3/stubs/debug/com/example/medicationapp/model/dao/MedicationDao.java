package com.example.medicationapp.model.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/example/medicationapp/model/dao/MedicationDao;", "", "deleteMedication", "", "medication", "Lcom/example/medicationapp/model/Medication;", "(Lcom/example/medicationapp/model/Medication;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllMedications", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMedicationById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertMedication", "updateMedication", "app_debug"})
@androidx.room.Dao()
public abstract interface MedicationDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Medication medication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM medications")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllMedications(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.Medication>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM medications WHERE medicationId = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMedicationById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.medicationapp.model.Medication> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Medication medication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Medication medication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}