package com.team5.cbl.cbl_app;

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
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Comic Library CLI Manager
 */
public class ComicLibraryManager {

  public static void main(String[] args) {
    var scanner = new Scanner(System.in);
    ComicLibrary sampleLibrary = createSampleLibrary();

    boolean flag = true;
    while (flag) {
      displayMainMenu();
      int input = scanner.nextInt();
      scanner.nextLine();
      switch (input) {
        case 1:
          displayAllComics(sampleLibrary);
          break;
        case 2:
          handleComicFiltering(scanner, sampleLibrary);
          break;
        case 3:
          handleAddNewComic(scanner, sampleLibrary);
          break;
        case 4:
          displayComicGrades(sampleLibrary);
          break;
        default:
          flag = false;
          break;
      }
    }
    scanner.close();
  }

  private static void displayMainMenu() {
    System.out.println("Comic Book Library Menu:");
    System.out.println("1. Display all comics");
    System.out.println("2. Filter comics");
    System.out.println("3. Add a new comic");
    System.out.println("4. View the comic grades of our Library");
    System.out.println("Any other key to exit");
  }

  private static void displayAllComics(ComicLibrary library) {
    for (Comic comic : library.getComics()) {
      displayComicInfo(comic);
      System.out.println();
    }
  }

  private static void displayComicInfo(Comic comic) {
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
  }

  private static void displayComicGrades(ComicLibrary library) {
    library
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
  }

  private static void handleComicFiltering(Scanner scanner, ComicLibrary library) {
    displayFilterMenu();
    int searchChoice = scanner.nextInt();
    scanner.nextLine();

    switch (searchChoice) {
      case 1:
        filterByCharacter(scanner, library);
        break;
      case 2:
        filterByGenre(scanner, library);
        break;
      case 3:
        filterByTitle(scanner, library);
        break;
      case 4:
        filterByCreator(scanner, library);
        break;
      case 5:
        filterByPublisher(scanner, library);
        break;
      default:
        System.out.println("Not a valid option");
        break;
    }
  }

  private static void displayFilterMenu() {
    System.out.println("What would you like to search by");
    System.out.println("1. By Character");
    System.out.println("2. Genre");
    System.out.println("3. Title");
    System.out.println("4. Creator (Writer/Artist)");
    System.out.println("5. Publisher");
  }

  private static void filterByCharacter(Scanner scanner, ComicLibrary library) {
    System.out.println("What is the name of the character");
    String characterName = scanner.nextLine().trim();
    library.filterByCharacter(characterName).forEach(comic -> System.out.println(comic.getTitle()));
  }

  private static void filterByGenre(Scanner scanner, ComicLibrary library) {
    Genre genre = readEnumInput(scanner, Genre.class, "What genre");
    library.filterByGenre(genre).forEach(comic -> System.out.println(comic.getTitle()));
  }

  private static void filterByTitle(Scanner scanner, ComicLibrary library) {
    System.out.println("What is the title of the comic");
    String title = scanner.nextLine().trim();
    library.filterByComicTitle(title).forEach(comic -> System.out.println(comic.getTitle()));
  }

  private static void filterByCreator(Scanner scanner, ComicLibrary library) {
    System.out.println("What is the name of the creator");
    String creatorName = scanner.nextLine().trim();
    library.filterByCreator(creatorName).forEach(comic -> System.out.println(comic.getTitle()));
  }

  private static void filterByPublisher(Scanner scanner, ComicLibrary library) {
    CompanyName companyName = readEnumInput(scanner, CompanyName.class, "What company");
    library.filterByPublisher(companyName).forEach(comic -> System.out.println(comic.getTitle()));
  }

  private static void handleAddNewComic(Scanner scanner, ComicLibrary library) {
    String comicTitle = getComicTitle(scanner);
    int numberOfIssues = getNumberOfIssues(scanner);
    List<Genre> genres = addMultipleGenres(scanner);
    List<Creator> creativeTeam = addCreativeTeam(scanner);
    Publisher publisher = getPublisher(scanner);
    Character character = createCharacter(scanner);
    RarityDetails rarityDetails = createRarityDetails(scanner);

    Comic newComic =
        new Comic(
            comicTitle, genres, numberOfIssues, rarityDetails, creativeTeam, character, publisher);

    library.addComic(newComic);
    System.out.println(
        "You added a new comic to the library with "
            + creativeTeam.size()
            + " creators and "
            + genres.size()
            + " genres!");
  }

  private static String getComicTitle(Scanner scanner) {
    System.out.println("What is the name of the comic");
    return scanner.nextLine().trim();
  }

  private static int getNumberOfIssues(Scanner scanner) {
    System.out.println("How many issues does it have");
    int numberOfIssues = scanner.nextInt();
    scanner.nextLine();
    return numberOfIssues;
  }

  private static Publisher getPublisher(Scanner scanner) {
    CompanyName publisherName = readEnumInput(scanner, CompanyName.class, "who is the publisher");
    return new Publisher(publisherName);
  }

  private static Character createCharacter(Scanner scanner) {
    System.out.println("Who is the main hero");
    String heroName = scanner.nextLine().trim();
    System.out.println("Give them a short bio");
    String bio = scanner.nextLine().trim();
    System.out.println("Do they have a secret identity? 1 for yes, any other number for no");
    int val = scanner.nextInt();
    scanner.nextLine();

    if (val == 1) {
      System.out.println("What is their secret identity name?");
      String secretIdentity = scanner.nextLine().trim();
      return new Character(heroName, bio, secretIdentity);
    } else {
      return new Character(heroName, bio);
    }
  }

  private static RarityDetails createRarityDetails(Scanner scanner) {
    Edition edition = readEnumInput(scanner, Edition.class, "What is the edition");
    System.out.println("What is the print count?");
    int printCount = scanner.nextInt();
    scanner.nextLine();
    System.out.println("What is the grade?");
    double grade = scanner.nextDouble();
    scanner.nextLine();
    System.out.println("What year was it released?");
    Year releaseDate = Year.of(scanner.nextInt());
    scanner.nextLine();

    return new RarityDetails(edition, printCount, grade, releaseDate);
  }

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

  private static List<Creator> addCreativeTeam(Scanner scanner) {
    List<Creator> creativeTeam = new ArrayList<>();
    boolean addingCreators = true;

    System.out.println("Let's build the creative team for your comic!");

    while (addingCreators) {
      Creator creator = createSingleCreator(scanner);
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

    displayCreativeTeam(creativeTeam);
    return creativeTeam;
  }

  private static Creator createSingleCreator(Scanner scanner) {
    displayCreatorTypeMenu();
    int creatorType = scanner.nextInt();
    scanner.nextLine();

    String creatorName = getCreatorName(scanner);
    int age = getCreatorAge(scanner);

    switch (creatorType) {
      case 1: // Writer
        return createWriter(scanner, creatorName, age);
      case 2: // Artist
        return createArtist(scanner, creatorName, age);
      case 3: // General Creator
        return createGeneralCreator(creatorName, age);
      default:
        System.out.println("Invalid option. Adding as general creator.");
        return createGeneralCreator(creatorName, age);
    }
  }

  private static void displayCreatorTypeMenu() {
    System.out.println("What type of creator would you like to add?");
    System.out.println("1. Writer");
    System.out.println("2. Artist");
    System.out.println("3. General Creator");
  }

  private static String getCreatorName(Scanner scanner) {
    System.out.println("What is the creator's name?");
    return scanner.nextLine().trim();
  }

  private static int getCreatorAge(Scanner scanner) {
    System.out.println("How old are they?");
    int age = scanner.nextInt();
    scanner.nextLine();
    return age;
  }

  private static Writer createWriter(Scanner scanner, String name, int age) {
    List<String> publishedWorks = getPublishedWorks(scanner);
    Writer writer = new Writer(name, age, publishedWorks);
    System.out.println(
        "Added Writer: " + name + " (Published works: " + publishedWorks.size() + ")");
    return writer;
  }

  private static List<String> getPublishedWorks(Scanner scanner) {
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
    return publishedWorks;
  }

  private static Artist createArtist(Scanner scanner, String name, int age) {
    System.out.println("What is their art style? (e.g., Realistic, Cartoon, Manga, Abstract)");
    String artStyle = scanner.nextLine().trim();
    Artist artist = new Artist(name, age, artStyle);
    System.out.println("Added Artist: " + name + " (Style: " + artStyle + ")");
    return artist;
  }

  private static Creator createGeneralCreator(String name, int age) {
    Creator creator = new Creator(name, age);
    System.out.println("Added Creator: " + name);
    return creator;
  }

  private static void displayCreativeTeam(List<Creator> creativeTeam) {
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
  }

  private static ComicLibrary createSampleLibrary() {
    List<Comic> comics = new ArrayList<>();

    // Sample creators
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

    // Characters
    Character spiderMan = new Character("Spider-Man", "Peter Parker");
    Character batman = new Character("Batman", "Bruce Wayne");
    Character superman = new Character("Superman", "Clark Kent");

    // Publishers
    Publisher marvel = new Publisher(CompanyName.MARVEL_COMICS);
    marvel.addCharacter("Spider-Man");

    Publisher dc = new Publisher(CompanyName.DC_COMICS);
    dc.addCharacter("Batman");
    dc.addCharacter("Superman");

    // Rarity
    RarityDetails commonRarity =
        new RarityDetails(Edition.FIRST_EDITION, 50000, 8.5, Year.of(2020));
    RarityDetails rareRarity =
        new RarityDetails(Edition.FIRST_EDITION, 10000, 9.5, Year.of(1986));

    // Comics
    comics.add(
        new Comic(
            "The Amazing Spider-Man #1",
            List.of(Genre.SUPERHERO, Genre.ACTION),
            1,
            commonRarity,
            List.of(stanLee, jackKirby),
            spiderMan,
            marvel));

    comics.add(
        new Comic(
            "Batman: The Dark Knight Returns",
            List.of(Genre.SUPERHERO, Genre.CRIME),
            4,
            rareRarity,
            List.of(frankMiller, steveRogers),
            batman,
            dc));

    return new ComicLibrary(comics);
  }

  private static <T extends Enum<T>> T readEnumInput(
      Scanner scanner, Class<T> enumClass, String prompt) {
    System.out.println(prompt + " (Choose from: " + String.join(", ", getEnumNames(enumClass)) + ")");
    String input = scanner.nextLine().trim().toUpperCase();
    try {
      return Enum.valueOf(enumClass, input);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid choice. Defaulting to " + enumClass.getEnumConstants()[0]);
      return enumClass.getEnumConstants()[0];
    }
  }

  private static <T extends Enum<T>> List<String> getEnumNames(Class<T> enumClass) {
    List<String> names = new ArrayList<>();
    for (T constant : enumClass.getEnumConstants()) {
      names.add(constant.name());
    }
    return names;
  }
}
