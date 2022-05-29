# tretton37
Task: Download a site recursively

Create a console program in Java that can recursively traverse and download https://tretton37.com and
save it to disk while keeping the online file structure. Show download progress in the console.
Focus on building a solid application that showcases your overall coding and Java skills, don’t get
caught up in technical details like html link extraction (a simple regex is totally fine).

## How to build.
This is a maven project, because that's what I'm the most comfortable with.  
```./mvnw package```  
Will build everything for you.

## How to run
The artifact is named tretton37.jar  
```java -jar target/sitedownloader-jar-with-dependencies.jar```  
  
The program takes two arguments:
 - -u or --url for the url to start downloading from
 - -d or --destination the target folder to download to.
