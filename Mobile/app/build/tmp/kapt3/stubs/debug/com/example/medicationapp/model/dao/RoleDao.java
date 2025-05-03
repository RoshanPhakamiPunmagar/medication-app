package com.example.medicationapp.model.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0018\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0011\u00a8\u0006\u0012"}, d2 = {"Lcom/example/medicationapp/model/dao/RoleDao;", "", "getAllRoleNames", "", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllRoles", "Lcom/example/medicationapp/model/Role;", "getRoleById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRoleIdByName", "roleName", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertRole", "role", "(Lcom/example/medicationapp/model/Role;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface RoleDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertRole(@org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.Role role, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM roles")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllRoles(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.medicationapp.model.Role>> $completion);
    
    @androidx.room.Query(value = "SELECT roleId FROM roles WHERE roleName = :roleName")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRoleIdByName(@org.jetbrains.annotations.NotNull()
    java.lang.String roleName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT roleName FROM roles")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllRoleNames(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM roles WHERE roleId = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRoleById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.medicationapp.model.Role> $completion);
}