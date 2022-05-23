package ru.aisa.carwash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.aisa.carwash.model.WashOption;

import javax.transaction.Transactional;

@Repository
public interface WashOptionRepository extends JpaRepository<WashOption, Long> {
    WashOption findByName(String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE WashOption SET name = ?2, cost = ?3, time = ?4 WHERE id = ?1")
    void updateWashOption(Long id, String name, Integer cost, Integer time);
}
