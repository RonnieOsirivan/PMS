package com.pms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
	private static Connection con = null;
	private static ConnectionDB connectionDB = null;
	
	private ConnectionDB(){
	}
	
	public static ConnectionDB getInstance(){
		if(connectionDB==null){
			try {
				connectionDB = new ConnectionDB();
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/parking_management", "root", "");
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return connectionDB;
	}
	
	public ResultSet getData(String sql){
		ResultSet result = null;
		Statement statement;
		try {
			statement = con.createStatement();
			result = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
