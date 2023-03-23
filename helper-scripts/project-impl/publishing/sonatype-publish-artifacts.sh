#!/bin/bash

source "./helper-scripts/abstract/abstract-publish.sh"
source "./helper-scripts/project-impl/variables.sh"

# obtain dependencies in the form 'groupId:artifact:version'
version=${1}
desktop_artifact="${build_dir}/jme3-alloc-desktop-${version}.jar"
android_artifact="${build_dir}/jme3-alloc-android-${version}.jar"

function publishBuild() {
    local artifactId=$1
    local artifact=$2

    ${maven_bin} gpg:sign-and-deploy-file -s ${settings} -Durl=${sonatype_url} \
                                 -DartifactId=${artifactId} \
                                 -DrepositoryId=${repository} \
                                 -DpomFile=${pomFile} \
                                 -Dgpg.passphrase=${passphrase} \
                                 -Dversion=${version} \
                                 -Dfile=${artifact}


    return $?
}

publishBuild "${artifactIds[0]}" "${desktop_artifact}"
publishBuild "${artifactIds[1]}" "${android_artifact}"