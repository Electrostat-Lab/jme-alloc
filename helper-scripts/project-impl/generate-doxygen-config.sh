#!/bin/bash

source "./helper-scripts/abstract/abstract-doxygen.sh"
source "./helper-scripts/project-impl/variables.sh"

function generateConfig1() {
    # create a config file 
    generateConfig ${website_dir} ${config}
    return $?
}

generateConfig1