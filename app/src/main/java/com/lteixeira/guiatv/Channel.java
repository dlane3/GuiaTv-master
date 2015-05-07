package com.lteixeira.guiatv;

public class Channel {
	private String sigla;
	private String name;
	
	Channel(String sigla,String name){
		this.sigla = sigla;
		this.name = name;
	}
	
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return sigla+"\t"+name;
	}
}
