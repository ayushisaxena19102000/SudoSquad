package com.eas.business;

import com.eas.exceptions.BorrowerNotFoundException;
import com.eas.exceptions.DatabaseException;
import com.eas.persistence.BorrowerRepository;

import java.util.List;

public class BorrowerServiceImpl implements BorrowerService {
    private final BorrowerRepository borrowerRepository;

    public BorrowerServiceImpl(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public List<Borrower> getAllBorrowers() {
        try {
			return borrowerRepository.getAllBorrowers();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    @Override
    public Borrower getBorrowerById(int borrowerId) throws BorrowerNotFoundException {
        Borrower borrower = null;
		try {
			borrower = borrowerRepository.getBorrowerById(borrowerId);
		} catch (BorrowerNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (borrower == null) {
            throw new BorrowerNotFoundException("Borrower not found with ID: " + borrowerId);
        }
        return borrower;
    }

    @Override
    public void addBorrower(Borrower borrower) {
        try {
			borrowerRepository.addBorrower(borrower);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void updateBorrower(Borrower borrower) throws BorrowerNotFoundException {
        Borrower existingBorrower = getBorrowerById(borrower.getId());
        if (existingBorrower == null) {
            throw new BorrowerNotFoundException("Borrower not found with ID: " + borrower.getId());
        }
        try {
			borrowerRepository.updateBorrower(borrower);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void deleteBorrower(int borrowerId) throws BorrowerNotFoundException {
        Borrower existingBorrower = getBorrowerById(borrowerId);
        if (existingBorrower == null) {
            throw new BorrowerNotFoundException("Borrower not found with ID: " + borrowerId);
        }
        try {
			borrowerRepository.deleteBorrower(borrowerId);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
