// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath(Libs.com_android_tools_build_gradle)
        classpath(Libs.kotlin_gradle_plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    buildSrcVersions
}

// See Plugin configuration at https://github.com/jmfayard/buildSrcVersions/issues/53
buildSrcVersions {
    indent = "  "
    renameLibs = "Libs"
    renameVersions = "Versions"
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}


allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<Wrapper> {
    distributionType = Wrapper.DistributionType.ALL
    // with buildSrcVersions
    gradleVersion = Versions.gradleLatestVersion
    // with refreshVersions
    gradleVersion = findProperty("gradleLatestVersion") as? String ?: gradle.gradleVersion
}