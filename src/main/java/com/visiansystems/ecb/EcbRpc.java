package com.visiansystems.ecb;

import com.visiansystems.util.MonetaryUtils;
import com.visiansystems.util.logger.CallLogging;
import com.visiansystems.util.logger.ReturnLogging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;

/**
 * CurrencyConverter provides an API for accessing the European Central Bank's
 * (ECB) foreign exchange rates. The published ECB rates contain exchange rates
 * for approx. 35 of the world's major currencies. They are updated daily at
 * 14:15 CET. These rates use EUR as reference currency and are specified with a
 * precision of 1/10000 of the currency unit (one hundredth cent). See:
 * <p/>
 * http://www.ecb.int/stats/exchange/eurofxref/html/index.en.html
 * <p/>
 * The convert() method performs currency conversions using either double values
 * or 64-bit long integer values. Long values are preferred in order to avoid
 * problems associated with floating point arithmetics. A local cache file is
 * used for storing exchange rates to reduce network latency. The cache file is
 * updated automatically when new exchange rates become available. It is
 * created/updated the first time a call to convert() is made.
 */
public class EcbRpc {
    protected Date referenceDate = null;
    transient protected File cacheFile = null;
    protected String cacheFileName = null;
    private String ecbRatesURL;
    private String lastError = null;

    //TODO: Move this property to the context
    public EcbRpc() {
        //        selectFullRates();
                selectLastDayRates();
//        selectLast90dRates();
        initCacheFile();
    }

    public void selectLastDayRates() {
        ecbRatesURL = EcbRatesFilename.LASTDAY_RATES.filename;
    }

    public void selectFullRates() {
        ecbRatesURL = EcbRatesFilename.FULL_RATES.filename;
    }

    public void selectLast90dRates() {
        ecbRatesURL = EcbRatesFilename.LAST90D_RATES.filename;
    }

    /**
     * Initialises cache file member variable if not already initialised.
     */
    protected void initCacheFile() {
        if (cacheFile == null) {
            if (cacheFileName == null || cacheFileName.equals("")) {
                cacheFileName = System.getProperty("java.io.tmpdir") + "/ExchangeRates.xml";
            }
        }
        cacheFile = new File(cacheFileName);
    }

    /**
     * Get the name of the fully qualified path name of the XML cache file. By
     * default this is a file named "ExchangeRates.xml" located in the system's
     * temporary file directory. The cache file can be shared by multiple
     * threads/applications.
     *
     * @return Path name of the XML cache file.
     */
    public String getCacheFileName() {
        return cacheFileName;
    }

    /**
     * Set the location where the XML cache file should be stored.
     *
     * @param cacheFileName
     * @see #getCacheFileName() Fully qualified path name of the XML cache file.
     */
    public void setCacheFileName(String cacheFileName) {
        this.cacheFileName = cacheFileName;
    }

    /**
     * Delete XML cache file and reset internal data structure. Calling
     * clearCache() before the convert() method forces a fresh downloadRate of the
     * currency exchange rates.
     */
    public void clearCache() {
        initCacheFile();
        cacheFile.delete();
        cacheFile = null;
        referenceDate = null;
    }

    /**
     * Get the reference date for the exchange rates as a Java Date. The time
     * part is always 14:15 Central European Time (CET).
     *
     * @return LocalDate for which currency exchange rates are valid, or null if the
     * data structure has not yet been initialised.
     */
    public Date getReferenceDate() {
        return referenceDate;
    }

    /**
     * Checks whether XML cache file needs to be updated. The cache file is up
     * to date for 24 hours after the reference date (plus a certain tolerance).
     * On weekends, it is 72 hours because no rates are published during
     * weekends.
     *
     * @return true if cache file needs to be updated, false otherwise.
     */
    public boolean isCacheExpired() {
//                return !MonetaryUtils.isRateFeedUpToDate(referenceDate, MonetaryUtils.ECB_CENTRAL_BANK_ID);
        return false;
    }

    /**
     * (Re-) downloadRate the XML cache file and store it in a temporary location.
     *
     * @throws IOException If (1) URL cannot be opened, or (2) if cache file cannot
     *                     be opened, or (3) if a read/write error occurs.
     */
    @CallLogging(CallLogging.Level.INFO)
    @ReturnLogging(ReturnLogging.Level.INFO)
    public void refreshCacheFile() throws IOException {
        lastError = null;
        initCacheFile();
        InputStreamReader in;
        FileWriter out;
        try {
            URL ecbRates = new URL(ecbRatesURL);
            in = new InputStreamReader(ecbRates.openStream());
            out = new FileWriter(cacheFile);
            try {
                int c;
                while ((c = in.read()) != -1)
                    out.write(c);
            }
            catch (IOException e) {
                lastError = "Read/Write Error: " + e.getMessage();
            }
            finally {
                out.flush();
                out.close();
                in.close();
            }
        }
        catch (IOException e) {
            lastError = "Connection/Open Error: " + e.getMessage();
        }
        if (lastError != null) {
            throw new IOException(lastError);
        }
    }

    public enum EcbRatesFilename {
        FULL_RATES("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist.xml"),
        LASTDAY_RATES("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"),
        LAST90D_RATES("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml");

        private String filename;

        private EcbRatesFilename(String filename) {
            this.filename = filename;
        }
    }
}
