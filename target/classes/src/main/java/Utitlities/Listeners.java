package Utitlities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {

    BaseUtilities base =new BaseUtilities();

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Test case is starting execution: "+iTestResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult){

        try{
            base.takeScreenshot(iTestResult.getName());
        }
        catch(Exception e)
        {
            e.getLocalizedMessage();
        }

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
