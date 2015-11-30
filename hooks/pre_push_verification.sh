#!/usr/bin/env bash
if [ `git rev-parse --abbrev-ref HEAD` == "develop" -o `git rev-parse --abbrev-ref HEAD` == "master" ]; then
  scripts/verifyDebug.sh && scripts/testUi.sh
fi