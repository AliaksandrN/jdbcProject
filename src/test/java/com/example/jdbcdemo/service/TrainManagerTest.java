package com.example.jdbcdemo.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.example.jdbcdemo.domain.Train;

public class TrainManagerTest {
	
	TrainManager trainManager = new TrainManager();
	
	private final static String TRAIN_NUM = "AB111";
	private final static String TRAIN_NUM1 = "AB222";
	private final static String TRAIN_NUM2 = "AB333";
	
	private final static String ARRIVAL_POINT = "Gdank glowny";
	private final static String DEPARTURE_POINT = "Warszawa centralna";
	
	private final static String ARRIVAL_POINT1 = "Krakow";
	private final static String DEPARTURE_POINT1 = "Bialystok";
	
	@Test
	public void checkConnection(){
		assertNotNull(trainManager.getConnection());
	}
	
	@Test
	public void checkAdding(){
		
		Train train = new Train(TRAIN_NUM, DEPARTURE_POINT, ARRIVAL_POINT);
		Train train1 = new Train(TRAIN_NUM1, DEPARTURE_POINT, ARRIVAL_POINT);
		Train train2 = new Train(TRAIN_NUM2, DEPARTURE_POINT, ARRIVAL_POINT);
		
		trainManager.clearTrain();
		assertEquals(1,trainManager.addTrain(train));
		List<Train> trains = trainManager.getAllTrains();
		Train trainRetrieved = trains.get(0);

		assertEquals(TRAIN_NUM, trainRetrieved.getTrainNum());
		assertEquals(ARRIVAL_POINT, trainRetrieved.getArrivalPoint());
		assertEquals(DEPARTURE_POINT, trainRetrieved.getDeparturePoint());
		
		trains = null;
		trainManager.clearTrain();
		
		assertEquals(1,trainManager.addTrain(train));
		assertEquals(1,trainManager.addTrain(train1));
		assertEquals(1,trainManager.addTrain(train2));
		
		trains = trainManager.getAllTrains();
		
		assertEquals(3, trains.size());
	}
	
	@Test
	public void checkDeletion(){
		
		Train train = new Train(TRAIN_NUM, DEPARTURE_POINT, ARRIVAL_POINT);
		Train train1 = new Train(TRAIN_NUM1, DEPARTURE_POINT, ARRIVAL_POINT);
		Train train2 = new Train(TRAIN_NUM2, DEPARTURE_POINT, ARRIVAL_POINT);
		
		trainManager.clearTrain();
		assertEquals(1,trainManager.addTrain(train));
		assertEquals(1,trainManager.addTrain(train1));
		assertEquals(1,trainManager.addTrain(train2));
		trainManager.clearTrain();
		
		List<Train> trains = trainManager.getAllTrains();
		assertEquals(0, trains.size());
	
		assertEquals(1,trainManager.addTrain(train));
		assertEquals(1,trainManager.addTrain(train1));
		assertEquals(1,trainManager.addTrain(train2));
		
		trains = trainManager.getAllTrains();
		assertEquals(3, trains.size());
		
		String trainNum = trains.get(0).getTrainNum();
		long deleted_train = trains.get(0).getId();
		trainManager.deleteOne(deleted_train);
		trains = trainManager.getAllTrains();

		assertEquals(2, trains.size());
		assertTrue(trainManager.checkRecordByUniqueVal(trainNum));		
	}
	
	@Test
	public void checkSelect(){
		
		Train train = new Train(TRAIN_NUM, DEPARTURE_POINT, ARRIVAL_POINT);
		Train train1 = new Train(TRAIN_NUM1, DEPARTURE_POINT, ARRIVAL_POINT);
		Train train2 = new Train(TRAIN_NUM2, DEPARTURE_POINT, ARRIVAL_POINT);
		
		trainManager.clearTrain();
		assertEquals(1,trainManager.addTrain(train));
		assertEquals(1,trainManager.addTrain(train1));
		assertEquals(1,trainManager.addTrain(train2));
		
		List<Train> trains = trainManager.getAllTrains();
		assertEquals(3, trains.size());
		
		assertEquals(TRAIN_NUM, trains.get(0).getTrainNum());
		assertEquals(TRAIN_NUM1, trains.get(1).getTrainNum());
		assertEquals(TRAIN_NUM2, trains.get(2).getTrainNum());
		
		assertFalse(trainManager.checkRecordByUniqueVal(trains.get(0).getTrainNum()));
		assertFalse(trainManager.checkRecordByUniqueVal(trains.get(1).getTrainNum()));
		assertFalse(trainManager.checkRecordByUniqueVal(trains.get(2).getTrainNum()));
	}
	
	@Test
	public void checkUpdate() {

		Train train = new Train(TRAIN_NUM, DEPARTURE_POINT, ARRIVAL_POINT);
		trainManager.clearTrain();
		assertEquals(1, trainManager.addTrain(train));
		List<Train> trains = trainManager.getAllTrains();
		Train trainRetrieved = trains.get(0);

		assertEquals(TRAIN_NUM, trainRetrieved.getTrainNum());
		assertEquals(ARRIVAL_POINT, trainRetrieved.getArrivalPoint());
		assertEquals(DEPARTURE_POINT, trainRetrieved.getDeparturePoint());

		assertEquals(1,
				trainManager.updateRecord(TRAIN_NUM1, DEPARTURE_POINT1, ARRIVAL_POINT1, trainRetrieved.getId()));

		trains = trainManager.getAllTrains();
		trainRetrieved = trains.get(0);

		assertEquals(TRAIN_NUM1, trainRetrieved.getTrainNum());
		assertEquals(ARRIVAL_POINT1, trainRetrieved.getArrivalPoint());
		assertEquals(DEPARTURE_POINT1, trainRetrieved.getDeparturePoint());

	}
}
