package home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@SpringBootApplication
public class Server {
    private byte[] bytes = new byte[0];
    private long bytesUpdatedAt = 0;
    private long bytesAccessedAt = 0;

    private String boMessage = "";
    private long boUpdatedAt = 0;
    private long boAccessedAt = 0;

    private String meMessage = "";
    private long meUpdatedAt = 0;
    private long meAccessedAt = 0;

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @PostMapping(path = "/screen-upload", consumes = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<String> screenUpload(@RequestBody byte[] bytes) {
        bytesUpdatedAt = System.currentTimeMillis();
        this.bytes = bytes;
        return ResponseEntity.ok("Ok con de");
    }

    @GetMapping(path = "/latest-screen", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] latestScreen() {
        bytesAccessedAt = System.currentTimeMillis();
        return bytes;
    }

    @GetMapping(path = "/need-reload", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> needReload(
            @RequestParam(name = "force", required = false, defaultValue = "false") boolean force) {
        if (force)
            return ResponseEntity.status(CREATED).body(null); // 201: you need to reload, 200: no need

        return bytesAccessedAt > bytesUpdatedAt ? ResponseEntity.ok(null) : ResponseEntity.status(CREATED).body(null);
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
        return ResponseEntity.ok(null);
    }

    @GetMapping(path = "/me-message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, String> getMeMessage() {
        Map<String, String> result = new HashMap<>();

        if (meAccessedAt < meUpdatedAt) {
            meAccessedAt = System.currentTimeMillis();
            result.put("message", meMessage);
            meMessage = "";
        }
        return result;
    }
}
