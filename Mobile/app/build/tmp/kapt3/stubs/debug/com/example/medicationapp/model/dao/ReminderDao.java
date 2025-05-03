package com.example.medicationapp.model.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\b2\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/example/medicationapp/model/dao/ReminderDao;", "", "deleteReminder", "", "reminder", "Lcom/example/medicationapp/model/Reminder;", "(Lcom/example/medicationapp/model/Reminder;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllReminders", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRemindersForClientMedication", "cmId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertReminder", "updateReminder", "app_debug"})
@androidx.room.Dao()
public abstract interface ReminderDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertReminder(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Reminder reminder, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateReminder(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Reminder reminder, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteReminder(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Reminder reminder, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM reminders WHERE clientMedicationId = :cmId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRemindersForClientMedication(int cmId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.Reminder>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM reminders")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllReminders(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.Reminder>> $completion);
}