[![Build Status](https://travis-ci.org/EmiliaCiastek/LibraryApp.svg?branch=master)](https://travis-ci.org/EmiliaCiastek/LibraryApp)

## LibraryApp

* Before run you need to provide backend library api url: 

    1. Create file: app/src/main/res/raw/config.properties (it's not tracked by git)
    2. Put value for key: `api_url` 

* `android:usesCleartextTraffic="true"` - this flag in manifest is temporary because of lack of https in backend api