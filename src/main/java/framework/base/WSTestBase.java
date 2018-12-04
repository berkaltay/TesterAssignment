package framework.base;

import framework.extentFactory.ReportFactory;
import framework.listener.Listener;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

@Listeners({Listener.class})
public class WSTestBase extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) throws  UnsupportedEncodingException {
        ReportFactory.createChildTest(testNameFromXml, method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        IRetryAnalyzer retry = result.getMethod().getRetryAnalyzer();
        if (retry != null) {
            result.getTestContext().getSkippedTests().removeResult(result.getMethod());
        }
    }
}
