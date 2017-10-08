package com.visiansystems.rate;

import java.util.Collection;

public interface RateService {
    Collection<Rate> findAll();

    Rate findOne(Long id);

    Rate create(Rate rate);
}
