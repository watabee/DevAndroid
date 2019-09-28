package dependencies

object Versions {
    const val androidCompileSdkVersion = 29
    const val androidMinSdkVersion = 21
    const val androidTargetSdkVersion = 29

    const val buildToolsVersion = "29.0.2"

    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionOffset = 0
    const val androidVersionCode =
        (versionMajor * 10000 + versionMinor * 100 + versionPatch) * 100 + versionOffset

    const val androidVersionName = "$versionMajor.$versionMinor.$versionPatch"
}

object Deps {

    const val androidGradlePlugin = "com.android.tools.build:gradle:3.5.0"
    const val androidJunit5GradlePlugin = "de.mannodermaus.gradle.plugins:android-junit5:1.5.1.0"
    const val spotlessGradlePlugin = "com.diffplug.spotless:spotless-plugin-gradle:3.24.2"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val junit = "junit:junit:4.12"

    object Kotlin {
        private const val version = "1.3.50"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    }

    object Google {
        const val material = "com.google.android.material:material:1.1.0-alpha10"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0-beta04"
        const val swiperefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0-alpha02"

        object Activity {
            private const val version = "1.1.0-alpha03"
            const val activityKtx = "androidx.activity:activity-ktx:$version"
        }

        object Fragment {
            private const val version = "1.2.0-alpha03"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }

        const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"

        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta2"

        const val coreKtx = "androidx.core:core-ktx:1.2.0-alpha04"

        object Lifecycle {
            private const val version = "2.2.0-alpha04"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Room {
            private const val version = "2.2.0-rc01"
            const val common = "androidx.room:room-common:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val testing = "androidx.room:room-testing:$version"
        }
    }

    object RxJava {
        const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.11"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
        const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"
    }

    object Dagger {
        private const val version = "2.24"
        const val dagger = "com.google.dagger:dagger:$version"
        const val androidSupport = "com.google.dagger:dagger-android-support:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
        const val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
    }

    object Glide {
        private const val version = "4.9.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object OkHttp {
        private const val version = "4.1.0"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        private const val version = "2.6.2"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val rxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:$version"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Moshi {
        private const val version = "1.8.0"
        const val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
    }

    object AssistedInject {
        private const val version = "0.5.0"
        const val annotationDagger2 =
            "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
        const val processorDagger2 =
            "com.squareup.inject:assisted-inject-processor-dagger2:$version"
    }

    object Spek2 {
        private const val version = "2.0.7"
        const val dslJvm = "org.spekframework.spek2:spek-dsl-jvm:$version"
        const val runner = "org.spekframework.spek2:spek-runner-junit5:$version"
    }
}

