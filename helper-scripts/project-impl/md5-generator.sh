#!/bin/bash

source "./helper-scripts/abstract/abstract-checksum-generator.sh"

input=${1}
output=${2}

getMd5Checksum "$input" "$output"