package event.addEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import event.addEvent.DBConnect;

public class eventDBUtill {
	
	private static final String INSERT_EVENT_SQL = "INSERT INTO event (title, type, location, number, description, imageUrl) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_EVENT_BY_ID_SQL = "SELECT * FROM event WHERE ID=?";
    private static final String SELECT_ALL_EVENT_SQL = "SELECT * FROM event";
    private static final String UPDATE_EVENT_SQL = "UPDATE event SET title=?, type=?, location=?, number=?, description=?, imageUrl=? WHERE ID=?";
    private static final String DELETE_EVENT_SQL = "DELETE FROM event WHERE ID=?";

    public void insertevent(event event) throws SQLException {
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_EVENT_SQL)) {
            preparedStatement.setString(1, event.getTitle());
            preparedStatement.setString(2, event.getType());
            preparedStatement.setString(3, event.getLocation());
            preparedStatement.setString(4, event.getNumber());
            preparedStatement.setString(5, event.getDescription());
            preparedStatement.setString(6, event.getImageUrl());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateevent(event event) throws SQLException {
        boolean rowUpdated = false;
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_EVENT_SQL)) {
            preparedStatement.setString(1, event.getTitle());
            preparedStatement.setString(2, event.getType());
            preparedStatement.setString(3, event.getLocation());
            preparedStatement.setString(4, event.getNumber());
            preparedStatement.setString(5, event.getDescription());
            preparedStatement.setString(6, event.getImageUrl());
            preparedStatement.setInt(7, event.getId());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public event selectevent(int id) {
        event event = null;
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_EVENT_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String title = rs.getString("Title");
                String type = rs.getString("type");
                String location = rs.getString("location");
                String number = rs.getString("number");
                String description = rs.getString("description");
                String imageUrl = rs.getString("imageUrl");

                event = new event(id, title, type, location, number, description, imageUrl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    public List<event> selectAllevent() {
        List<event> event = new ArrayList<>();
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_EVENT_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String title = rs.getString("Title");
                String type = rs.getString("type");
                String location = rs.getString("location");
                String number = rs.getString("number");
                String description = rs.getString("description");
                String imageUrl = rs.getString("imageUrl");

                event.add(new event(id, title, type, location, number, description, imageUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    public boolean deleteevent(int id) throws SQLException {
        boolean rowDeleted = false;
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_EVENT_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

}
