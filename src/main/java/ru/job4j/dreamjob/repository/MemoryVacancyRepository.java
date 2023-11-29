package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryVacancyRepository implements VacancyRepository {

    private static final MemoryVacancyRepository INSTANCE = new MemoryVacancyRepository();

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Знания основ языка java",
                LocalDateTime.of(2023, Month.NOVEMBER, 8, 12, 30)));
        save(new Vacancy(0, "Junior Java Developer", "Знания основ языка java и в наличии гитхаб с тестовыми проектами",
                LocalDateTime.of(2023, Month.OCTOBER, 8, 12, 30)));
        save(new Vacancy(0, "Junior+ Java Developer", "Опыт работы от 1 лет",
                LocalDateTime.of(2023, Month.SEPTEMBER, 8, 12, 30)));
        save(new Vacancy(0, "Middle Java Developer", "Опыт работы от 1-3 лет",
                LocalDateTime.of(2023, Month.AUGUST, 8, 12, 30)));
        save(new Vacancy(0, "Middle+ Java Developer", "Опыт работы от 3 лет",
                LocalDateTime.of(2023, Month.NOVEMBER, 8, 12, 30)));
        save(new Vacancy(0, "Senior Java Developer", "Опыт работы от 6 лет",
                LocalDateTime.of(2023, Month.APRIL, 15, 12, 30)));
    }

    public static MemoryVacancyRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public void deleteById(int id) {
        vacancies.remove(id);
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> new Vacancy(oldVacancy.getId(),
                vacancy.getTitle(), vacancy.getDescription(), vacancy.getCreationDate())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}