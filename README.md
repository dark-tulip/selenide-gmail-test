# selenide-gmail-test
First project on selenide tests, login to gmail, send_message and send msg with new signature

Логинимся,<br>
Отправляем сообщение,<br>
Отправляем сообщение с подписью текущей даты,<br>
P.S Можно юзить для спама<br>


**1) Should be Java installed**<br>
`java -version`

>openjdk version "17.0.1" 2021-10-19

**2) Should be maven installed**<br>
`mvn --version`

>Apache Maven 3.8.4 
>Maven home: apache-maven-3.8.4
>Java version: 17.0.1, vendor: Eclipse Adoptium

**3) Generate maven project**<br>
`mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false`

**4) Go to the created project directory**<br>
`cd my-app `

**5) Build for eclipse**<br>
`mvn eclipse:eclipse`<br>
For IDEA (not tested yet)<br>
`mvn idea:idea`

This should show something like this: [INFO] BUILD SUCCESS

**6) Open project in IDE**<br><br>
**7) Open pom.xml file and add dependency of selenide for Maven**<br>
Just copy from there https://ru.selenide.org/quick-start.html

**8) Rebuild project, it automatically installs required dependencies**<br>
`mvn test`

**9) Update the project for IDE again**<br>
`mvn eclipse:eclipse`  or  `mvn idea:idea`

**10) Open the project and write some code**<br>
Commonly recommend official website  https://ru.selenide.org/quick-start.html


**Just press RUN button on the IDE to start it**
