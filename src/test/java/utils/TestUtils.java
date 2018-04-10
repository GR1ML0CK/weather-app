package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestUtils {

  /**
   * Gets mock json data from a file in the test resource.
   * @param path the relative path to the file to get content from
   * @return a json string generated from /test/resources/*.json
   */
  public static String getMockJsonString(String path) {
    URL url = TestUtils.class.getClassLoader().getResource(path);
    try {
      if (url != null) {
        Path resPath = java.nio.file.Paths.get(url.toURI());
        return new String(Files.readAllBytes(resPath), "UTF8");
      }
    } catch (URISyntaxException | IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
