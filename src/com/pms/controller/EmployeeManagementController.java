package com.pms.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pms.model.Employee;
import com.pms.util.ConnectionDB;
import com.pms.util.ConvertDataType;
import com.pms.util.ResultSetMapper;
import com.pms.util.SendResponse;

/**
 * Servlet implementation class EmployeeManagement
 */
@WebServlet("/EmployeeManagementController")
public class EmployeeManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeManagementController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getEmployee(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	private void getEmployee(HttpServletRequest request, HttpServletResponse response){
		ResultSetMapper<Employee> employeeMap = new ResultSetMapper<Employee>();
		List<Employee> employeeList = employeeMap.mapRersultSetToObject(getEmployeeSQL(), Employee.class);
		String resultJson = ConvertDataType.getInstance().objectToJasonArray(employeeList);
		SendResponse.getInstance().sendRessponseToView(request, response, resultJson);
	}
	
	private ResultSet getEmployeeSQL(){
		String sql = "select * from employee";
		return ConnectionDB.getInstance().getData(sql);
	}
}