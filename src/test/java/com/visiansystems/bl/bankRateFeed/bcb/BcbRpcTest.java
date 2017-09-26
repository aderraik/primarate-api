package com.visiansystems.bl.bankRateFeed.bcb;

import com.google.common.math.DoubleMath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This test is an integration based test.
 * It represents a benchmark test which compares previously saved exchangeRates with the ones
 * returned by the BcbRpc API.
 * <p/>
 * Warning: This is not a proper unit test to the class "BcbRpc". That means to it successfully pass
 * it needs to the remote server to be working properly (BCB Webserver).
 */
public class BcbRpcTest {
    private static final String COMMA_DELIMITER = ",";
    private static final String USD_TO_BRL_FULL_FILE =
            "src/test/resources/bcb/usd_to_brl-full.csv";
    private static final String USD_TO_BRL_SHORT_FILE =
            "src/test/resources/bcb/usd_to_brl-short.csv";
    private static final int USD_CODE = 10813;
    private static final double TOLERANCE = 0.0001;
    private BcbRpc underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new BcbRpc();
    }

    /**
     * Warning: This test might be very slow! It's ignored by default.
     */
    @Test
    @Ignore
    public void UsdToBrlFullTest() {
        Assert.assertTrue(verifyBenchmarkFile(USD_TO_BRL_FULL_FILE, USD_CODE));
    }

    @Test
    public void UsdToBrlShortTest() {
        Assert.assertTrue(verifyBenchmarkFile(USD_TO_BRL_SHORT_FILE, USD_CODE));
    }

    private boolean verifyBenchmarkFile(String fileName, int currencyCode) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
            fileReader.readLine();
            String line = "";

            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(COMMA_DELIMITER);

                if (tokens.length > 0) {
                    System.out.println(tokens[0] + " | " + tokens[1] + " | " + tokens[2]);

                    double rateExpected = Double.valueOf(tokens[1]);
                    double rateReturned = Double.valueOf(
                            underTest.requestRateOnDate(currencyCode, tokens[0]));

                    if (DoubleMath.fuzzyCompare(rateExpected, rateReturned, TOLERANCE) != 0) {
                        return false;
                    }
                }
            }
            return true;
        }
        catch (Exception ignored) {
            return false;
        }
    }
}
