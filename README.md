# Weather App

This project creates a [Spring Boot](https://projects.spring.io/spring-boot/ "Spring Boot") App 
that utilizes the [openweathermap](https://openweathermap.org/ "openweathermap") api. 
This app allows for making REST requests to get the current wind conditions for a location by 
zip/postal code and country code. Other data is available from the api, but the purpose of this 
app is only to retrieve wind condition data.

## Installation
* Clone repository
* Build project (requires [Maven](https://maven.apache.org/ "Maven"))
* Obtain an API key from from [openweathermap.org](http://openweathermap.org/price "openweathermap.org")
* Execute jar via the java CLI.  your api key can be added as a CLI param as seen below.

```sh
java -jar weatherservice-0.0.1-SNAPSHOT.jar --api.key=abc123
```

## Usage
By default the api will be available from http://localhost:8080. Wind conditions data for a 
location is accessible via `/api/v1/wind/{zipcode}/{country}`. The country code is optional and 
defaults to `us` (United States).

All requests for weather data are cached for 15 mins, unless disabled or flushed. 
Caching is implemented using [Ehcache](http://www.ehcache.org/ "Ehcache")

*Note: Due to a current limitation in the OpenWeatherMap 
API only numerical postal codes will return valid responses.*

A api path is provided to allow for flushing the cache of a running instance via 
`/api/v1/cachflush?secret={cache.flush-key}`.

## API Options
#### /api/v1/wind/{zipCode}/{country}?noCache={noCache}
Gets wind condition data for a local based on zipcode and an optional country code. 
Optionally cache can be disabled for the request by adding `?noCache=true` to the url.

**Response Example**
```json
{
  "speed": 8.2,
  "deg": 340
}
```

| Param | Description | Default
| --- | --- | --- |
| zipCode | **required:** the numerical zip code for a local |  n/a  |
| country | **optional:** country code that can be used along with zipCode to get a location | `"us"` |
| noCache | **optional:** adding this param and setting it to true will disable caching for the request |  `false`  |



#### /api/v1/cacheflush?secret={secret}
Allows for flushing all cache for the app.

| Param | Description | Default
| --- | --- | --- |
| secret | **required:** an secret key that must be included to allow the cache flush request to be accepted. this should match the application property `cache.flush-key` |  n/a  |


## CLI Options

| Param | Type | Description | Default
| --- | --- | --- |---|
| api.key | `String`| **required:** the api key required to make requests to [api.openweathermap.org](http://api.openweathermap.org "api.openweathermap.org") | none
| cache.disabled | `boolean` | **optional:** should the cache be disabled | `false`
| cache.flush-key | `String` | **optional:** a secret key that can be used to flush the cache of a running instance (ie http://localhost:8080/api/v1/cacheflush?secret=12345) | `2qCY9w0icYBMNvNtj21Y`


## Included Dependecies
* Ehcache: http://www.ehcache.org/
* Jackson Modules: https://github.com/FasterXML/jackson-modules-base
* Junit: https://junit.org/junit4/
* Maven: https://maven.apache.org/
* Mockito: http://site.mockito.org/
* Powermock: http://powermock.github.io/
* Open Weather Map API: https://openweathermap.org
* Spring Boot: https://projects.spring.io/spring-boot/