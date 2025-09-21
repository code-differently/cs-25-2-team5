/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl.cbl_app.objects;

import java.util.List;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Genre;

/**
 * @author vscode
 */
public class Comic implements Comparable<Comic> {

  private String title;

  private List<Genre> genre;

  private RarityDetails rarityDetails;

  private Creator headWriter;
  private Character leadingCharacter;

  private Publisher publisher;
  public Integer numberOfIssues;

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
    this.headWriter = headWriter;
    this.leadingCharacter = leadingCharacter;
    this.publisher = publisher;
  }

  public Comic(
      String title, Creator headWriter, RarityDetails rarityDetails, Character leadingCharacter) {
    this.title = title;
    this.headWriter = headWriter;
    this.rarityDetails = rarityDetails;
    this.leadingCharacter = leadingCharacter;
    this.genre = null;
    this.publisher = new Publisher(CompanyName.INDIE);
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
    return Double.compare(rarityDetails.getGrade(), other.getRarityDetails().getGrade());
  }

  public Creator getHeadWriter() {
    return headWriter;
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
}
