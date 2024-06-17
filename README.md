# HDG #
Automate program to log in and log out. Use Chrome to automate a web test based on given params.

## WARNING: Use this system Carefully ##
This program is distributed AS-IS, no warranties at all.

## Requirements ##
This program run over Linux (debian distros like Ubuntu, Xubuntu, Lubuntu or debian), and uses the following requirements:
- Java runtime (openjdk) 17 or higher
- Apache Maven [https://maven.apache.org/download.cgi]
- Selenium Web Package
- Log 4 Java
- Google Chrome Version 109.0.5414.74 (check url)
- Selenium Chromedriver for Google Chrome [https://chromedriver.storage.googleapis.com/index.html?path=109.0.5414.74/]

## Installation instructions ##
To beginning, you have to download and install prerrequisites: Java runtime 17 (openjdk), Google Chrome and Chromedriver
This system has been tested on that components succesfully.

After that, you can clone this repository executing:
```
BASH
git clone https://github.com/jyefi/hdg.git

```
And then you have to generate jar file, executing:
```
mvn clean install package -Dskiptests
```

The last command, make a maven clean, installs the dependencies (Log4Java and Selenium), and after that generates the jar file.

## Use ##


