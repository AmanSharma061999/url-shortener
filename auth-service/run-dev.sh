#!/usr/bin/env bash
set -e
export SPRING_PROFILES_ACTIVE=dev
./mvnw spring-boot:run
