#!/bin/bash

source "./helper-scripts/project-impl/variables.sh"

function downloadMavenLatest() {
    wget "https://dlcdn.apache.org/maven/maven-3/3.9.1/binaries/apache-maven-${maven_version}-bin.tar.gz"
    return $?
}

function extractMavenLatest() {
    tar xzvf "./apache-maven-${maven_version}-bin.tar.gz"
    return $?
}

downloadMavenLatest
extractMavenLatest
