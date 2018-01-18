package com.student.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Driver;

public class ViewAllStudents extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
			PrintWriter out=resp.getWriter();
			resp.setContentType("text/html");
			
			Connection con=null;
			Statement stmt=null;
			ResultSet rs=null;
			
			
			try {
				//1.load the driver
				Driver driverRef=new Driver();
				DriverManager.registerDriver(driverRef);
				
				//2.Get the connection via driver
				String dbUrl="jdbc:mysql://localhost:3306/becme_db?user=j2ee&password=j2ee";
				con = DriverManager.getConnection(dbUrl);
				
				//3.Issue sql query
				String query="select * from student_info si, guardian_info gi, student_otherinfo so "
							+ "where si.regno=gi.regno and gi.regno=so.regno";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				
				//4. Process results
				out.print("<html>");
				out.print("<body>");
				out.print("<table border=\"1\">");
				out.print("<tr><th>Regno</th><th>First Name</th><th>Middle Name</th><th>Last Name</th>"
						+"<th>Guardian first name</th><th>Guardian middle name</th>"
						+"<th>Guardian last name</th><th>isAdmin</th><th>Password</th></tr>");
				
				while(rs.next()) {
					
					int regno = rs.getInt("regno");
					String fname = rs.getString("firstname");
					String mname = rs.getString("middlename");
					String lname = rs.getString("lastname");
					String gfname = rs.getString("gfname");
					String gmname = rs.getString("gmname");
					String glname = rs.getString("glname");
					String isAdmin = rs.getString("isAdmin");
					String pass = rs.getString("password");
					
					out.print("<tr><td>"+regno+"</td><td>"+fname+"</td><td>"+mname+"</td><td>"
							+lname+"</td><td>"+gfname+"</td><td>"+gmname+"</td><td>"
							+glname+"</td><td>"+isAdmin+"</td><td>"+pass+"</td></tr>");
				}
				out.print("</table>");
				out.print("</body>");
				out.print("</html>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(con!=null) {
						con.close();
					}
					if(stmt!=null) {
						stmt.close();
					}
					if(rs!=null) {
						rs.close();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
	}
}
