package com.team5.cbl;

import com.team5.cbl.cbl_app.objects.Comic;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ComicRankings {

  private final List<Comic> comics;

  public ComicRankings(List<Comic> comics) {
    this.comics = comics != null ? comics : new ArrayList<>();
  }

  /** Returns comics ranked by grade (highest grade first) */
  public List<Comic> getRankings() {
    return comics.stream()
        .sorted(
            Comparator.comparing((Comic c) -> c.getRarityDetails().getGrade())
                .reversed()) // Reverse to get highest grades first
        .collect(Collectors.toList());
  }
}
