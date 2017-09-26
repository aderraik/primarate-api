package com.visiansystems.model;

import com.visiansystems.util.MonetaryUtils;
import com.visiansystems.util.logger.CallLogging;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MonetarySeriesData {
    private long centralBankId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<LocalDate, Map<String, MonetaryData>> seriesMap;

    public MonetarySeriesData() {
        clear();
    }

    public MonetarySeriesData(long centralBankId) {
        this.centralBankId = centralBankId;
        clear();
    }

    public void clear() {
        startDate = null;
        endDate = null;

        if (seriesMap == null) {
            seriesMap = new HashMap<>();
        }
        else {
            seriesMap.clear();
        }
    }

    public int size() {
        return seriesMap.size();
    }

    public int getTotalRatesOnDate(LocalDate date) {
        Map<String, MonetaryData> currencyMap = seriesMap.get(date);
        if (currencyMap != null) {
            return currencyMap.size();
        }
        return 0;
    }

    public int getTotalRates() {
        int total = 0;

        for (Map.Entry<LocalDate, Map<String, MonetaryData>> entry : seriesMap.entrySet()) {
            total += entry.getValue().size();
        }
        return total;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getCentralBankId() {
        return centralBankId;
    }

    public void setCentralBankId(long centralBankId) {
        this.centralBankId = centralBankId;

        List<MonetaryData> dataList = getAllMonetaryData();
        for (MonetaryData data : dataList) {
            data.setCentralBankId(centralBankId);
        }
        createSeriesMap(dataList);
    }

    @CallLogging(CallLogging.Level.INFO)
    public void addMonetaryData(MonetaryData monetaryData) throws IllegalArgumentException {
        if (monetaryData != null &&
            monetaryData.isValid() &&
            monetaryData.getCentralBankId() == centralBankId) {
            LocalDate date = monetaryData.getDate();
            String currencyCode =
                    MonetaryUtils.getMonetaryCodeFromId(monetaryData.getMonetaryUnitId());

            if (startDate == null || date.isBefore(startDate)) {
                startDate = date;
            }
            if (endDate == null || date.isAfter(endDate)) {
                endDate = date;
            }

            Map<String, MonetaryData> currencyMap;
            if (seriesMap.containsKey(date)) {
                currencyMap = seriesMap.get(date);
                seriesMap.remove(date);
            }
            else {
                currencyMap = new HashMap<>();
            }
            currencyMap.put(currencyCode, monetaryData);
            seriesMap.put(date, currencyMap);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @CallLogging(CallLogging.Level.INFO)
    public void addMonetaryData(String currencyCode, LocalDate date, double amount) {
        if (!MonetaryUtils.isMonetaryDateValid(date)) {
            throw new IllegalArgumentException();
        }

        long monetaryUnitId = MonetaryUtils.getMonetaryIdFromCode(currencyCode);
        addMonetaryData(new MonetaryData(centralBankId, monetaryUnitId, date, amount));
    }

    @CallLogging(CallLogging.Level.INFO)
    public void addMonetarySeriesData(MonetarySeriesData seriesData) {
        for (MonetaryData data : seriesData.getAllMonetaryData()) {
            addMonetaryData(data);
        }
    }

    public MonetaryData getMonetaryDataOnDate(LocalDate date, String currencyCode)
            throws IllegalArgumentException {

        if (!MonetaryUtils.isMonetaryDateValid(date) ||
            !MonetaryUtils.isCurrencyCodeValid(currencyCode)) {
            throw new IllegalArgumentException();
        }

        if (seriesMap.containsKey(date)) {
            Map<String, MonetaryData> currencyMap = seriesMap.get(date);
            if (currencyMap.containsKey(currencyCode)) {
                return currencyMap.get(currencyCode);
            }
        }
        return null;
    }

    public List<MonetaryData> getAllMonetaryDataOnDate(LocalDate date)
            throws IllegalArgumentException {

        if (!MonetaryUtils.isMonetaryDateValid(date)) {
            throw new IllegalArgumentException();
        }

        if (seriesMap.containsKey(date)) {
            Map<String, MonetaryData> currencyMap = seriesMap.get(date);
            return new ArrayList<MonetaryData>(currencyMap.values());
        }
        return new ArrayList<>();
    }

    public List<MonetaryData> getAllMonetaryData() {
        List<MonetaryData> retList = new ArrayList<>();

        for (Map.Entry<LocalDate, Map<String, MonetaryData>> entry : seriesMap.entrySet()) {
            Map<String, MonetaryData> currencyMap = seriesMap.get(entry.getKey());

            for (Map.Entry<String, MonetaryData> currency : currencyMap.entrySet()) {
                retList.add(currency.getValue());
            }
        }
        return retList;
    }

    public List<MonetaryData> getAllMonetaryDataFromCurrency(String currencyCode)
            throws IllegalArgumentException {

        if (!MonetaryUtils.isCurrencyCodeValid(currencyCode)) {
            throw new IllegalArgumentException();
        }

        List<MonetaryData> retList = new ArrayList<>();

        for (Map.Entry<LocalDate, Map<String, MonetaryData>> entry : seriesMap.entrySet()) {
            Map<String, MonetaryData> currencyMap = seriesMap.get(entry.getKey());

            for (Map.Entry<String, MonetaryData> currency : currencyMap.entrySet()) {
                MonetaryData data = currency.getValue();
                if (currencyCode.equals(
                        MonetaryUtils.getMonetaryCodeFromId(data.getMonetaryUnitId()))) {
                    retList.add(data);
                }
            }
        }
        return retList;
    }

    public MonetarySeriesData getMonetarySeriesDataOnDateRange(
            String[] currencyCodes, LocalDate startDate, LocalDate endDate)
            throws IllegalArgumentException {

        if (!MonetaryUtils.isMonetaryDateValid(startDate) ||
            !MonetaryUtils.isMonetaryDateValid(endDate)) {
            throw new IllegalArgumentException();
        }

        for (String currencyCode : currencyCodes) {
            if (!MonetaryUtils.isCurrencyCodeValid(currencyCode)) {
                throw new IllegalArgumentException();
            }
        }

        MonetarySeriesData retSeriesData = new MonetarySeriesData(centralBankId);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Map<String, MonetaryData> currencyMap = seriesMap.get(date);
            for (String currencyCode : currencyCodes) {
                if (currencyMap != null && currencyMap.containsKey(currencyCode)) {
                    retSeriesData.addMonetaryData(currencyMap.get(currencyCode));
                }
            }
        }
        if (retSeriesData.size() == 0) {
            return null;
        }
        return retSeriesData;
    }

    public void createSeriesMap(List<MonetaryData> dataList) {
        clear();

        for (MonetaryData data : dataList) {
            addMonetaryData(data);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<LocalDate, Map<String, MonetaryData>> entry : seriesMap.entrySet()) {
            Map<String, MonetaryData> currencyMap = seriesMap.get(entry.getKey());

            for (Map.Entry<String, MonetaryData> currency : currencyMap.entrySet()) {
                stringBuilder.append(currency.getValue().toString() + "\n");
            }
        }
        return stringBuilder.toString();
    }
}
