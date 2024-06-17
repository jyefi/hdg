# HDG #
Automate program to log in and log out. Use Chrome to automate a web test based on given params.

## WARNING: Use this system Carefully ##
This program is distributed AS-IS, no warranties at all.

## Requirements ##
This program run over Linux (debian distros like Ubuntu, Xubuntu, Lubuntu or debian), and uses the following requirements:
- Java runtime (openjdk) 17 or higher
- Apache Maven [https://maven.apache.org/download.cgi]
- Selenium Web Package (included in pom.xml)
- Log 4 Java (included in pom.xml)
- Google Chrome Version 109.0.5414.74 (url TBD)
- Selenium Chromedriver for Google Chrome [https://chromedriver.storage.googleapis.com/index.html?path=109.0.5414.74/]

## Installation instructions ##
To beginning, you have to download and install prerrequisites: Java runtime 17 (openjdk), Google Chrome and Chromedriver
This system has been tested on that components succesfully.

After that, you can clone this repository executing in a terminal:
```
git clone https://github.com/jyefi/hdg.git
```

And then you have to generate jar file, executing in a terminal:

```
mvn clean install package -Dskiptests
```

The last command, make a maven clean, installs the dependencies (Log4Java and Selenium), and after that generates the jar file.

## Use ##

The jar program uses some parameters
- user (mandatory): the username used
- pass (mandatory): the password used
- silent (optional): show/hide UI interface ['true', 'false'](default=false)
- checkBefore (optional): Verify, if the shift has been previously opened ['true', 'false'](default=false)
- checkAfter (optional): Verify, if the shift has been previously closed ['true', 'false'](default=false)
- attendType (mandatory) : ['entrada','salida']
- lang (optional) ['es', 'cat'](default=false)

The parameters has to given using *parameter*=*value*, and it doesn't matter the order. If you don't use this sintaxis, you will get a warning

For example:
```
java -jar <jar file> user=johnDoe pass=MySuperSecretPassword1234 attendType=entrada
```
With that execution, will start test program to log in or log out, and it will show the results in the command line. 

## Future releases ##

- Send an email to communicate the result to the user
- Random time before/after 
- Read program params from a file

