package Node;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    InputStream inputStream;

    public Properties getPropValues() throws IOException {
        Properties prop = new Properties();
        try {
            String propFileName = "config.conf";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Config file '" + propFileName + "' not found in the classpath");
            }
        } catch (Exception e) {
            System.out.println("Unable to read config: " + e);
        } finally {
            inputStream.close();
        }
        return prop;
    }
}
