package com.example.medicationapp.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0087\b\u0018\u00002\u00020\u0001:\u0001)BM\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u0010 \u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\rH\u00c6\u0003JY\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u00c6\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\'H\u00d6\u0001J\t\u0010(\u001a\u00020\rH\u00d6\u0001R\u0013\u0010\t\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006*"}, d2 = {"Lcom/example/medicationapp/model/MedicationLog;", "", "logId", "", "clientMedicationId", "carerId", "scheduledTime", "", "Ljava/time/LocalTime;", "actualTime", "status", "Lcom/example/medicationapp/model/MedicationLog$Status;", "notes", "", "(JJJLjava/util/List;Ljava/time/LocalTime;Lcom/example/medicationapp/model/MedicationLog$Status;Ljava/lang/String;)V", "getActualTime", "()Ljava/time/LocalTime;", "getCarerId", "()J", "getClientMedicationId", "getLogId", "getNotes", "()Ljava/lang/String;", "getScheduledTime", "()Ljava/util/List;", "getStatus", "()Lcom/example/medicationapp/model/MedicationLog$Status;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "Status", "app_debug"})
@androidx.room.Entity(tableName = "medication_logs", foreignKeys = {@androidx.room.ForeignKey(entity = com.example.medicationapp.model.ClientMedication.class, parentColumns = {"clientMedicationId"}, childColumns = {"client_medication_id"}, onDelete = 5), @androidx.room.ForeignKey(entity = com.example.medicationapp.model.User.class, parentColumns = {"userId"}, childColumns = {"carerId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"client_medication_id"}), @androidx.room.Index(value = {"carerId"})})
public final class MedicationLog {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long logId = 0L;
    @androidx.room.ColumnInfo(name = "client_medication_id")
    private final long clientMedicationId = 0L;
    private final long carerId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.time.LocalTime> scheduledTime = null;
    @org.jetbrains.annotations.Nullable()
    private final java.time.LocalTime actualTime = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.MedicationLog.Status status = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String notes = null;
    
    public MedicationLog(long logId, long clientMedicationId, long carerId, @org.jetbrains.annotations.NotNull()
    java.util.List<java.time.LocalTime> scheduledTime, @org.jetbrains.annotations.Nullable()
    java.time.LocalTime actualTime, @org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.MedicationLog.Status status, @org.jetbrains.annotations.Nullable()
    java.lang.String notes) {
        super();
    }
    
    public final long getLogId() {
        return 0L;
    }
    
    public final long getClientMedicationId() {
        return 0L;
    }
    
    public final long getCarerId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.time.LocalTime> getScheduledTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalTime getActualTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.medicationapp.model.MedicationLog.Status getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNotes() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final long component3() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.time.LocalTime> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalTime component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.medicationapp.model.MedicationLog.Status component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.medicationapp.model.MedicationLog copy(long logId, long clientMedicationId, long carerId, @org.jetbrains.annotations.NotNull()
    java.util.List<java.time.LocalTime> scheduledTime, @org.jetbrains.annotations.Nullable()
    java.time.LocalTime actualTime, @org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.MedicationLog.Status status, @org.jetbrains.annotations.Nullable()
    java.lang.String notes) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/medicationapp/model/MedicationLog$Status;", "", "(Ljava/lang/String;I)V", "Given", "Skipped", "Missed", "Late", "app_debug"})
    public static enum Status {
        /*public static final*/ Given /* = new Given() */,
        /*public static final*/ Skipped /* = new Skipped() */,
        /*public static final*/ Missed /* = new Missed() */,
        /*public static final*/ Late /* = new Late() */;
        
        Status() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.example.medicationapp.model.MedicationLog.Status> getEntries() {
            return null;
        }
    }
}