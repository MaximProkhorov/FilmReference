package reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import reference.domain.*;

/**
 *
 * @author PM051
 */
public class RatingRegister {

    private Map<Film, List<Rating>> films; // список оценок (от всех оценивших) для каждого фильма
    private Map<Person, Map<Film, Rating>> people; // для каждого человека - его просмотренные фильмы
                                                   // с личной оценкой

    public RatingRegister() {
        films = new HashMap<Film, List<Rating>>();
        people = new HashMap<Person, Map<Film, Rating>>();
    }

    private void addRating(Film film, Rating rating) {
        if (!films.containsKey(film)) {
            films.put(film, new ArrayList<Rating>());
        }
        films.get(film).add(rating);
    }

    public List<Rating> getRatings(Film film) {
        return films.get(film);
    }

    public Map<Film, List<Rating>> filmRatings() {
        return films;
    }

    public void addRating(Person person, Film film, Rating rating) {
        if (!people.containsKey(person)) {
            people.put(person, new HashMap<Film, Rating>());
        }
        people.get(person).put(film, rating);
        addRating(film, rating);
    }

    public Rating getRating(Person person, Film film) {
        if (people.get(person).get(film) == null) {
            return Rating.NOT_WATCHED;
        }
        return people.get(person).get(film);
    }

    public Map<Film, Rating> getPersonalRatings(Person person) {
        if (people.get(person) == null) {
            return new HashMap<Film, Rating>();
        }
        return people.get(person);
    }

    public List<Person> reviewers() {
        List<Person> list = new ArrayList<Person>();
        for (Person a : people.keySet()) {
            list.add(a);
        }
        return list;
    }
}
