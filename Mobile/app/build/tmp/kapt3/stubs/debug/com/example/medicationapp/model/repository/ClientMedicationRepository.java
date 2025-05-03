package com.example.medicationapp.model.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/medicationapp/model/repository/ClientMedicationRepository;", "", "dao", "Lcom/example/medicationapp/model/dao/ClientMedicationDao;", "(Lcom/example/medicationapp/model/dao/ClientMedicationDao;)V", "assignMedication", "", "clientMedication", "Lcom/example/medicationapp/model/ClientMedication;", "(Lcom/example/medicationapp/model/ClientMedication;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAssignment", "", "getAllAssignments", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getClientsForMedication", "medicationId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateAssignment", "app_debug"})
public final class ClientMedicationRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.dao.ClientMedicationDao dao = null;
    
    public ClientMedicationRepository(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.dao.ClientMedicationDao dao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object assignMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.ClientMedication clientMedication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllAssignments(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.ClientMedication>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getClientsForMedication(int medicationId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.ClientMedication>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateAssignment(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.ClientMedication clientMedication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteAssignment(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.ClientMedication clientMedication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}