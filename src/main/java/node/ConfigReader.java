package node;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    public static final String DEFAULT_PROP_FILE_NAME = "config.conf";

    public Properties getPropValues() throws IOException {
        return getPropValues(DEFAULT_PROP_FILE_NAME);
    }

    public Properties getPropValues(String fileName) throws IOException {
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName) ) {
            if (inputStream != null) {
                Properties prop = new Properties();
                prop.load(inputStream);
                return prop;
            } else {
                throw new FileNotFoundException("Config file '" + fileName + "' not found in the classpath");
            }
        }
    }
}
