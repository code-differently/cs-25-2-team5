package com.team5.cbl.cbl_app.objects;

public class Creator {
  private String name;
  private int age;

  // Constructor
  public Creator(String name, int age) {
    this.name = name;
    this.age = age;
  }

  // Getters
  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + age;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Creator other = (Creator) obj;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    if (age != other.age) return false;
    return true;
  }
}
