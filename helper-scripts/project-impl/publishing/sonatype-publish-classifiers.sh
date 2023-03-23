#!/bin/bash

source "./helper-scripts/abstract/abstract-publish.sh"
source "./helper-scripts/project-impl/variables.sh"

# obtain dependencies in the form 'groupId:artifact:version'
version=${1}

function publishClassifier() {
    local classifierId=$1
    local classifier=$2

    ${maven_bin} gpg:sign-and-deploy-file -s ${settings} -Durl=${sonatype_url} \
                                 -Dclassifier=${classifierId} \
                                 -DrepositoryId=${repository} \
                                 -DpomFile=${pomFile} \
                                 -Dgpg.passphrase=${passphrase} \
                                 -Dversion=${version} \
                                 -Dfile=${classifier}


    return $?
}

publishClassifier "${classifierIds[0]}" "${sources_jar}"
publishClassifier "${classifierIds[1]}" "${javadoc_jar}"