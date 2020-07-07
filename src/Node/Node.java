package Node;

import java.io.IOException;
import java.net.ServerSocket;

public class Node extends Thread {
    public void run() {
        System.out.println("Staring up freesocial node");
        try {
            ServerSocket serverSocket = new ServerSocket(6868);
            DBConnector db = new DBConnector("jdbc:mysql://localhost:3306/anyjdbcurl", "admin", "123456");
        } catch (Exception e) {
            System.out.println("Cannot start node");
            e.printStackTrace();
        }
    }
}
