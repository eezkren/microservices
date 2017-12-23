#### Prerequisite
- Java 1.8
- Kafka
#### Service registry (Eureka)
- Build/Run
  - mvn clean install
  - java -jar target/ms-discovery-0.0.1-SNAPSHOT.jar
- Check
  - http://localhost:8761/
#### Config server (Spring Cloud Config)
- native profile is active reading propertes from local folder
- Update properties 
  - SET **PATH_TO_MICROSERVICES_PROJECT** in /microservices/ms-config-server/src/main/resources/application.yml
  - SET **YOUR_GMAIL_USERNAME** in /microservices/ms-config-properties/ms-mail/dev/ms-mail.yml
  - SET **YOUR_GMAIL_PASSWORD** in /microservices/ms-config-properties/ms-mail/dev/ms-mail.yml
- Run
  - mvn clean install
  - java -jar target/ms-config-server-0.0.1-SNAPSHOT.jar
- Check
  - http://localhost:8888/ms-user/dev -- expect {"name":"ms-user","profiles":["dev"],"label":null,"version":null,"state":null,"propertySources":[{"name":"file:///home/isilona/git/personal/microservices/ms-config-properties/ms-user/dev/ms-user.yml","source":{"spring.h2.console.enabled":true,"spring.h2.console.path":"/h2-console","spring.datasource.url":"jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE","spring.datasource.username":"sa","spring.datasource.password":"","spring.kafka.bootstrap-servers":"localhost:9092","spring.kafka.topic.userCreated":"USER_CREATED_TOPIC","security.basic.enabled":false}}]}
#### User service
- Run
  - mvn clean install
  - java -jar target/ms-user-0.0.1-SNAPSHOT.jar
- Check
  - localhost:8081/api/user/members -- expect 0 records returned
#### Email service
- Run
  - mvn clean install
  - java -jar target/ms-mail-0.0.1-SNAPSHOT.jar
- Check
#### Gateway (Zuul)
- Run
  - mvn clean install
  - java -jar target/ms-gateway-0.0.1-SNAPSHOT.jar
- Check
  - POST: localhost:8765/api/user/register {
	"username": "your_email@example.com",
	"password": "pass"
}
  - GET: localhost:8765/api/user/members -- expect 1 record returned
  - Check email receive at your_email@example.com
