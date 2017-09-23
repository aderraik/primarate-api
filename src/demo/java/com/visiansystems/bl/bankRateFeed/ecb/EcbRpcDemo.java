package com.visiansystems.bl.bankRateFeed.ecb;

import java.io.PrintStream;

public class EcbRpcDemo {
    public static void main(String[] args) {

        try {
            final PrintStream out = System.out;

            out.println("<< ECB Remote Procedure Call Demo >>\n");

            EcbRpc ecbRpc = new EcbRpc();

            out.println(ecbRpc.getCacheFileName());
            ecbRpc.setCacheFileName("/tmp/ecb.xml");
            out.println(ecbRpc.getCacheFileName());
            out.println(ecbRpc.getReferenceDate());
            out.println(ecbRpc.isCacheExpired());
            ecbRpc.refreshCacheFile();
            out.println("Done!\n");
        }
        catch (Exception ingnored) {
        }
    }
}
