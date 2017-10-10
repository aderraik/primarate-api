package com.visiansystems.bank;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CentralBankRepository extends CrudRepository<CentralBank, Long> {
    List<CentralBank> findByName(String name);
}
