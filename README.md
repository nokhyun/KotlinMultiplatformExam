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
- java 17
<br>
- kotlin 1.9.20
<br>

<table>
  <tr>
    <th scope="col">IOS 15</th>
    <th scope="col">AOS ver31</th>
  </tr>
  <tr>
    <td><img src="https://github.com/nokhyun/KotlinMultiplatformExam/assets/59673248/310ea58a-09d1-4ce5-bec8-232683ffbe30"></td>
    <td><img src="https://github.com/nokhyun/KotlinMultiplatformExam/assets/59673248/44305acf-4a9f-4e6c-b513-b0435fae9b7d"></td>
  </tr>
</table>

<h3>library</h3>
- voyager navigator
<br>
- ktor-client
<br>
- ktor-logging
<br>
- ktor-serialization
<br>
- ktor-core
<br>
- kamel
<br>
- coroutine
<br>
- datetime
  
