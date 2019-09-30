package home;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SpringBootApplication
public class Server {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private byte[] bytes = new byte[0];
    private byte[] lyBytes = new byte[0];
    private long bytesUpdatedAt = 0;
    private long bytesAccessedAt = 0;

    private String boMessage = "";
    private long boUpdatedAt = 0;
    private long boAccessedAt = 0;

    private String meMessage = "";
    private long meUpdatedAt = 0;
    private long meAccessedAt = 0;

    private MyQueue<Action> actionQueue = new MyQueue<>();

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @PostMapping(path = "/add-action", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addAction(@RequestBody Action action) throws Exception {
        actionQueue.add(action);
        return needReload(false);
    }

    @PostMapping(path = "/screen-upload", consumes = MediaType.IMAGE_JPEG_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Action> screenUpload(@RequestBody byte[] bytes) {
        bytesUpdatedAt = System.currentTimeMillis();
        this.bytes = bytes;

        return actionQueue.clear();
    }

    @PostMapping(path = "/ly-screen-upload", consumes = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<String> lyScreenUpload(@RequestBody byte[] bytes) {
        this.lyBytes = bytes;
        return ResponseEntity.ok("");
    }

    @GetMapping(path = "/latest-screen", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] latestScreen() {
        bytesAccessedAt = System.currentTimeMillis();
        return bytes;
    }

    @GetMapping(path = "/ly-latest-screen", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] lyLatestScreen() {
        return lyBytes;
    }

    @GetMapping(path = "/need-reload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> needReload(
            @RequestParam(name = "force", required = false, defaultValue = "false") boolean force) throws Exception {
        if (force)
            return ResponseEntity.status(CREATED).body("{}"); // 201: you need to reload, 200: no need

        Map<String, String> result = new HashMap<>();

        if (meAccessedAt < meUpdatedAt) {
            meAccessedAt = System.currentTimeMillis();
            result.put("message", meMessage);
            meMessage = "";
        }

        return ResponseEntity
                .status(bytesAccessedAt > bytesUpdatedAt ? OK : CREATED)
                .body(objectMapper.writeValueAsString(result));
    }

    @PostMapping(path = "/bo-send", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> boSend(@RequestBody String message) {
        boUpdatedAt = System.currentTimeMillis();
        boMessage += " " + message;
        System.out.printf("Bo sends: %s\n", message);
        return ResponseEntity.ok(null);
    }

    @GetMapping(path = "/bo-message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, String> getBoMessage() {
        Map<String, String> result = new HashMap<>();

        if (boAccessedAt < boUpdatedAt) {
            boAccessedAt = System.currentTimeMillis();
            result.put("message", boMessage);
            boMessage = "";
        }
        return result;
    }

    @PostMapping(path = "/me-send", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> meSend(@RequestBody String message) {
        meUpdatedAt = System.currentTimeMillis();
        meMessage += " " + message;
        return ResponseEntity.ok("");
    }
}
