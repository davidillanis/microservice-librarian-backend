#!/bin/bash

# Function to clean up all running microservices
cleanup() {
    echo "Stopping all microservices..."
    kill $PID1 $PID2 $PID3 $PID4 $PID5 $PID6 $PID7 $PID8 2>/dev/null
    wait $PID1 $PID2 $PID3 $PID4 $PID5 $PID6 $PID7 $PID8 2>/dev/null
    echo "All microservices stopped."
    exit 0
}

# Trap SIGINT and SIGTERM to run cleanup
trap cleanup SIGINT SIGTERM

echo "Start Microservice-Config"
java -jar /home/davidabel/Documentos/IntellyIdea/microservice-librarian-backend/microservice-config/target/microservice-config-0.0.1-SNAPSHOT.jar &
PID1=$!

sleep 6

if ps -p $PID1 > /dev/null; then
    echo "Start Microservice-Eureka"
    java -jar /home/davidabel/Documentos/IntellyIdea/microservice-librarian-backend/microservice-eureka/target/microservice-eureka-0.0.1-SNAPSHOT.jar &
    PID2=$!

    sleep 6
    if ps -p $PID2 > /dev/null; then
        echo "Start Microservice-Gateway"
        java -jar /home/davidabel/Documentos/IntellyIdea/microservice-librarian-backend/microservice-gateway/target/microservice-gateway-0.0.1-SNAPSHOT.jar &
        PID3=$!

        sleep 6
        if ps -p $PID3 > /dev/null; then
            echo "Start Microservice-Users"
            java -jar /home/davidabel/Documentos/IntellyIdea/microservice-librarian-backend/microservice-users/target/microservice-users-0.0.1-SNAPSHOT.jar &
            PID4=$!

            sleep 6
            if ps -p $PID4 > /dev/null; then
                echo "Start Microservice-Issue"
                java -jar /home/davidabel/Documentos/IntellyIdea/microservice-librarian-backend/microservice-issue/target/microservice-issue-0.0.1-SNAPSHOT.jar &
                PID5=$!

                sleep 6
                if ps -p $PID5 > /dev/null; then
                    echo "Start Microservice-Librarian"
                    java -jar /home/davidabel/Documentos/IntellyIdea/microservice-librarian-backend/microservice-librarian/target/microservice-librarian-0.0.1-SNAPSHOT.jar &
                    PID6=$!

                    sleep 6
                    if ps -p $PID6 > /dev/null; then
                        echo "Start Microservice-Chat"
                        java -jar /home/davidabel/Documentos/IntellyIdea/microservice-librarian-backend/microservice-chat/target/microservice-chat-0.0.1-SNAPSHOT.jar &
                        PID7=$!

                        sleep 6
                        if ps -p $PID7 > /dev/null; then
                            echo "Start Microservice-Utils"
                            java -jar /home/davidabel/Documentos/IntellyIdea/microservice-librarian-backend/microservice-utils/target/microservice-utils-0.0.1-SNAPSHOT.jar &
                            PID8=$!

                            sleep 6
                            if ps -p $PID8 > /dev/null; then
                                echo "All microservices started successfully."
                            else
                                echo "Error: Failed to start Microservice-Utils"
                                cleanup
                            fi
                        else
                            echo "Error: Failed to start Microservice-Chat"
                            cleanup
                        fi
                    else
                        echo "Error: Failed to start Microservice-Librarian"
                        cleanup
                    fi
                else
                    echo "Error: Failed to start Microservice-Issue"
                    cleanup
                fi
            else
                echo "Error: Failed to start Microservice-Users"
                cleanup
            fi
        else
            echo "Error: Failed to start Microservice-Gateway"
            cleanup
        fi
    else
        echo "Error: Failed to start Microservice-Eureka"
        cleanup
    fi
else
    echo "Error: Failed to start Microservice-Config"
    cleanup
fi
