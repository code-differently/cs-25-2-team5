/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.team5.cbl.cbl_app;

import java.util.List;

/**
 *
 * @author vscode
 */
public class Comic {

    private String title;

    private List<Genre> genre;

    public Comic(String title,List<Genre> genre) {
        this.title = title;
        this.genre = genre; 

    }


    public String getTitle() {
        return title;
    }

    public List<Genre> getGenre() {
        return genre;
    }

}
