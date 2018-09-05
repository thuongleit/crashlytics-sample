# Crashlytics and Timber combinations
---
This project shows how to set up [Firebase Crashlytics](https://firebase.google.com/docs/crashlytics/) with the awesome logger API [Timber](https://github.com/JakeWharton/timber)

## Features
- Get crash reports
- Get non-fatal errors into Firebase Crashlytics dashboard in an easy way with Timber
- Disable sending the reports to Firebase Crashlytics in `DEBUG` mode

## How to setup
- Install Firebase Crashlytics ([documentation](https://firebase.google.com/docs/crashlytics))
- Install [Timber](https://github.com/JakeWharton/timber)
 
```grovy
implementation 'com.jakewharton.timber:timber:4.7.1'
``` 

- Add a custom `Timber.Tree`

```kotlin
inner class CrashlyticsTree : Timber.Tree() {

 private val KEY_PRIORITY = "priority"
 private val KEY_TAG = "tag"
 private val KEY_MESSAGE = "message"

 override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
      when (priority) {
         Log.VERBOSE, Log.DEBUG, Log.INFO -> return

         else -> {
             Crashlytics.setInt(KEY_PRIORITY, priority)
  				Crashlytics.setString(KEY_TAG, tag)
				Crashlytics.setString(KEY_MESSAGE, message)

				if (t == null) {
					Crashlytics.logException(Exception(message))
 				} else {
					Crashlytics.logException(t)
				}
			}
		}
	}
```
- Install and integrate Firebase Crashlytics with Timber in your `Application` class
 
```kotlin
val crashlytics = CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build()
Fabric.with(this, Crashlytics.Builder().core(crashlytics).build())

 if (BuildConfig.DEBUG) {
 	Timber.plant(Timber.DebugTree())
 }
 Timber.plant(CrashlyticsTree())
```
    
see more at [App.kt](https://github.com/thuongleit/sample-crashlytics/blob/master/app/src/main/java/me/thuongle/crashlyticssample/App.kt)

## How to use

- A crash will be reported automatically
- Logs a non-fatal via `Timber.e()` or `Timber.w()`

## Credit

- https://blog.xmartlabs.com/2015/07/09/Android-logging-with-Crashlytics-and-Timber
