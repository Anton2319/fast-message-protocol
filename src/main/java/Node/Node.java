package Node;

//import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
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
            System.out.println("Node started at port " + port);
            while(true) {
                try(Socket socket = serverSocket.accept()){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    String command = reader.readLine();
                    switch (command) {
                        case "getuserbyid":
                            int userid = Integer.parseInt(reader.readLine());
                            ArrayList<Map<String, String>> line = db.query("SELECT username, wall_id, public_key FROM `users` WHERE id = " + userid);

                            String username = DBConnector.getRowValue(line, "username")
                                    .orElseThrow(() -> new RuntimeException("Cannot find user by id = " + userid));

                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("username", username);
                            DBConnector.getRowValue(line, "wall_id")
                                    .ifPresent(v -> jsonObject.put("wall_id", v));
                            DBConnector.getRowValue(line, "public_key")
                                    .ifPresent(v -> jsonObject.put("public_key", v));
                            writer.println(JSONObject.quote(jsonObject.toString()));
                            break;
                        case "test":
                            writer.println(ServerInfo.getServerInfo(showOS, showLocaltime, port));
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            System.out.println("Cannot start node");
            e.printStackTrace();
            System.exit(1);
        }
    }


}
