#!/usr/bin/env bash
#The assembleDebug task is just to force the method count (no assemble task -> no method count).
./gradlew lint dependencyUpdates -Drevision=release -DoutputFormatter=plain -DoutputDir=presentation/staticAnalysisReport assembleDebug