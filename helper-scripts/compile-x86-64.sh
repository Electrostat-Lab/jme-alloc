#!/bin/bash

source "./helper-scripts/abstract-compile.sh"

java_home=${1}

function compileX86_64() {
    getCurrentSystem
    echo -e "Compiling ${system}-X86_64"
    `compile "$gcc" "$native_sources" "$compiler_optionsX86_64" "$native_headers" "$java_home" "${output}/${system}/x86-64/${lib}"`
}

compileX86_64
