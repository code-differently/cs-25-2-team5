package com.team5.cbl.cbl_app.interfaces;

import com.team5.cbl.cbl_app.exceptions.ComicNotFoundException;
import com.team5.cbl.cbl_app.objects.Comic;
import java.util.List;

/**
 * Repository interface for managing Comic entities. Provides basic CRUD operations and follows the
 * Repository design pattern.
 */
public interface ComicRepository {

  /**
   * Adds a comic to the repository
   *
   * @param comic The comic to add
   * @throws IllegalArgumentException if comic is null
   */
  void addComic(Comic comic);

  /**
   * Removes a comic from the repository
   *
   * @param comic The comic to remove
   * @throws ComicNotFoundException if comic is not found
   */
  void removeComic(Comic comic) throws ComicNotFoundException;

  /**
   * Retrieves all comics from the repository
   *
   * @return List of all comics
   */
  List<Comic> getComics();

  /**
   * Gets the total number of comics in the repository
   *
   * @return The count of comics
   */
  int getComicCount();
}
