# Getting Started

### Reference Documentation

The Following Websites was used in developing this application

* [Stackoverflow](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#boot-features-developing-web-applications)
* [Thymeleaf](https://www.thymeleaf.org/documentation.html)
* [CoinMarketCap](https://coinmarketcap.com/api/documentation/v1/)

### APIs used

The following APIs are used to get the information necessary for conversion.

* [IpApi](https://ipapi.co/)
* [CoinMarketCap API](https://coinmarketcap.com/api/documentation/v1/)

### How to run this App

Prerequisites:
- Java 11
- Maven
- being able to access `https://coinmarketcap.com/`
- being able to access `https://ipapi.co/`

I -  Intellij
1. Clone the repository
2. Checkout `feature/implementing_converter`
3. Import the project in Intellij
4. Go and run: `src/main/java/com/cryptocurrencies/converter/CryptoConverterApplication.java`

Extra Steps:
5. It is possible you may need to manually add framework support
   1. In the `Project tree`: richt click on the project
   2. Click on `Add Framework Support`
   3. Add framework support for `Maven`
   4. You will also need to `Trust the project` before trying to run the application

II - Classic
1. Clone the repository
2. Checkout `feature/implementing_converter`
3. It is important to run the below steps from the `${project_directory}`
4. Execute `mvn clean package` in the folder location
5. execute: `java -jar target\converter-0.0.1-SNAPSHOT.jar`


Notes & Limitations
* In a real environment, the Api Key from the properties would probably be retrieved from some sort of a Credential Manager or in a secure manner, and not saved in plain text in the properties file
* The API used are from a free account and have about 300 calls daily available.
* The DB would also be password protected and encrypted
* When you run `mvn clean package` all test should pass. Including `InternetConnectionTest.java`. This just verifies that you can connect to the internet and there are not some security policies in place that would prevent the application connect to the necessary APIs
* The steps are created for someone that has used Intellij or is familiar with an development environment
