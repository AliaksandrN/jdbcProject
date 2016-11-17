package com.example.jdbcdemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbcdemo.domain.Ticket;

public class TicketManager implements IManager {
	private Connection connection;

	private String url = "jdbc:hsqldb:file:/tmp/projectdb;ifexists=false";
	private String createTableTicket = "CREATE TABLE PUBLIC.Ticket(id_ti bigint GENERATED BY DEFAULT AS IDENTITY, firstClassPrice decimal(10,2) NOT NULL, secondClassPrice decimal(10,2) NOT NULL, id_train bigint NOT NULL)";
	private String addFkToTicket = "ALTER TABLE PUBLIC.Ticket ADD FOREIGN KEY (id_train) REFERENCES PUBLIC.Train(id_tr)";
	private PreparedStatement addStmt;
	private PreparedStatement deleteAllStmt;
	private PreparedStatement getAlltsStmt;
	private PreparedStatement deleteOneStmt;
	private PreparedStatement checkIdStmt;
	private PreparedStatement updateStmt;

	Statement statement;

	public TicketManager() {
		try {
			connection = DriverManager.getConnection(url, "SA", "");
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Ticket".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists) {
				statement.executeUpdate(createTableTicket);
				statement.executeUpdate(addFkToTicket);
			}
			addStmt = connection
					.prepareStatement("INSERT INTO Ticket (firstClassPrice, secondClassPrice, id_train) VALUES (?, ?, ?)");
			deleteAllStmt = connection
					.prepareStatement("DELETE FROM Ticket");
			getAlltsStmt = connection
					.prepareStatement("SELECT id_ti, firstClassPrice, secondClassPrice, id_train FROM Ticket");
			deleteOneStmt = connection
					.prepareStatement("DELETE FROM Ticket WHERE id_ti = ? ");
			checkIdStmt = connection
					.prepareStatement("SELECT firstClassPrice FROM Ticket where id_ti = ?");
			updateStmt = connection
					.prepareStatement("UPDATE Ticket SET firstClassPrice = ?, secondClassPrice = ?, id_train = ? WHERE id_ti = ?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public void clear() {
		try {
			deleteAllStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOne(long id) {
		try {
			deleteOneStmt.setLong(1, id);
			deleteOneStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int add(Ticket ticket) {
		int count = 0;
		try {

			addStmt.setDouble(1, ticket.getFirstClassPrice());
			addStmt.setDouble(2, ticket.getSecondClassPrice());
			addStmt.setLong(3, ticket.getId_train());
			count = addStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateRecord(Double firstClassPrice, Double secondClassPrice, long fk, long id) {
		int i = -1;
		try {
			updateStmt.setDouble(1, firstClassPrice);
			updateStmt.setDouble(2, secondClassPrice);
			updateStmt.setLong(3, fk);
			updateStmt.setLong(4, id);

			i = updateStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public boolean checkRecordByUniqueVal(long id) {
		ResultSet rs = null;
		boolean b = false;
		try {
			checkIdStmt.setLong(1, id);
			rs = checkIdStmt.executeQuery();

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

	@Override
	public List<Ticket> getAll() {
		List<Ticket> tickets = new ArrayList<Ticket>();

		try {
			ResultSet rs = getAlltsStmt.executeQuery();

			while (rs.next()) {
				Ticket t = new Ticket();
				t.setId(rs.getInt("id_ti"));
				t.setFirstClassPrice(rs.getDouble("firstClassPrice"));
				t.setSecondClassPrice(rs.getDouble("secondClassPrice"));
				t.setId_train(rs.getLong("id_train"));
				tickets.add(t);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tickets;
	}
}