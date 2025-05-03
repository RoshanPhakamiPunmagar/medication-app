package com.example.medicationapp.model.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lcom/example/medicationapp/model/dao/MedicationInteractionDao;", "", "getInteractionsForMedication", "", "Lcom/example/medicationapp/model/MedicationInteraction;", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertInteraction", "", "interaction", "(Lcom/example/medicationapp/model/MedicationInteraction;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface MedicationInteractionDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertInteraction(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.MedicationInteraction interaction, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM medication_interactions WHERE medication_id_1 = :id OR medication_id_2 = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getInteractionsForMedication(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.MedicationInteraction>> $completion);
}