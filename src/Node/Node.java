package Node;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Node extends Thread {
    public void run() {
        System.out.println("Reading configs...");
        int port = 6868;
        String url = null;
        String user = null;
        String password = null;
        Boolean showOS = false;
        Boolean showLocaltime = false;
        ConfigReader configReader = new ConfigReader();
        try {
            Properties properties = configReader.getPropValues();
            port = Integer.parseInt(properties.getProperty("port"));
            url = properties.getProperty("jdbcURL");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            showOS = Boolean.parseBoolean(properties.getProperty("showOS"));
            showLocaltime = Boolean.parseBoolean(properties.getProperty("showLocaltime"));
        } catch (IOException e) {
            System.out.println("Error while reading the config");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Staring up freesocial node");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            DBConnector db = new DBConnector(url, user, password);
            System.out.println("Node started at port "+port);
            while(true) {
                Socket socket = serverSocket.accept();
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                PrintWriter writer = new PrintWriter(output, true);
                if(reader.readLine().equals("test")) {
                    writer.println(ServerInfo.getServerInfo(showOS, showLocaltime, port));
                    socket.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot start node");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
