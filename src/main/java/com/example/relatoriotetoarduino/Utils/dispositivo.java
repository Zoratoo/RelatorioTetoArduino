package com.example.relatoriotetoarduino.Utils;

import java.time.LocalDate;

public class dispositivo {
    public float manha, tarde, noite;
    public LocalDate data;
    public int id;

    public dispositivo(int id, float manha, float tarde, float noite, LocalDate data) {
        this.id = id;
        this.manha = manha;
        this.tarde = tarde;
        this.noite = noite;
        this.data = data;
    }

    public dispositivo(float manha, float tarde, float noite, LocalDate data) {
        this(0,manha,tarde,noite,data);
    }

    public dispositivo() {
        this(0,0,0,0,LocalDate.now());
    }

    public float getManha() {
        return manha;
    }

    public void setManha(float manha) {
        this.manha = manha;
    }

    public float getTarde() {
        return tarde;
    }

    public void setTarde(float tarde) {
        this.tarde = tarde;
    }

    public float getNoite() {
        return noite;
    }

    public void setNoite(float noite) {
        this.noite = noite;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
