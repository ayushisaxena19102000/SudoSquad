package com.eas.business;

import java.util.Objects;

public class AssetCategory {
    private int id;
    private String name;

    public AssetCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

	public String getCategoryName() {
	    return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssetCategory other = (AssetCategory) obj;
		return id == other.id && Objects.equals(name, other.name);
	}


    // Getters and setters for id and name
    
	
}
