package com.team5.cbl.cbl_app.objects;

public class Character {
  String heroName;
  String bio;

  public Character(String heroName, String bio) {
    this.heroName = heroName;
    this.bio = bio;
  }

  public String getHeroName() {
    return heroName;
  }

  public String getBio() {
    return bio;
  }

  @Override
  public String toString() {
    return "Character{" + "heroName='" + heroName + '\'' + ", bio='" + bio + '\'' + '}';
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((heroName == null) ? 0 : heroName.hashCode());
    result = prime * result + ((bio == null) ? 0 : bio.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Character other = (Character) obj;
    if (heroName == null) {
      if (other.heroName != null) return false;
    } else if (!heroName.equals(other.heroName)) return false;
    if (bio == null) {
      if (other.bio != null) return false;
    } else if (!bio.equals(other.bio)) return false;
    return true;
  }
}
