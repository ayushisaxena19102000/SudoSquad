package com.eas.business;

import java.util.Date;
import java.util.Objects;

public class Asset {
    private int id;
    private String name;
    private String type;
    private String description;
    private String dateAdded;
    private boolean isAvailable;

    // Constructors, getters, setters, and other methods

    public Asset(int id, String name, String type, String description, String dateAdded, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.dateAdded = dateAdded;
        this.isAvailable = isAvailable;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getdate() {
		return dateAdded;
	}

	public void setdate(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(dateAdded, description, id, isAvailable, name, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asset other = (Asset) obj;
		return Objects.equals(dateAdded, other.dateAdded) && Objects.equals(description, other.description)
				&& id == other.id && isAvailable == other.isAvailable && Objects.equals(name, other.name)
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Asset [id=" + id + ", name=" + name + ", type=" + type + ", description=" + description + ", dateAdded="
				+ dateAdded + ", isAvailable=" + isAvailable + "]";
	}
	
    
	
}
