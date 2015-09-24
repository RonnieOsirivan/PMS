package com.pms.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pms.model.ReportSummaryIncome;
import com.pms.util.ConnectionDB;
import com.pms.util.ConvertDataType;
import com.pms.util.ResultSetMapper;
import com.pms.util.SendResponse;

/**
 * Servlet implementation class ReportSummaryIncomeController
 */
@WebServlet("/ReportSummaryIncomeController")
public class ReportSummaryIncomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportSummaryIncomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("month") != null){
			getSummaryByMount(request, response);
		}else{
			getReportSummaryIncome(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	private void getReportSummaryIncome(HttpServletRequest request, HttpServletResponse response){
		ResultSetMapper<ReportSummaryIncome> reportMapper = new ResultSetMapper<ReportSummaryIncome>();
		List<ReportSummaryIncome> repotrList = reportMapper.mapRersultSetToObject(getReportSummaryIncomeSQL(), ReportSummaryIncome.class);
		String resultJson = ConvertDataType.getInstance().objectToJasonArray(repotrList);
		SendResponse.getInstance().sendRessponseToView(request, response, resultJson);
	}
	
	private ResultSet getReportSummaryIncomeSQL(){
		String sql = "	select date_format(timeIn,'%b %y') as 'month',	"+
				"	sum(serviceCost) as 'sumIncom'	"+
				"	from parking_management.bill b	"+
				"	where b.status like 'OUT'	"+
				"	group by MONTH(timeIn) ";
		return ConnectionDB.getInstance().getData(sql);
	}
	
	private void getSummaryByMount(HttpServletRequest request, HttpServletResponse response){
		ResultSetMapper<ReportSummaryIncome> reportMapper = new ResultSetMapper<ReportSummaryIncome>();
		List<ReportSummaryIncome> reportList = reportMapper.mapRersultSetToObject(getSummaryByMonthSQL(request.getParameter("month")), ReportSummaryIncome.class);
		String resultJson = ConvertDataType.getInstance().objectToJasonArray(reportList);
		SendResponse.getInstance().sendRessponseToView(request, response, resultJson);
	}
	
	private ResultSet getSummaryByMonthSQL(String month){
		String sql = "	SELECT ct.customerTypeName    AS 'customerTypeName',	"+
				"	  date_format(timeIn,'%b %y') AS 'month',	"+
				"	  SUM(serviceCost)            AS 'sumIncom'	"+
				"	FROM parking_management.bill b,	"+
				"	  parking_management.customer c,	"+
				"	  parking_management.customerType ct	"+
				"	WHERE b.customerID   = c.customerID	"+
				"	AND c.customerTypeID = ct.customerTypeID	"+
				"	AND b.status like 'OUT'	"+
				"	AND date_format(b.timeIn,'%b %y') like '"+month+"'"+
				"	GROUP BY ct.customerTypeName,	"+
				"	  MONTH(timeIn) ";
		return ConnectionDB.getInstance().getData(sql);
	}
}
