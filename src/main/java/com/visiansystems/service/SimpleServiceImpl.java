package com.visiansystems.service;

import com.visiansystems.model.SimpleObject;
import com.visiansystems.repository.SimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * The SimpleServiceImpl encapsulates all business behaviors operating on the 'SimpleObject' entity
 * model.
 */
@Service
public class SimpleServiceImpl implements SimpleService {

    @Autowired
    private SimpleRepository simpleRepository;

    @Override
    public Collection<SimpleObject> findAll() {
        return simpleRepository.findAll();
    }

    @Override
    public SimpleObject findOne(Long id) {
        return simpleRepository.findOne(id);
    }

    @Transactional
    @Override
    public SimpleObject create(SimpleObject greeting) {
        // Ensure the entity object to be created does NOT exist in the repository. Prevent the
        // default behavior of save() which will update an existing entity if the entity matching
        // the supplied id exists.
        if (greeting.getId() != null) {
            throw new EntityExistsException("Cannot create new Greeting with supplied id. " +
                                            "The id attribute must be null to create an entity.");
        }

        return simpleRepository.save(greeting);
    }

    @Transactional
    @Override
    public SimpleObject update(SimpleObject greeting) {
        // Ensure the entity object to be updated exists in the repository to prevent the default
        // behavior of save() which will persist a new entity if the entity matching the id does
        // not exist.
        SimpleObject greetingToUpdate = findOne(greeting.getId());
        if (greetingToUpdate == null) {
            throw new NoResultException("Requested Greeting not found.");
        }

        return simpleRepository.save(greeting);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        simpleRepository.delete(id);
    }
}
