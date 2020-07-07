package Node;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

public class Node extends Thread {
    public void run() {
        System.out.println("Reading configs...");
        int port = 6868;
        String url = null;
        String user = null;
        String password = null;
        ConfigReader configReader = new ConfigReader();
        try {
            Properties properties = configReader.getPropValues();
            port = Integer.parseInt(properties.getProperty("port"));
            url = properties.getProperty("jdbcURL");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (IOException e) {
            System.out.println("Error while reading the config");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Staring up freesocial node");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            DBConnector db = new DBConnector(url, user, password);
            System.out.println("Node started");
        } catch (Exception e) {
            System.out.println("Cannot start node");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
