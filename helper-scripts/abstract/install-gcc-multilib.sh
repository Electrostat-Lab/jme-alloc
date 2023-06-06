#!/bin/bash

function installGccMultiLib() {
    if [[ `uname` == "Linux" ]]; then
      sudo apt-get update
      sudo apt-get install gcc-multilib
    fi
  
  return $?
}

installGccMultiLib
