package com.example.medicationapp.controller.rest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\"\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\b\b\u0001\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\n\u001a\u00020\u0006H\'J\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\b\b\u0001\u0010\f\u001a\u00020\u0004H\'\u00a8\u0006\r"}, d2 = {"Lcom/example/medicationapp/controller/rest/ApiService;", "", "getMedicationWithName", "Lretrofit2/Call;", "Lcom/example/medicationapp/model/User;", "name", "", "login", "Lcom/example/medicationapp/controller/rest/Status;", "email", "password", "register", "user", "app_debug"})
public abstract interface ApiService {
    
    @retrofit2.http.POST(value = "mobile/check")
    @retrofit2.http.FormUrlEncoded()
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.example.medicationapp.controller.rest.Status> login(@retrofit2.http.Field(value = "email")
    @org.jetbrains.annotations.NotNull()
    java.lang.String email, @retrofit2.http.Field(value = "password")
    @org.jetbrains.annotations.NotNull()
    java.lang.String password);
    
    @retrofit2.http.POST(value = "mobile/user")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.example.medicationapp.controller.rest.Status> register(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.medicationapp.model.User user);
    
    @retrofit2.http.GET(value = "meds/{name}")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.example.medicationapp.model.User> getMedicationWithName(@retrofit2.http.Path(value = "name")
    @org.jetbrains.annotations.NotNull()
    java.lang.String name);
}