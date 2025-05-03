package com.example.medicationapp.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/example/medicationapp/repository/MedicationRepository;", "", "medicationDao", "Lcom/example/medicationapp/model/dao/MedicationDao;", "(Lcom/example/medicationapp/model/dao/MedicationDao;)V", "addMedication", "", "medication", "Lcom/example/medicationapp/model/Medication;", "(Lcom/example/medicationapp/model/Medication;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMedication", "", "getAllMedications", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateMedication", "app_debug"})
public final class MedicationRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.dao.MedicationDao medicationDao = null;
    
    public MedicationRepository(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.dao.MedicationDao medicationDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Medication medication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllMedications(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.Medication>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Medication medication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Medication medication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}