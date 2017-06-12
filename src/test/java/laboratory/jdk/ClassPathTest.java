package laboratory.jdk;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassPathTest {
//	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ClassPathTest.class);
	
	@Test
	public void testGetClasspaths() {
		URL[] url = ((URLClassLoader) (Thread.currentThread().getContextClassLoader())).getURLs();
		log.debug(Arrays.toString(url));
	}
	
	@Test
	public void testAccessFileAtClasspath() throws URISyntaxException, IOException {
		ClassLoader loader = ClassPathTest.class.getClassLoader();
		URL url = loader.getResource("file/exist-test.txt");
		File file = Paths.get(url.toURI()).toFile();
		Assert.assertTrue(file.exists());
	}
	
	@Test
	public void testGetResource() {
		URL url = this.getClass().getResource("");
		log.debug(String.valueOf(url.toString())); // file:/C:/dev/git/laboratory/target/test-classes/laboratory/jdk/
	}
}
