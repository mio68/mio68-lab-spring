### How to load a map from application.yaml using @ConfigurationProperties?

Use the following syntax:
```yaml
kafka:
  consumer:
    "[bootstrap.servers]": localhost:9092 # This puts "bootstrap.servers" key into the map
    "[max.poll.records]": 10
```