#!/bin/bash

function generateConfig() {
    directory=$1
    config=$2
    if [[ ! -e "${directory}/${config}" ]]; then 
        doxygen -dcite -g -x "${directory}/${config}"
    fi
    
    return $?
}

function generateNativeDoc() {
    config_file=$1
    output_directory=$2
    
    # keep track of the original working directory
    working_dir_org=$(pwd)

    if [[ ! -e ${output_directory} ]]; then
        mkdir -p "${output_directory}"
    fi
    
    cd "${output_directory}"

    # generate documentation using the doxygen config file
    doxygen -d"cite" "../../${config_file}"

    # change directory back to the original dir
    cd "${working_dir_org}"

    return $?
}
