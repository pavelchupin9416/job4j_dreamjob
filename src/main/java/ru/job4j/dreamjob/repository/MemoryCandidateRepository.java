package ru.job4j.dreamjob.repository;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements  CandidateRepository {
    @GuardedBy("this")
    private AtomicInteger nextId = new AtomicInteger(0);
    private final Map<Integer, Candidate> candidates = new HashMap<>();


    private MemoryCandidateRepository() {
        save(new Candidate(0, "Фролов Петр Иванович", "Intern Java Developer",
                LocalDateTime.of(2023, Month.NOVEMBER, 1, 11, 25)));
        save(new Candidate(1, "Белугин Сергей Андреевич", "Junior Java Developer",
                LocalDateTime.of(2023, Month.OCTOBER, 2, 15, 36)));
        save(new Candidate(2, "Дмитрий Анисимов Петрович", "Junior+ Java Developer",
                LocalDateTime.of(2023, Month.SEPTEMBER, 6, 12, 30)));
        save(new Candidate(3, "Силкин Евгений Сергеевич", "Middle Java Developer",
                LocalDateTime.of(2023, Month.AUGUST, 16, 17, 46)));
        save(new Candidate(4, "Савельев Николай Александрович", "Middle+ Java Developer",
                LocalDateTime.of(2023, Month.NOVEMBER, 8, 11, 12)));
        save(new Candidate(5, "Цапин Антон Олегович", "Senior Java Developer",
                LocalDateTime.of(2023, Month.APRIL, 15, 7, 13)));
    }


    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) -> new Candidate(oldCandidate.getId(),
                candidate.getName(), candidate.getDescription(), candidate.getCreationDate())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
