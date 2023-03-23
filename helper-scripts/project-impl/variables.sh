#!/bin/bash

lib="libjmealloc"

# Website Stuff
# ---------------------
native_module="jme3-alloc-native"
java_module="jme3-alloc"
website_dir="./website"
config="native-doc-config"
javadoc="./jme3-alloc/build/docs/javadoc"
# ---------------------

native_sources="./${native_module}/src/lib/"
native_headers="./${native_module}/src/include/"
output="./${native_module}/build/lib"

gcc=$(which gcc)
compiler_optionsX86="-shared -m32 -g0 -s"
compiler_optionsX86_debug="-shared -m32 -g3 -D__ENABLE_DEBUG_LOGGER -D__ENABLE_LOGGER"

compiler_optionsX86_64="-shared -m64 -g0 -s"
compiler_optionsX86_64_debug="-shared -m64 -g3 -D__ENABLE_DEBUG_LOGGER -D__ENABLE_LOGGER"

# android compiler properties
NDK_HOME=$ANDROID_NDK_LATEST_HOME
android_clang="$NDK_HOME/toolchains/llvm/prebuilt/linux-x86_64/bin/clang"

min_android_sdk=21

# abi triples and output directories
# ---------------------
arm64="aarch64-linux-android"
arm64_lib_directory="arm64-v8a"

arm32="armv7a-linux-androideabi"
arm32_lib_directory="armeabi-v7a"

intel32="i686-linux-android"
intel32_lib_directory="x86"

intel64="x86_64-linux-android"
intel64_lib_directory="x86_64"
# ---------------------

# android compiler options, append the target to them when compiling
# ---------------------
compiler_options_android="-shared -g0 -s"
compiler_options_android_debug="-shared -g3 -D__ENABLE_DEBUG_LOGGER -D__ENABLE_LOGGER"
# ---------------------

# Maven sonatype stuff
# ---------------------
groupId="io.github.software-hardware-codesign"
artifacts=("${java_module}-desktop" "${java_module}-android")
settings="./helper-scripts/project-impl/publishing/maven-settings.xml"
build_dir="./jme3-alloc/build/libs"
sources_jar="${build_dir}/${java_module}-sources.jar"
javadoc_jar"${build_dir}/${java_module}-javadoc.jar"
# ---------------------