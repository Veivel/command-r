## Development

Command R uses Balm API for its multi-platform capability: https://balm.twelveiterations.com/getting-started

## Build

Build:
```
./gradlew build
```

Build only for one platform:
```
./gradlew :fabric:build
```

## Tests

Run tests:
```
./gradlew test
```

Run tests with coverage:
```
./gradlew test jacocoTestReport
```
You'll find the coverage report in `common/build/reports/jacoco/test/html/index.html`.