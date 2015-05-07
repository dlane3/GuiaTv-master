package com.lteixeira.guiatv;

import android.text.format.Time;

public class Show {

	private int id;
	private String name;
	private String description;
	private Time inicio;
	private Time fim;
	
	public Show(int id,String name,String description,Time inicio,Time fim){
		this.id = id;
		this.name = name;
		this.description = description;
		this.inicio = inicio;
		this.fim = fim;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Time getInicio() {
		return inicio;
	}
	public void setInicio(Time inicio) {
		this.inicio = inicio;
	}
	public Time getFim() {
		return fim;
	}
	public void setFim(Time fim) {
		this.fim = fim;
	}
	
	
	
}
