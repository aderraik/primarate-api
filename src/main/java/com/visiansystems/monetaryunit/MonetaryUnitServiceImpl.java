package com.visiansystems.monetaryunit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MonetaryUnitServiceImpl implements MonetaryUnitService {

    @Autowired
    private MonetaryUnitRepository repository;

    @Override
    public Collection<MonetaryUnit> findAll() {
        return repository.findAll();
    }

    @Override
    public MonetaryUnit findOne(Long id) {
        return repository.findOne(id);
    }
}
