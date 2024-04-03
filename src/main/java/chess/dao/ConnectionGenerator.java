package chess.dao;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class ConnectionGenerator {
    private static final String SERVER; // MySQL 서버 주소
    private static final String OPTION;
    private static final String USERNAME; //  MySQL 서버 아이디
    private static final String PASSWORD; // MySQL 서버 비밀번호

    static {
        // YAML 파일 경로
        String yamlFilePath = "src/main/resources/server_config.yaml";

        // YAML 파일을 FileInputStream으로 읽기
        try (final var inputStream = new FileInputStream(yamlFilePath)) {

            // SnakeYAML을 사용하여 YAML 파싱
            Yaml yaml = new Yaml();
            Map<String, Object> yamlMap = yaml.load(inputStream);
            Map<String, String> resultMap = (Map<String, String>) yamlMap.get("Server");

            SERVER = resultMap.get("address");
            OPTION = resultMap.get("option");
            USERNAME = resultMap.get("user_name");
            PASSWORD = resultMap.get("password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionGenerator() {
    }

    public static Connection getConnection(String databaseName) {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + databaseName + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
