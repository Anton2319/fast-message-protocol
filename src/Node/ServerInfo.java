package Node;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class ServerInfo {
    public static String getServerInfo(boolean showOS, boolean showLocaltime, int port) {
        String servInfo = "Freesocial node at port "+port;
        if(showOS) {
            servInfo = servInfo + " on " + System.getProperty("os.name");
        }
        if(showLocaltime) {
            servInfo = servInfo + ", server datetime: " + new Date().toString();
        }
        return servInfo;
    }
}
