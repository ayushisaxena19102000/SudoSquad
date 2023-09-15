package com.eas.business;

import com.eas.business.AssetCategory;

import com.eas.exceptions.AssetNotFoundException;
import com.eas.exceptions.BorrowerNotFoundException;
import com.eas.exceptions.BorrowingException;
import com.eas.exceptions.DatabaseException;
import com.eas.persistence.AssetRepository;

import java.util.List;

public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public List<Asset> getAllAssets() throws AssetNotFoundException {
        try {
            List<Asset> assets = assetRepository.getAllAssets();
            
            if (assets.isEmpty()) {
                throw new AssetNotFoundException("No assets found in the database.");
            }
            
            return assets;
        } catch (DatabaseException | AssetNotFoundException e) {
            // Handle the exception 
            throw new AssetNotFoundException("Error while fetching assets from the database.", e);
        }
    }



    public Asset getAssetById(int assetId) throws AssetNotFoundException {
        Asset asset = null;
		try {
			asset = assetRepository.getAssetById(assetId);
		} catch (AssetNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (asset == null) {
            throw new AssetNotFoundException("Asset not found with ID: " + assetId);
        }
        return asset;
    }

    @Override
    public void addAsset(Asset asset) {
        try {
			assetRepository.addAsset(asset);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void updateAsset(Asset asset) throws AssetNotFoundException {
        Asset existingAsset = getAssetById(asset.getId());
        if (existingAsset == null) {
            throw new AssetNotFoundException("Asset not found with ID: " + asset.getId());
        }
        try {
			assetRepository.updateAsset(asset);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void deleteAsset(int assetId) throws AssetNotFoundException {
        Asset existingAsset = getAssetById(assetId);
        if (existingAsset == null) {
            throw new AssetNotFoundException("Asset not found with ID: " + assetId);
        }
        try {
			assetRepository.deleteAsset(assetId);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public List<Asset> searchAssets(String keyword) {
        try {
			return assetRepository.searchAssets(keyword);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    @Override
    public void borrowAsset(int assetId, int borrowerId) throws AssetNotFoundException, BorrowingException {
        Asset asset = getAssetById(assetId);
        if (asset == null) {
            throw new AssetNotFoundException("Asset not found with ID: " + assetId);
        }
        // Implement the borrowing logic and handle exceptions
    }

    @Override
    public void returnAsset(int assetId) throws AssetNotFoundException {
        Asset asset = getAssetById(assetId);
        if (asset == null) {
            throw new AssetNotFoundException("Asset not found with ID: " + assetId);
        }
        // Implement the return logic and handle exceptions
    }

    @Override
    public void imposeFine(int borrowerId, double amount, int days) {
        // Implement fine logic
    }

    @Override
    public List<AssetCategory> getAllAssetCategories() {
        try {
			return assetRepository.getAllAssetCategories();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    @Override
    public void addAssetCategory(AssetCategory category) {
        try {
			assetRepository.addAssetCategory(category);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

}
