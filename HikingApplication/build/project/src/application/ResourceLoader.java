package application;

import java.io.File;
import java.io.InputStream;

final public class ResourceLoader {
	
	public static InputStream load(String picList) {
		
		InputStream input = ResourceLoader.class.getResourceAsStream(picList);
		if ( input == null) {
			
			input = ResourceLoader.class.getResourceAsStream("/"+picList);
		}
		return input;
	}

}
