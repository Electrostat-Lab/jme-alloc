#!/bin/bash

# get the canonical link to this file
canonical_link=`readlink -f ${0}`
workflow=`dirname $canonical_link`

# include the sources
source "${workflow}/compile.sh"
source "${workflow}/assemble.sh"

##
# Builds the application for current variant.
# @return the result of the gradle wrapper
##
function build() {
    clean && compileJava && compileNatives && assemble
    return $?
}

build 
