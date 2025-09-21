/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl.cbl_app.objects;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Genre;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vscode
 */
public class Comic implements Comparable<Comic> {

  private String title;
  private List<Genre> genre;
  private RarityDetails rarityDetails;
  private List<Creator> creativeTeam;
  private Character leadingCharacter;
  private Publisher publisher;
  private Integer numberOfIssues;

  // Main constructor with creative team
  public Comic(
      String title,
      List<Genre> genre,
      Integer numberOfIssues,
      RarityDetails rarityDetails,
      List<Creator> creativeTeam,
      Character leadingCharacter,
      Publisher publisher) {
    this.genre = genre;
    this.title = title;
    this.rarityDetails = rarityDetails;
    this.creativeTeam = creativeTeam != null ? new ArrayList<>(creativeTeam) : new ArrayList<>();
    this.leadingCharacter = leadingCharacter;
    this.publisher = publisher;
    this.numberOfIssues = numberOfIssues;
  }

  // Constructor for backward compatibility with single creator
  public Comic(
      String title,
      List<Genre> genre,
      Integer numberOfIssues,
      RarityDetails rarityDetails,
      Creator headWriter,
      Character leadingCharacter,
      Publisher publisher) {
    this.genre = genre;
    this.title = title;
    this.rarityDetails = rarityDetails;
    this.creativeTeam = new ArrayList<>();
    if (headWriter != null) {
      this.creativeTeam.add(headWriter);
    }
    this.leadingCharacter = leadingCharacter;
    this.publisher = publisher;
    this.numberOfIssues = numberOfIssues;
  }

  // Simple constructor
  public Comic(
      String title, Creator headWriter, RarityDetails rarityDetails, Character leadingCharacter) {
    this.title = title;
    this.creativeTeam = new ArrayList<>();
    if (headWriter != null) {
      this.creativeTeam.add(headWriter);
    }
    this.rarityDetails = rarityDetails;
    this.leadingCharacter = leadingCharacter;
    this.genre = new ArrayList<>();
    this.publisher = new Publisher(CompanyName.INDIE);
    this.numberOfIssues = 1;
  }

  public String getTitle() {
    return title;
  }

  public List<Genre> getGenre() {
    return genre;
  }

  public RarityDetails getRarityDetails() {
    return rarityDetails;
  }

  @Override
  public int compareTo(Comic other) {
    return Double.compare(this.rarityDetails.getGrade(), other.getRarityDetails().getGrade());
  }

  // Backward compatibility method - returns first creator or null
  public Creator getHeadWriter() {
    return creativeTeam.isEmpty() ? null : creativeTeam.get(0);
  }

  // New method to get the full creative team
  public List<Creator> getCreativeTeam() {
    return new ArrayList<>(creativeTeam);
  }

  // Method to add a creator to the team
  public void addCreator(Creator creator) {
    if (creator != null && !creativeTeam.contains(creator)) {
      creativeTeam.add(creator);
    }
  }

  // Method to remove a creator from the team
  public void removeCreator(Creator creator) {
    creativeTeam.remove(creator);
  }

  public Character getLeadingCharacter() {
    return leadingCharacter;
  }

  public Publisher getPublisher() {
    return publisher;
  }

  public Integer getNumberOfIssues() {
    return numberOfIssues;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Comic comic = (Comic) obj;

    if (title != null ? !title.equals(comic.title) : comic.title != null) return false;
    if (genre != null ? !genre.equals(comic.genre) : comic.genre != null) return false;
    if (rarityDetails != null
        ? !rarityDetails.equals(comic.rarityDetails)
        : comic.rarityDetails != null) return false;
    if (creativeTeam != null
        ? !creativeTeam.equals(comic.creativeTeam)
        : comic.creativeTeam != null) return false;
    if (leadingCharacter != null
        ? !leadingCharacter.equals(comic.leadingCharacter)
        : comic.leadingCharacter != null) return false;
    if (publisher != null ? !publisher.equals(comic.publisher) : comic.publisher != null)
      return false;
    return numberOfIssues != null
        ? numberOfIssues.equals(comic.numberOfIssues)
        : comic.numberOfIssues == null;
  }

  @Override
  public int hashCode() {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + (genre != null ? genre.hashCode() : 0);
    result = 31 * result + (rarityDetails != null ? rarityDetails.hashCode() : 0);
    result = 31 * result + (creativeTeam != null ? creativeTeam.hashCode() : 0);
    result = 31 * result + (leadingCharacter != null ? leadingCharacter.hashCode() : 0);
    result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
    result = 31 * result + (numberOfIssues != null ? numberOfIssues.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Comic{"
        + "title='"
        + title
        + '\''
        + ", genre="
        + genre
        + ", numberOfIssues="
        + numberOfIssues
        + ", creativeTeam="
        + creativeTeam
        + ", leadingCharacter="
        + leadingCharacter
        + ", publisher="
        + publisher
        + ", rarityDetails="
        + rarityDetails
        + '}';
  }
}
