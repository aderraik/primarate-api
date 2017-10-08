package com.visiansystems.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Collection;

@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository repository;

    @Override
    public Collection<Rate> findAll() {
        return repository.findAll();
    }

    @Override
    public Rate findOne(Long id) {
        return repository.findOne(id);
    }

    @Transactional
    @Override
    public Rate create(Rate rate) {
        // Ensure the entity object to be created does NOT exist in the repository. Prevent the
        // default behavior of save() which will update an existing entity if the entity matching
        // the supplied id exists.
        if (rate.getId() != null) {
            throw new EntityExistsException("Cannot create new Greeting with supplied id. " +
                    "The id attribute must be null to create an entity.");
        }

        return repository.save(rate);
    }
}
