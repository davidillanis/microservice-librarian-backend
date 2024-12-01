#!/bin/bash

# List of microservices JAR names
SERVICES=(
    "microservice-config-0.0.1-SNAPSHOT.jar"
    "microservice-eureka-0.0.1-SNAPSHOT.jar"
    "microservice-gateway-0.0.1-SNAPSHOT.jar"
    "microservice-users-0.0.1-SNAPSHOT.jar"
    "microservice-issue-0.0.1-SNAPSHOT.jar"
    "microservice-librarian-0.0.1-SNAPSHOT.jar"
    "microservice-chat-0.0.1-SNAPSHOT.jar"
    "microservice-utils-0.0.1-SNAPSHOT.jar"
)

echo "Stopping all microservices..."

# Iterate through the services and stop each one
for SERVICE in "${SERVICES[@]}"; do
    PID=$(ps -ef | grep "$SERVICE" | grep -v "grep" | awk '{print $2}')
    if [ -n "$PID" ]; then
        echo "Stopping $SERVICE (PID: $PID)..."
        kill "$PID"
        wait "$PID" 2>/dev/null
        echo "$SERVICE stopped."
    else
        echo "$SERVICE is not running."
    fi
done

echo "All microservices stopped."
