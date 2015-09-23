package com.pms.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pms.model.Bill;
import com.pms.model.Park;
import com.pms.util.ConnectionDB;
import com.pms.util.ConvertDataType;
import com.pms.util.ResultSetMapper;
import com.pms.util.SendResponse;

/**
 * Servlet implementation class ParkingManagementController
 */
@WebServlet("/ParkingManagementController")
public class ParkingManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParkingManagementController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("IN".equals(request.getParameter("status"))){
			ProcessIn(request.getParameter("cusID"), request.getParameter("parkingNum"), request.getParameter("status"));
		}else if("OUT".equals(request.getParameter("status"))){
			processOut(request, response);
		}else{
			getParkingInfo(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void getParkingInfo(HttpServletRequest request, HttpServletResponse response){
		ResultSetMapper<Park> parkMapper = new ResultSetMapper<Park>();
		List<Park> parkList = parkMapper.mapRersultSetToObject(getParkingInfoSQL(), Park.class);
		String resultJson = ConvertDataType.getInstance().objectToJasonArray(parkList);
		SendResponse.getInstance().sendRessponseToView(request, response, resultJson);
	}
	
	private ResultSet getParkingInfoSQL(){
		String sql = "SELECT * "+
				"	FROM parking_management.park	"+
				"	order by status asc	";
		return ConnectionDB.getInstance().getData(sql);
	}
	
	private void ProcessIn(String cusID,String parkingNum,String status){
		String sql = "	INSERT INTO `parking_management`.`bill` 	"+
				"	(`parkingNumber`, `customerID`, `employeeID`, `status`) "+
				"	VALUES (?, ?, '1', 'IN') ";
		
		String sql2 = "	update parking_management.park 	"+
				"	set status = 'Not Available' 	"+
				"	where parkingNumber= ?";
		
		Connection con = ConnectionDB.getInstance().getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1,Integer.parseInt(parkingNum));
			stmt.setInt(2, Integer.parseInt(cusID));
			stmt.execute();
			stmt.close();
			
			stmt = con.prepareStatement(sql2);
			stmt.setInt(1, Integer.parseInt(parkingNum));
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void processOut(HttpServletRequest request, HttpServletResponse response){
		ResultSetMapper<Bill> billMapper = new ResultSetMapper<Bill>();
		
		ResultSet rs = getBillSQL(request.getParameter("billNum"));
		try {
			if(rs.next()){
				Bill bill = billMapper.mapRersultSetToObject(getBillSQL(request.getParameter("billNum")), Bill.class).get(0);
				updateToAvailable(bill.getParkingNum());
				Date timeOut = new Date();
				int serviceHours = (int) (timeOut.getTime() - bill.getTimeIn().getTime())/(1000*60*60);
				double serviceCost;
				if(serviceHours > 0){
					serviceCost = serviceHours * bill.getCostPerHour();
				}else{
					serviceCost = bill.getCostPerHour();
				}
				
				if("OUT".equals(bill.getStatus())){
					SendResponse.getInstance().sendRessponseToView(request, response, "aleadySuccess");
				}else{
					updateBill(timeOut, serviceHours, request.getParameter("billNum"),serviceCost);
					bill = billMapper.mapRersultSetToObject(getBillSQL(request.getParameter("billNum")), Bill.class).get(0);
					String jsonResult = ConvertDataType.getInstance().objectToJasonArray(bill);
					SendResponse.getInstance().sendRessponseToView(request, response, jsonResult);
				}
			}else{
				SendResponse.getInstance().sendRessponseToView(request, response, "empty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private ResultSet getBillSQL(String billNum){
		String sql = "	select b.billNumber,b.parkingNumber,	"+
				"	b.customerID,b.employeeID,	"+
				"	b.timeIn,b.timeOut,	"+
				"	b.hoursService,	"+
				"	b.status,b.serviceCost,	"+
				"	concat(c.customerFirstName ,' ',c.customerLastName) as 'customerName',	"+
				"	concat(emp.employeeFirstName,' ',emp.employeeLastName) as 'employeeName',	"+
				"	ct.costPerHour	"+
				"	from parking_management.bill b, 	"+
				"	parking_management.customer c, 	"+
				"	parking_management.customerType ct,	"+
				"	parking_management.employee emp	"+
				"	where b.customerID = c.customerID	"+
				"	and b.employeeID = emp.employeeID	"+
				"	and c.customerTypeID = ct.customerTypeID	"+
				"	and b.billNumber = "+billNum;
		return ConnectionDB.getInstance().getData(sql);
	}
	
	private void updateToAvailable(int parkingNumber){
		String sql = "	update parking_management.park 	"+
				"	set status = 'Available' 	"+
				"	where parkingNumber= ?";
		Connection con = ConnectionDB.getInstance().getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, parkingNumber);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updateBill(Date timeOut, int serviceHours,String billNumber,double serviceCose){
		String sql = "update parking_management.bill "+
				"	set timeOut = ? , hoursService = ? , status = ? ,serviceCost = ?"+
				"	where billNumber = ? ";
		Connection con = ConnectionDB.getInstance().getConnection();
		try {
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(timeOut.getTime());
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setTimestamp(1, sqlDate);
			stmt.setInt(2, serviceHours);
			stmt.setString(3, "OUT");
			stmt.setDouble(4, serviceCose);
			stmt.setString(5, billNumber);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}