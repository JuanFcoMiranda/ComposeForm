import Versions.accompanistVersion
import Versions.activityVersion
import Versions.firebaseAnalyticsVersion
import Versions.appCompatVersion
import Versions.firebaseAuthVersion
import Versions.coilVersion
import Versions.collectionsImmutableVersion
import Versions.composeActivityVersion
import Versions.composeConstraintVersion
import Versions.composeNavigationVersion
import Versions.composePaging
import Versions.composeVersion
import Versions.coreKtxVersion
import Versions.coroutinesVersion
import Versions.daggerVersion
import Versions.dataStoreVersion
import Versions.espressoVersion
import Versions.firebaseCommonVersion
import Versions.firebaseCoreVersion
import Versions.firebaseCrashlyticsVersion
import Versions.firebaseDatabaseVersion
import Versions.firebaseFirestoreVersion
import Versions.firebaseMessagingVersion
import Versions.firebaseStorageVersion
import Versions.fragmentVersion
import Versions.glideCompilerVersion
import Versions.glideVersion
import Versions.gsonConverterVersion
import Versions.hiltCompilerVersion
import Versions.hiltLifecycleVersion
import Versions.hiltNavigationVersion
import Versions.hiltVersion
import Versions.jUnitVersion
import Versions.jsoupVersion
import Versions.kotlinVersion
import Versions.ktorClientVersion
import Versions.lifecycleRuntimeVersion
import Versions.lifecycleSavedstateVersion
import Versions.lifecycleVersion
import Versions.lottieVersion
import Versions.materialVersion
import Versions.mockitoVersion
import Versions.mockitoKotlinVersion
import Versions.navigationVersion
import Versions.objectboxVersion
import Versions.objectBoxProcessorVersion
import Versions.okHttpVersion
import Versions.okHttpLogginInterceptorVersion
import Versions.pagingVersion
import Versions.picassoVersion
import Versions.retrofitCoroutinesAdapterVersion
import Versions.retrofitMockVersion
import Versions.retrofitRxJavaVersion
import Versions.retrofitVersion
import Versions.serializationJsonVersion
import Versions.splashScreenVersion
import Versions.testsVersion

object Libs {
    object Airbnb {
        object Compose {
            const val lottie = "com.airbnb.android:lottie-compose:$lottieVersion"
        }
    }

    object AndroidX {
        const val activity = "androidx.activity:activity-ktx:$activityVersion"
        const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
        const val core = "androidx.core:core-ktx:$coreKtxVersion"
        const val datastore = "androidx.datastore:datastore-core:$dataStoreVersion"
        const val fragment = "androidx.fragment:fragment-ktx:$fragmentVersion"
        const val splash = "androidx.core:core-splashscreen:$splashScreenVersion"
        const val paging = "androidx.paging:paging-runtime:$pagingVersion"

        object Compose {
            const val livedata = "androidx.compose.runtime:runtime-livedata:$composeVersion"

            const val activity = "androidx.activity:activity-compose:$composeActivityVersion"
            const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:$composeConstraintVersion"

            const val animation = "androidx.compose.animation:animation:$composeVersion"
            const val animationCore = "androidx.compose.animation:animation-core:$composeVersion"
            const val compiler = "androidx.compose.compiler:compiler:$composeVersion"
            const val foundation = "androidx.compose.foundation:foundation:$composeVersion"
            const val iconsCore = "androidx.compose.material:material-icons-core:$composeVersion"
            const val iconsExtended = "androidx.compose.material:material-icons-extended:$composeVersion"
            const val layout = "androidx.compose.foundation:foundation-layout:$composeVersion"
            const val material = "androidx.compose.material:material:$composeVersion"
            const val navigation = "androidx.navigation:navigation-compose:$composeNavigationVersion"

            const val paging = "androidx.paging:paging-compose:$composePaging"
            const val runtime = "androidx.compose.runtime:runtime:$composeVersion"
            const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:$composeVersion"
            const val tooling = "androidx.compose.ui:ui-tooling:$composeVersion"
            const val ui = "androidx.compose.ui:ui:$composeVersion"
            const val uiUtil = "androidx.compose.ui:ui-util:$composeVersion"
        }

        object Hilt {
            const val lifeCycle = "androidx.hilt:hilt-lifecycle-viewmodel:$hiltLifecycleVersion"
            const val compiler = "androidx.hilt:hilt-compiler:$hiltCompilerVersion"
            const val navigation = "androidx.hilt:hilt-navigation-compose:$hiltNavigationVersion"
        }

        object Lifecycle {
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeVersion"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
            const val savedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleSavedstateVersion"
        }

        object Test {
            const val core = "androidx.test:core:$testsVersion"
            const val espressoCore = "androidx.test.espresso:espresso-core:$espressoVersion"
            const val navigation = "androidx.navigation:navigation-testing:$navigationVersion"
            const val rules = "androidx.test:rules:$testsVersion"
            const val runner = "androidx.test:runner:1.4.0"

            object Ext {
                const val junit = "androidx.test.ext:junit-ktx:1.1.3"
            }
        }
    }

    object BumpTech {
        object Glide {
            const val glide = "com.github.bumptech.glide:glide:$glideVersion"
            const val compiler = "com.github.bumptech.glide:compiler:$glideCompilerVersion"
        }
    }

    object Coil {
        const val compose = "io.coil-kt:coil-compose:$coilVersion"
    }

    object Google {
        object Accompanist {
            const val insets = "com.google.accompanist:accompanist-insets:$accompanistVersion"
            const val insetsUI = "com.google.accompanist:accompanist-insets-ui:$accompanistVersion"
            const val navigationAnimation = "com.google.accompanist:accompanist-navigation-animation:$accompanistVersion"
            const val pager = "com.google.accompanist:accompanist-pager:$accompanistVersion"
            const val pagerIndicators = "com.google.accompanist:accompanist-pager-indicators:$accompanistVersion"
            const val swiperefresh = "com.google.accompanist:accompanist-swiperefresh:$accompanistVersion"
        }

        object Android {
            object Material {
                const val material = "com.google.android.material:material:$materialVersion"
            }
        }

        object Dagger {
            const val core = "com.google.dagger:dagger:$daggerVersion"
            const val compiler = "com.google.dagger:dagger-compiler:$daggerVersion"
        }

        object Firebase {
            const val analytics = "com.google.firebase:firebase-analytics:$firebaseAnalyticsVersion"
            const val auth = "com.google.firebase:firebase-auth:$firebaseAuthVersion"
            const val core = "com.google.firebase:firebase-core:$firebaseCoreVersion"
            const val common_ktx = "com.google.firebase:firebase-common-ktx:$firebaseCommonVersion"
            const val crashlytics = " com.google.firebase:firebase-crashlytics-ktx:$firebaseCrashlyticsVersion"
            const val database = "com.google.firebase:firebase-database:$firebaseDatabaseVersion"
            const val firestore = "com.google.firebase:firebase-firestore-ktx:$$firebaseFirestoreVersion"
            const val messaging = "com.google.firebase:firebase-messaging:$firebaseMessagingVersion"
            const val storage = "com.google.firebase:firebase-storage:$firebaseStorageVersion"
        }

        object Hilt {
            const val core = "com.google.dagger:hilt-android:$hiltVersion"
            const val compiler = "com.google.dagger:hilt-compiler:$hiltVersion"
        }
    }

    object JSoup {
        const val core = "org.jsoup:jsoup:$jsoupVersion"
    }

    object JUnit {
        const val junit = "junit:junit:$jUnitVersion"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"

        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$kotlinVersion"
        const val collectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:$collectionsImmutableVersion"

        object Test {
            const val junit = "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion"
        }
    }

    object KotlinX {
        object Coroutines {
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
        }

        object Datetime {
            const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.2.0"
        }

        object Serialization {
            const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationJsonVersion"
        }
    }

    object Ktor {
        const val core = "io.ktor:ktor-client-core:$ktorClientVersion"
        const val android = "io.ktor:ktor-client-android:$ktorClientVersion"
        const val serialization = "io.ktor:ktor-client-serialization:$ktorClientVersion"
        const val logging = "io.ktor:ktor-client-logging:$ktorClientVersion"
    }

    object Mockito {
        const val core = "org.mockito:mockito-core:$mockitoVersion"
        const val kotlin = "org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion"
    }

    object ObjectBox {
        const val core = "io.objectbox:objectbox-kotlin:$objectboxVersion"
        const val processor = "io.objectbox:objectbox-processor:$objectBoxProcessorVersion"
    }

    object SquareUp {
        object OkHttp {
            const val core = "com.squareup.okhttp3:okhttp:$okHttpVersion"
            const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpLogginInterceptorVersion"
        }

        object Picasso {
            const val core = "com.squareup.picasso:picasso:$picassoVersion"
        }

        object Retrofit {
            const val core = "com.squareup.retrofit2:retrofit:$retrofitVersion"

            object Adapter {
                const val coroutines = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofitCoroutinesAdapterVersion"
                const val rxjava = "com.squareup.retrofit2:adapter-rxjava2:$retrofitRxJavaVersion"
            }

            object Converter {
                const val gson = "com.squareup.retrofit2:converter-gson:$gsonConverterVersion"
            }

            object Test {
                const val mock = "com.squareup.retrofit2:retrofit-mock:$retrofitMockVersion"
            }
        }
    }
}