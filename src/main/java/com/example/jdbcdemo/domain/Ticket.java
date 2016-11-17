package com.example.jdbcdemo.domain;

public class Ticket {

	private long id_ti;
	private double firstClassPrice;
	private double secondClassPrice;
	private long id_train;

	public Ticket() {
	}

	public Ticket(double firstClassPrice, double secondClassPrice, long id_train) {
		super();
		this.firstClassPrice = firstClassPrice;
		this.secondClassPrice = secondClassPrice;
		this.id_train = id_train;
	}

	public long getId() {
		return id_ti;
	}

	public void setId(long id_ti) {
		this.id_ti = id_ti;
	}

	public long getId_train() {
		return id_train;
	}

	public void setId_train(long id_train) {
		this.id_train = id_train;
	}

	public double getFirstClassPrice() {
		return firstClassPrice;
	}

	public void setFirstClassPrice(double firstClassPrice) {
		this.firstClassPrice = firstClassPrice;
	}

	public double getSecondClassPrice() {
		return secondClassPrice;
	}

	public void setSecondClassPrice(double secondClassPrice) {
		this.secondClassPrice = secondClassPrice;
	}

}
