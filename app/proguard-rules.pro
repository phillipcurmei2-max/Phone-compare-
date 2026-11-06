# PhoneCompare ProGuard / R8 rules.
#
# Jetpack Compose, Room and kotlinx.serialization all ship their own
# consumer rules, so this file only needs project-specific additions.

# Keep kotlinx.serialization generated serializers for @Serializable types.
-keepclassmembers,allowshrinking,allowoptimization class * {
    *** Companion;
}
-keepclasseswithmembers class ** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep Room entities' no-arg constructors (defensive; Room's own rules cover most cases).
-keepclassmembers class com.phonecompare.app.data.local.** {
    <init>(...);
}
