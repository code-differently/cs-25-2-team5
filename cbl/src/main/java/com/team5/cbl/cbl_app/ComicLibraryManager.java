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
import com.team5.cbl.cbl_app.objects.Artist;
import com.team5.cbl.cbl_app.objects.Character;
import com.team5.cbl.cbl_app.objects.Comic;
import com.team5.cbl.cbl_app.objects.ComicLibrary;
import com.team5.cbl.cbl_app.objects.Creator;
import com.team5.cbl.cbl_app.objects.Publisher;
import com.team5.cbl.cbl_app.objects.RarityDetails;
import com.team5.cbl.cbl_app.objects.Writer;

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
            System.out.println();
          }
          break;
        case 2:
          System.out.println("What would you like to search by");
          System.out.println("1. By Character");
          System.out.println("2. Genre");
          System.out.println("3. Title");
          System.out.println("4. Creator (Writer/Artist)");
          System.out.println("5. Publisher");
          int searchChoice = scanner.nextInt();
          scanner.nextLine();
          switch (searchChoice) {
            case 1:
              System.out.println("What is the name of the character");
              String characterName = scanner.nextLine().trim();
              sampleLibrary
                  .filterByCharacter(characterName)
                  .forEach(comic -> System.out.println(comic.getTitle()));
              break;
            case 2:
              Genre genre = readEnumInput(scanner, Genre.class, "What genre");
              sampleLibrary
                  .filterByGenre(genre)
                  .forEach(comic -> System.out.println(comic.getTitle()));
              break;
            case 3:
              System.out.println("What is the title of the comic");
              String title = scanner.nextLine().trim();
              sampleLibrary
                  .filterByComicTitle(title)
                  .forEach(comic -> System.out.println(comic.getTitle()));
              break;
            case 4:
              System.out.println("What is the name of the creator");
              String creatorName = scanner.nextLine().trim();
              sampleLibrary
                  .filterByCreator(creatorName)
                  .forEach(comic -> System.out.println(comic.getTitle()));
              break;
            case 5:
              CompanyName companyName = readEnumInput(scanner, CompanyName.class, "What company");
              sampleLibrary
                  .filterByPublisher(companyName)
                  .forEach(comic -> System.out.println(comic.getTitle()));
              break;
            default:
              System.out.println("Not a valid option");
              break;
          }

          break;
        case 3:
          System.out.println("What is the name of the comic");
          String comicTitle = scanner.nextLine().trim();
          System.out.println("How many issues does it have");
          int numberOfIssues = scanner.nextInt();
          scanner.nextLine();

          // Handle multiple genres using the enum input function
          List<Genre> genres = addMultipleGenres(scanner);

          // Handle multiple creators (writers and artists)
          List<Creator> creativeTeam = addCreativeTeam(scanner);

          // Creates new publisher object
          CompanyName publisherName =
              readEnumInput(scanner, CompanyName.class, "who is the publisher");
          Publisher publisher = new Publisher(publisherName);

          System.out.println("Who is the main hero");
          String heroName = scanner.nextLine().trim();
          System.out.println("Give them a short bio");
          String bio = scanner.nextLine().trim();
          System.out.println("Do they have a secret identity? 1 for yes, any other number for no");
          var val = scanner.nextInt();
          scanner.nextLine();
          Character newComicCharacter = null;
          if (val == 1) {
            System.out.println("What is their secret identity name?");
            String secretIdentity = scanner.nextLine().trim();
            newComicCharacter = new Character(heroName, bio, secretIdentity);
          } else {
            newComicCharacter = new Character(heroName, bio);
          }

          Edition edition = readEnumInput(scanner, Edition.class, "What is the edition");
          System.out.println("What is the print count?");
          var printCount = scanner.nextInt();
          scanner.nextLine();
          System.out.println("What is the grade?");
          var grade = scanner.nextDouble();
          scanner.nextLine();
          System.out.println("What year was it released?");
          var releaseDate = Year.of(scanner.nextInt());
          scanner.nextLine();

          var newRarityDetails = new RarityDetails(edition, printCount, grade, releaseDate);
          Comic newComic =
              new Comic(
                  comicTitle,
                  genres,
                  numberOfIssues,
                  newRarityDetails,
                  creativeTeam,
                  newComicCharacter,
                  publisher);
          sampleLibrary.addComic(newComic);
          System.out.println(
              "You added a new comic to the library with "
                  + creativeTeam.size()
                  + " creators and "
                  + genres.size()
                  + " genres!");
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
          break;
        default:
          flag = false;
          break;
      }
    }
    scanner.close();
  }

  /**
   * Handles adding multiple genres to a comic using the readEnumInput function
   *
   * @param scanner Scanner for user input
   * @return List of selected genres
   */
  private static List<Genre> addMultipleGenres(Scanner scanner) {
    List<Genre> genres = new ArrayList<>();
    boolean addingGenres = true;

    System.out.println("Let's add genres to your comic. You can add multiple genres.");

    while (addingGenres) {
      Genre genre = readEnumInput(scanner, Genre.class, "What genre would you like to add");

      if (!genres.contains(genre)) {
        genres.add(genre);
        System.out.println("Added genre: " + genre);
      } else {
        System.out.println("Genre " + genre + " already added!");
      }

      System.out.println("Would you like to add another genre? 1 for yes, any other number for no");
      int continueAdding = scanner.nextInt();
      scanner.nextLine();

      if (continueAdding != 1) {
        addingGenres = false;
      }
    }

    if (genres.isEmpty()) {
      System.out.println("No genres added. Adding default genre: ACTION");
      genres.add(Genre.ACTION);
    }

    System.out.println("Final genres: " + genres);
    return genres;
  }

  /**
   * Handles adding multiple creators (writers and artists) to a comic
   *
   * @param scanner Scanner for user input
   * @return List of creators in the creative team
   */
  private static List<Creator> addCreativeTeam(Scanner scanner) {
    List<Creator> creativeTeam = new ArrayList<>();
    boolean addingCreators = true;

    System.out.println("Let's build the creative team for your comic!");

    while (addingCreators) {
      System.out.println("What type of creator would you like to add?");
      System.out.println("1. Writer");
      System.out.println("2. Artist");
      System.out.println("3. General Creator");

      int creatorType = scanner.nextInt();
      scanner.nextLine();

      System.out.println("What is the creator's name?");
      String creatorName = scanner.nextLine().trim();

      System.out.println("How old are they?");
      int age = scanner.nextInt();
      scanner.nextLine();

      Creator creator = null;

      switch (creatorType) {
        case 1: // Writer
          List<String> publishedWorks = new ArrayList<>();
          System.out.println("Let's add their published works. You can add multiple works.");
          boolean addingWorks = true;

          while (addingWorks) {
            System.out.println("Enter a published work:");
            String work = scanner.nextLine().trim();
            if (!work.isEmpty() && !publishedWorks.contains(work)) {
              publishedWorks.add(work);
              System.out.println("Added work: " + work);
            }

            System.out.println("Add another work? 1 for yes, any other number for no");
            int continueWorks = scanner.nextInt();
            scanner.nextLine();

            if (continueWorks != 1) {
              addingWorks = false;
            }
          }

          creator = new Writer(creatorName, age, publishedWorks);
          System.out.println(
              "Added Writer: " + creatorName + " (Published works: " + publishedWorks.size() + ")");
          break;

        case 2: // Artist
          System.out.println(
              "What is their art style? (e.g., Realistic, Cartoon, Manga, Abstract)");
          String artStyle = scanner.nextLine().trim();
          creator = new Artist(creatorName, age, artStyle);
          System.out.println("Added Artist: " + creatorName + " (Style: " + artStyle + ")");
          break;

        case 3: // General Creator
          creator = new Creator(creatorName, age);
          System.out.println("Added Creator: " + creatorName);
          break;

        default:
          System.out.println("Invalid option. Adding as general creator.");
          creator = new Creator(creatorName, age);
          System.out.println("Added Creator: " + creatorName);
          break;
      }

      if (creator != null) {
        creativeTeam.add(creator);
      }

      System.out.println(
          "Would you like to add another creator? 1 for yes, any other number for no");
      int continueAdding = scanner.nextInt();
      scanner.nextLine();

      if (continueAdding != 1) {
        addingCreators = false;
      }
    }

    if (creativeTeam.isEmpty()) {
      System.out.println("No creators added. Adding default creator.");
      creativeTeam.add(new Creator("Unknown Creator", 30));
    }

    System.out.println("Final creative team:");
    for (int i = 0; i < creativeTeam.size(); i++) {
      Creator c = creativeTeam.get(i);
      String type = "Creator";
      String details = "";

      if (c instanceof Writer) {
        type = "Writer";
        Writer writer = (Writer) c;
        details = " (Published works: " + writer.getPublishedWorks().size() + ")";
      } else if (c instanceof Artist) {
        type = "Artist";
        details = " (Style: " + ((Artist) c).getArtStyle() + ")";
      }

      System.out.println((i + 1) + ". " + type + ": " + c.getName() + details);
    }

    return creativeTeam;
  }

  private static ComicLibrary createSampleLibrary() {
    List<Comic> comics = new ArrayList<>();

    // Create sample creators with different types using correct Writer structure
    Writer stanLee =
        new Writer(
            "Stan Lee", 95, List.of("The Amazing Spider-Man", "The X-Men", "The Fantastic Four"));
    Artist jackKirby = new Artist("Jack Kirby", 76, "Cosmic Art");
    Writer frankMiller =
        new Writer(
            "Frank Miller",
            67,
            List.of("Batman: The Dark Knight Returns", "Daredevil", "Sin City"));
    Artist steveRogers = new Artist("Steve Rogers", 45, "Realistic");
    Writer alanMoore =
        new Writer(
            "Alan Moore",
            71,
            List.of("Watchmen", "V for Vendetta", "The League of Extraordinary Gentlemen"));
    Writer scottSnyder =
        new Writer("Scott Snyder", 49, List.of("Batman", "American Vampire", "The Wake"));
    Writer brianBendis =
        new Writer(
            "Brian Michael Bendis", 56, List.of("Ultimate Spider-Man", "Powers", "Jessica Jones"));

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
    RarityDetails commonRarity =
        new RarityDetails(Edition.FIRST_EDITION, 50000, 8.5, Year.of(2020));
    RarityDetails uncommonRarity =
        new RarityDetails(Edition.SECOND_EDITION, 25000, 9.0, Year.of(2019));
    RarityDetails rareRarity = new RarityDetails(Edition.FIRST_EDITION, 10000, 9.5, Year.of(1986));
    RarityDetails veryRareRarity =
        new RarityDetails(Edition.FIRST_EDITION, 5000, 9.8, Year.of(1987));
    RarityDetails ultraRareRarity =
        new RarityDetails(Edition.FIRST_EDITION, 1000, 9.9, Year.of(1975));

    // Create genre lists with multiple genres
    List<Genre> superheroActionGenre = List.of(Genre.SUPERHERO, Genre.ACTION);
    List<Genre> superheroCrimeGenre = List.of(Genre.SUPERHERO, Genre.CRIME);
    List<Genre> superheroMysteryGenre = List.of(Genre.SUPERHERO, Genre.MYSTERY);
    List<Genre> superheroAdventureGenre = List.of(Genre.SUPERHERO, Genre.ADVENTURE);
    List<Genre> superheroSciFiGenre = List.of(Genre.SUPERHERO, Genre.SCI_FI);

    // Create creative teams with different types of creators
    List<Creator> stanLeeTeam = List.of(stanLee, jackKirby);
    List<Creator> frankMillerTeam = List.of(frankMiller, steveRogers);
    List<Creator> alanMooreTeam = List.of(alanMoore, steveRogers);
    List<Creator> scottSnyderTeam = List.of(scottSnyder);
    List<Creator> brianBendisTeam = List.of(brianBendis);

    // Create sample comics using creative teams and multiple genres
    comics.add(
        new Comic(
            "The Amazing Spider-Man #1",
            superheroActionGenre,
            1,
            commonRarity,
            stanLeeTeam,
            spiderMan,
            marvel));

    comics.add(
        new Comic(
            "Batman: The Dark Knight Returns",
            superheroCrimeGenre,
            4,
            rareRarity,
            frankMillerTeam,
            batman,
            dc));

    comics.add(
        new Comic(
            "Watchmen #1",
            superheroMysteryGenre,
            12,
            veryRareRarity,
            alanMooreTeam,
            null, // No single main character
            dc));

    comics.add(
        new Comic(
            "Superman: Action Comics #1000",
            superheroAdventureGenre,
            1000,
            uncommonRarity,
            scottSnyderTeam,
            superman,
            dc));

    comics.add(
        new Comic(
            "Ultimate Spider-Man #1",
            superheroSciFiGenre,
            160,
            rareRarity,
            brianBendisTeam,
            spiderMan,
            marvel));

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
