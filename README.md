# QuizApp
quizApp
About the app, this is an app made in androidstudio. 
Its a quiz where it shows a photo of a cat and the user needs to answer.
the user also can add and delete entries for different cats(by adding a PNG image and a text). 



[Log of running the tests]

D:\skole-6-semester\QuizAppOblig2 (main -> origin)
λ gradlew connectedAndroidTest

> Task :app:connectedDebugAndroidTest
Starting 5 tests on SM-MYPHONE - 8.0.0

BUILD SUCCESSFUL in 1m 14s
50 actionable tasks: 15 executed, 35 up-to-date
D:\skole-6-semester\QuizAppOblig2 (main -> origin)
λ

--
Testing started at 14:14 ...

02/15 14:14:38: Launching 'All Tests' on samsung SM-MYPHONE.
App restart successful without re-installing the following APK(s): com.example.navdrawerdemo
Running tests

$ adb shell am instrument -w -m    -e debug false com.example.navdrawerdemo.test/androidx.test.runner.AndroidJUnitRunner
Connected to process 13796 on device 'samsung-sm'.

Started running tests

Tests ran to completion.
