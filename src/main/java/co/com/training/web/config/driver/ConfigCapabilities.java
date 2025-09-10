package co.com.training.web.config.driver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class ConfigCapabilities {

    private Optional<Capabilities> capabilities;
    private static final String FILE_SEPARATOR = File.separator;
    public static final String PATH_RESOURCES = String.format("src%stest%sresources%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR);

    ConfigCapabilities() {
        this.capabilities = Optional.empty();
    }

    DesiredCapabilities loadCapabilities() {
        String fileName = PATH_RESOURCES + "web.properties";
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        ReadPropertiesFile properties = ReadPropertiesFile.getInstance(fileName);
        properties.getProperties().entrySet().iterator().forEachRemaining(entry -> {
                    String property = entry.getKey().toString();
                    String value = entry.getValue().toString();
                    desiredCapabilities.setCapability(property, value);
                    if(property.equals("args")){
                        desiredCapabilities.setCapability("args", argsToList(value));
                    }
                }
        );
        if(this.capabilities.isPresent()){
            desiredCapabilities.merge(capabilities.get());
        }
        return desiredCapabilities;
    }

    private List<String> argsToList(String args){
        String[] argsArray = args.split(",");
        return Arrays.asList(argsArray);
    }
}
