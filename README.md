
Documentation
-------------

Application does following:

- Search Github for repositories that match the given search 1st parameter - **search**
- Sort the results by the number of stars in a descending order and print the top result name and stars
- For the top result repository reads the latest release tag and verify if the 2nd parameter - **tag** is the latest tag and print it in a message


Execution
-------------
```
 mvn clean package exec:java -Dexec.mainClass="com.kbc.Main" -Dsearch=selenium -Dtag=selenium-3.141.59
```
