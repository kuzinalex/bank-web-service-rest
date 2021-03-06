package com.devcolibri.dataexam.repository;

import com.devcolibri.dataexam.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query("select b from Bank b where b.name = :name")
    Bank findByName(@Param("name") String name);

    @Query("select b from Bank b where b.id = :id")
    Bank findById(@Param("id") long id);

}
