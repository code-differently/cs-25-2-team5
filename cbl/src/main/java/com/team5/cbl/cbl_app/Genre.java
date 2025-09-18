/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

package com.team5.cbl.cbl_app;

/**
 *
 * @author vscode
 */
public enum Genre {

    ACTION("Action"),ADVENTURE("Adventure"),FANTASTY("Fantasy"),HORROR("Horror"),SUPERHERO("Superhero");
    
    private String displayName;

    private Genre(String displayName) {
        this.displayName = displayName;
    }



}
