
package com.myApp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myApp.EjbServer.GreeterBean;

import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class callGreeter
 */
@WebServlet("/callGreeter")
public class EjbCaller {
	
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */	
    public EjbCaller() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //@EJB(lookup ="ejb:/AdditionEJB//Addition!com.logic.AdditionRemote")
    @Inject
    private GreeterBean greeter;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("Amira");
		String greeting = greeter.greet(name);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(greeting);		
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
 
	

}
