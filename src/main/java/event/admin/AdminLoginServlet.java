package event.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import event.addEvent.DBConnect;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminLoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Retrieve user input for username and password
        String ruid = request.getParameter("rusername");
        String rpwd = request.getParameter("rpassword");
        
        // Initialize session and connection variables
        HttpSession session = request.getSession();
        Connection con = null;
        RequestDispatcher dispatcher = null;

        try {
            // Get database connection
            con = DBConnect.getConnection();

            // SQL query to validate admin credentials
            String sql = "SELECT * FROM admin WHERE uid = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, ruid);
            pst.setString(2, rpwd);
            
            // Execute query
            ResultSet rs = pst.executeQuery();

            // If the query returns a result, login is successful
            if (rs.next()) {
                // Set session attribute and redirect to admin home page
                session.setAttribute("id", rs.getString("id"));
                dispatcher = request.getRequestDispatcher("adminHome.jsp");
            } else {
                // Set failure message and redirect back to login page
                request.setAttribute("status", "failed");
                dispatcher = request.getRequestDispatcher("adminLogin.jsp");
            }

            // Forward the request to the respective JSP
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // Log the exception and redirect to an error page if needed
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error.");
        } finally {
            // Ensure the connection is properly closed to avoid resource leakage
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
