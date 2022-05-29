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
```java -jar target/tretton37.jar```  
Currently, when the program is started it will ask you for a URL to download from and
save the contents to a folder named `download`. 
