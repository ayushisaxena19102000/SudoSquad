package com.eas.persistence;

import com.eas.business.Asset;
import com.eas.business.AssetCategory;
import com.eas.exceptions.AssetNotFoundException;
import com.eas.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.*;


public class JdbcAssetRepository implements AssetRepository {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ams";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "Nw$18330";

    @Override
    public List<Asset> getAllAssets() throws DatabaseException {
        List<Asset> assets = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM assets";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description"); // Retrieve description
                String date = resultSet.getString("date"); // Retrieve date
                boolean isAvailable = resultSet.getBoolean("isAvailable"); // Retrieve isAvailable

                Asset asset = new Asset(id, name, type, description, date, isAvailable);
                assets.add(asset);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while fetching assets from the database.", e);
        }

        return assets;
    }


    @Override
    public Asset getAssetById(int assetId) throws AssetNotFoundException, DatabaseException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM assets WHERE id = ?")) {
            preparedStatement.setInt(1, assetId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description"); // Retrieve description
                String date = resultSet.getString("date"); // Retrieve date
                boolean isAvailable = resultSet.getBoolean("isAvailable"); // Retrieve isAvailable

                Asset asset = new Asset(id, name, type, description, date, isAvailable);
                return asset;
            } else {
                throw new AssetNotFoundException("Asset not found with ID: " + assetId);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while fetching asset by ID from the database.", e);
        }
    }

    // Implement the remaining methods similarly

    @Override
    public void addAsset(Asset asset) throws DatabaseException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO assets (name, type, description, date, isAvailable) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, asset.getName());
            preparedStatement.setString(2, asset.getType());
            preparedStatement.setString(3, asset.getDescription()); // Set description
            
            preparedStatement.setString(4, asset.getdate()); // Set date
            
            preparedStatement.setBoolean(5, asset.isAvailable()); // Set isAvailable

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error while adding an asset to the database.", e);
        }
    }


    @Override
    public void updateAsset(Asset asset) throws DatabaseException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE assets SET name = ?, type = ?, description = ?, date = ?, isAvailable = ? WHERE id = ?")) {
            preparedStatement.setString(1, asset.getName());
            preparedStatement.setString(2, asset.getType());
            preparedStatement.setString(3, asset.getDescription()); // Set description
            preparedStatement.setString(4, asset.getdate()); // Set date
            preparedStatement.setBoolean(5, asset.isAvailable()); // Set isAvailable
            preparedStatement.setInt(6, asset.getId()); // Set ID for the WHERE clause

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new AssetNotFoundException("Asset not found with ID: " + asset.getId());
            }
        } catch (SQLException | AssetNotFoundException e) {
            throw new DatabaseException("Error while updating an asset in the database.", e);
        }
    }


    @Override
    public void deleteAsset(int assetId) throws DatabaseException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM assets WHERE id = ?")) {
            preparedStatement.setInt(1, assetId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new AssetNotFoundException("Asset not found with ID: " + assetId);
            }
        } catch (SQLException | AssetNotFoundException e) {
            throw new DatabaseException("Error while deleting an asset from the database.", e);
        }
    }

    @Override
    public List<Asset> searchAssets(String keyword) throws DatabaseException {
        List<Asset> assets = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM assets WHERE name LIKE ?")) {
            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description"); // Retrieve description
                String date = resultSet.getString("date"); // Retrieve date
                boolean isAvailable = resultSet.getBoolean("isAvailable"); // Retrieve isAvailable

                Asset asset = new Asset(id, name, type, description, date, isAvailable);
                assets.add(asset);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while searching for assets in the database.", e);
        }

        return assets;
    }

    @Override
    public void borrowAsset(int assetId, int borrowerId) throws DatabaseException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement borrowStatement = connection.prepareStatement("INSERT INTO borrows (asset_id, borrower_id, borrow_date, due_date) VALUES (?, ?, ?, ?)");
             PreparedStatement updateAvailabilityStatement = connection.prepareStatement("UPDATE assets SET isAvailable = 0 WHERE id = ?")) {

            // Check if the asset is available
            boolean isAssetAvailable = checkAssetAvailability(assetId);
            if (!isAssetAvailable) {
                throw new DatabaseException("Asset with ID " + assetId + " is not available for borrowing.");
            }

            // Calculate due date (example: due date is 7 days from today)
            java.util.Date currentDate = new java.util.Date();
            long currentTime = currentDate.getTime();
            long dueTime = currentTime + (7 * 24 * 60 * 60 * 1000); // 7 days in milliseconds
            java.util.Date dueDate = new java.util.Date(dueTime);

            // Set parameters for the borrow statement
            borrowStatement.setInt(1, assetId);
            borrowStatement.setInt(2, borrowerId);
            borrowStatement.setDate(3, new java.sql.Date(currentDate.getTime()));
            borrowStatement.setDate(4, new java.sql.Date(dueDate.getTime()));

            // Execute the borrow statement to record the borrowing
            borrowStatement.executeUpdate();

            // Update asset availability (set isAvailable to 0 to mark as unavailable)
            updateAvailabilityStatement.setInt(1, assetId);
            updateAvailabilityStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error while borrowing an asset from the database.", e);
        }
    }


    private boolean checkAssetAvailability(int assetId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT is_available FROM assets WHERE id = ?")) {
            statement.setInt(1, assetId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("is_available");
            }
            return false; // Asset not found
        }
    }

	@Override
    public void returnAsset(int assetId) throws DatabaseException {
        // Implement the logic for returning an asset, update the database accordingly
		try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
		         PreparedStatement returnStatement = connection.prepareStatement("DELETE FROM borrows WHERE asset_id = ?");
		         PreparedStatement updateAvailabilityStatement = connection.prepareStatement("UPDATE assets SET is_available = 1 WHERE id = ?")) {

		        // Check if the asset is currently borrowed
		        boolean isAssetBorrowed = checkAssetBorrowed(assetId);
		        if (!isAssetBorrowed) {
		            throw new DatabaseException("Asset with ID " + assetId + " is not currently borrowed.");
		        }

		        // Set parameter for the return statement
		        returnStatement.setInt(1, assetId);

		        // Execute the return statement
		        returnStatement.executeUpdate();

		        // Update asset availability
		        updateAvailabilityStatement.setInt(1, assetId);
		        updateAvailabilityStatement.executeUpdate();
		    } catch (SQLException e) {
		        throw new DatabaseException("Error while returning an asset to the database.", e);
		    }
    }
	private boolean checkAssetBorrowed(int assetId) throws SQLException {
	    try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
	         PreparedStatement statement = connection.prepareStatement("SELECT asset_id FROM borrows WHERE asset_id = ?")) {
	        statement.setInt(1, assetId);
	        ResultSet resultSet = statement.executeQuery();
	        return resultSet.next();}
	    }// Returns true if asset is found

    @Override
    public void imposeFine(int borrowerId, double amount, int days) throws DatabaseException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement fineStatement = connection.prepareStatement("INSERT INTO fines (borrower_id, fine_amount, fine_due_date) VALUES (?, ?, ?)")) {

            // Calculate fine due date (example: due date is 'days' days from today)
            java.util.Date currentDate = new java.util.Date();
            long currentTime = currentDate.getTime();
            long dueTime = currentTime + (days * 24 * 60 * 60 * 1000); // 'days' days in milliseconds
            java.util.Date fineDueDate = new java.util.Date(dueTime);

            // Set parameters for the fine statement
            fineStatement.setInt(1, borrowerId);
            fineStatement.setDouble(2, amount);
            fineStatement.setDate(3, new java.sql.Date(fineDueDate.getTime()));

            // Execute the fine statement to record the fine
            fineStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error while imposing a fine in the database.", e);
        }
    }

    @Override
    public List<AssetCategory> getAllAssetCategories() throws DatabaseException {
        List<AssetCategory> categories = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM asset_categories";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("id");
                String categoryName = resultSet.getString("category_name");
                // Retrieve other category details as needed from the ResultSet

                AssetCategory category = new AssetCategory(categoryId, categoryName);
                // Populate the category with additional details as needed
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while fetching asset categories from the database.", e);
        }

        return categories;
    }

    @Override
    public void addAssetCategory(AssetCategory category) throws DatabaseException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO asset_categories (category_name) VALUES (?)")) {
            preparedStatement.setString(1, category.getCategoryName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error while adding an asset category to the database.", e);
        }
    }
}
