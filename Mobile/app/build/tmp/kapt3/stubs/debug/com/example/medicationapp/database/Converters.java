package com.example.medicationapp.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007J\u0014\u0010\u000b\u001a\u0004\u0018\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0007J\u001a\u0010\u000e\u001a\u0004\u0018\u00010\b2\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010H\u0007J\u0014\u0010\u0012\u001a\u0004\u0018\u00010\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0011H\u0007J\u0014\u0010\u0014\u001a\u0004\u0018\u00010\r2\b\u0010\u0015\u001a\u0004\u0018\u00010\bH\u0007J\u0014\u0010\u0016\u001a\u0004\u0018\u00010\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\bH\u0007J\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\bH\u0007J\u001a\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/medicationapp/database/Converters;", "", "()V", "dateFormatter", "Ljava/time/format/DateTimeFormatter;", "dateTimeFormatter", "timeFormatter", "fromLocalDateTimeToString", "", "dateTime", "Ljava/time/LocalDateTime;", "fromLocalDateToString", "date", "Ljava/time/LocalDate;", "fromLocalTimeListToString", "list", "", "Ljava/time/LocalTime;", "fromLocalTimeToString", "value", "fromStringToLocalDate", "dateString", "fromStringToLocalDateTime", "fromStringToLocalTime", "fromStringToLocalTimeList", "app_debug"})
public final class Converters {
    @org.jetbrains.annotations.NotNull()
    private final java.time.format.DateTimeFormatter timeFormatter = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.format.DateTimeFormatter dateFormatter = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.format.DateTimeFormatter dateTimeFormatter = null;
    
    public Converters() {
        super();
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromLocalDateTimeToString(@org.jetbrains.annotations.Nullable()
    java.time.LocalDateTime dateTime) {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDateTime fromStringToLocalDateTime(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromLocalTimeListToString(@org.jetbrains.annotations.Nullable()
    java.util.List<java.time.LocalTime> list) {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.time.LocalTime> fromStringToLocalTimeList(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromLocalTimeToString(@org.jetbrains.annotations.Nullable()
    java.time.LocalTime value) {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalTime fromStringToLocalTime(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromLocalDateToString(@org.jetbrains.annotations.Nullable()
    java.time.LocalDate date) {
        return null;
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDate fromStringToLocalDate(@org.jetbrains.annotations.Nullable()
    java.lang.String dateString) {
        return null;
    }
}