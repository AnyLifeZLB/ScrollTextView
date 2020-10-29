[![Download](https://api.bintray.com/packages/jialian/goodJia/scrollTextSurfaceView/images/download.svg) ](https://bintray.com/jialian/goodJia/scrollTextSurfaceView/_latestVersion)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](https://www.apache.org/licenses/LICENSE-2.0)

# Scroll Text View
The library can scorll text view.

# How to use
**1 . root/build.gradle**
````gradle
allprojects {
    repositories {
        ...
        maven { url "https://dl.bintray.com/jialian/goodJia"
    }
}
````

**2. app/build.gradle**
````gradle
implementation 'com.goodjia:scrollTextSurfaceView:'{_latestVersion}'
````
**3. xml**
```xml
    <com.goodjia.ScrollTextSurfaceView
           android:id="@+id/scrollTextView"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           app:speed="4"
           app:text="scroll text view"
           app:letterSpacing="0.2"
           app:textPadding="6dp"
           app:text_color="@android:color/white"
           app:text_size="22sp" />
```

**4. ScrollTextSurfaceView show/dismiss**

```kotlin
     scrollTextSurfaceView.show(
                content = content,
                speed = speed,
                textSize = textSize,
                textColor = textColor,
                bgColor = bgColor,
                letterSpacing = letterSpacing,
                playTime = playTime,
                repeatTimes = repeatTimes
            )

     scrollTextSurfaceView.dismiss()
```

## LICENSE
````
Copyright 2020 Jia

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
````

