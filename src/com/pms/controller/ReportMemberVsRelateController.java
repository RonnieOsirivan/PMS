package com.pms.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pms.model.ReportMemberVsRelate;
import com.pms.util.ConnectionDB;
import com.pms.util.ConvertDataType;
import com.pms.util.ResultSetMapper;
import com.pms.util.SendResponse;

/**
 * Servlet implementation class ReportMemberVsRelate
 */
@WebServlet("/ReportMemberVsRelateController")
public class ReportMemberVsRelateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportMemberVsRelateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSetMapper<ReportMemberVsRelate> reportMapper = new ResultSetMapper<ReportMemberVsRelate>();
		List<ReportMemberVsRelate> reportList = reportMapper.mapRersultSetToObject(getDataReportSQL(), ReportMemberVsRelate.class);
		String resultJson = ConvertDataType.getInstance().objectToJasonArray(reportList);
		SendResponse.getInstance().sendRessponseToView(request, response, resultJson);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private ResultSet getDataReportSQL(){
		String sql = "	select ct.customerTypeName as 'customerTypeName', 	"+
				"	date_format(timeIn,'%b %y') as 'month',	"+
				"	sum(serviceCost) as 'sumCost'	"+
				"	from parking_management.bill b,	"+
				"	parking_management.customer c,	"+
				"	parking_management.customerType ct	"+
				"	where b.customerID = c.customerID	"+
				"	and c.customerTypeID = ct.customerTypeID	"+
				"	group by ct.customerTypeName,MONTH(timeIn)	";
		return ConnectionDB.getInstance().getData(sql);
	}
}