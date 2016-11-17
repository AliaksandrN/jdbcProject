package com.example.jdbcdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.example.jdbcdemo.domain.Ticket;
import com.example.jdbcdemo.domain.Train;

public class TicketManagerTest {

	TicketManager ticketManager = new TicketManager();
	TrainManager trainManager = new TrainManager();

	private long id_train;
	private final static String TRAIN_NUM = "AB123";
	private final static String TRAIN_NUM1 = "AB222";
	private final static String TRAIN_NUM2 = "AB333";

	private final static String DEPARTURE_POINT = "Warszawa centralna";
	private final static String ARRIVAL_POINT = "Gdank glowny";

	private final static double FIRST_CLASS_PRICE = 25.99;
	private final static double SECOND_CLASS_PRICE = 18.99;

	private final static double FIRST_CLASS_PRICE1 = 11.11;
	private final static double SECOND_CLASS_PRICE1 = 15.15;

	private final static double FIRST_CLASS_PRICE2 = 22.22;
	private final static double SECOND_CLASS_PRICE2 = 25.25;

	@Test
	public void checkConnection() {
		assertNotNull(ticketManager.getConnection());

	}

	@Test
	public void checkAdding() {

		Train train = new Train(TRAIN_NUM, DEPARTURE_POINT, ARRIVAL_POINT);
		trainManager.clearTrain();
		assertEquals(1, trainManager.addTrain(train));

		List<Train> trains = trainManager.getAllTrains();
		Train trainRetrieved = trains.get(0);
		id_train = trainRetrieved.getId();

		Ticket ticket = new Ticket(FIRST_CLASS_PRICE, SECOND_CLASS_PRICE, id_train);
		ticketManager.clearTicket();

		assertEquals(1, ticketManager.addTicket(ticket));
		List<Ticket> tickets = ticketManager.getAllTickets();
		Ticket ticketRetrieved = tickets.get(0);

		assertEquals(FIRST_CLASS_PRICE, ticketRetrieved.getFirstClassPrice(), 0.02);
		assertEquals(SECOND_CLASS_PRICE, ticketRetrieved.getSecondClassPrice(), 0.02);
		assertEquals(id_train, ticketRetrieved.getId_train());
	}

	@Test
	public void checkRelation() {

		trainManager.clearTrain();
		ticketManager.clearTicket();

		Train train = new Train(TRAIN_NUM, DEPARTURE_POINT, ARRIVAL_POINT);
		assertEquals(1, trainManager.addTrain(train));

		List<Train> trains = trainManager.getAllTrains();
		Train trainRetrieved = trains.get(0);
		id_train = trainRetrieved.getId();

		Ticket ticket = new Ticket(FIRST_CLASS_PRICE, SECOND_CLASS_PRICE, id_train);
		assertEquals(1, ticketManager.addTicket(ticket));

		List<Ticket> tickets = ticketManager.getAllTickets();
		Ticket ticketRetrieved = tickets.get(0);

		assertEquals(id_train, ticketRetrieved.getId_train());
	}

	@Test
	public void checkDeletion() {

		trainManager.clearTrain();
		ticketManager.clearTicket();

		Train train = new Train(TRAIN_NUM, DEPARTURE_POINT, ARRIVAL_POINT);
		Train train1 = new Train(TRAIN_NUM1, DEPARTURE_POINT, ARRIVAL_POINT);
		Train train2 = new Train(TRAIN_NUM2, DEPARTURE_POINT, ARRIVAL_POINT);

		assertEquals(1, trainManager.addTrain(train));
		assertEquals(1, trainManager.addTrain(train1));
		assertEquals(1, trainManager.addTrain(train2));

		List<Train> trains = trainManager.getAllTrains();
		assertEquals(3, trains.size());

		Ticket ticket = new Ticket(FIRST_CLASS_PRICE, SECOND_CLASS_PRICE, trains.get(0).getId());
		Ticket ticket1 = new Ticket(FIRST_CLASS_PRICE1, SECOND_CLASS_PRICE1, trains.get(1).getId());
		Ticket ticket2 = new Ticket(FIRST_CLASS_PRICE2, SECOND_CLASS_PRICE2, trains.get(2).getId());

		assertEquals(1, ticketManager.addTicket(ticket));
		assertEquals(1, ticketManager.addTicket(ticket1));
		assertEquals(1, ticketManager.addTicket(ticket2));

		List<Ticket> tickets = ticketManager.getAllTickets();
		assertEquals(3, tickets.size());

		ticketManager.clearTicket();

		tickets = ticketManager.getAllTickets();
		assertEquals(0, tickets.size());

		assertEquals(1, ticketManager.addTicket(ticket));
		assertEquals(1, ticketManager.addTicket(ticket1));
		assertEquals(1, ticketManager.addTicket(ticket2));

		tickets = ticketManager.getAllTickets();
		assertEquals(3, tickets.size());

		long deleting_ticket = tickets.get(0).getId();
		ticketManager.deleteOne(deleting_ticket);

		tickets = ticketManager.getAllTickets();
		assertEquals(2, tickets.size());

		assertTrue(ticketManager.checkRecordByUniqueVal(deleting_ticket));
	}

	@Test
	public void checkSelect() {

		Train train = new Train(TRAIN_NUM, DEPARTURE_POINT, ARRIVAL_POINT);
		Train train1 = new Train(TRAIN_NUM1, DEPARTURE_POINT, ARRIVAL_POINT);
		Train train2 = new Train(TRAIN_NUM2, DEPARTURE_POINT, ARRIVAL_POINT);

		trainManager.clearTrain();
		assertEquals(1, trainManager.addTrain(train));
		assertEquals(1, trainManager.addTrain(train1));
		assertEquals(1, trainManager.addTrain(train2));

		List<Train> trains = trainManager.getAllTrains();
		assertEquals(3, trains.size());

		Ticket ticket = new Ticket(FIRST_CLASS_PRICE, SECOND_CLASS_PRICE, trains.get(0).getId());
		Ticket ticket1 = new Ticket(FIRST_CLASS_PRICE1, SECOND_CLASS_PRICE1, trains.get(1).getId());
		Ticket ticket2 = new Ticket(FIRST_CLASS_PRICE2, SECOND_CLASS_PRICE2, trains.get(2).getId());

		ticketManager.clearTicket();
		assertEquals(1, ticketManager.addTicket(ticket));
		assertEquals(1, ticketManager.addTicket(ticket1));
		assertEquals(1, ticketManager.addTicket(ticket2));

		List<Ticket> tickets = ticketManager.getAllTickets();
		assertEquals(3, tickets.size());

		assertEquals(FIRST_CLASS_PRICE, tickets.get(0).getFirstClassPrice(), 0.02);
		assertEquals(FIRST_CLASS_PRICE1, tickets.get(1).getFirstClassPrice(), 0.02);
		assertEquals(FIRST_CLASS_PRICE2, tickets.get(2).getFirstClassPrice(), 0.02);

		assertFalse(ticketManager.checkRecordByUniqueVal(tickets.get(0).getId()));
		assertFalse(ticketManager.checkRecordByUniqueVal(tickets.get(1).getId()));
		assertFalse(ticketManager.checkRecordByUniqueVal(tickets.get(2).getId()));
	}

	@Test
	public void checkUpdate() {

		Train train = new Train(TRAIN_NUM, DEPARTURE_POINT, ARRIVAL_POINT);
		Train train1 = new Train(TRAIN_NUM1, DEPARTURE_POINT, ARRIVAL_POINT);

		trainManager.clearTrain();
		assertEquals(1, trainManager.addTrain(train));
		assertEquals(1, trainManager.addTrain(train1));

		List<Train> trains = trainManager.getAllTrains();

		Ticket ticket = new Ticket(FIRST_CLASS_PRICE, SECOND_CLASS_PRICE, trains.get(0).getId());

		ticketManager.clearTicket();
		assertEquals(1, ticketManager.addTicket(ticket));

		List<Ticket> tickets = ticketManager.getAllTickets();

		assertEquals(FIRST_CLASS_PRICE, tickets.get(0).getFirstClassPrice(), 0.02);
		assertEquals(SECOND_CLASS_PRICE, tickets.get(0).getSecondClassPrice(), 0.02);
		assertEquals(trains.get(0).getId(), tickets.get(0).getId_train());

		assertEquals(1, ticketManager.updateRecord(FIRST_CLASS_PRICE1, SECOND_CLASS_PRICE1, trains.get(1).getId(),
				tickets.get(0).getId()));

		tickets = ticketManager.getAllTickets();

		assertEquals(FIRST_CLASS_PRICE1, tickets.get(0).getFirstClassPrice(), 0.02);
		assertEquals(SECOND_CLASS_PRICE1, tickets.get(0).getSecondClassPrice(), 0.02);
		assertEquals(trains.get(1).getId(), tickets.get(0).getId_train());

	}
}
