include("algorithm")
project(":algorithm").projectDir = file("${rootDir}/00-algorithm")

include("algorithm:solve-java")
project(":algorithm:solve-java").projectDir = file("${rootDir}/00-algorithm/solve-java")

include("algorithm:solve-kotlin")
project(":algorithm:solve-kotlin").projectDir = file("${rootDir}/00-algorithm/solve-kotlin")
