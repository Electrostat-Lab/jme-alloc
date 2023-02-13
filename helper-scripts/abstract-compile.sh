#!/bin/bash

source "./helper-scripts/variables.sh"

system="NULL"

function getCurrentSystem() {
    if [[ `uname` == "Darwin" ]]; then 
        system="macos"
    elif [[ `uname` == "Linux" ]]; then 
        system="linux"
    else
        system="windows"
    fi 
    return $?
}

function prepareRootDirectory() {
    local output=$1
    if [[ -e $output ]]; then
        return 0
    fi
    mkdir -p $output
    return $?
}

function prepareBinaryDirectory() {
    local system=$1
    local arch=$2

    if [[ -e "${system}/${arch}" ]]; then
        return 0
    fi
    mkdir -p "${system}/${arch}"
    return $?
}

function compile() {
    # function parameters
    local compiler=$1
    local native_sources=$2
    local compiler_options=$3
    local native_headers=$4
    local java_home=$5
    local output_lib=$6
    
    local jni_headers0="${java_home}/include"
    
    if [[ `uname` == "Darwin" ]]; then 
        local shared_lib="${output_lib}.dylb"
        local jni_headers1="${jni_headers0}/darwin"
    elif [[ `uname` == "Linux" ]]; then 
        local shared_lib="${output_lib}.so"
        local jni_headers1="${jni_headers0}/linux"
    else
        local shared_lib="${output_lib}.dll"
        local jni_headers1="${jni_headers0}/win32"
    fi 
 
    # find sources and compile them
    local src=`find $native_sources -name "*.c" -o -name "*.c++" -o -name "*.cxx" -o -name "*.cpp"`
    # compile as a shared native files
    `$compiler $src $compiler_options -I"${native_headers}" -I"${jni_headers0}" -I"${jni_headers1}" -o $shared_lib`
    return $?    
}

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
