#!/bin/bash

# get the canonical link to this file
canonical_link=`readlink -f ${0}`
workflow=`dirname $canonical_link`

project_dir="${workflow%/*}"

##
# Clean the build directorys.
# @return the result of the gradle wrapper
##
function clean() {
    "${project_dir}/gradlew" clean
}

##
# Compiles the java code into byte-code and generates native # header files at the native-alloc/src/include module.
# @return the result of the gradle wrapper
##
function compileJava() {
    "${project_dir}/gradlew" :jvm-alloc:compileJava
}

##
# Compiles the native code into shared object file.
# @return the result of the gradle wrapper
##
function compileNatives() {
    "${project_dir}/gradlew" :native-alloc:build 
}
