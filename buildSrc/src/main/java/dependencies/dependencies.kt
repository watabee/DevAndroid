package dependencies

object Versions {
    const val androidCompileSdkVersion = 29
    const val androidMinSdkVersion = 21
    const val androidTargetSdkVersion = 29

    const val buildToolsVersion = "29.0.2"
    const val ndkVersion = "21.0.6113669"

    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionOffset = 0
    const val androidVersionCode =
        (versionMajor * 10000 + versionMinor * 100 + versionPatch) * 100 + versionOffset

    const val androidVersionName = "$versionMajor.$versionMinor.$versionPatch"
}

object Deps {

    const val androidGradlePlugin = "com.android.tools.build:gradle:4.0.0-beta03"
    const val googleServicesGradlePlugin = "com.google.gms:google-services:4.3.3"
    const val androidJunit5GradlePlugin = "de.mannodermaus.gradle.plugins:android-junit5:1.5.1.0"
    const val ktlintGradlePlugin = "org.jlleitschuh.gradle:ktlint-gradle:9.2.1"
    const val detektGradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.7.1"
    // https://arunkumar9t2.github.io/scabbard/
    const val scabbardGradlePlugin = "gradle.plugin.dev.arunkumar:scabbard-gradle-plugin:0.1.0"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    object Kotlin {
        private const val version = "1.3.71"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"

        object Coroutines {
            private const val version = "1.3.5"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object Google {
        const val material = "com.google.android.material:material:1.2.0-alpha03"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val swiperefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0-alpha03"

        object Activity {
            private const val version = "1.2.0-alpha03"
            const val activityKtx = "androidx.activity:activity-ktx:$version"
        }

        object Fragment {
            private const val version = "1.3.0-alpha03"
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

        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta4"

        const val viewPager2 = "androidx.viewpager2:viewpager2:1.1.0-alpha01"

        const val coreKtx = "androidx.core:core-ktx:1.3.0-beta01"

        const val browser = "androidx.browser:browser:1.2.0"

        object Lifecycle {
            private const val version = "2.3.0-alpha01"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Room {
            private const val version = "2.2.5"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val testing = "androidx.room:room-testing:$version"
        }
    }

    object Dagger {
        private const val version = "2.27"
        const val dagger = "com.google.dagger:dagger:$version"
        const val androidSupport = "com.google.dagger:dagger-android-support:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
        const val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
    }

    object Glide {
        private const val version = "4.11.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
        const val integration = "com.github.bumptech.glide:okhttp3-integration:$version"
    }

    object OkHttp {
        private const val version = "4.4.1"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        private const val version = "2.8.1"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val rxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:$version"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Moshi {
        private const val version = "1.9.2"
        const val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        const val adapters = "com.squareup.moshi:moshi-adapters:$version"
    }

    object AssistedInject {
        private const val version = "0.5.2"
        const val annotationDagger2 =
            "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
        const val processorDagger2 =
            "com.squareup.inject:assisted-inject-processor-dagger2:$version"
    }

    object Groupie {
        private const val version = "2.8.0"
        const val group = "com.xwray"
        const val groupie = "com.xwray:groupie:$version"
        const val dataBinding = "com.xwray:groupie-databinding:$version"
    }

    object Insetter {
        private const val version = "0.2.1"
        const val insetterKtx = "dev.chrisbanes:insetter-ktx:$version"
        const val insetterDbx = "dev.chrisbanes:insetter-dbx:$version"
    }

    const val inboxRecyclerView = "me.saket:inboxrecyclerview:2.0.0-beta3"

    // https://github.com/hadilq/LiveEvent
    const val liveEvent = "com.github.hadilq.liveevent:liveevent:1.0.1"

    object Markwon {
        private const val version = "4.2.0"
        const val core = "io.noties.markwon:core:$version"
    }

    object Firebase {
        const val auth = "com.google.firebase:firebase-auth:19.2.0"
        const val uiAuth = "com.firebaseui:firebase-ui-auth:6.1.0"
    }

    const val junit = "junit:junit:4.13"
    const val truth = "com.google.truth:truth:1.0"

    object Spek2 {
        private const val version = "2.0.7"
        const val dslJvm = "org.spekframework.spek2:spek-dsl-jvm:$version"
        const val runner = "org.spekframework.spek2:spek-runner-junit5:$version"
    }

    object Flipper {
        private const val version = "0.29.3"
        const val flipper = "com.facebook.flipper:flipper:$version"
        const val networkPlugin = "com.facebook.flipper:flipper-network-plugin:$version"

        const val flipperNoOp = "com.facebook.flipper:flipper-noop:$version"
    }

    const val soLoader = "com.facebook.soloader:soloader:0.5.1"

    object Ktlint {
        const val version = "0.36.0"
    }
}

