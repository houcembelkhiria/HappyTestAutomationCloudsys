package cloudsyCreatesAffaire;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomTestListener extends TestListenerAdapter {

    @Override
    public void onTestSuccess(ITestResult tr) {
        printTestResult(tr.getMethod().getMethodName(), 1, 0, 0);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        printTestResult(tr.getMethod().getMethodName(), 0, 1, 0);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        printTestResult(tr.getMethod().getMethodName(), 0, 0, 1);
    }

    private void printTestResult(String testName, int passes, int failures, int skips) {
        System.out.println("\n");
        System.out.println("===============================================");
        System.out.println(testName);
        System.out.println("Total tests run: 1, Passes: " + passes + ", Failures: " + failures + ", Skips: " + skips);
        System.out.println("===============================================");
        System.out.println("\n");

    }
}
