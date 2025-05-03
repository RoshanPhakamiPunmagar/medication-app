package com.example.medicationapp.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0087\b\u0018\u00002\u00020\u0001:\u0001\u001fB/\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\tH\u00c6\u0003J;\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006 "}, d2 = {"Lcom/example/medicationapp/model/MedicationInteraction;", "", "interactionId", "", "medication1Id", "medication2Id", "interactionDescription", "", "severity", "Lcom/example/medicationapp/model/MedicationInteraction$Severity;", "(JJJLjava/lang/String;Lcom/example/medicationapp/model/MedicationInteraction$Severity;)V", "getInteractionDescription", "()Ljava/lang/String;", "getInteractionId", "()J", "getMedication1Id", "getMedication2Id", "getSeverity", "()Lcom/example/medicationapp/model/MedicationInteraction$Severity;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "Severity", "app_debug"})
@androidx.room.Entity(tableName = "medication_interactions", foreignKeys = {@androidx.room.ForeignKey(entity = com.example.medicationapp.model.Medication.class, parentColumns = {"medicationId"}, childColumns = {"medication_id_1"}), @androidx.room.ForeignKey(entity = com.example.medicationapp.model.Medication.class, parentColumns = {"medicationId"}, childColumns = {"medication_id_2"})}, indices = {@androidx.room.Index(value = {"medication_id_1"}), @androidx.room.Index(value = {"medication_id_2"})})
public final class MedicationInteraction {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long interactionId = 0L;
    @androidx.room.ColumnInfo(name = "medication_id_1")
    private final long medication1Id = 0L;
    @androidx.room.ColumnInfo(name = "medication_id_2")
    private final long medication2Id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String interactionDescription = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.medicationapp.model.MedicationInteraction.Severity severity = null;
    
    public MedicationInteraction(long interactionId, long medication1Id, long medication2Id, @org.jetbrains.annotations.NotNull()
    java.lang.String interactionDescription, @org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.MedicationInteraction.Severity severity) {
        super();
    }
    
    public final long getInteractionId() {
        return 0L;
    }
    
    public final long getMedication1Id() {
        return 0L;
    }
    
    public final long getMedication2Id() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getInteractionDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.medicationapp.model.MedicationInteraction.Severity getSeverity() {
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
    public final com.example.medicationapp.model.MedicationInteraction.Severity component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.medicationapp.model.MedicationInteraction copy(long interactionId, long medication1Id, long medication2Id, @org.jetbrains.annotations.NotNull()
    java.lang.String interactionDescription, @org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.MedicationInteraction.Severity severity) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/medicationapp/model/MedicationInteraction$Severity;", "", "(Ljava/lang/String;I)V", "LOW", "MEDIUM", "HIGH", "CRITICAL", "app_debug"})
    public static enum Severity {
        /*public static final*/ LOW /* = new LOW() */,
        /*public static final*/ MEDIUM /* = new MEDIUM() */,
        /*public static final*/ HIGH /* = new HIGH() */,
        /*public static final*/ CRITICAL /* = new CRITICAL() */;
        
        Severity() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.example.medicationapp.model.MedicationInteraction.Severity> getEntries() {
            return null;
        }
    }
}