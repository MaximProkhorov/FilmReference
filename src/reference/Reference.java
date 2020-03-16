package reference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import reference.comparator.*;
import reference.domain.*;

/**
 *
 * @author PM051
 */
public class Reference {

    private RatingRegister register;

    public Reference(RatingRegister register) {
        this.register = register;
    }

    public Film recommendFilm(Person person) {
        if (register.getPersonalRatings(person).isEmpty()) {
            List<Film> films = new ArrayList<Film>();
            for (Film a : register.filmRatings().keySet()) {
                films.add(a);
            }
            Collections.sort(films, new FilmComparator(register.filmRatings()));
            return films.get(0);        //Нет оценок - самый высокооцененный фильм
        }

        for (Person a : similarity(person)) {
            Map<Film, Rating> filmRating = new HashMap<Film, Rating>();
            List<Film> films = new ArrayList<Film>();

            for (Film f : register.getPersonalRatings(a).keySet()) {
                films.add(f);
                filmRating.put(f, register.getRating(a, f));
            }
            Collections.sort(films, new FilmComparatorSingleRated(filmRating));

            for (Film b : films) {
                if (register.getRating(person, b) != Rating.NOT_WATCHED) {
                    continue;
                }
                switch (register.getRating(a, b)) {
                    case BAD:
                    case MEDIOCRE:
                    case NEUTRAL:
                        break;      //Не рекомендуется, если низко оценен у данного человека
                    default:
                        return b;
                }
            }
        }
        return null; // - если все фильмы просмотрены, либо у всех других людей нехорошо оценен
    }

    public List<Person> similarity(Person person) {
        Map<Person, Integer> tastes = new HashMap<Person, Integer>();
        List<Person> list = register.reviewers();
        list.remove(person);
        for (Person a : list) {
            int points = 0;
            for (Film b : register.getPersonalRatings(person).keySet()) {
                points += register.getRating(person, b).getValue() * register.getRating(a, b).getValue();
            }
            tastes.put(a, points);      //каждому другому человеку начисляется количество баллов - чем их 
        }                               //больше, тем более схожи его вкусы с нашим человеком
        Collections.sort(list, new PersonComparator(tastes)); //сортировка по баллам по убыванию
        return list;
    }
}
