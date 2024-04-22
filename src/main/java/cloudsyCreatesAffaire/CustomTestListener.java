package cloudsyCreatesAffaire;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomTestListener extends TestListenerAdapter {

    @Override
    public void onTestSuccess(ITestResult tr) {
        printTestResult(tr.getMethod().getMethodName(), "PASS", 0, 0, "\u001B[32m"); // Green color
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        printTestResult(tr.getMethod().getMethodName(), "FAIL", 0, 0, "\u001B[31m"); // Red color
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        printTestResult(tr.getMethod().getMethodName(), "SKIP", 0, 0, "\u001B[33m"); // Yellow color (skipped)
    }

    private void printTestResult(String testName, String status, int passes, int failures, String color) {
        System.out.println("\n");
        System.out.print(color);
        System.out.println("===============================================");
        System.out.println(testName + " - " + status);
        System.out.println("Total tests run: 1, Passes: " + passes + ", Failures: " + failures);
        System.out.println("===============================================");
        System.out.print("\u001B[0m");
        System.out.println("\n");
    }
}
