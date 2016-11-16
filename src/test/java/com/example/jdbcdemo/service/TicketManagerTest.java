package com.example.jdbcdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.example.jdbcdemo.domain.Ticket;
import com.example.jdbcdemo.domain.Train;

public class TicketManagerTest {
	
	TicketManager ticketManager = new TicketManager();
	TrainManager trainManager = new TrainManager();
	
	private long id_train;
	private final static String TRAIN_NUM = "AB123";
	private final static String DEPARTURE_POINT = "Warszawa centralna";
	private final static String ARRIVAL_POINT = "Gdank glowny";
	
	private final static double FIRST_CLASS_PRICE = 25.99;
	private final static double SECOND_CLASS_PRICE = 18.99;

	@Test
	public void checkConnection(){
		assertNotNull(ticketManager.getConnection());

	}
	
	@Test
	public void checkAdding(){
		
		Train train = new Train(TRAIN_NUM,DEPARTURE_POINT,ARRIVAL_POINT);
		trainManager.clearTrain();
		assertEquals(1,trainManager.addTrain(train));
		
		List<Train> trains = trainManager.getAllTrains();
		Train trainRetrieved = trains.get(0);
		id_train = trainRetrieved.getId();
		
		Ticket ticket = new Ticket(FIRST_CLASS_PRICE, SECOND_CLASS_PRICE, id_train);
		ticketManager.clearTicket();
		
		assertEquals(1,ticketManager.addTicket(ticket));
		List<Ticket> tickets = ticketManager.getAllTickets();
		Ticket ticketRetrieved = tickets.get(0);
		
		assertEquals(FIRST_CLASS_PRICE, ticketRetrieved.getFirstClassPrice(), 0.02);
		assertEquals(SECOND_CLASS_PRICE, ticketRetrieved.getSecondClassPrice(), 0.02);
		assertEquals(id_train, ticketRetrieved.getId_train());
	}
	
	@Test
	public void checkRelation(){
		
		trainManager.clearTrain();
		ticketManager.clearTicket();
		
		Train train = new Train(TRAIN_NUM,DEPARTURE_POINT,ARRIVAL_POINT);
		assertEquals(1,trainManager.addTrain(train));
		
		List<Train> trains = trainManager.getAllTrains();
		Train trainRetrieved = trains.get(0);
		id_train = trainRetrieved.getId();
		
		Ticket ticket = new Ticket(FIRST_CLASS_PRICE, SECOND_CLASS_PRICE, id_train);
		assertEquals(1,ticketManager.addTicket(ticket));
		
		List<Ticket> tickets = ticketManager.getAllTickets();
		Ticket ticketRetrieved = tickets.get(0);
		
		assertEquals(id_train, ticketRetrieved.getId_train());
	}
}
