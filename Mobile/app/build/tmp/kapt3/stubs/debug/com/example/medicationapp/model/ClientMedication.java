package com.example.medicationapp.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BO\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\u0002\u0010\u0010J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0007H\u00c6\u0003J\t\u0010!\u001a\u00020\tH\u00c6\u0003J\t\u0010\"\u001a\u00020\tH\u00c6\u0003J\t\u0010#\u001a\u00020\fH\u00c6\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00c6\u0003J_\u0010%\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00c6\u0001J\t\u0010&\u001a\u00020\'H\u00d6\u0001J\u0013\u0010(\u001a\u00020\f2\b\u0010)\u001a\u0004\u0018\u00010*H\u00d6\u0003J\t\u0010+\u001a\u00020\'H\u00d6\u0001J\t\u0010,\u001a\u00020\u0007H\u00d6\u0001J\u0019\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\'H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0018R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017\u00a8\u00062"}, d2 = {"Lcom/example/medicationapp/model/ClientMedication;", "Landroid/os/Parcelable;", "clientMedicationId", "", "clientId", "medicationId", "dosage", "", "startDate", "Ljava/time/LocalDate;", "endDate", "isPaused", "", "scheduledTimes", "", "Ljava/time/LocalTime;", "(JJJLjava/lang/String;Ljava/time/LocalDate;Ljava/time/LocalDate;ZLjava/util/List;)V", "getClientId", "()J", "getClientMedicationId", "getDosage", "()Ljava/lang/String;", "getEndDate", "()Ljava/time/LocalDate;", "()Z", "getMedicationId", "getScheduledTimes", "()Ljava/util/List;", "getStartDate", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "describeContents", "", "equals", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_debug"})
@androidx.room.Entity(tableName = "client_medications", foreignKeys = {@androidx.room.ForeignKey(entity = com.example.medicationapp.model.Client.class, parentColumns = {"clientId"}, childColumns = {"clientId"}, onDelete = 5), @androidx.room.ForeignKey(entity = com.example.medicationapp.model.Medication.class, parentColumns = {"medicationId"}, childColumns = {"medicationId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"clientId"}), @androidx.room.Index(value = {"medicationId"})})
@kotlinx.parcelize.Parcelize()
public final class ClientMedication implements android.os.Parcelable {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long clientMedicationId = 0L;
    private final long clientId = 0L;
    private final long medicationId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dosage = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDate startDate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDate endDate = null;
    private final boolean isPaused = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.time.LocalTime> scheduledTimes = null;
    
    public ClientMedication(long clientMedicationId, long clientId, long medicationId, @org.jetbrains.annotations.NotNull()
    java.lang.String dosage, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate, boolean isPaused, @org.jetbrains.annotations.NotNull()
    java.util.List<java.time.LocalTime> scheduledTimes) {
        super();
    }
    
    public final long getClientMedicationId() {
        return 0L;
    }
    
    public final long getClientId() {
        return 0L;
    }
    
    public final long getMedicationId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDosage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate getStartDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate getEndDate() {
        return null;
    }
    
    public final boolean isPaused() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.time.LocalTime> getScheduledTimes() {
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
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.time.LocalTime> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.medicationapp.model.ClientMedication copy(long clientMedicationId, long clientId, long medicationId, @org.jetbrains.annotations.NotNull()
    java.lang.String dosage, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate, boolean isPaused, @org.jetbrains.annotations.NotNull()
    java.util.List<java.time.LocalTime> scheduledTimes) {
        return null;
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
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
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
}