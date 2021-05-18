package com.gomesmauricio.crud_api_rest;

public class Pessoa {

    private int id;
    private String nome;

    public Pessoa(int ID, String nome){
        this.id = ID;
        this.nome = nome;
    }

    public Pessoa(){
    }

    public int getID(){ return id; }

    public void  setID(int ID) {this.id = ID; }

    public String getNome() {return nome; }

    public void setNome (String nome) {this.nome = nome; }
}
