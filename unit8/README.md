# Unit 8 - AI-Enhanced GPS Data Web Service

Simulated RESTful web service for real-time GPS location tracking and route optimization.

## Services

- **GpsDistanceService** - Haversine distance calculation between GPS coordinates
- **RouteOptimizerService** - Greedy and Dijkstra route optimization with adjacency matrix
- **RealTimeTrackingService** - Simulated live GPS feed with speed tracking and AI predictions

## Build and Run

```bash
./build.sh
        java -cp build GpsDistanceService
        java -cp build RouteOptimizerService
        java -cp build RealTimeTrackingService
        java -cp build Test
```

## Deploy

```bash
./deploy.sh
        java -jar deploy/Unit8App.jar
```
