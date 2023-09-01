package ru.javawebinar.topjava;

public class TestResult {
    private String testName;
    private String result;
    private long startTime;
    private long endTime;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getExecutionTime() {
        return endTime - startTime;
    }
}
