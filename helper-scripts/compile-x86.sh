#!/bin/bash

source "./helper-scripts/abstract-compile.sh"

function compileX86() {
    getCurrentSystem 
    echo -e "Compiling ${system}-X86"
    `compile "$gcc" "$native_sources" "$compiler_optionsX86" "$native_headers" "${output}/${system}/x86/${lib}"`
    return $?    
}

compileX86
