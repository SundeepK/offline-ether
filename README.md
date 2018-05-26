# Offline Ether

Run test:
```bash
    $ ./gradlew test
```

UI test (connect device first)
```bash
    ./gradlew connectedAndroidTest
```

App which allows users to sign transactions from an air gap computer. It works by allowing users to use the camera to scan a QR code and broadcasts transactions automatically. You can also add addresses and view recent transactions as well as monitor pending transactions.

## Why?
I wanted to create a simple focused app that demonstrates how to add unit tests, presenter pattern, dagger with espresso test support, rx-java and object box. Since the app is small in focus it should be simple enough to maintain when majour changes happen in android eco system. Hopefully I can maintain the app to keep it relevant and a good example on how to build apps with testing.

<img src="https://github.com/SundeepK/offline-ether/blob/master/screenshots/device-2018-05-26-225653-small.png" width="300" height="600">
<img src="https://github.com/SundeepK/offline-ether/blob/master/screenshots/device-2018-05-26-225732-small.png" width="300" height="600">
<img src="https://github.com/SundeepK/offline-ether/blob/master/screenshots/device-2018-05-26-225815-small.png" width="300" height="600">



