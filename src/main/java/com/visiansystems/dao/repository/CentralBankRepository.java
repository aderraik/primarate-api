package com.visiansystems.dao.repository;

import com.visiansystems.model.CentralBank;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CentralBankRepository extends CrudRepository<CentralBank, Long> {
    List<CentralBank> findByName(String name);
}
