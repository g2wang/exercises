#!/bin/sh

javac -Xlint:unchecked --source-path ./src -d ./bin -cp ./bin src/BaseballElimination.java && java -cp ./bin BaseballElimination $1
