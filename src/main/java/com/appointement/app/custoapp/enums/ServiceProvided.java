package com.appointement.app.custoapp.enums;

public enum ServiceProvided {
	LAVAGE_INTERIEUR("Lavage Intérieur", 25),
	LAVAGE_EXTERIEUR("Lavage Extérieur", 25),
	LAVAGE_INTERIEUR_EXTERIEUR("Lavage Intérieur Et Extérieur", 40),
	NETTOYAGE_SIEGE("Nettoyage des sièges", 65),
	NETTOYAGE_DE_PLAFONNIER("Nettoyage de Plafonnier", 65);

	private String name;
	private int price;

	ServiceProvided(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}
}
