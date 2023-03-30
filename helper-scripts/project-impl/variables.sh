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
sonatype_url="https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
repository="ossrh"
groupId="io.github.software-hardware-codesign"
maven_version="3.9.1"
maven_bin="./apache-maven-$maven_version/bin/mvn"
desktop_pomFile="./helper-scripts/project-impl/publishing/jme3-alloc-desktop.pom"
android_pomFile="./helper-scripts/project-impl/publishing/jme3-alloc-android.pom"
passphrase="jme-alloc"

desktop_artifactId_release="${java_module}-desktop"
android_artifactId_release="${java_module}-android"

desktop_artifactId_debug="${desktop_artifactId_release}-debug"
android_artifactId_debug="${android_artifactId_release}-debug"

settings="./helper-scripts/project-impl/publishing/maven-settings.xml"
build_dir="./jme3-alloc/build/libs"
# ---------------------