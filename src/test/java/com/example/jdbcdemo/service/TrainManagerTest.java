package com.example.jdbcdemo.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.example.jdbcdemo.domain.Train;

public class TrainManagerTest {
	
	TrainManager trainManager = new TrainManager();
	
	private final static String TRAIN_NUM = "AB123";
	private final static String ARRIVAL_POINT = "Gdank glowny";
	private final static String DEPARTURE_POINT = "Warszawa centralna";
	
	@Test
	public void checkConnection(){
		assertNotNull(trainManager.getConnection());
	}
	
	@Test
	public void checkAdding(){
		
		Train train = new Train(TRAIN_NUM, DEPARTURE_POINT, ARRIVAL_POINT);
		
		trainManager.clearTrain();
		assertEquals(1,trainManager.addTrain(train));
		List<Train> trains = trainManager.getAllTrains();
		Train trainRetrieved = trains.get(0);

		assertEquals(TRAIN_NUM, trainRetrieved.getTrainNum());
		assertEquals(ARRIVAL_POINT, trainRetrieved.getArrivalPoint());
		assertEquals(DEPARTURE_POINT, trainRetrieved.getDeparturePoint());
	}
	
}
