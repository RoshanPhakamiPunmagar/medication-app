package com.example.medicationapp.view.carerviews;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a(\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0014\u0010\u0004\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a \u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007\u001a\u001e\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0011H\u0007\u001aJ\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0018\u001a\u00020\u00032\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u001a\u00a8\u0006\u001b"}, d2 = {"ActualTimeInput", "", "actualTime", "Ljava/time/LocalTime;", "onTimeChange", "Lkotlin/Function1;", "ClientSelectionScreen", "clientController", "Lcom/example/medicationapp/controller/ClientController;", "navController", "Landroidx/navigation/NavController;", "carerId", "", "MedicationStatusButton", "text", "", "onClick", "Lkotlin/Function0;", "logMedication", "clientMedication", "Lcom/example/medicationapp/model/ClientMedication;", "status", "Lcom/example/medicationapp/model/MedicationLog$Status;", "clientId", "scheduledTime", "notes", "(Lcom/example/medicationapp/model/ClientMedication;Lcom/example/medicationapp/model/MedicationLog$Status;Ljava/lang/Long;Ljava/time/LocalTime;Ljava/time/LocalTime;Ljava/lang/String;Lcom/example/medicationapp/controller/ClientController;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ClientSelectionScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void ActualTimeInput(@org.jetbrains.annotations.Nullable()
    java.time.LocalTime actualTime, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.time.LocalTime, kotlin.Unit> onTimeChange) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ClientSelectionScreen(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.controller.ClientController clientController, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, long carerId) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MedicationStatusButton(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.Object logMedication(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.ClientMedication clientMedication, @org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.MedicationLog.Status status, @org.jetbrains.annotations.Nullable()
    java.lang.Long clientId, @org.jetbrains.annotations.NotNull()
    java.time.LocalTime scheduledTime, @org.jetbrains.annotations.Nullable()
    java.time.LocalTime actualTime, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    com.example.medicationapp.controller.ClientController clientController, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}