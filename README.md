# E2E Test for setOrientation command on Android

This repo aims at providing the minimum material to have an end-to-end test for the setOrientation
command on Android:

- an android app that observe the `user_rotation` system settings and display a human readable
  version of it in a text view:
    - 0 -> "PORTRAIT"
    - 1 -> "LANDSCAPE_LEFT"
    - 2 -> "UPSIDE_DOWN"
    - 3 -> "LANDSCAPE_RIGHT"
- a maestro script `test-set-orientation-flow.yaml` that calls the `setOrientation` command for each
  supported orientation and that assert that its visible on screen.

I tried to use no extra libraries and good old java to keep the assembled debug APK as small as
possible: ~8Kb on my mac.

Context: https://github.com/mobile-dev-inc/maestro/pull/2121

- Assemble a debug APK: `./gradlew assembleDebug`
- Install it: `adb install ./app/build/outputs/apk/debug/app-debug.apk`
- Run the maestro flow: `./maestro test ./test-set-orientation-flow.yaml`
- Observe test passing:

```
 ║
 ║  > Flow: test-set-orientation-flow
 ║
 ║    ✅   Launch app "com.example.maestro.orientation"
 ║    ✅   Set orientation LANDSCAPE_LEFT
 ║    ✅   Assert that "LANDSCAPE_LEFT" is visible
 ║    ✅   Set orientation LANDSCAPE_RIGHT
 ║    ✅   Assert that "LANDSCAPE_RIGHT" is visible
 ║    ✅   Set orientation UPSIDE_DOWN
 ║    ✅   Assert that "UPSIDE_DOWN" is visible
 ║    ✅   Set orientation PORTRAIT
 ║    ✅   Assert that "PORTRAIT" is visible
 ║
```

Note: this requires a version of maestro with the `setOrientation` command.
See https://github.com/mobile-dev-inc/maestro/pull/2121