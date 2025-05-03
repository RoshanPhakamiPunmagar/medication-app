package com.example.medicationapp.controller;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u000e\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJD\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u001c\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001b2\u0006\u0010\u0014\u001a\u00020 H\u0086@\u00a2\u0006\u0002\u0010!J\u0016\u0010\"\u001a\u00020\f2\u0006\u0010#\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010$J\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001bH\u0086@\u00a2\u0006\u0002\u0010&J\u001c\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00110\u001b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010(J\u0018\u0010)\u001a\u0004\u0018\u00010\u000e2\u0006\u0010*\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010(J\u0016\u0010+\u001a\u00020\f2\u0006\u0010#\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010$J\u0016\u0010,\u001a\u00020\f2\u0006\u0010#\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010$J\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001bH\u0086@\u00a2\u0006\u0002\u0010&R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/example/medicationapp/controller/MedicationController;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "clientMedicationDao", "Lcom/example/medicationapp/model/dao/ClientMedicationDao;", "interactionDao", "Lcom/example/medicationapp/model/dao/MedicationInteractionDao;", "medicationDao", "Lcom/example/medicationapp/model/dao/MedicationDao;", "addMedication", "", "medication", "Lcom/example/medicationapp/model/Medication;", "(Lcom/example/medicationapp/model/Medication;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "assignMedicationToClient", "Lcom/example/medicationapp/model/ClientMedication;", "clientId", "", "medicationId", "dosage", "", "startDate", "Ljava/time/LocalDate;", "endDate", "scheduledTimes", "", "Ljava/time/LocalTime;", "(JJLjava/lang/String;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkInteractions", "Lcom/example/medicationapp/model/MedicationInteraction;", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteClientMedication", "clientMedication", "(Lcom/example/medicationapp/model/ClientMedication;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllMedications", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getClientMedications", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMedications", "id", "updateClientMedication", "updateSchedule", "viewMedicationDetails", "app_debug"})
public final class MedicationController {
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.dao.MedicationDao medicationDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.dao.MedicationInteractionDao interactionDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.dao.ClientMedicationDao clientMedicationDao = null;
    
    public MedicationController(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllMedications(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.Medication>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getMedications(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.medicationapp.model.Medication> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateSchedule(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.ClientMedication clientMedication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Medication medication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object viewMedicationDetails(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.Medication>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object checkInteractions(int medicationId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.MedicationInteraction>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object assignMedicationToClient(long clientId, long medicationId, @org.jetbrains.annotations.NotNull()
    java.lang.String dosage, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate, @org.jetbrains.annotations.NotNull()
    java.util.List<java.time.LocalTime> scheduledTimes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.medicationapp.model.ClientMedication> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateClientMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.ClientMedication clientMedication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteClientMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.ClientMedication clientMedication, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getClientMedications(long clientId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.ClientMedication>> $completion) {
        return null;
    }
}