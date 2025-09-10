package co.com.training.web.config.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertiesFile {

    private static final Logger LOGGER = LogManager.getLogger(ReadPropertiesFile.class.getName());
    private String path;

    private ReadPropertiesFile(String path) {
        this.path = path;
    }

    public static ReadPropertiesFile getInstance(String path){
        return new ReadPropertiesFile(path);
    }

    public Properties getProperties(){
        Properties properties = new Properties();
        File file = new File(path);
        FileInputStream fileInputStream= null;
        try {
            fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
        } catch (IOException e) {
            LOGGER.error(e);
        }finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                   LOGGER.error(e);
                }
            }
        }
        return properties;
    }


}
