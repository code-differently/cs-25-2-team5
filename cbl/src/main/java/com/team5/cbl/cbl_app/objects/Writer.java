package com.team5.cbl.cbl_app.objects;

import java.util.ArrayList;
import java.util.List;

public class Writer extends Creator {
  private List<String> publishedWorks;

  // Constructor
  public Writer(String name, int age) {
    super(name, age);
    this.publishedWorks = new ArrayList<>();
  }

  // Constructor with published works
  public Writer(String name, int age, List<String> publishedWorks) {
    super(name, age);
    this.publishedWorks =
        new ArrayList<>(publishedWorks != null ? publishedWorks : new ArrayList<>());
  }

  // Getter for published works
  public List<String> getPublishedWorks() {
    return new ArrayList<>(publishedWorks); // Defensive copy
  }

  // Add a published work
  public void addPublishedWork(String work) {
    if (work != null && !publishedWorks.contains(work)) {
      publishedWorks.add(work);
    }
  }

  // Remove a published work
  public void removePublishedWork(String work) {
    publishedWorks.remove(work);
  }

  @Override
  public String toString() {
    return "Writer{"
        + "name='"
        + getName()
        + '\''
        + ", age="
        + getAge()
        + ", publishedWorks="
        + publishedWorks
        + '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) return false;
    if (getClass() != obj.getClass()) return false;
    Writer other = (Writer) obj;
    return publishedWorks.equals(other.publishedWorks);
  }

  @Override
  public int hashCode() {
    return super.hashCode() + publishedWorks.hashCode();
  }
}
