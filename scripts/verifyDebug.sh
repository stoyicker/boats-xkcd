#!/usr/bin/env bash
./gradlew --stop &&
#The assembleDebug task is just to force the method count (no assemble task -> no method count).
./gradlew clean lint dependencyUpdates -Drevision=release -DoutputFormatter=plain -DoutputDir=presentation/staticAnalysisReport assembleDebug