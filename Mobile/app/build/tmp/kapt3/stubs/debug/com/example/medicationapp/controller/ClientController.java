package com.example.medicationapp.controller;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0016J(\u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u00190\u00102\u0006\u0010\u001c\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0086@\u00a2\u0006\u0002\u0010!R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/example/medicationapp/controller/ClientController;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "adherenceLogDao", "Lcom/example/medicationapp/model/dao/AdherenceLogDao;", "clientDao", "Lcom/example/medicationapp/model/dao/ClientDao;", "clientMedicationDao", "Lcom/example/medicationapp/model/dao/ClientMedicationDao;", "medicationDao", "Lcom/example/medicationapp/model/dao/MedicationDao;", "medicationLogDao", "Lcom/example/medicationapp/model/dao/MedicationLogDao;", "getAllClients", "", "Lcom/example/medicationapp/model/Client;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getClientById", "carerId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getClientsForCarer", "getMedicationsForClient", "Lkotlin/Pair;", "Lcom/example/medicationapp/model/ClientMedication;", "", "clientId", "logMedication", "", "medicationLog", "Lcom/example/medicationapp/model/MedicationLog;", "(Lcom/example/medicationapp/model/MedicationLog;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ClientController {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.dao.ClientDao clientDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.dao.ClientMedicationDao clientMedicationDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.dao.MedicationLogDao medicationLogDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.dao.MedicationDao medicationDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.dao.AdherenceLogDao adherenceLogDao = null;
    
    public ClientController(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllClients(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.Client>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getClientsForCarer(long carerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.Client>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getClientById(long carerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.medicationapp.model.Client> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object logMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.MedicationLog medicationLog, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getMedicationsForClient(long clientId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<kotlin.Pair<com.example.medicationapp.model.ClientMedication, java.lang.String>>> $completion) {
        return null;
    }
}