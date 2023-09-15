package com.eas.persistence;

import com.eas.business.Asset;
import com.eas.business.AssetCategory;
import com.eas.exceptions.AssetNotFoundException;
import com.eas.exceptions.DatabaseException;

import java.util.List;

public interface AssetRepository {
    List<Asset> getAllAssets() throws DatabaseException, AssetNotFoundException;

    Asset getAssetById(int assetId) throws AssetNotFoundException, DatabaseException;

    void addAsset(Asset asset) throws DatabaseException;

    void updateAsset(Asset asset) throws DatabaseException;

    void deleteAsset(int assetId) throws DatabaseException;

    List<Asset> searchAssets(String keyword) throws DatabaseException;

    void borrowAsset(int assetId, int borrowerId) throws DatabaseException;

    void returnAsset(int assetId) throws DatabaseException;

    void imposeFine(int borrowerId, double amount, int days) throws DatabaseException;

    List<AssetCategory> getAllAssetCategories() throws DatabaseException;

    void addAssetCategory(AssetCategory category) throws DatabaseException;
}
