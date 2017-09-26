package com.visiansystems.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.visiansystems.dao.repository.MonetaryDataRepository;
import com.visiansystems.model.MonetaryData;
import com.visiansystems.model.MonetarySeriesData;

import java.util.List;

@Component
public class MonetarySeriesDataDao {

    @Autowired
    private MonetaryDataRepository dataRepository;

    public MonetarySeriesData findByCentralBankId(long bankId) {
        List<MonetaryData> dataList = dataRepository.findByCentralBankId(bankId);

        MonetarySeriesData seriesData = new MonetarySeriesData(bankId);

        for (MonetaryData data : dataList) {
            seriesData.addMonetaryData(data);
        }

        return seriesData;
    }

    public void save(MonetarySeriesData seriesData) {
        for (MonetaryData data : seriesData.getAllMonetaryData()) {
            dataRepository.save(data);
        }
    }
}

