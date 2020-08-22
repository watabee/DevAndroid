package dependencies

object Versions {
    const val androidCompileSdkVersion = 29
    const val androidMinSdkVersion = 21
    const val androidTargetSdkVersion = 29

    const val buildToolsVersion = "29.0.3"
    const val ndkVersion = "21.3.6528147"

    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionOffset = 0
    const val androidVersionCode =
        (versionMajor * 10000 + versionMinor * 100 + versionPatch) * 100 + versionOffset

    const val androidVersionName = "$versionMajor.$versionMinor.$versionPatch"
}

object Deps {

    const val androidGradlePlugin = "com.android.tools.build:gradle:4.1.0-rc01"
    const val androidJunit5GradlePlugin = "de.mannodermaus.gradle.plugins:android-junit5:1.6.2.0"
    const val ktlintGradlePlugin = "org.jlleitschuh.gradle:ktlint-gradle:9.3.0"
    const val detektGradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.9.1"
    // https://arunkumar9t2.github.io/scabbard/
    const val scabbardGradlePlugin = "gradle.plugin.dev.arunkumar:scabbard-gradle-plugin:0.4.0"
    const val releaseHubGradlePlugin = "com.releaseshub:releases-hub-gradle-plugin:1.6.0"
    const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:2.28-alpha"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.0"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.0"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:1.4.0"

        object Coroutines {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9"
        }
    }

    object Google {
        const val material = "com.google.android.material:material:1.2.0"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.0-alpha04"
        const val swiperefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

        const val activityKtx = "androidx.activity:activity-ktx:1.2.0-alpha06"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.0-alpha06"

        object Test {
            const val core = "androidx.test:core:1.2.0"
            const val runner = "androidx.test:runner:1.2.0"
            const val rules = "androidx.test:rules:1.2.0"

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }

        const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"

        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta8"

        const val viewPager2 = "androidx.viewpager2:viewpager2:1.1.0-alpha01"

        const val coreKtx = "androidx.core:core-ktx:1.3.1"

        const val browser = "androidx.browser:browser:1.2.0"

        const val startup = "androidx.startup:startup-runtime:1.0.0-alpha01"

        object Lifecycle {
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.0-alpha03"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:2.3.0-alpha03"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0-alpha03"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:2.2.5"
            const val compiler = "androidx.room:room-compiler:2.2.5"
            const val ktx = "androidx.room:room-ktx:2.2.5"
            const val testing = "androidx.room:room-testing:2.2.5"
        }

        object Hilt {
            const val viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01"
            const val compiler = "androidx.hilt:hilt-compiler:1.0.0-alpha01"
        }

        object Paging {
            const val runtime = "androidx.paging:paging-runtime:3.0.0-alpha02"
        }
    }

    object Dagger {
        object Hilt {
            const val android = "com.google.dagger:hilt-android:2.28-alpha"
            const val androidCompiler = "com.google.dagger:hilt-android-compiler:2.28-alpha"
        }
    }

    object Glide {
        const val glide = "com.github.bumptech.glide:glide:4.11.0"
        const val compiler = "com.github.bumptech.glide:compiler:4.11.0"
        const val integration = "com.github.bumptech.glide:okhttp3-integration:4.11.0"
    }

    object OkHttp {
        const val okhttp = "com.squareup.okhttp3:okhttp:4.8.1"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.8.1"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val rxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:2.9.0"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:2.9.0"
    }

    object Moshi {
        const val kotlin = "com.squareup.moshi:moshi-kotlin:1.9.3"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:1.9.3"
        const val adapters = "com.squareup.moshi:moshi-adapters:1.9.3"
    }

    object AssistedInject {
        const val annotationDagger2 = "com.squareup.inject:assisted-inject-annotations-dagger2:0.5.2"
        const val processorDagger2 = "com.squareup.inject:assisted-inject-processor-dagger2:0.5.2"
    }

    object Insetter {
        const val insetterKtx = "dev.chrisbanes:insetter-ktx:0.3.1"
        const val insetterDbx = "dev.chrisbanes:insetter-dbx:0.3.1"
    }

    // https://github.com/hadilq/LiveEvent
    const val liveEvent = "com.github.hadilq.liveevent:liveevent:1.2.0"

    object Markwon {
        const val core = "io.noties.markwon:core:4.5.1"
    }

    const val junit = "junit:junit:4.13"
    const val truth = "com.google.truth:truth:1.0.1"

    object Spek2 {
        const val dslJvm = "org.spekframework.spek2:spek-dsl-jvm:2.0.7"
        const val runner = "org.spekframework.spek2:spek-runner-junit5:2.0.7"
    }

    object Flipper {
        const val flipper = "com.facebook.flipper:flipper:0.52.1"
        const val networkPlugin = "com.facebook.flipper:flipper-network-plugin:0.52.1"

        const val flipperNoOp = "com.facebook.flipper:flipper-noop:0.52.1"
    }

    const val soLoader = "com.facebook.soloader:soloader:0.9.0"

    object Ktlint {
        const val version = "0.36.0"
    }
}

