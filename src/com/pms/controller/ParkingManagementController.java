package com.pms.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		getParkingInfo(request, response);
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

}
