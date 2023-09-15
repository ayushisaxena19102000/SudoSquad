package com.eas.persistence;


import com.eas.business.Borrower;
import com.eas.exceptions.BorrowerNotFoundException;
import com.eas.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class JdbcBorrowerRepository implements BorrowerRepository {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ams";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "Nw$18330";

    @Override
    public List<Borrower> getAllBorrowers() throws DatabaseException {
        List<Borrower> borrowers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM borrowers";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String telephone = resultSet.getString("telephone");
                String email = resultSet.getString("email");
                // Retrieve other borrower details as needed from the ResultSet

                Borrower borrower = new Borrower(id, name, telephone, email);
                // Populate the borrower with additional details as needed
                borrowers.add(borrower);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while fetching borrowers from the database.", e);
        }

        return borrowers;
    }

    @Override
    public Borrower getBorrowerById(int borrowerId) throws BorrowerNotFoundException, DatabaseException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM borrowers WHERE id = ?")) {
            preparedStatement.setInt(1, borrowerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String telephone = resultSet.getString("telephone");
                String email = resultSet.getString("email");
                // Retrieve other borrower details as needed from the ResultSet

                Borrower borrower = new Borrower(id, name, telephone, email);
                // Populate the borrower with additional details as needed
                return borrower;
            } else {
                throw new BorrowerNotFoundException("Borrower not found with ID: " + borrowerId);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while fetching borrower by ID from the database.", e);
        }
    }

    // Implement the remaining methods similarly

    @Override
    public void addBorrower(Borrower borrower) throws DatabaseException {
        // Implement the logic to insert a new borrower into the database
    	try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    	         PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO borrowers (name, telephone, email) VALUES (?, ?, ?)")) {
    	        preparedStatement.setString(1, borrower.getName());
    	        preparedStatement.setString(2, borrower.getTelephone());
    	        preparedStatement.setString(3, borrower.getEmail());
    	        // Set other borrower details as needed

    	        preparedStatement.executeUpdate();
    	    } catch (SQLException e) {
    	        throw new DatabaseException("Error while adding a borrower to the database.", e);
    	    }
    }

    @Override
    public void updateBorrower(Borrower borrower) throws DatabaseException {
        // Implement the logic to update a borrower in the database
    	try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    	         PreparedStatement preparedStatement = connection.prepareStatement("UPDATE borrowers SET name = ?, telephone = ?, email = ? WHERE id = ?")) {
    	        preparedStatement.setString(1, borrower.getName());
    	        preparedStatement.setString(2, borrower.getTelephone());
    	        preparedStatement.setString(3, borrower.getEmail());
    	        preparedStatement.setInt(4, borrower.getId());
    	        // Set other borrower details as needed

    	        int affectedRows = preparedStatement.executeUpdate();
    	        if (affectedRows == 0) {
    	            throw new BorrowerNotFoundException("Borrower not found with ID: " + borrower.getId());
    	        }
    	    } catch (SQLException | BorrowerNotFoundException e) {
    	        throw new DatabaseException("Error while updating a borrower in the database.", e);
    	    }
    }

    @Override
    public void deleteBorrower(int borrowerId) throws DatabaseException {
        // Implement the logic to delete a borrower from the database
    	 try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    	         PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM borrowers WHERE id = ?")) {
    	        preparedStatement.setInt(1, borrowerId);

    	        int affectedRows = preparedStatement.executeUpdate();
    	        if (affectedRows == 0) {
    	            throw new BorrowerNotFoundException("Borrower not found with ID: " + borrowerId);
    	        }
    	    } catch (SQLException | BorrowerNotFoundException e) {
    	        throw new DatabaseException("Error while deleting a borrower from the database.", e);
    	    }
    }

    @Override
    public List<Borrower> searchBorrowers(String keyword) throws DatabaseException {
        // Implement the logic to search for borrowers in the database
    	List<Borrower> borrowers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM borrowers WHERE name LIKE ? OR telephone LIKE ? OR email LIKE ?")) {
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String telephone = resultSet.getString("telephone");
                String email = resultSet.getString("email");
                // Retrieve other borrower details as needed from the ResultSet

                Borrower borrower = new Borrower(id, name, telephone, email);
                // Populate the borrower with additional details as needed
                borrowers.add(borrower);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while searching for borrowers in the database.", e);
        }

        return borrowers;
		
    }
}
