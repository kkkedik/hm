package properties.test;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;

public class SystemPropertiesTests {

    @Test
    void systemPropertiesTest() {
        String browser = System.getProperty("browser");
        System.out.println(browser);
    }

    @Test
    void systemPropertiesTest1() {
        System.setProperty("browser", "chrome");
        String browser = System.getProperty("browser");
        System.out.println(browser);
    }

    @Test
    void systemPropertiesTest2() {
        String browser = System.getProperty("browser", "mozilla");// если не задано, по дефолту берёт "mozilla"
        System.out.println(browser);
    }

    @Test
    void systemPropertiesTest3() {
        System.setProperty("browser", "chrome");
        String browser = System.getProperty("browser", "mozilla");// если не задано, по дефолту берёт "mozilla", но т.к задано вернёт "chrome"
        System.out.println(browser);
    }

    @Test
    @Tag("property")
    void systemPropertiesTest4() {
        String browser = System.getProperty("browser", "mozilla");// если не задано, по дефолту берёт "mozilla"
        System.out.println(browser);
        // запустив .\gradlew property_test должно быть mozilla
        // запустив .\gradlew property_test -Dbrowser=opera должно быть opera, т.к устанавливаем параметр browser=opera
    }

    @Test
    @Tag("hello")
    void systemPropertiesTest5() {
        String name = System.getProperty("name", "default student");
        String message = format("Hello, %s!", name);
        System.out.println(message);

        // запустив .\gradlew hello_test -Dname="Alex Trololo"
        // запустив .\gradlew hello_test "-Dname=Alex Trololo" будет работать
    }
}
