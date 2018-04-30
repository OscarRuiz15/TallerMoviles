package com.example.oscar.tallermoviles.clases;

public class Usuario {
    private int id;
    private String nombre;
    private int tipo;
    private String email;
    private String password;

    public Usuario(int id,String nombre,int tipo,String email,String password){
        this.id=id;
        this.nombre=nombre;
        this.tipo=tipo;
        this.email=email;
        this.password=password;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
