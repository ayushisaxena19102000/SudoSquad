package com.eas.business;



import com.eas.exceptions.BorrowerNotFoundException;

import java.util.List;

public interface BorrowerService {
    List<Borrower> getAllBorrowers() throws BorrowerNotFoundException;

    Borrower getBorrowerById(int borrowerId) throws BorrowerNotFoundException;

    void addBorrower(Borrower borrower);

    void updateBorrower(Borrower borrower) throws BorrowerNotFoundException;

    void deleteBorrower(int borrowerId) throws BorrowerNotFoundException;
}
