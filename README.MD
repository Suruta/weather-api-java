# Weather Alert Platform
We build Weather Monitoring System. People track weather alert.

## Files
Currently, we added: WeatherAlertId.java, WeatherAlertPolicy.java, WeatherAlertStatus.java

# Explaination of each .java file
WeatherAlertStatus.java need to create enum, so that we can catch bugs early before compiling the code.
WeatherAlertPolicy.java used for safely transitioning status of WeatherAlertStatus.
WeatherAlertId.java the main purpose is domain validation.

## Health check
java -version
mvn -q test
