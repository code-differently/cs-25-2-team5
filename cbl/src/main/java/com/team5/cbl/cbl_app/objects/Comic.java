/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl.cbl_app.objects;

import com.team5.cbl.cbl_app.enums.Genre;
import java.util.List;

/**
 * @author vscode
 */
public class Comic implements Comparable<Comic> {

  private String title;

  private List<Genre> genre;

  private RarityDetails rarityDetails;

  private Creator headWriter;

  public Comic(String title, List<Genre> genre, RarityDetails rarityDetails, Creator headWriter) {
    this.title = title;
    this.genre = genre;
    this.rarityDetails = rarityDetails;
    this.headWriter = headWriter;
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
}
