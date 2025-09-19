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
}
