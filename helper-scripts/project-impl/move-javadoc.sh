#!/bin/bash

source "./helper-scripts/abstract/abstract-move-files.sh"
source "./helper-scripts/project-impl/variables.sh"

# get the version of the generated documentation from the command-line args
version=${1}

##
# Moves the generated javadocs to the deployment folder.
##
function moveJavadoc() {
    move "${javadoc}/" "${website_dir}/${java_module}-${version}"
}

moveJavadoc