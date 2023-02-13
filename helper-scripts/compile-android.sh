#!/bin/bash

# source directory from the gradle wrapper working directory
source "./helper-scripts/abstract-compile.sh"

java_home=${1}

function compileAndroidArm64() {
    getCurrentSystem
    echo -e "Compiling Android arm64 binaries"
    # compile an aarch64 binary on the arm64-v8a directory with min_sdk = 21 (android lollipop)
    `compile "$android_clang" "$native_sources" "$compiler_options_android -target ${arm64}${min_android_sdk}" "$native_headers" "$java_home" "${output}/${arm64_lib_directory}/${lib}"`
    return $?
}

function compileAndroidArm32() {
    getCurrentSystem
    echo -e "Compiling Android arm32 binaries"
    # compile an arm32 binary on the armeabi-v7a directory with min_sdk = 21 (android lollipop)
    `compile "$android_clang" "$native_sources" "$compiler_options_android -target ${arm32}${min_android_sdk}" "$native_headers" "$java_home" "${output}/${arm32_lib_directory}/${lib}"`
    return $?
}

function compileAndroidIntel32() {
    getCurrentSystem
    echo -e "Compiling Android intel32 binaries"
    `compile "$android_clang" "$native_sources" "$compiler_options_android -target ${intel32}${min_android_sdk}" "$native_headers" "$java_home" "${output}/${intel32_lib_directory}/${lib}"`
    return $?
}

function compileAndroidIntel64() {
    getCurrentSystem
    echo -e "Compiling Android intel64 binaries"
    `compile "$android_clang" "$native_sources" "$compiler_options_android -target ${intel64}${min_android_sdk}" "$native_headers" "$java_home" "${output}/${intel64_lib_directory}/${lib}"`
    return $?
}

compileAndroidArm64
compileAndroidArm32 
compileAndroidIntel64
compileAndroidIntel32  