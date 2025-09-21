/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl.cbl_app;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Edition;
import com.team5.cbl.cbl_app.enums.Genre;
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
        while(flag) {
            System.out.println("Comic Book Library Menu:");
            System.out.println("1. Display all comics");
            System.out.println("2. Filter comics");
            System.out.println("3. Add a new comic");
            System.out.println("4. Exit");
            int input = scanner.nextInt();
            switch(input) {
                case 1:
                for (Comic comic : sampleLibrary.getComics()) {
                    System.out.print(comic.getTitle() + " ");
                    comic.getGenre().forEach(genre-> System.out.print(" " +  genre));
                    
                    System.out.println();
                }
                break;
                default:
                    break;
            }
            
    }
}

    private static ComicLibrary createSampleLibrary() {
        List<Comic> comics = new ArrayList<>();
        
        // Create sample creators (Creator requires name AND age)
        Creator stanLee = new Creator("Stan Lee", 95);
        Creator frankMiller = new Creator("Frank Miller", 67);
        Creator alanMoore = new Creator("Alan Moore", 71);
        Creator scottSnyder = new Creator("Scott Snyder", 49);
        Creator brianBendis = new Creator("Brian Michael Bendis", 56);
        
        // Create sample characters
        com.team5.cbl.cbl_app.objects.Character spiderMan = new com.team5.cbl.cbl_app.objects.Character("Spider-Man", "Peter Parker");
        com.team5.cbl.cbl_app.objects.Character batman = new com.team5.cbl.cbl_app.objects.Character("Batman", "Bruce Wayne");
        com.team5.cbl.cbl_app.objects.Character superman = new com.team5.cbl.cbl_app.objects.Character("Superman", "Clark Kent");
        com.team5.cbl.cbl_app.objects.Character wolverine = new com.team5.cbl.cbl_app.objects.Character("Wolverine", "Logan");
        com.team5.cbl.cbl_app.objects.Character wonderWoman = new com.team5.cbl.cbl_app.objects.Character("Wonder Woman", "Diana Prince");
        
        // Create sample publishers using correct CompanyName enum values
        Publisher marvel = new Publisher(CompanyName.MARVEL_COMICS);
        marvel.addCharacter("Spider-Man");  // Publisher.addCharacter() takes String, not Character object
        marvel.addCharacter("Wolverine");
        
        Publisher dc = new Publisher(CompanyName.DC_COMICS);
        dc.addCharacter("Batman");
        dc.addCharacter("Superman");
        dc.addCharacter("Wonder Woman");
        
        // Create sample rarity details (Edition, printCount, grade, Year)
        RarityDetails commonRarity = new RarityDetails(Edition.FIRST_EDITION, 50000, 8.5, Year.of(2020));
        RarityDetails uncommonRarity = new RarityDetails(Edition.SECOND_EDITION, 25000, 9.0, Year.of(2019));
        RarityDetails rareRarity = new RarityDetails(Edition.FIRST_EDITION, 10000, 9.5, Year.of(1986));
        RarityDetails veryRareRarity = new RarityDetails(Edition.FIRST_EDITION, 5000, 9.8, Year.of(1987));
        RarityDetails ultraRareRarity = new RarityDetails(Edition.FIRST_EDITION, 1000, 9.9, Year.of(1975));
        
        // Create genre lists (Comic constructor expects List<Genre>, not single Genre)
        List<Genre> superheroGenre = List.of(Genre.SUPERHERO);
        List<Genre> actionGenre = List.of(Genre.ACTION);
        List<Genre> crimeGenre = List.of(Genre.CRIME);
        List<Genre> superheroActionGenre = List.of(Genre.SUPERHERO, Genre.ACTION);
        
        // Create sample comics using the full constructor
        // Comic(String title, List<Genre> genre, RarityDetails rarityDetails, Creator headWriter, Character leadingCharacter, Publisher publisher)
        comics.add(new Comic(
            "The Amazing Spider-Man #1",
            superheroGenre,
            commonRarity,
            stanLee,
            spiderMan,
            marvel
        ));
        
        comics.add(new Comic(
            "Batman: The Dark Knight Returns",
            superheroActionGenre,
            rareRarity,
            frankMiller,
            batman,
            dc
        ));
        
        comics.add(new Comic(
            "Watchmen #1",
            superheroGenre,
            veryRareRarity,
            alanMoore,
            null, // No single main character
            dc
        ));
        
        comics.add(new Comic(
            "Superman: Action Comics #1000",
            superheroGenre,
            uncommonRarity,
            scottSnyder,
            superman,
            dc
        ));
        
        comics.add(new Comic(
            "Ultimate Spider-Man #1",
            superheroGenre,
            rareRarity,
            brianBendis,
            spiderMan,
            marvel
        ));
        
        comics.add(new Comic(
            "Wonder Woman #750",
            superheroGenre,
            commonRarity,
            stanLee,
            wonderWoman,
            dc
        ));
        
        comics.add(new Comic(
            "X-Men Origins: Wolverine",
            actionGenre,
            ultraRareRarity,
            frankMiller,
            wolverine,
            marvel
        ));
        
        comics.add(new Comic(
            "Batman: Year One",
            crimeGenre,
            rareRarity,
            frankMiller,
            batman,
            dc
        ));
        
        // Create and populate the comic library
        ComicLibrary library = new ComicLibrary(comics);
        
        return library;
    }
}
