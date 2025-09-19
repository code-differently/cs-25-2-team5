package com.team5.cbl.cbl_app.objects;

import com.team5.cbl.cbl_app.enums.CompanyName;
import java.util.ArrayList;
import java.util.List;

public class Publisher {
  private CompanyName companyName;
  private final List<String> characters; // Make final

  // Constructor with company name only
  public Publisher(CompanyName companyName) {
    this.companyName = companyName;
    this.characters = new ArrayList<>();
  }

  // Constructor with company name and characters list
  public Publisher(CompanyName companyName, List<String> characters) {
    this.companyName = companyName;
    this.characters = new ArrayList<>();
    if (characters != null) {
      this.characters.addAll(characters);
    }
  }

  // Getter for company name
  public CompanyName getCompanyName() {
    return companyName;
  }

  // Setter for company name
  public void setCompanyName(CompanyName companyName) {
    this.companyName = companyName;
  }

  // Getter for characters (returns defensive copy)
  public List<String> getCharacters() {
    return new ArrayList<>(characters);
  }

  // Setter for characters (clears and repopulates existing list)
  public void setCharacters(List<String> characters) {
    this.characters.clear();
    if (characters != null) {
      this.characters.addAll(characters);
    }
  }

  // Add a character if it doesn't already exist
  public void addCharacter(String character) {
    if (character != null && !characters.contains(character)) {
      characters.add(character);
    }
  }

  // Check if publisher has a specific character
  public boolean hasCharacter(String character) {
    return character != null && characters.contains(character);
  }

  @Override
  public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
	result = prime * result + ((characters == null) ? 0 : characters.hashCode());
	return result;
  }

  @Override
  public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Publisher other = (Publisher) obj;
	if (companyName != other.companyName)
		return false;
	if (characters == null) {
		if (other.characters != null)
			return false;
	} else if (!characters.equals(other.characters))
		return false;
	return true;
  }

  
}
