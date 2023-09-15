package com.eas.business;


import com.eas.exceptions.AssetNotFoundException;
import com.eas.exceptions.BorrowingException;
import java.util.List;

public interface AssetService {
    List<Asset> getAllAssets() throws AssetNotFoundException;

    Asset getAssetById(int assetId) throws AssetNotFoundException;

    void addAsset(Asset asset);

    void updateAsset(Asset asset) throws AssetNotFoundException;

    void deleteAsset(int assetId) throws AssetNotFoundException;

    List<Asset> searchAssets(String keyword);

    void borrowAsset(int assetId, int borrowerId) throws BorrowingException, AssetNotFoundException;

    void returnAsset(int assetId) throws AssetNotFoundException;

    void imposeFine(int borrowerId, double amount, int days);

    List<AssetCategory> getAllAssetCategories();

    void addAssetCategory(AssetCategory category);
}
