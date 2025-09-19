package com.team5.cbl.cbl_app.objects;

import com.team5.cbl.cbl_app.enums.Edition;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((edition == null) ? 0 : edition.hashCode());
    result = prime * result + printCount;
    long temp;
    temp = Double.doubleToLongBits(grade);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((year == null) ? 0 : year.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RarityDetails other = (RarityDetails) obj;
    if (edition != other.edition) return false;
    if (printCount != other.printCount) return false;
    if (Double.doubleToLongBits(grade) != Double.doubleToLongBits(other.grade)) return false;
    if (year == null) {
      if (other.year != null) return false;
    } else if (!year.equals(other.year)) return false;
    return true;
  }
}
