-- Create User table

use ams
CREATE TABLE `borrowers` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Role ENUM('Admin', 'Borrower') NOT NULL,
    Telephone VARCHAR(15),
    Email VARCHAR(255) ,
    Username VARCHAR(50) UNIQUE,
    Password VARCHAR(255) NOT NULL
);

-- Create Asset table
CREATE TABLE assets (
    id INT AUTO_INCREMENT PRIMARY KEY, -- Rename to id for consistency
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    description TEXT, -- Keep the same column name
	date varchar(255) NOT NULL, -- Keep the same column name
    isAvailable BOOLEAN NOT NULL,
    borrowerID INT, -- Rename to borrowerID
    returnDate DATE, -- Keep the same column name
    FOREIGN KEY (borrowerID) REFERENCES User(UserID)
);



INSERT INTO assets(name, type, description, date, isAvailable, borrowerID, returnDate)
VALUES
    ('Asset 1', 'Type A', 'Description of Asset 1', '2023-09-14', true, NULL, NULL),
    ('Asset 2', 'Type B', 'Description of Asset 2', '2023-09-14', true, NULL, NULL),
    ('Asset 3', 'Type A', 'Description of Asset 3', '2023-09-14', true, NULL, NULL);

INSERT INTO Borrower (Name, Role, Telephone, Email, Username, Password)
VALUES
    ('John Doe', 'Admin', '123-456-7890', 'john.doe@example.com', 'johndoe', 'password123'),
    ('Jane Smith', 'Borrower', '987-654-3210', 'jane.smith@example.com', 'janesmith', 'securepwd'),
    ('Robert Johnson', 'Borrower', NULL, 'robert.johnson@example.com', 'robertjohnson', 'strongpassword');
INSERT INTO `borrowers` (Name, Role, Telephone, Email, Username, Password)
VALUES
    ('John Doe', 'Admin', '123-456-7890', 'john.doe@example.com', 'johndoe', 'password123'),
    ('Jane Smith', 'Borrower', '987-654-3210', 'jane.smith@example.com', 'janesmith', 'securepwd'),
    ('Robert Johnson', 'Borrower', NULL, 'robert.johnson@example.com', 'robertjohnson', 'strongpassword');




CREATE TABLE borrows (
    id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT NOT NULL,
    borrower_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    FOREIGN KEY (asset_id) REFERENCES assets(id),
    FOREIGN KEY (borrower_id) REFERENCES borrowers(id)
);



