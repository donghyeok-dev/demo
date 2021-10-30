## H2 데이터베이스 

H2는 자바기반 오픈소스 인 메모리 데이터베이스입니다.  

- 설치 및 구동
  - SpringBoot H2 내장 데이터베이스를 사용하거나  [H2 공식사이트](https://www.h2database.com/){:target="_blank"}에서 다운로드하여 설치하여 사용합니다.
  - 구동은 임베디드 모드 또는 서버 모드로 구동할 수 있습니다. 
    - 임베디드 모드: JVM 위에 H2 데이터베이스를 구동시키는 방식으로 애플리케이션이 종료되면 Data가 없어지는 모드입니다.
    - 서버 모드: 별도의 프로세스로 구동되며 외부 클라이언 접속이 허용됩니다.  TCP/IP를 통하여 데이터 처리가 되기 때문에 상대적으로 임베디드 모드보단 느립니다. 

- 웹 기반 콘솔 프로그램(h2 console) 지원

- 가볍고 별도 설치 없이 임베디드 모드로 실행하여 로컬에서 테스트 하기 용의함.



## 테스트 환경

- Intellij
- AdoptOpenJDK 11
- SpringBoot 2.5.5
- Spring JPA (Hibernate)
- [H2](https://www.h2database.com/){:target="_blank"}
- [DBeaver](https://dbeaver.io/){:target="_blank"}
- JUnit5



## SpringBoot에서 임베디드 모드로  H2 연결하기

build.gradle

```groovy
plugins {
    id 'org.springframework.boot' version '2.5.5'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'com.h2database:h2'
    annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

test {
    useJUnitPlatform()
}
```

application.yml

```yaml
spring:
  profiles:
    active: local

---
spring:
  config:
    activate:
      on-profile: local

  h2:
    console:
      enabled: true
      path: /h2-console

  datasource:
    hikari:
      jdbc-url: jdbc:h2:mem:testdb
      driver-class-name: org.h2.Driver
      username: sa
      password:
  jpa:
    show-sql: true
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
      naming:
        physical-strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
        implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
```

MemberDto.java

```java
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String name;
    private String mobile;
}
```

Member.java

```java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mobile;

    public void updateMember(MemberDto memberDto) {
        this.name = memberDto.getName();
        this.mobile = memberDto.getMobile();
    }
    
    @Builder
    public Member(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }
}
```

Spring Boot 실행

![image-20211021172559609](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211022101533855.png)

실행 콘솔을 보면 member 테이블이 생성되는 것을 확인할 수있습니다.



## h2 console 접속 해보기

Spring Boot Application이 실행되고 있는 상태에서 브라우저를 엽니다.

브라우저에서 http://localhost:8080/h2-console 로 접속하여 JDBC URL을 편집하고 Connect 합니다.

![image-20211021150416706](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211021150416706.png)

정상적으로 접속이되었다면 h2-console 메인화면 좌측에 생성된 테이블이 보입니다.

![image-20211021150655786](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211022101705351.png)

## 외부 클라이언트 툴로 접속 해보기

h2-console이 아닌 외부 데이터베이스 클라이언트([DBeaver](https://dbeaver.io/){:target="_blank"})로 H2에 접속하기 위해서는 TCP 서버 설정을 추가 및  일부 설정을 변경 추가 해야 됩니다.

1. **H2라이브러리를 소스코드 수준에서 사용해야 되므로 build.gradle에서 runtimeOnly 부분을 implementation로 변경합니다.**

   ```groovy
   ...
   implementation 'com.h2database:h2'
   ...
   ```

   

2. **서버 Bean 설정 추가합니다.**

   ```java
   package com.example.testh2.config;
   
   import org.h2.tools.Server;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.context.annotation.Profile;
   
   import java.sql.SQLException;
   
   @Configuration
   @Profile("local")
   public class H2ServerConfig {
   
       @Bean(initMethod = "start", destroyMethod = "stop")
       public Server H2DatabaseServer() throws SQLException {
           return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
       }
       /*
           만약 위 코드에서 오류가 발생한다면 다음과 같이 시도 해볼 것
           @Bean
           @ConfigurationProperties("spring.datasource.hikari")
           public DataSource dataSource() throws SQLException {
               Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
               return new HikariDataSource();
           }
       */
   }
   ```

   



3. **Intellij에서 Application을 실행합니다.**



4. **외부 데이터베이스 클라이언트에서 접속 정보를 입력하고 접속합니다.**

   접속 정보의  JDBC URL은 **jdbc:h2:tcp://localhost:9092/mem:testdb**를 입력합니다.

![image-20211021172929800](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211021172929800.png)

​		클라이언트에 접속 및 데이터베이스도 정상적으로 접근됩니다.

![image-20211021173030232](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211022101843984.png)



5. **Intellij Database Client에서도 접근해봅니다.**

![image-20211021174336309](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211021174336309.png)

![image-20211021174401233](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211021174401233.png)



​		정상적으로 접속이 됩니다.

​		하지만 한가지 문제점이 있습니다.  지금까지 테스트는 Application 내의 H2 데이터베이스에  접근하기 때문에 Application이 종료되		면 접근할 수 없습니다. 이 문제를 해결하기 위해서는 H2 데이터베이스를 로컬에 설치하여 별도 프로세스로 실행시켜 Application		과 H2 	데이터 베이스를 분리 시켜야됩니다.

![image-20211021222342558](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211021222342558.png)
=======
​		이미지 출처: [https://www.h2database.com/html/features.html](https://www.h2database.com/html/features.html){:target="_blank"}

​		H2 공식 홈페이지에서 Install 파일을 다운로드하여 설치한 다음 H2 데이터베이스를 실행시키고, application.yml의 jdbc 부분도 외		부 클라이언트 접속처럼 jdbc:h2:tcp://localhost:9092/mem:testdb로 변경하면 됩니다.

```yaml
  datasource:
    hikari:
      jdbc-url: jdbc:h2:tcp://localhost:9092/mem:testdb
      driver-class-name: org.h2.Driver
      username: sa
      password:
```



## H2 데이터베이스 JPA 테스트 해보기

Spring boot에 내장된 JUnit5를 이용하여 H2 데이터베이스를 테스트 합니다.



1. MemberRepository.java 생성

   ```java
   public interface MemberRepository extends JpaRepository<Member, Long> {
   }
   ```

2. MemberRepository에서 단축키 Ctrl + Shift + T를 눌러 테스트 파일을 만들고 아래와 같이 수정합니다.

   ```java
   @DataJpaTest
   @ActiveProfiles("local")
   @DisplayName("Member Entity Jpa 테스트")
   class MemberRepositoryTest {
       @Autowired
       MemberRepository memberRepository;
       @Autowired
       private TestEntityManager entityManager;
   
       @Test
       @DisplayName("insert 테스트")
       void testInsert() {
           // given
           List<Member> members = new ArrayList<>();
   
           members.add(Member.builder()
                   .name("세종대왕")
                   .mobile("01030007777")
                   .build());
   
           members.add(Member.builder()
                   .name("장영실")
                   .mobile("01055559999")
                   .build());
   
           // when
           this.memberRepository.saveAll(members);
           List<Member> findMembers = this.memberRepository.findAll();
   
           // then
           assertEquals(2, findMembers.size());
           assertEquals("세종대왕", findMembers.get(0).getName());
           assertEquals("01030007777", findMembers.get(0).getMobile());
           assertEquals("장영실", findMembers.get(1).getName());
           assertEquals("01055559999", findMembers.get(1).getMobile());
       }
   
       @Test
       @DisplayName("Update 테스트")
       void testUpdate() {
           // given
           Member member = this.memberRepository.save(Member.builder()
                   .name("세종대왕")
                   .mobile("01030007777")
                   .build());
   
           // when
           member.updateMember(MemberDto.builder()
                   .id(member.getId())
                   .name("문종")
                   .mobile("01040005555")
                   .build());
   
           this.entityManager.persistAndFlush(member); // Manually Dirty Checking
           this.entityManager.clear();
   
           Optional<Member> findMember = this.memberRepository.findById(member.getId());
   
           // then
           assertTrue(findMember.isPresent());
           assertEquals("문종", findMember.get().getName());
           assertEquals("01040005555", findMember.get().getMobile());
       }
   }
   ```

   

3. 테스트 코드 실행

   ![image-20211022111615297](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211022111615297.png)

   테스트가 정상적으로 완료되었고 좌측 콘솔에 실행된 쿼리도 보입니다.

   항목별 테스트 결과는 Open Gradle test report를 클릭하면 웹 브라우저가 열리고 아래 그림처럼 상세 내역을 볼 수 있습니다.

   ![image-20211022111735895](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211022111735895.png)

   

   만약 Intellij에서 바로 상세내역을 확인하고 싶다면  Ctrl + Alt + S를 눌러 프로젝트 설정을 열고 아래 처럼 빌드 옵션을 Gradle에서 Intellij로 변경 하시면 됩니다.

   ![image-20211022112715769](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211022112715769.png)

   ![image-20211022112847968](https://cdn.jsdelivr.net/gh/donghyeok-dev/donghyeok-dev.github.io@master/assets/images/posts/image-20211022112847968.png)
