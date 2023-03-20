#!/bin/bash

source "./helper-scripts/abstract/abstract-doxygen.sh"
source "./helper-scripts/project-impl/variables.sh"

# get the version of the generated documentation from the command-line args
version=${1}

function generateNativeDoc1() {
    # generate native docuemnation using a doxygen config file to an output directory
    generateNativeDoc "${website_dir}/${config}" "${website_dir}/${native_module}-${version}"
    return $?
}

generateNativeDoc1