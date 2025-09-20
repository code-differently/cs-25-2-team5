/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl.cbl_app.objects;

import com.team5.cbl.cbl_app.enums.Genre;
import com.team5.cbl.cbl_app.exceptions.ComicNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vscode
 */
public class ComicLibrary {

  private List<Comic> comics;

  public ComicLibrary(List<Comic> comics) {
    this.comics = comics;
  }

  // Filter comics by Title, Publisher, Character, Genre, Creator

  public List<Comic> filterByComicTitle(String title) {
    List<Comic> comicsByFilter =
        comics.stream()
            .filter(comic -> comic.getTitle().equals(title))
            .collect(Collectors.toList());
    if (comicsByFilter.isEmpty()) {
      throw new ComicNotFoundException("Comic title not found");
    }
    return comicsByFilter;
  }

  public List<Comic> filterByPublisher(Publisher publisher) {
    List<Comic> comicsByFilter =
        comics.stream()
            .filter(comic -> comic.getPublisher().equals(publisher))
            .collect(Collectors.toList());
    if (comicsByFilter.isEmpty()) {
      throw new ComicNotFoundException("Publisher not found");
    }
    return comicsByFilter;
  }

  public List<Comic> filterByCharacter(String heroName) {
    List<Comic> comicsByFilter =
        comics.stream()
            .filter(
                comic ->
                    comic.getLeadingCharacter() != null
                        && comic.getLeadingCharacter().getHeroName().equals(heroName))
            .collect(Collectors.toList());
    if (comicsByFilter.isEmpty()) {
      throw new ComicNotFoundException("Character not found");
    }
    return comicsByFilter;
  }

  public List<Comic> filterByGenre(Genre genre) {
    List<Comic> comicsByFilter =
        comics.stream()
            .filter(comic -> comic.getGenre().contains(genre))
            .collect(Collectors.toList());
    if (comicsByFilter.isEmpty()) {
      throw new ComicNotFoundException("Genre not found");
    }
    return comicsByFilter;
  }

  public List<Comic> filterByCreator(String name) {
    List<Comic> comicsByFilter =
        comics.stream()
            .filter(
                comic ->
                    comic.getHeadWriter() != null && comic.getHeadWriter().getName().equals(name))
            .collect(Collectors.toList());
    if (comicsByFilter.isEmpty()) {
      throw new ComicNotFoundException("Creator not found");
    }
    return comicsByFilter;
  }

  public List<Comic> getComics() {
    return comics;
  }

  // sorts the lists by the grade and returns the list of comics
  public List<Comic> getRankings() {
    return null;
  }

  // Adds a new comic to library
  public void addComic(Comic comic) {}

  // removes a
  public void removeComic(Comic comic) {}
}
