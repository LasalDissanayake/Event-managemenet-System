package event.addEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/eventServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class eventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private eventDBUtill eventDBUtill;

    public eventServlet() {
        super();
        this.eventDBUtill = new eventDBUtill();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getServletPath();

        try {
            switch (action) {
                case "/event_new":
                    showNeweventForm(request, response);
                    break;
                case "/event_insert":
                    insertevent(request, response);
                    break;
                case "/event_delete":
                    deleteevent(request, response);
                    break;
                case "/event_edit":
                    showEditeventForm(request, response);
                    break;
                case "/event_update":
                    updateevent(request, response);
                    break;
                case "/event_view":
                    viewevent(request, response);
                    break;
                default:
                    listevent(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showNeweventForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("eventForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertevent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String location = request.getParameter("location");
        String number = request.getParameter("number");
        String description = request.getParameter("description");

        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + "images";
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        String imageUrl = "";
        for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            if (!fileName.equals("")) {
                part.write(savePath + File.separator + fileName);
                imageUrl = "images" + File.separator + fileName;
            }
        }

        event event = new event(title, type, location, number, description, imageUrl);
        eventDBUtill.insertevent(event);
        response.sendRedirect("event_list");
    }

    private void deleteevent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        eventDBUtill.deleteevent(id);
        response.sendRedirect("event_list");
    }

    private void showEditeventForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        event event = eventDBUtill.selectevent(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("eventForm.jsp");
        request.setAttribute("event", event);
        dispatcher.forward(request, response);
    }

    private void updateevent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String location = request.getParameter("location");
        String number = request.getParameter("number");
        String description = request.getParameter("description");

        Part filePart = request.getPart("image");
        String fileName = extractFileName(filePart);

        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + "images";
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        String imageUrl = "";
        if (!fileName.isEmpty()) {
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            String filePath = savePath + File.separator + uniqueFileName;
            filePart.write(filePath);
            imageUrl = "/images/" + uniqueFileName;
        } else {
            imageUrl = request.getParameter("imageUrl");
        }

        event event = new event(id, title, type, location, number, description, imageUrl);
        eventDBUtill.updateevent(event);
        response.sendRedirect("event_list");
    }

    private void listevent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<event> listevent = eventDBUtill.selectAllevent();
        request.setAttribute("listevent", listevent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("eventList.jsp");
        dispatcher.forward(request, response);
    }

    private void viewevent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<event> listevent = eventDBUtill.selectAllevent();
        request.setAttribute("listevent", listevent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("eventView.jsp");
        dispatcher.forward(request, response);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
