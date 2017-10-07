package com.visiansystems.ecb;

import com.visiansystems.util.MonetaryUtils;
import com.visiansystems.util.logger.CallLogging;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.visiansystems.util.logger.ReturnLogging;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EcbRpcParser extends EcbRpc {
    private SimpleDateFormat dateFormat;
    private Date parseDate; //TODO: Remove?
    private MonetarySeriesData seriesData;
    private long centralBankId;

    @Autowired
    private Logger logger;

    public EcbRpcParser() {
        super();
        seriesData = new MonetarySeriesData(centralBankId);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm z");
    }

    public void update() {
        try {
            if (referenceDate == null) {
                initCacheFile();
                if (!cacheFile.exists()) {
                    refreshCacheFile();
                }
            }
            if (isCacheExpired()) {
                refreshCacheFile();
                parse();
            }
        }
        catch (Exception e) {
            logger.warn(e);
        }
    }

    public void clear() {
        seriesData.clear();
    }

    @CallLogging(CallLogging.Level.INFO)
    @ReturnLogging(ReturnLogging.Level.INFO)
    public void parse() throws ParseException, IOException, SAXException {
        FileReader input = new FileReader(cacheFile);
        XMLReader saxReader = XMLReaderFactory.createXMLReader();

        seriesData.clear();

        DefaultHandler handler = new DefaultHandler() {

            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                MonetaryData data = new MonetaryData();

                if (localName.equals("Cube")) {
                    String date = attributes.getValue("time");

                    if (date != null) {
                        try {
                            parseDate = dateFormat.parse(date + " 14:15 CET");
                            System.out.println("\nParsing date - " + parseDate);

                            if (referenceDate == null || parseDate.after(referenceDate)) {
                                referenceDate = parseDate;
                            }
                        }
                        catch (ParseException ignored) {
                        }
                    }

                    String amount = attributes.getValue("rate");
                    String currencyCode = attributes.getValue("currency");

                    if (amount != null && currencyCode != null) {
//                        System.out.println(amount + ", " + currencyCode);
                        System.out.println(currencyCode);

                        data.setAmount(Double.parseDouble(amount));
                        data.setMonetaryUnitId(MonetaryUtils.getMonetaryIdFromCode(currencyCode));
                        data.setCentralBankId(centralBankId);
//                        data.setDate(new LocalDate(parseDate));

//                        seriesData.addMonetaryData(data);
                    }
                }
            }
        };
        saxReader.setContentHandler(handler);
        saxReader.setErrorHandler(handler);
        saxReader.parse(new InputSource(input));
        input.close();
    }

    public void setCentralBankId(long centralBankId) {
        this.centralBankId = centralBankId;
    }

    public MonetarySeriesData getMonetarySeriesData() {
        return seriesData;
    }
}
