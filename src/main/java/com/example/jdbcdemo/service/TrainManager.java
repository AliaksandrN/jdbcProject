package com.example.jdbcdemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbcdemo.domain.Train;

public class TrainManager {
	private Connection connection;

	private String url = "jdbc:hsqldb:file:/tmp/projectdb;ifexists=false";

	private String createTableTrain = "CREATE TABLE PUBLIC.Train(id_tr bigint UNIQUE GENERATED BY DEFAULT AS IDENTITY, trainNum varchar(20) NOT NULL UNIQUE, departurePoint varchar(20) NOT NULL, arrivalPoint varchar(20) NOT NULL)";
	private PreparedStatement addTrainStmt;
	private PreparedStatement deleteAllTrainsStmt;
	private PreparedStatement deleteOneTrainStmt;
	private PreparedStatement getAllTrainsStmt;
	private PreparedStatement getIdStmt;
	private PreparedStatement updateStmt;
	private Statement statement;

	public TrainManager() {
		try {
			connection = DriverManager.getConnection(url, "SA", "");
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Train".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists) {
				statement.executeUpdate(createTableTrain);
			}
			addTrainStmt = connection
					.prepareStatement("INSERT INTO Train (trainNum, departurePoint, arrivalPoint) VALUES (?, ?, ?)");
			deleteAllTrainsStmt = connection.prepareStatement("DELETE FROM Train");
			getAllTrainsStmt = connection
					.prepareStatement("SELECT id_tr, trainNum, departurePoint, arrivalPoint FROM Train");
			deleteOneTrainStmt = connection.prepareStatement("DELETE FROM PUBLIC.TRAIN WHERE id_tr = ?");
			getIdStmt = connection.prepareStatement("SELECT id_tr FROM Train WHERE trainNum = ?");
			updateStmt = connection.prepareStatement(
					"UPDATE Train SET trainNum = ?, departurePoint = ?, arrivalPoint = ? where id_tr = ?");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("exception is here!!!");
		}
	}

	Connection getConnection() {
		return connection;
	}

	public void clearTrain() {
		try {
			deleteAllTrainsStmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
			e.printStackTrace();
		}
	}

	public void deleteOne(long idTrain) {
		try {
			deleteOneTrainStmt.setLong(1, idTrain);
			deleteOneTrainStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addTrain(Train train) {
		int count = 0;
		try {
			addTrainStmt.setString(1, train.getTrainNum());
			addTrainStmt.setString(2, train.getDeparturePoint());
			addTrainStmt.setString(3, train.getArrivalPoint());

			count = addTrainStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateRecord(String trainNum, String departurePoint, String arrivalPoint, long id) {
		int i = -1;
		try {
			updateStmt.setString(1, trainNum);
			updateStmt.setString(2, departurePoint);
			updateStmt.setString(3, arrivalPoint);
			updateStmt.setLong(4, id);

			i = updateStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public boolean checkRecordByUniqueVal(String trainNum) {
		ResultSet rs = null;
		boolean b = false;
		try {
			getIdStmt.setString(1, trainNum);
			rs = getIdStmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!rs.isBeforeFirst())
				b = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public List<Train> getAllTrains() {

		List<Train> trains = new ArrayList<Train>();

		try {
			ResultSet rs = getAllTrainsStmt.executeQuery();

			while (rs.next()) {
				Train t = new Train();
				t.setId(rs.getInt("id_tr"));
				t.setTrainNum(rs.getString("trainNum"));
				t.setDeparturePoint(rs.getString("departurePoint"));
				t.setArrivalPoint(rs.getString("arrivalPoint"));
				trains.add(t);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trains;
	}

}
