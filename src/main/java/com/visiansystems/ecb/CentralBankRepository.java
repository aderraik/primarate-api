package com.visiansystems.ecb;

import com.visiansystems.bank.CentralBank;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CentralBankRepository extends CrudRepository<CentralBank, Long> {
    List<CentralBank> findByName(String name);
}
