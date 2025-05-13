package com.proj.proj.Service;

import com.proj.proj.Model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final List<Person> personnes = List.of(
            new Person(1, "Durand", "Paul", "b.pascaud@alltechconsulting.fr"),
            new Person(2, "Lemoine", "Claire", "bastienpascaud1@gmail.com"),
            new Person(3, "Nguyen", "Thierry", "xbabapvp@gmail.com")
    );

    public Person getPersonById(int id) {
        return personnes.stream().filter(person -> person.getId() == id).findFirst().orElse(null);
    }
    public String getMailById(Integer id) {
        return personnes.stream()
                .filter(p -> p.getId().equals(id))
                .map(Person::getMail)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Aucun utilisateur avec l'id : " + id));
    }
}
