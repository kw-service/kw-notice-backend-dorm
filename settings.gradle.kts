rootProject.name = "dormitory"

val moduleNames = arrayListOf("batch-server", "api-server")

fun file(dir: File, name: String): File = file("${dir.absolutePath}/${name}")

val GRADLE_TEXT = "dependencies{}"


moduleNames.forEach { moduleName ->
    // 만약 기존에 하위 모듈이 없다면
    val moduleDir = file(rootDir, moduleName)
    if (!moduleDir.exists()){ moduleDir.mkdirs()}

    // 하위 모듈에 build.gradle을 생성
    val buildFile = file(moduleDir, "build.gradle.kts")
    if (!buildFile.exists()){ buildFile.writeText(GRADLE_TEXT)}

    val projectName = ":${rootProject.name}-${moduleDir.name}"

    // 새롭게 생성한 모듈을 include
    include(projectName)
    project(projectName).projectDir = moduleDir
}