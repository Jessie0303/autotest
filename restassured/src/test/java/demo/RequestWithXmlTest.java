package demo;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;

public class RequestWithXmlTest {
    @Test
    public void testSingleXMLContent1() {
        given().
                get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10/").
                then().
                body("CUSTOMER.ID", equalTo("10")).
                log().all();

    }

    @Test
    public void testSingleXMLContent2() {
        given().
                get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10/").
                then().
                body("CUSTOMER.ID", equalTo("10")).
                body("CUSTOMER.FIRSTNAME", equalTo("Sue")).
                body("CUSTOMER.LASTNAME", equalTo("Fuller")).
                body("CUSTOMER.STREET", equalTo("135 Upland Pl.")).
                body("CUSTOMER.CITY", equalTo("Dallas")).
                log().all();

    }

    @Test
    public void testSingleXMLContent3() {
        given().
                get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10/").
                then().
                body("CUSTOMER.text()", equalTo("10SueFuller135 Upland Pl.Dallas")).
                log().all();

    }

    @Test
    public void testSingleXMLContent4() {
        given().
                get("http://www.thomas-bayer.com/sqlrest/INVOICE/").
                then().
                body(hasXPath("/INVOICEList/INVOICE[text()='20']")).
                log().all();
    }
}
