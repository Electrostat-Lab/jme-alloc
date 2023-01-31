#!/bin/bash

module="jme3-alloc-native"
lib="libjmealloc"

native_sources="./${module}/src/lib/"
native_headers="./${module}/src/include/"
output="./${module}/build/libs"

gcc=`which gcc`
compiler_optionsX86="-shared -m32 -g0 -s -O3"
compiler_optionsX86_64="-shared -m64 -g0 -s -O3"