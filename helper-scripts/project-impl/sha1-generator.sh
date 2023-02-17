#!/bin/bash

source "./helper-scripts/abstract/abstract-checksum-generator.sh"

input=${1}
output=${2}

getSha1Checksum "$input" "$output"