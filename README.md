# Super-Stock-World
Super simple stock world is an application for the management of trades on a set of stocks


Requirements:

a. For a given stock:

i. Calculate the dividend yield. ii. Calculate the P/E Ratio. iii. Record a trade, with timestamp, quantity of shares, buy or sell indicator and price. iv. Calculate Stock Price based on trades recorded in past 15 minutes.

b. Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

Global Beverage Corporation Exchange

Stock Symbol Type Last Dividend Fixed Dividend Par Value TEA Common 0 100 POP Common 8 100 ALE Common 23 60 GIN Preferred 8 2% 100 JOE Common 13 250

To comply with the requirement of the problem at hand, the implementation of the solution was written in the Java language using Apache maven as the tool at hand. As per the known conventions by the Java community, Maven provides solid features in software development like an easy and pragmatic way to build all objects in the project, the generation of quality reports, execution and reporting of unit tests and many more features like continuous support of integrated development...

Unit Test

To test the code , it has been used Test Driven Approach provided by maven, coding some junit test for each requirement of the problem at hand.

Minimum Requirements :

The minimum requirements for this project are a JDK 1.8 and Maven 3.3,

In the Folder directory:

maven clean install This will compile the code and will execute the unit test.
