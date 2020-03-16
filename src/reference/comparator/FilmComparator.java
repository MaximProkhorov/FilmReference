package reference.comparator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import reference.domain.*;

/**
 *
 * @author PM051
 */
public class FilmComparator implements Comparator<Film> {

    private Map<Film, List<Rating>> ratings;

    public FilmComparator(Map<Film, List<Rating>> ratings) {
        this.ratings = ratings;
    }

    @Override
    public int compare(Film o1, Film o2) {      //сортировка по средней оценке по убыванию
        int rating1 = 0;
        int rating2 = 0;
        int i1 = 0;
        int i2 = 0;
        for (Rating a : ratings.get(o1)) {
            rating1 += a.getValue();
            i1++;
        }
        for (Rating a : ratings.get(o2)) {
            rating2 += a.getValue();
            i2++;
        }
        double average1 = (double) rating1 / i1;
        double average2 = (double) rating2 / i2;
        return new Double(average2).compareTo(average1);
    }
}
