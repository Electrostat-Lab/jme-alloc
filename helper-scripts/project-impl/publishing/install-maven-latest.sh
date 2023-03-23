#!/bin/bash

source "./helper-scripts/project-impl/variables.sh"

function downloadMavenLatest() {
    wget "https://dlcdn.apache.org/maven/maven-3/3.9.1/binaries/apache-maven-${maven_version}-bin.tar.gz"
    return $?
}

function installMavenLatest() {
    tar xzvf "./apache-maven-${maven_version}-bin.tar.gz"
    sudo mk
    mv -v "./apache-maven-${maven_version}/" "${maven_sys_dir}/"
}

downloadMavenLatest
installMavenLatest