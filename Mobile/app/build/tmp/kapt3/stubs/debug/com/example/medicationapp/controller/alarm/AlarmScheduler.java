package com.example.medicationapp.controller.alarm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0007R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \u0007*\u0004\u0018\u00010\t0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/example/medicationapp/controller/alarm/AlarmScheduler;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "alarmManager", "Landroid/app/AlarmManager;", "kotlin.jvm.PlatformType", "today", "Ljava/time/LocalDate;", "alarmIntent", "Landroid/app/PendingIntent;", "item", "Lcom/example/medicationapp/model/ClientMedication;", "setUpAlarm", "", "clientMedication", "app_debug"})
public final class AlarmScheduler {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final java.time.LocalDate today = null;
    private final android.app.AlarmManager alarmManager = null;
    
    public AlarmScheduler(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @androidx.annotation.RequiresPermission(value = "android.permission.SCHEDULE_EXACT_ALARM")
    public final void setUpAlarm(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.ClientMedication clientMedication) {
    }
    
    private final android.app.PendingIntent alarmIntent(com.example.medicationapp.model.ClientMedication item) {
        return null;
    }
}