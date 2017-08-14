# Hardis Demo

## Description

This prototype aims to convert text file to xml/Json.
This project is using `spring-boot, spring-batch and apache-cli`.

## Run the tutorial

### From the command line

Open a terminal and run the following commands:

```
$>cd demo
$>mvn clean install
$>cd target/
$>java -jar demo*.jar -f ${inputFilePath}.txt -f ${formet(xml/json)} -o ${outPutFilePath}.${formet(xml/json)}
```

### From Your IDE

* Import the `demo` project in your IDE
* Resolve maven dependencies
* Navigate to the `fr.hardis.baz.demo` package
* Run the `fr.hardis.baz.demo.DemoApplication` class with options like this `-f /home/file.txt -f {formet(xml/json)} -o /home/file.{formet(xml/json)}`.



### sreenshot
![Alt text](./src/main/resoures/screen.png?raw=true "How to run appliation")

