/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl.cbl_app.objects;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author vscode
 */
public class ComicLibrary {

    private List<Comic> comics;


    public ComicLibrary(List<Comic> comics) {
        this.comics = comics;

    }


    public List<Comic> filterByComicTitle(String title) {
        return comics.stream()
        .filter(comic-> comic.getTitle().equals(title))
        .collect(Collectors.toList());

    }

    // sorts the lists by the grade and returns the list of comics
    public List<Comic> getRankings() {
        return null;
    }

    // Adds a new comic to library
    public void addComic(Comic comic) {

    }

    // removes a 
    public void removeComic(Comic comic) {

    }




}
