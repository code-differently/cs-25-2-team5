package com.team5.cbl.cbl_app.objects;

public class Character {
  String heroName;
  String secretIdentity;
  String bio;

  public Character(String heroName, String bio) {
    this.heroName = heroName;
    this.bio = bio;
  }

  public Character(String heroName, String bio, String secretIdentity) {
    this.heroName = heroName;
    this.bio = bio;
    this.secretIdentity = secretIdentity;
  }

  public String getHeroName() {
    return heroName;
  }

  public String getBio() {
    return bio;
  }

  public String getSecretIdentity() {
    return secretIdentity;
  }

  @Override
  public String toString() {
    return "Character{" + "heroName='" + heroName + '\'' + ", bio='" + bio + '\'' + '}';
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Character character = (Character) obj;
    return heroName.equals(character.heroName) && bio.equals(character.bio);
  }

  @Override
  public int hashCode() {
    return heroName.hashCode() + bio.hashCode();
  }
}
