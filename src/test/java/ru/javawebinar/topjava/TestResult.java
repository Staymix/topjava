package ru.javawebinar.topjava;

public class TestResult {
    private final String testName;
    private final long time;

    public TestResult(String testName, long time) {
        this.testName = testName;
        this.time = time;
    }

    public String getTestName() {
        return testName;
    }

    public long getTime() {
        return time;
    }
}
