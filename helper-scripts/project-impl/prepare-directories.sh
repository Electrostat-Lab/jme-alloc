#!/bin/bash

source "./helper-scripts/abstract/abstract-compile.sh"
source "./helper-scripts/project-impl/variables.sh"

function autoPrepareBinaryDirectory() {
    local output=$1

    echo -e "Preparing the desktop binary output directory"

    # define system
    if [[ `uname` == "Linux" ]]; then
        local system="linux"
    elif [[ `uname` == "Darwin" ]]; then
        local system="macos"
    else 
        local system="windows"
    fi
    
    # prepare architectures
    prepareBinaryDirectory "${output}/${system}" "x86-64"
    prepareBinaryDirectory "${output}/${system}" "x86"

    return $?
}

function autoPrepareAndroidBinaryDirectory() {
    local output=$1
    
    echo -e "Preparing the android binary output directory"

    # prepare architectures
    prepareBinaryDirectory "${output}" "${arm64_lib_directory}"
    prepareBinaryDirectory "${output}" "${arm32_lib_directory}"

    prepareBinaryDirectory "${output}" "${intel64_lib_directory}"
    prepareBinaryDirectory "${output}" "${intel32_lib_directory}"

    return $?
}