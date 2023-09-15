package com.eas.business;

import java.util.Objects;

public class Borrower {
    private int id;
    private String name;
    private String role;
    private String telephone;
    private String email;

    // Constructors, getters, setters, and other methods

    public Borrower(int id, String name, String role, String telephone) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.telephone = telephone;
        this.email = email;
    }

    // Getter and setter methods for properties
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, name, role, telephone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrower other = (Borrower) obj;
		return Objects.equals(email, other.email) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(role, other.role) && Objects.equals(telephone, other.telephone);
	}

	@Override
	public String toString() {
		return "Borrower [id=" + id + ", name=" + name + ", role=" + role + ", telephone=" + telephone + ", email="
				+ email + "]";
	}

  
    
}
