#!/bin/bash

module="jme3-alloc-native"
lib="libjmealloc.so"

native_sources="./${module}/src/c/"
native_headers="./${module}/src/include/"
gcc=`which gcc`
compiler_options="-shared -m32 -g0"

output="./${module}/build/lib/main/debug/shared"

##
# Prepares the x86 folder in a system oriented approach.
# @return [0] in case of no errors, [1] otherwise.
##
function prepareX86() {
    local base_folder=$1

    if [[ ! -e "${base_folder}/${system_x86}" ]]; then
        mkdir "${base_folder}/${system_x86}"
    fi
    return $?
}

##
# Compiles the sources to the x86 folder in a system oriented approach.
# @return [0] in case of no errors, [1] otherwise.
##
function compileX86() {
    # function parameters
    local compiler=$1
    local native_sources=$2
    local compiler_options=$3
    local native_headers=$4
    local output_lib=$5
 
    # find sources and compile them
    local src=`find $native_sources -name "*.c" -o -name "*.c++" -o -name "*.cxx" -o -name "*.cpp"`
    # compile as a shared native files
    `$compiler $src $compiler_options -I"${native_headers}" -o $output_lib`
    return $?    
}

# run

# define system and architecture
if [[ `uname` == "Darwin" ]]; then 
    system_x86="macos/x86"
elif [[ `uname` -eq "Linux" ]]; then 
    system_x86="linux/x86"
else
    system_x86="windows/x86"
fi

`prepareX86 $output`
`compileX86 "$gcc" "$native_sources" "$compiler_options" "$native_headers" "${output}/${system_x86}/${lib}"`
