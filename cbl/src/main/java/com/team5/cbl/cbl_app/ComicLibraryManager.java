/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl.cbl_app;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Edition;
import com.team5.cbl.cbl_app.enums.Genre;
import com.team5.cbl.cbl_app.objects.Character;
import com.team5.cbl.cbl_app.objects.Comic;
import com.team5.cbl.cbl_app.objects.ComicLibrary;
import com.team5.cbl.cbl_app.objects.Creator;
import com.team5.cbl.cbl_app.objects.Publisher;
import com.team5.cbl.cbl_app.objects.RarityDetails;

/**
 * @author vscode
 */
public class ComicLibraryManager {

  public static void main(String[] args) {
    var scanner = new Scanner(System.in);
    ComicLibrary sampleLibrary = createSampleLibrary();

    boolean flag = true;
    while (flag) {
      System.out.println("Comic Book Library Menu:");
      System.out.println("1. Display all comics");
      System.out.println("2. Filter comics");
      System.out.println("3. Add a new comic");
      System.out.println("4. View the comic grades of our Library");
      System.out.println("any other key to exit");
      int input = scanner.nextInt();
      scanner.nextLine();
      switch (input) {
        case 1:
          for (Comic comic : sampleLibrary.getComics()) {
            System.out.print(comic.getTitle() + " ");
            comic.getGenre().forEach(genre -> System.out.print(" " + genre));
            System.out.print(" " + comic.getHeadWriter().getName() + " ");
            System.out.print(comic.getRarityDetails().getEdition() + " ");
            System.out.print(comic.getPublisher().getCompanyName() + " ");
            String leadingCharacterName =
                comic.getLeadingCharacter() == null
                    ? "No leading Character"
                    : comic.getLeadingCharacter().getHeroName();
            System.out.println(leadingCharacterName + " ");
            System.out.println(comic.getPublisher().getCompanyName() + " ");
            System.out.println();
          }
          break;
        case 2:
          System.out.println("What would you like to search by");
          System.out.println("1. By Character");
          System.out.println("2. Genre");
          System.out.println("3. Title");
          System.out.println("4. Writer");
          System.out.println("5. Publisher");
          break;
        case 3:
          System.out.println("What is the name of the comic");
          String comicTitle = scanner.nextLine().trim();
          System.out.println("How many issue does it have");
          int numberOfIssues = scanner.nextInt();
          scanner.nextLine();
          Genre genre = readEnumInput(scanner, Genre.class, "What is the genre");
          System.out.println("Who is the writer");
          String writerName = scanner.nextLine().trim();
          System.out.println("How old are they");
          int age = scanner.nextInt();
          scanner.nextLine();
          // creaters new creator object
          Creator writer = new Creator(writerName, age);
          // Creates new publisher object
          CompanyName publisherName =
              readEnumInput(scanner, CompanyName.class, "who is the publisher");
          Publisher publisher = new Publisher(publisherName);

          System.out.println("Who is the main hero");
          String heroName = scanner.nextLine().trim();
          System.out.println("Give them a short bio");
          String bio = scanner.nextLine().trim();
          System.out.println("Do they have a secret Identity 1 for yes any key for no");
          var val = scanner.nextInt();
          scanner.nextLine();
          Character newComicCharater = null;
          if (val == 1) {
            System.out.println("What is their name");
            newComicCharater = new Character(heroName, bio, scanner.nextLine());

          } else {
            newComicCharater = new Character(heroName, bio);
          }
          Edition edition = readEnumInput(scanner, Edition.class, "What is the edition");
          System.out.println("What is the print count");
          var printCount = scanner.nextInt();
          scanner.nextLine();
          System.out.println("What is the grade");
          var grade = scanner.nextDouble();
          scanner.nextLine();
          System.out.println("What year was it relased");
          var relaseDate = Year.of(scanner.nextInt());
          var newRarityDetails = new RarityDetails(edition, printCount, grade, relaseDate);
          Comic newComic =
              new Comic(
                  comicTitle,
                  List.of(genre),
                  numberOfIssues,
                  newRarityDetails,
                  writer,
                  newComicCharater,
                  publisher);
          sampleLibrary.addComic(newComic);
          System.out.println("You added a new comic to the library");
          break;
        case 4:
          sampleLibrary
              .getRankings()
              .forEach(
                  comic ->
                      System.out.println(
                          comic.getTitle()
                              + " Edition: "
                              + comic.getRarityDetails().getEdition()
                              + " print count: "
                              + comic.getRarityDetails().getPrintCount()
                              + " grade: "
                              + comic.getRarityDetails().getGrade()
                              + " year: "
                              + comic.getRarityDetails().getYear()));
        default:
          flag = false;
          break;
      }
    }
  }

  private static ComicLibrary createSampleLibrary() {
    List<Comic> comics = new ArrayList<>();
    
    // Create sample creators
    Creator stanLee = new Creator("Stan Lee", 95);
    Creator frankMiller = new Creator("Frank Miller", 67);
    Creator alanMoore = new Creator("Alan Moore", 71);
    Creator scottSnyder = new Creator("Scott Snyder", 49);
    Creator brianBendis = new Creator("Brian Michael Bendis", 56);
    Creator jackKirby = new Creator("Jack Kirby", 76);
    Creator steveRogers = new Creator("Steve Rogers", 45);
    
    // Create sample characters
    Character spiderMan = new Character("Spider-Man", "Peter Parker");
    Character batman = new Character("Batman", "Bruce Wayne");
    Character superman = new Character("Superman", "Clark Kent");
    Character wolverine = new Character("Wolverine", "Logan");
    Character wonderWoman = new Character("Wonder Woman", "Diana Prince");
    
    // Create sample publishers using correct CompanyName enum values
    Publisher marvel = new Publisher(CompanyName.MARVEL_COMICS);
    marvel.addCharacter("Spider-Man");
    marvel.addCharacter("Wolverine");
    
    Publisher dc = new Publisher(CompanyName.DC_COMICS);
    dc.addCharacter("Batman");
    dc.addCharacter("Superman");
    dc.addCharacter("Wonder Woman");
    
    // Create sample rarity details
    RarityDetails commonRarity = new RarityDetails(Edition.FIRST_EDITION, 50000, 8.5, Year.of(2020));
    RarityDetails uncommonRarity = new RarityDetails(Edition.SECOND_EDITION, 25000, 9.0, Year.of(2019));
    RarityDetails rareRarity = new RarityDetails(Edition.FIRST_EDITION, 10000, 9.5, Year.of(1986));
    RarityDetails veryRareRarity = new RarityDetails(Edition.FIRST_EDITION, 5000, 9.8, Year.of(1987));
    RarityDetails ultraRareRarity = new RarityDetails(Edition.FIRST_EDITION, 1000, 9.9, Year.of(1975));
    
    // Create genre lists
    List<Genre> superheroGenre = List.of(Genre.SUPERHERO);
    List<Genre> actionGenre = List.of(Genre.ACTION);
    List<Genre> crimeGenre = List.of(Genre.CRIME);
    List<Genre> superheroActionGenre = List.of(Genre.SUPERHERO, Genre.ACTION);
    
    // Create creative teams
    List<Creator> stanLeeTeam = List.of(stanLee, jackKirby);
    List<Creator> frankMillerTeam = List.of(frankMiller);
    List<Creator> alanMooreTeam = List.of(alanMoore, steveRogers);
    List<Creator> scottSnyderTeam = List.of(scottSnyder);
    List<Creator> brianBendisTeam = List.of(brianBendis);
    
    // Create sample comics using creative teams
    comics.add(new Comic(
        "The Amazing Spider-Man #1",
        superheroGenre,
        1,
        commonRarity,
        stanLeeTeam,
        spiderMan,
        marvel
    ));
    
    comics.add(new Comic(
        "Batman: The Dark Knight Returns",
        superheroActionGenre,
        4,
        rareRarity,
        frankMillerTeam,
        batman,
        dc
    ));
    
    comics.add(new Comic(
        "Watchmen #1",
        superheroGenre,
        12,
        veryRareRarity,
        alanMooreTeam,
        null, // No single main character
        dc
    ));
    
    comics.add(new Comic(
        "Superman: Action Comics #1000",
        superheroGenre,
        1000,
        uncommonRarity,
        scottSnyderTeam,
        superman,
        dc
    ));
    
    comics.add(new Comic(
        "Ultimate Spider-Man #1",
        superheroGenre,
        160,
        rareRarity,
        brianBendisTeam,
        spiderMan,
        marvel
    ));
    
    // Create and populate the comic library
    ComicLibrary library = new ComicLibrary(comics);
    
    return library;
  }

  public static <T extends Enum<T>> T readEnumInput(
      Scanner scanner, Class<T> enumClass, String prompt) {
    T result = null;
    while (result == null) {
      System.out.println(
          prompt + " (options: " + Arrays.toString(enumClass.getEnumConstants()) + ")");
      String input = scanner.nextLine().trim().toUpperCase();

      try {
        result = Enum.valueOf(enumClass, input);
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid input. Please try again.");
      }
    }
    return result;
  }
}
