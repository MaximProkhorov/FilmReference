package reference.comparator;

import java.util.Comparator;
import java.util.Map;
import reference.domain.*;

/**
 *
 * @author PM051
 */
public class FilmComparatorSingleRated implements Comparator<Film> {

    private Map<Film, Rating> rating;

    public FilmComparatorSingleRated(Map<Film, Rating> rating) {
        this.rating = rating;
    }

    @Override
    public int compare(Film o1, Film o2) {
        return rating.get(o2).getValue() - rating.get(o1).getValue();
    }

}
