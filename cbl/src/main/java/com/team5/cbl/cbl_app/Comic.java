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

    private RarityDetails rarityDetails;

    public Comic(String title,List<Genre> genre,RarityDetails rarityDetails) {
        this.title = title;
        this.genre = genre; 
        this.rarityDetails = rarityDetails;

    }


    public String getTitle() {
        return title;
    }

    public List<Genre> getGenre() {
        return genre;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rarityDetails == null) ? 0 : rarityDetails.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Comic other = (Comic) obj;
        if (rarityDetails == null) {
            if (other.rarityDetails != null)
                return false;
        } else if (!rarityDetails.equals(other.rarityDetails))
            return false;
        return true;
    }

    



}
