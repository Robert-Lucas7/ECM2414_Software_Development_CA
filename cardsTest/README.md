To run our test suite, navigate to the directory where this zip file (cardsTest.zip) has been extracted to and execute the following command:

java -cp .:lib/junit-platform-suite-engine-1.10.1.jar:lib/junit-platform-console-standalone-1.10.1.jar org.junit.platform.console.ConsoleLauncher execute --select-class TestSuite
