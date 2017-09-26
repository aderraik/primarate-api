package com.visiansystems.bl.bankRateFeed.bcb;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.namespace.QName;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;

/**
 * This class represents the remote procedure call (RPC) that talks with the Brazilian Central
 * Bank (BCB) via a remote Web Service Definition Language (WSDL). It provides several methods that
 * returns exchange rates in the various different formats.
 * The rates are updated at 13:00 GMT-3 on working days.
 * <p/>
 * For more information about the remote web service, please visit:
 *
 * @see <a href="http://www4.bcb.gov.br/pec/series/port/aviso.asp">SERVICE</a>
 * @see <a href="http://catalogo.governoeletronico.gov.br/arquivos/Documentos/WS_SGS_BCB.pdf">API</a>
 */
public class BcbRpc {
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final String wsdl = "bl/bankRateFeed/bcb/bcb.wsdl";
    private static final String tns = "https://www3.bcb.gov.br/wssgs/services/FachadaWSSGS";
    private static final String serviceName = "FachadaWSSGSService";
    private static final String portName = "FachadaWSSGS";
    private RPCServiceClient client;

    @SuppressWarnings("rawtypes")
    private Class types[] = new Class[1];

    @Autowired
    private Logger logger;

    public BcbRpc() throws FileNotFoundException {
        final QName qname = new QName(tns, serviceName);
        final URL url = this.getClass().getClassLoader().getResource(wsdl);

        if (url == null) {
            throw new FileNotFoundException("File " + wsdl + " not found.");
        }

        try {
            client = new RPCServiceClient(null, url, qname, portName);

            Options option = client.getOptions();
            option.setProperty(HTTPConstants.USER_AGENT, "org.visian.BcbClient");
            option.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
            option.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Boolean.TRUE);
            option.setProperty(HTTPConstants.SO_TIMEOUT, 30000);
            option.setProperty(HTTPConstants.CONNECTION_TIMEOUT, 30000);
            option.setTimeOutInMilliSeconds(2000);

            types[0] = Class.forName("java.lang.String");
        }
        catch (ClassNotFoundException | AxisFault e) {
            logger.error(e);
        }
    }

    /**
     * Returns the last value of a series in the XML format. It contains the tags:
     * <NOME>, <CODIGO>, <PERIODICIDADE>, <UNIDADE>, <DATA>, and <VALOR>.
     *
     * @param code Numeric code of the desired series.
     * @return String XML document requested.
     */
    public String requestLastRateXml(long code) {
        Object parameters[] = new Object[] { code };
        return invoke(new QName(tns, "getUltimoValorXML"), parameters);
    }

    /**
     * Returns the exchange rate of a series on the specified date (dd/MM/yyyy).
     *
     * @param code Numeric code of the desired series.
     * @param date LocalDate in the format dd/MM/yyyy.
     * @return String Requested exchange rate.
     */
    public String requestRateOnDate(long code, String date) {
        Object[] parameters = new Object[] { code, date };
        return invoke(new QName(tns, "getValor"), parameters);
    }

    /**
     * Returns values of one or more series on a date range. The output result is on the
     * XML format. The returned list might be in a different order than the one passed
     * on the input.
     *
     * @param codes       List of numeric series code.
     * @param initialDate Initial date in the format dd/MM/yyyy.
     * @param finalDate   Final date in the format dd/MM/yyyy.
     * @return String Requested exchange rate.
     */
    public String requestRatesOnDateRange(long codes[], String initialDate, String finalDate) {
        Object[] parameters = new Object[] { codes, initialDate, finalDate };
        return invoke(new QName(tns, "getValoresSeriesXML"), parameters);
    }

    /**
     * Responsible for the remote procedure call.
     *
     * @param procedure  QName of the remote procedure.
     * @param parameters Array containing the request parameters.
     * @return String containing the requested XML document if the call was successful or
     * null otherwise.
     */
    private String invoke(QName procedure, Object[] parameters) {
        String result = null;

        try {
            result = (String)client.invokeBlocking(procedure, parameters, types)[0];
        }
        catch (AxisFault e) {
            // Some bcb procedures does not return null
            result = null;

            StringBuilder stringBuilder = new StringBuilder(e.getMessage());
            String NEWLINE = System.getProperty("line.separator");
            stringBuilder.append(NEWLINE).append(NEWLINE).append("Exception got at: \"")
                    .append(Thread.currentThread().getStackTrace()[1].getMethodName())
                    .append("\" with the following parameters: ").append(NEWLINE).append(NEWLINE)
                    .append(" - procedure: ").append(procedure.getLocalPart())
                    .append(NEWLINE).append(" - parameters:");
            for (Object par : parameters) {
                stringBuilder.append(' ');
                if (par instanceof String) {
                    stringBuilder.append((String)par);
                }
                else if (par instanceof Long) {
                    stringBuilder.append((Long)par);
                }
                else if (par.getClass().isArray()) {
                    stringBuilder.append(Arrays.toString((long[])par));
                }
                else {
                    stringBuilder.append("UNKNOWN");
                }
            }
            stringBuilder.append(NEWLINE);

            logger.error(stringBuilder.toString());
        }
        return result;
    }
}
