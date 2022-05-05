#!/bin/bash
export $(cat .env | xargs)
java -jar build/libs/expensetracker-0.0.1-SNAPSHOT.jar
