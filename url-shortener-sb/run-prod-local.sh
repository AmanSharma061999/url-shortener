#!/usr/bin/env bash
set -e

export SPRING_PROFILES_ACTIVE=prod

# required by application-prod.yml (LOCAL simulation values)
export DB_URL='jdbc:postgresql://localhost:5433/URL_Shortener'
export DB_USER='postgres'
export DB_PASSWORD='6521'
export FRONTEND_URL='http://localhost:5173'

# optional (prod yml has a default, but set anyway)
export JWT_SECRET='VYqxdFGY2cnbGFRO5aKjJ2CqoOMuJBIVC9Hf7O7Il/E='

./mvnw spring-boot:run
