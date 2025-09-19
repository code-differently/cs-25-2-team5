package com.team5.cbl.cbl;

public class Character {
  String heroName = "Hero Name";
  String publisher = "Publisher";
  String bio = "Bio";

  public Character(String heroName, String publisher, String bio) {
    this.heroName = heroName;
    this.publisher = publisher;
    this.bio = bio;
  }

  public String getHeroName() {
    return heroName;
  }

  public String getPublisher() {
    return publisher;
  }

  public String getBio() {
    return bio;
  }

  @Override
  public String toString() {
    return "Character{"
        + "heroName='"
        + heroName
        + '\''
        + ", publisher='"
        + publisher
        + '\''
        + ", bio='"
        + bio
        + '\''
        + '}';
  }
}
