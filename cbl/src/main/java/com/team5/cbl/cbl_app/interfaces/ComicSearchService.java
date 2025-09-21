package com.team5.cbl.cbl_app.interfaces;

import com.team5.cbl.cbl_app.enums.CompanyName;
import com.team5.cbl.cbl_app.enums.Genre;
import com.team5.cbl.cbl_app.objects.Comic;
import java.util.List;

/**
 * Service interface for searching and filtering comics. Provides various search and filter
 * operations.
 */
public interface ComicSearchService {

  /**
   * Filters comics by title (exact match)
   *
   * @param title The title to search for
   * @return List of comics matching the title criteria
   * @throws com.team5.cbl.cbl_app.exceptions.ComicNotFoundException if no comics found
   */
  List<Comic> filterByComicTitle(String title);

  /**
   * Filters comics by genre
   *
   * @param genre The genre to filter by
   * @return List of comics with the specified genre
   * @throws com.team5.cbl.cbl_app.exceptions.ComicNotFoundException if no comics found
   */
  List<Comic> filterByGenre(Genre genre);

  /**
   * Filters comics by creator name
   *
   * @param name The creator name to filter by
   * @return List of comics by the specified creator
   * @throws com.team5.cbl.cbl_app.exceptions.ComicNotFoundException if no comics found
   */
  List<Comic> filterByCreator(String name);

  /**
   * Filters comics by character hero name
   *
   * @param heroName The hero name to filter by
   * @return List of comics featuring the specified character
   * @throws com.team5.cbl.cbl_app.exceptions.ComicNotFoundException if no comics found
   */
  List<Comic> filterByCharacter(String heroName);

  /**
   * Filters comics by publisher company name
   *
   * @param companyName The publisher company name to filter by
   * @return List of comics from the specified publisher
   * @throws com.team5.cbl.cbl_app.exceptions.ComicNotFoundException if no comics found
   */
  List<Comic> filterByPublisher(CompanyName companyName);

  List<Comic> getRankings();
}
