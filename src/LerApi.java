import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LerApi {
    public static Properties getProp() throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream(
                "src/api.properties");
        props.load(file);
        return props;
    }


}
