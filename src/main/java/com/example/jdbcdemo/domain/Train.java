package com.example.jdbcdemo.domain;

public class Train {
	
	private long id_tr;
	private String trainNum;
	private String departurePoint;
	private String arrivalPoint;

	public Train(){}
	
	public Train(String trainNum, String departurePoint, String arrivalPoint){
		super();
		this.trainNum = trainNum;
		this.departurePoint = departurePoint;
		this.arrivalPoint = arrivalPoint;
	}
	
	//long idTicket;
	public long getId() {
		return id_tr;
	}
	public void setId(long id_tr) {
		this.id_tr = id_tr;
	}
	public String getTrainNum() {
		return trainNum;
	}
	public void setTrainNum(String trainNum) {
		this.trainNum = trainNum;
	}
	public String getDeparturePoint() {
		return departurePoint;
	}
	public void setDeparturePoint(String departurePoint) {
		this.departurePoint = departurePoint;
	}
	public String getArrivalPoint() {
		return arrivalPoint;
	}
	public void setArrivalPoint(String arrivalPoint) {
		this.arrivalPoint = arrivalPoint;
	}
	/*public long getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(long idTicket) {
		this.idTicket = idTicket;
	}*/
}
