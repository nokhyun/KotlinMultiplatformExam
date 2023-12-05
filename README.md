This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CryptoKit for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
----------------
<h1>Paging Exam</h1> 
<br>
* IOS
https://github.com/nokhyun/KotlinMultiplatformExam/assets/59673248/7b96d1f9-19d2-4e3a-8dfb-e23c577345df
<br>
* AOS
https://github.com/nokhyun/KotlinMultiplatformExam/assets/59673248/713d3b39-ce93-4815-8db6-623299bb9cb1

