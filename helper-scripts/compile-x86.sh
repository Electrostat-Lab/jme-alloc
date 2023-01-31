#!/bin/bash

source "./helper-scripts/abstract-compile.sh"

java_home=${1}

function compileX86() {
    getCurrentSystem 
    echo -e "Compiling ${system}-X86"
    `compile "$gcc" "$native_sources" "$compiler_optionsX86" "$native_headers" "$java_home" "${output}/${system}/x86/${lib}"`
    return $?    
}

printInfo "x86" "${java_home}"

compileX86
