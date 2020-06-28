package qaFramework.UserDefinedFunctions;

import java.io.InputStream;
import java.util.Properties;

public class ReadPropertyFiles {
	
	public Properties ReadEnvironment() throws Exception {
		Properties environmentProps = new Properties();
		String propertiesFilename = System.getProperty("path.file",
				"/PropertiesFiles/environment");
		InputStream is = this.getClass().getResourceAsStream(propertiesFilename);
		environmentProps.load(is);
		return environmentProps;
	}

	
}
