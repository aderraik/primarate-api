package com.visiansystems.rate;

import com.visiansystems.ecb.EcbRpcParser;
import com.visiansystems.util.MonetaryUtils;

import java.util.concurrent.TimeUnit;

public class RatesFetcherDaemon implements Runnable {
    private EcbRpcParser ecbRpc;

    public RatesFetcherDaemon(MonetaryUtils monetaryUtils) {

        try {
            ecbRpc = new EcbRpcParser(monetaryUtils);
            ecbRpc.setCacheFileName("/tmp/ecb.xml");
            ecbRpc.refreshCacheFile();

            System.out.println("Cache file location: \t\t" + ecbRpc.getCacheFileName());
            System.out.println("Reference date: \t\t\t" + ecbRpc.getReferenceDate());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                ecbRpc.parse();
//                MonetarySeriesData monetarySeriesData = ecbRpc.getMonetarySeriesData();
//                System.out.println("-->" + monetarySeriesData);
//                System.out.println(monetarySeriesData.size());
                TimeUnit.HOURS.sleep(6);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
