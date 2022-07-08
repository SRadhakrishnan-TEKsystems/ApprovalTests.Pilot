package org;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CustomApproval {
    private String className;
    private String testName;
    private String approvedFileName;
    private String path;

    @BeforeAll
    static void initialize() {
    }

    @BeforeEach
    public void initializeTest(TestInfo testInfo) {
        this.className = this.getClass().getSimpleName();
        this.testName = testInfo.getDisplayName().replace("()", "");
        this.approvedFileName = String.format("%s.%s.approved.json", className, testName);
        String partialPath = this.getClass().getPackage().getName().replaceAll("\\.", "/");
        this.path = String.format("src/test/java/%s/%s", partialPath, approvedFileName);
    }

    protected void approve(Object existing, Object actual) {
        createGoldenMaster(existing);
        JsonApprovals.verifyAsJson(actual, GsonBuilder::serializeNulls);
    }

    private void createGoldenMaster(Object existing) {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        String goldenMaster = gson.toJson(existing);
        try {
            Files.write(Paths.get(path), goldenMaster.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
