package node;

import java.util.Date;

public class ServerInfo {
    public static String getServerInfo(boolean showOS, boolean showLocaltime, int port) {
        String servInfo = "FMP node at port "+port;
        if(showOS) {
            servInfo = servInfo + " on " + System.getProperty("os.name");
        }
        if(showLocaltime) {
            servInfo = servInfo + ", server datetime: " + new Date().toString();
        }
        return servInfo;
    }
}
