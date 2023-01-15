#!/bin/bash

# get the canonical link to this file
canonical_link=`readlink -f ${0}`
workflow=`dirname $canonical_link`

project_dir="${workflow%/*}"

##
# Assembles the application with its natives.
# @return the result of the gradle wrapper
##
function assemble() {
    "${project_dir}/gradlew" :native-alloc:copyNatives 
    "${project_dir}/gradlew" :jvm-alloc:assemble
}
