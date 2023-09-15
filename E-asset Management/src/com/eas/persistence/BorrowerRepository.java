package com.eas.persistence;

import com.eas.business.Borrower;
import com.eas.exceptions.BorrowerNotFoundException;
import com.eas.exceptions.DatabaseException;

import java.util.List;

public interface BorrowerRepository {
    List<Borrower> getAllBorrowers() throws DatabaseException;

    Borrower getBorrowerById(int borrowerId) throws BorrowerNotFoundException, DatabaseException;

    void addBorrower(Borrower borrower) throws DatabaseException;

    void updateBorrower(Borrower borrower) throws DatabaseException;

    void deleteBorrower(int borrowerId) throws DatabaseException;

	List<Borrower> searchBorrowers(String keyword) throws DatabaseException;
}
