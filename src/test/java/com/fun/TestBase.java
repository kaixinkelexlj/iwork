package com.fun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

/**
 * @author xulujun 2020/03/24.
 */
public class TestBase {

  private Yaml yml = new Yaml();

  protected String getRequestData(String path) {
    return getRequestData(path, ".json");
  }

  protected String getRequestData(String path, String suffix) {
    try {
      return Files
          .readAllLines(
              Paths.get(new ClassPathResource(path + suffix).getURI()))
          .stream().collect(Collectors.joining("\n"));
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  protected Map<String, String> getYmlMap(String path) throws IOException {
    return getYmlMap(path, ".yml");
  }

  @SuppressWarnings("unchecked")
  protected Map<String, String> getYmlMap(String path, String suffix) throws IOException {
    return yml.loadAs(
        new FileInputStream(new ClassPathResource(path + suffix).getFile()),
        Map.class);
  }

  protected JSONObject getRequestJsonObject(String path) {
    return getRequestJsonObject(path, ".json");
  }

  protected JSONObject getRequestJsonObject(String path, String suffix) {
    return JSON.parseObject(getRequestData(path, suffix));
  }

}
