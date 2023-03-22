#!/bin/bash

source "./helper-scripts/abstract/abstract-publish.sh"
source "./helper-scripts/project-impl/variables.sh"

# obtain dependencies in the form 'groupId:artifact:version'
version=${1}

function publishBuild() {
    
    deployJarsFromDirectoryIntoOSSRH "${settings}" \
                                     "${groupId}" \
                                     "${artifacts}" \
                                     "${version}" \
                                     "${build_dir}" 

    return $?
}

publishBuild