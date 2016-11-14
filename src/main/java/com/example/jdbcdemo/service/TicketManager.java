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

public class TicketManager {
	private Connection connection;
	
	private String url = "jdbc:hsqldb:file:/home/aleksander/jdbcDb/newdb;ifexists=false";
	
	private String createTableTicket = "CREATE TABLE Ticket(id_ti bigint GENERATED BY DEFAULT AS IDENTITY, firstClassPrice decimal(10,2) NOT NULL, secondClassPrice decimal(10,2) NOT NULL, id_train bigint NOT NULL, FOREIGN KEY (id_train) REFERENCES Train(id_tr) ON DELETE CASCADE ON UPDATE CASCADE)";//, CONSTRAINT fk_t_t FOREIGN KEY (id_train) REFERENCES Train(id))";

	private PreparedStatement addTicketStmt;
	private PreparedStatement deleteAllTicketsStmt;
	private PreparedStatement getAllTicketsStmt;
	
	Statement statement;
	
	public TicketManager() {
		try {
			connection = DriverManager.getConnection(url,"SA","");
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Ticket".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					break;
				}
			}

			if (!tableExists)
			statement.executeUpdate(createTableTicket);
			
			addTicketStmt = connection
					.prepareStatement("INSERT INTO Ticket (firstClassPrice, secondClassPrice, id_train) VALUES (?, ?, ?)");
			deleteAllTicketsStmt = connection
					.prepareStatement("DELETE FROM Ticket");
			getAllTicketsStmt = connection
					.prepareStatement("SELECT id_ti, firstClassPrice, secondClassPrice, id_train FROM Ticket");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}
	void clearTicket() {
		try {
			deleteAllTicketsStmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addTicket(Ticket ticket) {
		int count = 0;
		try {
			
			addTicketStmt.setDouble(1, ticket.getFirstClassPrice());
			addTicketStmt.setDouble(2, ticket.getSecondClassPrice());
			addTicketStmt.setLong(3, ticket.getId_train());
			count = addTicketStmt.executeUpdate();			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Ticket> getAllTickets() {
		List<Ticket> tickets = new ArrayList<Ticket>();

		try {
			ResultSet rs = getAllTicketsStmt.executeQuery();

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