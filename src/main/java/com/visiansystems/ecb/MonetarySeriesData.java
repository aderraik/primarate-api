package com.visiansystems.ecb;

import com.visiansystems.rates.Rate;
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
    private Map<LocalDate, Map<String, Rate>> seriesMap;

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
        Map<String, Rate> currencyMap = seriesMap.get(date);
        if (currencyMap != null) {
            return currencyMap.size();
        }
        return 0;
    }

    public int getTotalRates() {
        int total = 0;

        for (Map.Entry<LocalDate, Map<String, Rate>> entry : seriesMap.entrySet()) {
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

        List<Rate> dataList = getAllMonetaryData();
        for (Rate data : dataList) {
            data.setCentralBankId(centralBankId);
        }
        createSeriesMap(dataList);
    }

    @CallLogging(CallLogging.Level.INFO)
    public void addMonetaryData(Rate rate) throws IllegalArgumentException {
//        if (rate != null &&
//            rate.isValid() &&
//            rate.getCentralBankId() == centralBankId) {
//            LocalDate date = rate.getDate();
////            String currencyCode = MonetaryUtils.getMonetaryCodeFromId(rate.getMonetaryUnitId());
//            String currencyCode = "ZZZ";
//
//            if (startDate == null || date.isBefore(startDate)) {
//                startDate = date;
//            }
//            if (endDate == null || date.isAfter(endDate)) {
//                endDate = date;
//            }
//
//            Map<String, Rate> currencyMap;
//            if (seriesMap.containsKey(date)) {
//                currencyMap = seriesMap.get(date);
//                seriesMap.remove(date);
//            }
//            else {
//                currencyMap = new HashMap<>();
//            }
//            currencyMap.put(currencyCode, rate);
//            seriesMap.put(date, currencyMap);
//        }
//        else {
//            throw new IllegalArgumentException();
//        }
    }

    @CallLogging(CallLogging.Level.INFO)
    public void addMonetaryData(String currencyCode, LocalDate date, double amount) {
//        if (!MonetaryUtils.isMonetaryDateValid(date)) {
//            throw new IllegalArgumentException();
//        }

//        long monetaryUnitId = MonetaryUtils.getCurrencyId(currencyCode);
//        addMonetaryData(new Rate(centralBankId, monetaryUnitId, date, amount));
    }

    @CallLogging(CallLogging.Level.INFO)
    public void addMonetarySeriesData(MonetarySeriesData seriesData) {
        for (Rate data : seriesData.getAllMonetaryData()) {
            addMonetaryData(data);
        }
    }

    public Rate getMonetaryDataOnDate(LocalDate date, String currencyCode)
            throws IllegalArgumentException {

//        if (!MonetaryUtils.isMonetaryDateValid(date) ||
//            !MonetaryUtils.isCurrencyCodeValid(currencyCode)) {
//            throw new IllegalArgumentException();
//        }

        if (seriesMap.containsKey(date)) {
            Map<String, Rate> currencyMap = seriesMap.get(date);
            if (currencyMap.containsKey(currencyCode)) {
                return currencyMap.get(currencyCode);
            }
        }
        return null;
    }

    public List<Rate> getAllMonetaryDataOnDate(LocalDate date)
            throws IllegalArgumentException {

//        if (!MonetaryUtils.isMonetaryDateValid(date)) {
//            throw new IllegalArgumentException();
//        }

        if (seriesMap.containsKey(date)) {
            Map<String, Rate> currencyMap = seriesMap.get(date);
            return new ArrayList<Rate>(currencyMap.values());
        }
        return new ArrayList<>();
    }

    public List<Rate> getAllMonetaryData() {
        List<Rate> retList = new ArrayList<>();

        for (Map.Entry<LocalDate, Map<String, Rate>> entry : seriesMap.entrySet()) {
            Map<String, Rate> currencyMap = seriesMap.get(entry.getKey());

            for (Map.Entry<String, Rate> currency : currencyMap.entrySet()) {
                retList.add(currency.getValue());
            }
        }
        return retList;
    }

    public List<Rate> getAllMonetaryDataFromCurrency(String currencyCode)
            throws IllegalArgumentException {

//        if (!MonetaryUtils.isCurrencyCodeValid(currencyCode)) {
//            throw new IllegalArgumentException();
//        }

        List<Rate> retList = new ArrayList<>();

        for (Map.Entry<LocalDate, Map<String, Rate>> entry : seriesMap.entrySet()) {
            Map<String, Rate> currencyMap = seriesMap.get(entry.getKey());

            for (Map.Entry<String, Rate> currency : currencyMap.entrySet()) {
                Rate data = currency.getValue();
//                if (currencyCode.equals(
//                        MonetaryUtils.getMonetaryCodeFromId(data.getMonetaryUnitId()))) {
//                    retList.add(data);
//                }
            }
        }
        return retList;
    }

    public MonetarySeriesData getMonetarySeriesDataOnDateRange(
            String[] currencyCodes, LocalDate startDate, LocalDate endDate)
            throws IllegalArgumentException {

//        if (!MonetaryUtils.isMonetaryDateValid(startDate) ||
//            !MonetaryUtils.isMonetaryDateValid(endDate)) {
//            throw new IllegalArgumentException();
//        }

        for (String currencyCode : currencyCodes) {
//            if (!MonetaryUtils.isCurrencyCodeValid(currencyCode)) {
//                throw new IllegalArgumentException();
//            }
        }

        MonetarySeriesData retSeriesData = new MonetarySeriesData(centralBankId);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Map<String, Rate> currencyMap = seriesMap.get(date);
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

    public void createSeriesMap(List<Rate> dataList) {
        clear();

        for (Rate data : dataList) {
            addMonetaryData(data);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<LocalDate, Map<String, Rate>> entry : seriesMap.entrySet()) {
            Map<String, Rate> currencyMap = seriesMap.get(entry.getKey());

            for (Map.Entry<String, Rate> currency : currencyMap.entrySet()) {
                stringBuilder.append(currency.getValue().toString() + "\n");
            }
        }
        return stringBuilder.toString();
    }
}
