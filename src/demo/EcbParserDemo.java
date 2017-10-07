import com.visiansystems.ecb.EcbRpc;
import com.visiansystems.ecb.EcbRpcParser;
import com.visiansystems.ecb.MonetarySeriesData;

import java.io.PrintStream;

public class EcbParserDemo {
    public static void main(String[] args) {

        try {
            final PrintStream out = System.out;


            EcbRpcParser ecbRpc = new EcbRpcParser();
            ecbRpc.setCacheFileName("/tmp/ecb.xml");
            ecbRpc.refreshCacheFile();

            out.println("Cache file location: \t\t" + ecbRpc.getCacheFileName());
            out.println("Reference date: \t\t\t" + ecbRpc.getReferenceDate());

            ecbRpc.parse();
            MonetarySeriesData monetarySeriesData = ecbRpc.getMonetarySeriesData();
            out.println("-->" + monetarySeriesData);
            out.println(monetarySeriesData.size());

            out.println("Done!\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
