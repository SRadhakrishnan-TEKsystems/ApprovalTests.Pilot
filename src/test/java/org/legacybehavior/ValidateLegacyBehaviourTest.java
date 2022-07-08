package org.legacybehavior;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ValidateLegacyBehaviourTest {
    private String fileName;
    static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JSR310Module())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.INDENT_OUTPUT, true);

    @BeforeEach
    public void initialize(TestInfo testInfo) {
        String className = this.getClass().getSimpleName();
        String testName = testInfo.getDisplayName().replace("()", "");
        this.fileName = String.format("%s.%s.approved.json", className, testName);

    }

    @Test
    public void willHaveCorrectBehavior() {
        Object someFilters = new Object();
        final Person existing = getExistingSystemResponse();
        final Person newSystem = getNewSystemResponse();
        CustomApprovals.master(existing, fileName).verifyAsJson(newSystem, GsonBuilder::setPrettyPrinting);
    }

    private Person getNewSystemResponse() {
        return Person.getInstance("Carol Danvers", "Captain Marvel", "MCU", UUID.randomUUID(), Instant.now().minusSeconds(360));
    }


    private Person getExistingSystemResponse() {
        return Person.getInstance("Carol Danvers", "Captain Marvel", "MCU", UUID.randomUUID(), Instant.now());
    }

    public static class CustomApprovals extends JsonApprovals{


        public static CustomApprovals master(Person goldenMaster, String fileName) {
            return new CustomApprovals().initMaster(goldenMaster, fileName);
        }

        private CustomApprovals initMaster(Person goldenMaster, String fileName) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeHierarchyAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) -> new JsonPrimitive(DateTimeFormatter.ISO_INSTANT.format(src)))
                    .create();

            String existingString  = gson.toJson(goldenMaster);
            System.out.println("==================");
            String path = String.format("src/test/java/%s/%s/", getClass().getPackage().getName().replaceAll("\\.", "/"), fileName);
            System.out.println(path);
            System.out.println("==================");
            try {
                Files.write(Paths.get(path), existingString.getBytes());
                //Files.write(Paths.get(String.format("src/test/java/org/legacybehavior/%s", fileName)), existingString.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }
    }
}
