# Simplify Your Android Build Scripts with Kotlin DSL and Convention Plugins

Kotlin DSL is now the default for new Android projects, and Google recommends using Kotlin DSL Gradle scripts for configuring your dependencies. This article will explain how to share code across your Gradle scripts, allowing you to avoid copying and pasting large chunks of Kotlin DSL across your Gradle scripts.

## Groovy allowed us to use apply

In the past, we could use apply with Kotlin build scripts, which allowed us to avoid copying large snippets of Groovy code across different modules in our Android projects.
A common example of a block of groovy that can easily replicate across modules in an Android project are the android and dependencies blocks, as most of the UI modules in your project need to know your android build configuration and the android dependencies to be used across your app. For example an explore page that displays a list of items and a profile page that displays a user's information would share many lines of build configuration code:

```
android {
    compileSdkVersion settings.compileSdkVersion
    buildToolsVersion settings.buildToolsVersion

    defaultConfig {
        minSdkVersion settings.minSdkVersion
        targetSdkVersion settings.targetSdkVersion
        testInstrumentationRunner "com.lmorda.testbase.BaseTestRunner"
    }
    ...
}

dependencies {
    ...
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:+"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:+"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.5.0"
    ...
}
```
and the solution for this problem is to create a shared build script so that you don’t need to define this block for each module:

```
apply from: "$rootDir/build-scripts/android_module.gradle"
```

## Reusing build configuration with convention plugins
Gradle now fully supports using native Kotlin code to configure your builds. In an Android project, as demonstrated in the NowInAndroid sample project, a build module typically contains all of the convention plugin code. A core module, commonly named build-logic, will have its own settings.gradle.kts file and a submodule named convention. This submodule is where you define all your convention plugins within a gradlePlugin block:

```
/build-logic/settings.gradle.kts

gradlePlugin {
    plugins {
        register("conventionAndroidApplication") {
            id = "convention.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("conventionAndroidLibrary") {
            id = "convention.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("conventionCoreLifecycle") {
            id = "convention.core.lifecycle"
            implementationClass = "AndroidCoreLifecycleConventionPlugin"
        }
        register("conventionCompose") {
            id = "convention.compose"
            implementationClass = "ComposeConventionPlugin"
        }
    }
}
```

The implementationClass must match the actual class name that contains your Kotlin source code defining the shared dependencies. In the above example, the AndroidApplicationConventionPlugin source code file applies the Kotlin Android and Android application plugins while defining the compileSdk, minSdk, and Kotlin compile options.
```
/build-logic/convention/main/java/com/lmorda/buildlogic/
AndroidApplicationConventionPlugin.kt

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
                apply("com.android.application")
            }
            extensions.configure<BaseAppModuleExtension> {
                configureAndroid(commonExtension = this)
            }
        }
    }
}

private fun configureAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 30
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}
```
## Applying Convention Plugins in Android Modules
Now that we have our first convention plugin that defines common Android build configuration, how can we apply this convention plugin in our modules that want to share this configuration?

The first step we must complete is to add the convention plugin to the libs.versions.toml file in the plugins section. This ensures that the plugin is recognized and can be applied across your modules. Here’s how to do it:
```
/build-logic/libs.version.toml

[plugins]

convention-android-library = { id = "convention.android.library"}
```

Now we can add our custom convention plugin to the plugins block of any module in our app. For example, if we have a module named explore, we can include the convention plugin in its build.gradle.kts file like this:

```
/explore/build.gradle.kts

plugins {
    alias(libs.plugins.convention.android.library)
    ...
}
```
Our explore UI module now has the shared Android build configuration applied with just a single line of Kotlin DSL code.

## Library Convention Plugins
There are a few differences in nomenclature when defining implementation dependencies in a library convention plugin. For example instead of copying and pasting all of your Hilt and testing dependencies to each module in your project, we can define them in a convention plugin dependencies block
```
/build-logic/convention/main/java/com/lmorda/buildlogic/
AndroidLibraryConventionPlugin.kt

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
                apply("com.android.library")
                apply("dagger.hilt.android.plugin")
                apply("org.jetbrains.kotlin.kapt")
            }
            extensions.configure<LibraryExtension> {
                configureAndroid(commonExtension = this)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("kapt", libs.findLibrary("hilt.compiler").get())
                add("implementation", libs.findLibrary("timber").get())
                add("testImplementation", libs.findLibrary("junit").get())
                add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
                add("testImplementation", libs.findLibrary("mockk").get())
                add("testImplementation", libs.findLibrary("mockk.android").get())
                add("testImplementation", libs.findLibrary("androidx.core.testing").get())
                add("testImplementation", libs.findLibrary("turbine").get())
            }
        }
    }
}
```
The add function takes in the implementation string and the library to add. It's important to note that the findLibrary function requires the input string value to match a value defined in your libs.versions.toml file's libraries block. For example, these lines of code must be connected:

```
add("implementation", libs.findLibrary("hilt.android").get())
```

where the "hilt.android" string value passed into the findLibrary function must match the defined "hilt-android" library in the libs.versions.toml file:
/build-logic/libs.version.toml
```
[libraries]
hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hiltAndroidGradlePlugin" }
```