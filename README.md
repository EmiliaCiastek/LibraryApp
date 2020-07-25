# LibraryApp

This is a 'playground' project for checking new libraries or patterns. 

TODO:
- [ ] Example of MVI pattern
- [ ] Authentication with Firebase  
- [ ] Add error handling for communication with backend
- [ ] Add custom view for choosing author in new book form (replacing spinner)

## Dependencies: 
* [Backend library api](https://github.com/EmiliaCiastek/LibraryAppBackend)

## Before run you need to provide backend library api url:

* Create file: app/src/main/res/raw/config.properties (it's not tracked by git)
* Put value for key: `api_url`
* `android:usesCleartextTraffic="true"` - this flag in manifest is temporary because of lack of https in backend api


#### Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>