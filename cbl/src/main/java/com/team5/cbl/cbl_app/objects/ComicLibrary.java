/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl.cbl_app.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Genre;
import com.team5.cbl.cbl_app.exceptions.ComicNotFoundException;
import com.team5.cbl.cbl_app.interfaces.ComicRepository;
import com.team5.cbl.cbl_app.interfaces.ComicSearchService;
public class ComicLibrary implements ComicRepository, ComicSearchService {

/**
 * @author vscode
 */


  private List<Comic> comics;

  public ComicLibrary() {
    this.comics = new ArrayList<>();
  }

  public ComicLibrary(List<Comic> comics) {
    this.comics = new ArrayList<>(comics);
  }

  @Override
  public void addComic(Comic comic) {
    if (comic == null) {
      throw new ComicNotFoundException("Comic cannot be null");
    }
    // Check if comic already exists
    if (comics.contains(comic)) {
      throw new ComicNotFoundException("Comic already exists");
    }
    comics.add(comic);
  }

  @Override
  public void removeComic(Comic comic) {
    if (comic == null || !comics.remove(comic)) {
      throw new ComicNotFoundException("Comic title not found");
    }
  }

  @Override
  public int getComicCount() {
    return comics.size();
  }

  @Override
  public List<Comic> filterByComicTitle(String title) {
    List<Comic> comicsByFilter =
        comics.stream()
            .filter(comic -> comic.getTitle().contains(title))
            .collect(Collectors.toList());
    if (comicsByFilter.isEmpty()) {
      throw new ComicNotFoundException("Comic title not found");
    }
    return comicsByFilter;
  }

  @Override
  public List<Comic> filterByGenre(Genre genre) {
    List<Comic> comicsByFilter =
        comics.stream()
            .filter(comic -> comic.getGenre() != null && comic.getGenre().contains(genre))
            .collect(Collectors.toList());
    if (comicsByFilter.isEmpty()) {
      throw new ComicNotFoundException("Genre not found");
    }
    return comicsByFilter;
  }

  @Override
  public List<Comic> filterByCreator(String name) {
    List<Comic> comicsByFilter =
        comics.stream()
            .filter(
                comic ->
                    comic.getCreativeTeam().stream()
                        .anyMatch(creator -> creator.getName().contains(name)))
            .collect(Collectors.toList());
    if (comicsByFilter.isEmpty()) {
      throw new ComicNotFoundException("Creator not found");
    }
    return comicsByFilter;
  }

  public List<Comic> filterByCharacter(String heroName) {
    List<Comic> comicsByFilter =
        comics.stream()
            .filter(
                comic ->
                    comic.getLeadingCharacter() != null
                        && comic.getLeadingCharacter().getHeroName().contains(heroName))
            .collect(Collectors.toList());
    if (comicsByFilter.isEmpty()) {
      throw new ComicNotFoundException("Character not found");
    }
    return comicsByFilter;
  }

  @Override
  public List<Comic> filterByPublisher(CompanyName companyName) {
    List<Comic> comicsByFilter =
        comics.stream()
            .filter(comic -> comic.getPublisher().getCompanyName().equals(companyName))
            .collect(Collectors.toList());
    if (comicsByFilter.isEmpty()) {
      throw new ComicNotFoundException("Publisher not found");
    }
    return comicsByFilter;
  }

  @Override
  public List<Comic> getComics() {
    return new ArrayList<>(comics);
  }

  @Override
  public List<Comic> getRankings() {
    List<Comic> sortedComics = new ArrayList<>(comics);
    Collections.sort(sortedComics);
    return sortedComics.reversed();
  }
}
