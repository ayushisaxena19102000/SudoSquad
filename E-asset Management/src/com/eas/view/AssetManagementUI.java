package com.eas.view;

import java.util.List;
import java.util.Scanner;

import com.eas.business.Asset;
import com.eas.business.AssetService;
import com.eas.business.AssetServiceImpl;
import com.eas.business.Borrower;
import com.eas.business.BorrowerService;
import com.eas.business.BorrowerServiceImpl;
import com.eas.exceptions.AssetNotFoundException;
import com.eas.exceptions.BorrowingException;
import com.eas.exceptions.DatabaseException;
import com.eas.exceptions.BorrowerNotFoundException;
import com.eas.persistence.AssetRepository;
import com.eas.persistence.BorrowerRepository;
import com.eas.persistence.JdbcAssetRepository;
import com.eas.persistence.JdbcBorrowerRepository;

public class AssetManagementUI {
    private final AssetService assetService;
    private final BorrowerService borrowerService;

    public AssetManagementUI(AssetService assetService, BorrowerService borrowerService) {
        this.assetService = assetService;
        this.borrowerService = borrowerService;
    }

    public void run() throws AssetNotFoundException, DatabaseException, BorrowerNotFoundException {
        Scanner scanner = new Scanner(System.in);

        int choice;

        do {
            System.out.println("Asset Management System");
            System.out.println("1. List All Assets");
            System.out.println("2. Add Asset");
            System.out.println("3. Borrow Asset");
            System.out.println("4. Return Asset");
            System.out.println("5. List All Borrowers");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            // Consume any remaining input on the current line
            scanner.nextLine();

            switch (choice) {
                case 1:
                    listAllAssets();
                    break;
                case 2:
                    addAsset(scanner);
                    break;
                case 3:
                    borrowAsset(scanner);
                    break;
                case 4:
                    returnAsset(scanner);
                    break;
                case 5:
                    listAllBorrowers();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close(); // Close the scanner when done
    }

    private void listAllAssets() throws AssetNotFoundException, DatabaseException {
        List<Asset> assets = assetService.getAllAssets();
		System.out.println("List of Assets:");
		for (Asset it : assets) {
		    System.out.println(it);
		}
    }

    private void addAsset(Scanner scanner) throws DatabaseException {
        System.out.print("Enter asset name: ");
        String name = scanner.nextLine();

        System.out.print("Enter asset type: ");
        String type = scanner.nextLine();
        
        System.out.print("Enter asset description: ");
        String desc = scanner.nextLine();
        
        System.out.print("Enter asset date: ");
        String datee = scanner.nextLine();
        
        System.out.print("Enter asset availability: ");
        boolean ava = scanner.nextBoolean();

        Asset asset = new Asset(0, name, type, desc, datee, ava );

        assetService.addAsset(asset);
		System.out.println("Asset added successfully.");
    }

    private void borrowAsset(Scanner scanner) throws BorrowerNotFoundException, DatabaseException {
        System.out.print("Enter asset ID to borrow: ");
        int assetId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter borrower ID: ");
        int borrowerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            assetService.borrowAsset(assetId, borrowerId);
            System.out.println("Asset borrowed successfully.");
        } catch (AssetNotFoundException e) {
            System.out.println("Error: Asset not found.");
        } catch (BorrowingException e) {
            System.out.println("Error: Unable to borrow the asset.");
        }
    }

    private void returnAsset(Scanner scanner) throws DatabaseException {
        System.out.print("Enter asset ID to return: ");
        int assetId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            assetService.returnAsset(assetId);
            System.out.println("Asset returned successfully.");
        } catch (AssetNotFoundException e) {
            System.out.println("Error: Asset not found.");
        }
    }

    private void listAllBorrowers() throws DatabaseException {
        try {
            // Implement the logic to list all borrowers
            List<Borrower> borrowers = borrowerService.getAllBorrowers();
            System.out.println("List of Borrowers:");
            for (Borrower borrower : borrowers) {
                System.out.println(borrower);
            }
        } catch (BorrowerNotFoundException e) {
            System.out.println("Error: Unable to retrieve borrowers from the database.");
        }
    }

    public static void main(String[] args) throws AssetNotFoundException, DatabaseException, BorrowerNotFoundException {
        // Initialize your services and repositories here
        AssetRepository assetRepository = new JdbcAssetRepository(/* Initialize with appropriate database configuration */);
        BorrowerRepository borrowerRepository = new JdbcBorrowerRepository(/* Initialize with appropriate database configuration */);

        AssetService assetService = new AssetServiceImpl(assetRepository);
        BorrowerService borrowerService = new BorrowerServiceImpl(borrowerRepository);

        AssetManagementUI ui = new AssetManagementUI(assetService, borrowerService);
        ui.run();
    }
}
