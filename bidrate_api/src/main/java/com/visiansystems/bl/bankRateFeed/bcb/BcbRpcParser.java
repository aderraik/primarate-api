package com.visiansystems.bl.bankRateFeed.bcb;

import com.visiansystems.model.MonetaryData;
import com.visiansystems.model.MonetarySeriesData;
import com.visiansystems.util.ExcludeFilter;
import com.visiansystems.util.MonetaryUtils;
import com.visiansystems.util.logger.CallLogging;
import com.visiansystems.util.logger.ReturnLogging;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.stream.*;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.time.LocalDate;

/**
 * Has the responsibility to request and parse the remote procedure calls and to construct and
 * return monetary objects.
 */
@Component
public class BcbRpcParser extends BcbRpc {
    public static final String BAD_ENTITY = "&([^;\\W]*([^;\\w]|$))";
    public static final String FIX_BAD_ENTITY = "&amp;$1";
    private XMLInputFactory inputFactory;
    private BcbDateFormat dateFormat;

    @Autowired
    private Logger logger;

    public BcbRpcParser() throws FileNotFoundException {
        super();
    }

    @PostConstruct
    public void init() {
        dateFormat = new BcbDateFormat();
        try {
            inputFactory = XMLInputFactory.newInstance();
            inputFactory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
            inputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
        }
        catch (FactoryConfigurationError e) {
            logger.error(e);
        }
    }

    @CallLogging(CallLogging.Level.INFO)
    @ReturnLogging(ReturnLogging.Level.INFO)
    public MonetaryData requestLastRate(String currencyCode) {

        MonetaryData monetaryData = null;

        try {
            String xml = requestLastRateXml(MonetaryUtils.getBcbMonetaryIdFromCode(currencyCode));
            XMLStreamReader rd = inputFactory.createFilteredReader(
                    inputFactory.createXMLStreamReader(
                            new StringReader(xml.replaceAll(BAD_ENTITY, FIX_BAD_ENTITY))),
                    new ExcludeFilter(
                            "resposta", "SERIE", "NOME", "PERIODICIDADE", "UNIDADE"));

            String sCode = rd.getElementText();
            rd.next();

            String sDates[] = new String[] { null, null };
            int j = 0;
            do {
                rd.next();

                char[] buf = new char[10];
                int pos = 0;
                for (int i = 0; i < 3; ++i) {
                    char[] s = rd.getElementText().toCharArray();
                    rd.next();
                    System.arraycopy(s, 0, buf, pos, s.length);
                    pos += s.length;
                    if (i < 2) {
                        buf[pos++] = BcbDateFormat.DATE_FIELDS_SEPARATOR;
                    }
                }

                sDates[j++] = new String(buf, 0, pos);
                rd.next();
            } while (!rd.getLocalName().equals("VALOR"));

            String sValue = rd.getElementText();
            rd.close();

            monetaryData = createMonetaryData(sCode, sDates[0], sValue);
        }
        catch (XMLStreamException | NullPointerException ignored) {
        }
        return monetaryData;
    }

    @CallLogging(CallLogging.Level.INFO)
    public MonetarySeriesData requestRatesOnDateRange(
            String currencyCodes[], String initialDate, String finalDate) {

        MonetarySeriesData seriesData = new MonetarySeriesData(MonetaryUtils.BCB_CENTRAL_BANK_ID);

        try {
            long[] lcodes = new long[currencyCodes.length];

            for (int i = 0; i < currencyCodes.length; i++) {
                lcodes[i] = MonetaryUtils.getBcbMonetaryIdFromCode(currencyCodes[i]);
            }

            String xml = requestRatesOnDateRange(lcodes, initialDate, finalDate);
            XMLStreamReader rd = inputFactory.createFilteredReader(
                    inputFactory.createXMLStreamReader(new StringReader(xml)),
                    new ExcludeFilter("SERIES", "ITEM", "BLOQUEADO"));

            do {
                String sCode = rd.getAttributeValue(null, "ID");

                while (rd.next() == XMLStreamConstants.START_ELEMENT
                       && rd.getLocalName().equals("DATA")) {

                    String[] sDates = new String[] { null, null };
                    for (int j = 0; !rd.getLocalName().equals("VALOR"); ++j) {
                        sDates[j] = dateFormat.complete(rd.getElementText());
                        rd.next();
                    }

                    String sValue = rd.getElementText();

                    if (sValue.length() > 0) {
                        seriesData.addMonetaryData(
                                createMonetaryData(sCode, sDates[0], sValue));
                    }
                }
                rd.next();
            } while (rd.isStartElement() && rd.getLocalName().equals("SERIE"));

            seriesData.setStartDate(dateFormat.parse(initialDate));
            seriesData.setEndDate(dateFormat.parse(finalDate));
            rd.close();

        }
        catch (XMLStreamException | NullPointerException ignored) {
        }
        return seriesData;
    }

    private MonetaryData createMonetaryData(String sCode, String sDate, String sValue) {
        int bcbId = Integer.parseInt(sCode);
        long id = MonetaryUtils.getBcbMonetaryCodeFromId(bcbId);
        double rate = Double.parseDouble(sValue.replace(",", "."));
        LocalDate date = dateFormat.parse(sDate);

        return new MonetaryData(MonetaryUtils.BCB_CENTRAL_BANK_ID, id, date, rate);
    }
}
