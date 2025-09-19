package com.team5.cbl.cbl_app;

import java.time.Year;

public class RarityDetails {

  private Edition edition;
  private int printCount;
  private double grade;
  private Year year;

  public RarityDetails(Edition edition, int printCount, double grade, Year year) {
    this.edition = edition;
    this.printCount = printCount;
    this.grade = grade;
    this.year = year;
  }

  public Edition getEdition() {
    return edition;
  }

  public void setEdition(Edition edition) {
    this.edition = edition;
  }

  public int getPrintCount() {
    return printCount;
  }

  public void setPrintCount(int printCount) {
    this.printCount = printCount;
  }

  public double getGrade() {
    return grade;
  }

  public void setGrade(double grade) {
    this.grade = grade;
  }

  public Year getYear() {
    return year;
  }

  public void setYear(Year year) {
    this.year = year;
  }

  

  
}
