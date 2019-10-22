
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

/**
 * Generated by https://github.com/jmfayard/buildSrcVersions
 *
 * Find which updates are available by running
 *     `$ ./gradlew buildSrcVersions`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version.
 */
object Versions {
  const val de_fayard_buildsrcversions_gradle_plugin: String = "0.6.1"

  const val retrofit2_kotlin_coroutines_adapter: String = "0.9.2"

  const val com_android_tools_build_gradle: String = "3.5.0"

  const val kotlinx_coroutines_android: String = "1.3.1"

  const val androidx_databinding: String = "3.5.0"

  const val androidx_test_runner: String = "1.2.0"

  const val org_jetbrains_kotlin: String = "1.3.50"

  const val com_facebook_stetho: String = "1.5.1"

  const val logging_interceptor: String = "4.2.0"

  const val androidx_lifecycle: String = "2.2.0-alpha04"

  const val constraintlayout: String = "2.0.0-beta1"

  const val converter_gson: String = "2.6.2"

  const val androidx_room: String = "2.1.0"

  const val espresso_core: String = "3.2.0"

  const val recyclerview: String = "1.1.0-beta04"

  const val android_ktx: String = "1.0.12"

  const val lint_gradle: String = "26.5.0"

  const val appcompat: String = "1.1.0"

  const val core_ktx: String = "1.1.0"

  const val material: String = "1.1.0-alpha10"

  const val org_koin: String = "2.0.1"

  const val lottie: String = "3.0.7"

  const val timber: String = "4.7.1"

  const val aapt2: String = "3.5.0-5435860"

  const val glide: String = "4.10.0"

  const val junit: String = "4.12"

  const val gson: String = "2.8.5"

  /**
   * Current version: "5.4.1"
   * See issue 19: How to update Gradle itself?
   * https://github.com/jmfayard/buildSrcVersions/issues/19
   */
  const val gradleLatestVersion: String = "5.6.2"
}

/**
 * See issue #47: how to update buildSrcVersions itself
 * https://github.com/jmfayard/buildSrcVersions/issues/47
 */
val PluginDependenciesSpec.buildSrcVersions: PluginDependencySpec
  inline get() =
      id("de.fayard.buildSrcVersions").version(Versions.de_fayard_buildsrcversions_gradle_plugin)