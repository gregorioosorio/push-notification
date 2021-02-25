# push-notification
PoC of push notifications

# How to test
- start the application with `./gradlew bootRun`
- open in a browser `http://localhost:8080/transaction/{id}/status`. **Note**: the id can be any long number, example: 123456
- you can see in the browser periodic messages in the browser
- fire a webhook event with command: `curl -X POST http://localhost:8080/webhook/{id}/status`, where the id should be the same id number you used in the browser url
