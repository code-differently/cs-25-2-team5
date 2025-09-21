package com.team5.cbl.cbl_app.objects;

public class Artist extends Creator {
    private String artStyle;

    public Artist(String name, int age, String artStyle) {
        super(name, age);
        this.artStyle = artStyle;
    }

    public String getArtStyle() {
        return artStyle;
    }

    public void setArtStyle(String artStyle) {
        this.artStyle = artStyle;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((artStyle == null) ? 0 : artStyle.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Artist other = (Artist) obj;
        if (artStyle == null) {
            if (other.artStyle != null) return false;
        } else if (!artStyle.equals(other.artStyle)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", artStyle='" + artStyle + '\'' +
                '}';
    }
}
