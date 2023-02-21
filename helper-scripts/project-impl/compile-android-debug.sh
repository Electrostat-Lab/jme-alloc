#!/bin/bash

# source directory from the gradle wrapper working directory
source "./helper-scripts/abstract/abstract-compile.sh"
source "./helper-scripts/project-impl/variables.sh"

java_home=${1}

function compileAndroidArm64Debug() {
    getCurrentSystem
    echo -e "Compiling debug Android arm64 binaries"
    # compile an aarch64 binary on the arm64-v8a directory with min_sdk = 21 (android lollipop)
    compile "$android_clang" "$native_sources" "$compiler_options_android_debug -target ${arm64}${min_android_sdk}" "$native_headers" "$java_home" "${output}/${arm64_lib_directory}/${lib}"
    return $?
}

function compileAndroidArm32Debug() {
    getCurrentSystem
    echo -e "Compiling debug Android arm32 binaries"
    # compile an arm32 binary on the armeabi-v7a directory with min_sdk = 21 (android lollipop)
    compile "$android_clang" "$native_sources" "$compiler_options_android_debug -target ${arm32}${min_android_sdk}" "$native_headers" "$java_home" "${output}/${arm32_lib_directory}/${lib}"
    return $?
}

function compileAndroidIntel32Debug() {
    getCurrentSystem
    echo -e "Compiling debug Android intel32 binaries"
    compile "$android_clang" "$native_sources" "$compiler_options_android_debug -target ${intel32}${min_android_sdk}" "$native_headers" "$java_home" "${output}/${intel32_lib_directory}/${lib}"
    return $?
}

function compileAndroidIntel64Debug() {
    getCurrentSystem
    echo -e "Compiling debug Android intel64 binaries"
    compile "$android_clang" "$native_sources" "$compiler_options_android_debug -target ${intel64}${min_android_sdk}" "$native_headers" "$java_home" "${output}/${intel64_lib_directory}/${lib}"
    return $?
}

compileAndroidArm64Debug
compileAndroidArm32Debug
compileAndroidIntel64Debug
compileAndroidIntel32Debug