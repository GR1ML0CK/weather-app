package com.asuscomm.gr1ml0ck.weatherapp.utils;

import com.asuscomm.gr1ml0ck.weatherapp.models.WindConditions;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.mrbean.AbstractTypeMaterializer;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;
import java.io.IOException;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class JsonMapper extends ObjectMapper {
  private static final Logger log = LoggerFactory.getLogger(JsonMapper.class);

  private static final String WIND = "wind";

  private boolean isValid = false;

  @Autowired
  public JsonMapper() {
    ClassLoader classLoader = getSpringClassLoader();
    if (classLoader != null) isValid = true;

    MrBeanModule mrBeanModule = getMrBean(classLoader);

    super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(mrBeanModule);
  }

  /**
   * True if the ObjectMapper is valid.
   * If something caused initialization to fail this will be false (this shouldn't happen).
   * @return true if valid
   */
  @Bean
  public boolean isValid() {
    return isValid;
  }

  /**
   * Deserializes a json string into a WindConditions object. "wind" is one of the properties
   * of the json object retrieved from the API, but since we only need "wind" we need to
   * traverse down the tree to get only "wind".
   *
   * @param json the json string to deserialize
   * @return the WindConditions object parsed from the json string
   */
  @Nullable
  public WindConditions getWindConditionsFromWeatherJson(@Nullable String json) {
    if (json == null || json.length() == 0) return null;

    WindConditions windConditions = null;

    try {
      JsonNode root = this.readTree(json);
      JsonNode windNode = root.get(WIND);
      windConditions = this.getMappedWindConditions(windNode);
    } catch (IOException e) {
      log.error("Error parsing weather object: {}", e);
    }

    return windConditions;
  }

  /**
   * Converts a json object into a WindConditions object.
   * @param value the original value
   * @return the converted WindCondition value
   */
  @Nullable
  private WindConditions getMappedWindConditions(Object value) {
    return this.convertValue(value, WindConditions.class);
  }

  /**
   * Creates an MrBeanModule. Registering this within the Jackson JsonMapper will
   *  allow it to be able to parse data into objects using only an interface with no
   *  concrete implementation (no class required). AbstractTypeMaterializer will create
   *  class instances without an actual class.
   *
   * @return the mrbean module. Not Mr.Bean ¯\_(ツ)_/¯
   */
  @NotNull
  private MrBeanModule getMrBean(@Nullable ClassLoader classLoader) {
    AbstractTypeMaterializer materializer = new AbstractTypeMaterializer(classLoader);
    return new MrBeanModule(materializer);
  }

  /**
   * Gets the ClassLoader instance for the Spring app. This is needed because the
   *  Jackson MrBean Module need to know to use this loader to properly work.
   *
   * Relates to this issue:
   *   https://github.com/FasterXML/jackson-modules-base/issues/5#issuecomment-182678289
   *
   * @return the ClassLoader for the running Spring app
   */
  @Bean
  @Nullable
  public ClassLoader getSpringClassLoader() {
    ClassLoader loader = JsonMapper.class.getClassLoader();
    // ClassLoader loader = Thread.currentThread().getContextClassLoader().getParent();
    if (loader == null) log.error("Failed to retrieve Spring ClassLoader");
    return loader;
  }
}
