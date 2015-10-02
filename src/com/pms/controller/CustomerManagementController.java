package com.pms.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pms.model.Customer;
import com.pms.util.ConnectionDB;
import com.pms.util.ConvertDataType;
import com.pms.util.ResultSetMapper;
import com.pms.util.SendResponse;

/**
 * Servlet implementation class CustomerManagementController
 */
@WebServlet("/CustomerManagementController")
public class CustomerManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CustomerManagementController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("method"));
		if("delete".equalsIgnoreCase(request.getParameter("method"))){
			deleteCustomer(request.getParameter("cusID"));
		}else if("insert".equalsIgnoreCase(request.getParameter("method"))){
			insertCustomer( request,  response);
			response.sendRedirect("./customerManagement.html");
		}else if("selectCustomer".equalsIgnoreCase(request.getParameter("method"))){
			selectCustomer(request, response);
		}else if("update".equalsIgnoreCase(request.getParameter("method"))){
			updateCustomer(request, response);
			response.sendRedirect("./customerManagement.html");
		}else{
			getCustomer(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	private void getCustomer(HttpServletRequest request, HttpServletResponse response){
		ResultSetMapper<Customer> cusMapper = new ResultSetMapper<Customer>();
		List<Customer> cusList = cusMapper.mapRersultSetToObject(getCustomerSQL(), Customer.class);
		String resultJson = ConvertDataType.getInstance().objectToJasonArray(cusList);
		SendResponse.getInstance().sendRessponseToView(request, response, resultJson);
	}
	
	private ResultSet getCustomerSQL(){
		String sql = "	SELECT cus.customerID, cus.customerTypeID,	"+
				"	concat(customerFirstName,' ',cus.customerLastName) as 'customerName',	"+
				"	cus.phoneNumber,	"+
				"	concat(address.city,' ',address.country,' ',address.zipcode) as 'address' 	"+
				"	FROM parking_management.customer cus,	"+
				"	parking_management.address address	"+
				"	where cus.addressID = address.addressID ";
		System.out.println(sql);
		return ConnectionDB.getInstance().getData(sql);
	}
	
	private void deleteCustomer(String cusID){
		try {
			String sql = "DELETE FROM parking_management.customer WHERE customerID=?";
			PreparedStatement stmt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(cusID));
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertCustomer(HttpServletRequest request, HttpServletResponse response){
		int addID = insertAddress(request.getParameter("country"), request.getParameter("city"), request.getParameter("zipCode"));
		String sql = "	INSERT INTO `parking_management`.`customer`	"+
				"	(`customerTypeID`, `customerFirstName`, 	"+
				"	`customerLastName`, `addressID`, `gender`, `phoneNumber`) 	"+
				"	VALUES ('1', ?, ?, ?, ?, ?)	";
		try {
			PreparedStatement stmt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
			stmt.setString(1, request.getParameter("cusFirstName"));
			stmt.setString(2, request.getParameter("cusLastName"));
			stmt.setInt(3, addID);
			stmt.setString(4, request.getParameter("gender"));
			stmt.setString(5, request.getParameter("phone"));
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private int insertAddress(String country,String city,String zipcode){
		String sql= "	insert into parking_management.address	"+
				"	(city,country,zipcode)	"+
				"	values(?,?,?)	";
		
		String sql2 = "	select addressID 	"+
				"	from parking_management.address	"+
				"	order by addressID desc	"+
				"	limit 1	";
		
		int addID = 0;
		try {
			PreparedStatement stmt = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
			stmt.setString(1, country);
			stmt.setString(2, city);
			stmt.setInt(3, Integer.parseInt(zipcode));
			addID = stmt.executeUpdate();
			
			ResultSet rs = ConnectionDB.getInstance().getData(sql2);
			if(rs.next()){
				addID = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addID;
	}
	
	private void selectCustomer(HttpServletRequest request, HttpServletResponse response){
		ResultSetMapper<Customer> cusMapper = new ResultSetMapper<Customer>();
		Customer cus = cusMapper.mapRersultSetToObject(selectCustomerSQL(request.getParameter("cusID")), Customer.class).get(0);
		String resultJson = ConvertDataType.getInstance().objectToJasonArray(cus);
		System.out.println(resultJson);
		SendResponse.getInstance().sendRessponseToView(request, response, resultJson);
	}
	
	private ResultSet selectCustomerSQL(String cusID){
		String sql = "	SELECT cus.customerID,	"+
				"	cus.customerTypeID,	"+
				"	cus.customerFirstName,	"+
				"	cus.customerLastName,	"+
				"	cus.phoneNumber,	"+
				"	cus.gender, "+
				"	address.addressID,	"+
				"	address.city,	"+
				"	address.country,	"+
				"	address.zipcode	"+
				"	FROM parking_management.customer cus,	"+
				"	parking_management.address address	"+
				"	where cus.addressID = address.addressID	"+
				"	and cus.customerID = "+cusID;
		return ConnectionDB.getInstance().getData(sql);
	}
	
	private void updateCustomer(HttpServletRequest request, HttpServletResponse response){
		String sql = "	UPDATE `parking_management`.`customer`"+
				"	SET `customerFirstName`= '"+request.getParameter("cusFirstName")+"'"+
				"	, `customerLastName`='"+request.getParameter("cusLastName")+"'"+
				"	, `gender`='"+request.getParameter("gender")+"'"+
				"	, `phoneNumber`='"+request.getParameter("phone")+"'"+
				"	WHERE `customerID`="+request.getParameter("cusID");
		String sql2 = "	UPDATE `parking_management`.`address` 	"+
				"	SET `city`='"+request.getParameter("city")+"'"+
				"	, `country`='"+request.getParameter("country")+"'"+
				"	, `zipcode`='"+request.getParameter("zipCode")+"'"+
				"	WHERE `addressID`="+request.getParameter("addressID");
		try {
			Statement stmt = ConnectionDB.getInstance().getConnection().createStatement();
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
