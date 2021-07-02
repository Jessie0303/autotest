package testng;

import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.*;

@Test(groups={"demo-test"})
public class RunTestNGTest {
    public static void main(String[] args){
        XmlSuite suite = new XmlSuite();
        suite.setName("Suite");
        XmlTest test = new XmlTest(suite);
        test.setName("hogwarts");
        List<XmlClass> classes = new ArrayList<XmlClass>();
        classes.add(new XmlClass("demo.SearchTest"));
        test.setXmlClasses(classes);

        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.run();
    }

}
