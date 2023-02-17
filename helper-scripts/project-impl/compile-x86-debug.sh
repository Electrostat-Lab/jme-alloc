#!/bin/bash

source "./helper-scripts/abstract/abstract-compile.sh"
source "./helper-scripts/project-impl/variables.sh"

java_home=${1}

function debugLoggerCompileX86() {
    getCurrentSystem 
    echo -e "Compiling with debug logger ${system}-X86"
    `compile "$gcc" "$native_sources" "$compiler_optionsX86_debug" "$native_headers" "$java_home" "${output}/${system}/x86/${lib}"`  
    return $?
}

debugLoggerCompileX86
