# Playground

> 학습 실습용 Gradle 멀티모듈 프로젝트. 
> 
> 각 컨테이너 모듈은 학습 주제별 카테고리이며, 하위에 독립적인 실습 모듈을 추가하는 구조.

---

## Project Usage

<table>
<tr>
<td width="30%">

### Tech Stack

| 항목 | 버전 |
|------|------|
| Java | 25 |
| Kotlin | 2.2.0 |
| Gradle | 9.2.0 |
| Spring Boot | 4.0.3 |

</td>
<td width="35%">

### Project Structure

| 디렉토리 | 설명 |
|----------|------|
| `00-algorithm/` | 알고리즘 |
| `01-java/` | Java 학습 |
| `02-kotlin/` | Kotlin 학습 |
| `03-spring/` | Spring 학습 |

</td>
<td width="35%">

### Directory Mapping

| 디렉토리 | Gradle 경로 |
|----------|------------|
| `00-algorithm/` | `:algorithm` |
| `01-java/` | `:java` |
| `02-kotlin/` | `:kotlin` |
| `03-spring/` | `:spring` |

</td>
</tr>
</table>

숫자 prefix는 정렬용이며, Gradle 프로젝트명은 영문자로 매핑된다.

각 컨테이너 디렉토리에는 `modules.gradle.kts` 파일이 있으며, 루트 `settings.gradle.kts`에서 `apply(from = ...)`로 불러온다. 하위 모듈 등록은 컨테이너의 `modules.gradle.kts`에서 관리하므로, 루트 설정 파일을 수정할 필요가 없다.

```
settings.gradle.kts
  apply(from = "00-algorithm/modules.gradle.kts")   ← 각 컨테이너의 모듈 정의가 합쳐짐
  apply(from = "01-java/modules.gradle.kts")
  apply(from = "02-kotlin/modules.gradle.kts")
  apply(from = "03-spring/modules.gradle.kts")
```

### Adding a Module

#### 1. 하위 모듈 디렉토리 생성

```bash
mkdir -p 03-spring/security/src/main/java
```

#### 2. build.gradle.kts 작성

<table>
<tr>
<td width="50%">

**Spring Boot 모듈**

```groovy
// import: SpringBootPlugin
plugins {
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(platform(
        SpringBootPlugin.BOM_COORDINATES))
    implementation(
        libs.spring.boot.starter.web)
    testImplementation(
        libs.spring.boot.starter.test)
}
```

</td>
<td>

**순수 Java 모듈**

```groovy
dependencies {
    testImplementation(platform(
        "org.junit:junit-bom:5.11.4"))
    testImplementation(
        "org.junit.jupiter:junit-jupiter")
}
```

**Kotlin 모듈**

```groovy
plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    testImplementation(platform(
        "org.junit:junit-bom:5.11.4"))
    testImplementation(
        "org.junit.jupiter:junit-jupiter")
    testImplementation(kotlin("test"))
}
```

</td>
</tr>
</table>

#### 3. 컨테이너의 modules.gradle.kts에 등록

```groovy
// 03-spring/modules.gradle.kts
include("spring")
project(":spring").projectDir = file("${rootDir}/03-spring")

include("spring:security")                                                 // 추가
project(":spring:security").projectDir = file("${rootDir}/03-spring/security")  // 추가
```

루트 `settings.gradle.kts`는 수정하지 않는다.

#### 4. 새 컨테이너 모듈 추가 시

1. 디렉토리 생성 및 `modules.gradle.kts` 작성
2. 루트 `settings.gradle.kts`에 `apply(from = "...")` 추가
3. 루트 `build.gradle.kts`의 `containerModules`에 path 추가

```groovy
val containerModules = setOf(":algorithm", ":java", ":kotlin", ":spring", ":new-container")
```

### Build Commands

```bash
./gradlew build                          # 전체 빌드
./gradlew :spring:modulith:build         # 특정 모듈 빌드
./gradlew :spring:modulith:bootRun       # Spring Boot 실행
./gradlew :algorithm:solve-java:test     # 특정 모듈 테스트
./gradlew projects                       # 모듈 구조 확인
```

---

## 00 Algorithm

> 알고리즘 문제 풀이. Java와 Kotlin 두 언어로 풀이할 수 있다.
> > Wiki: [Algorithm](../../wiki/Algorithm)

| 모듈 | 설명 |
|------|------|
| `algorithm:solve-java` | Java 풀이 |
| `algorithm:solve-kotlin` | Kotlin 풀이 |



## 01 Java

> Java 언어 심화 학습.
> > Wiki: [Java](../../wiki/Java)

| 모듈 | 설명 | 참고 자료 |
|------|------|----------|
| `java:effective-java` | Effective Java 3/E 아이템별 실습 | Joshua Bloch 저 |



## 02 Kotlin

> Kotlin 언어 학습.
> > Wiki: [Kotlin](../../wiki/Kotlin)

| 모듈 | 설명 | 참고 자료 |
|------|------|----------|




## 03 Spring

> Spring Framework 학습.
> > Wiki: [Spring](../../wiki/Spring)

| 모듈 | 설명 | 참고 자료 |
|------|------|----------|
| `spring:modulith` | Spring Modulith 기반 모듈러 모놀리스 | Spring Modulith 2.0 |


---
