#!/bin/bash

function deployFile() {

    local settings=$1
    local groupId=$2
    local artifactId=$3
    local version=$4
    local packaging=$5
    local file=$6
    local repository=$7
    local url=$8
	
	mvn gpg:sign-and-deploy-file -s "${settings}" "-DgroupId=$groupId" \
                                                  "-DartifactId=$artifactId" \
                                                  "-Dversion=$version" \
                                                  "-Dpackaging=$packaging" \
                                                  "-Dfile=$file" \
                                                  "-DrepositoryId=$repository" \
                                                  "-Durl=$url" \
                                                  "-Dgpg.passphrase=jme-alloc" 
                                                          
    return $?
}

function deployClassifier() {

    local settings=$1
    local groupId=$2
    local classifier=$3
    local version=$4
    local packaging=$5
    local file=$6
    local repository=$7
    local url=$8
	
	mvn gpg:sign-and-deploy-file -s "${settings}" "-DgroupId=$groupId" \
                                                          "-Dclassifier=$classifier" \
                                                          "-Dversion=$version" \
                                                          "-Dpackaging=$packaging" \
                                                          "-Dfile=$file" \
                                                          "-DrepositoryId=$repository" \
                                                          "-Durl=$url" \
                                                          "-Dgpg.passphrase=jme-alloc" 
    return $?
}

function deployFilesFromDirectory() {

    local settings=$1
    local groupId=$2
    local artifacts=$3
    local version=$4
    local packaging=$5
    local directory=$6
    local repository=$7
    local url=$8

    project_dir=$(pwd)

	files=$(cd "${directory}" && ls *.* && cd "${project_dir}")

	for ((i=0; i < ${#files[@]}; i++)); do	
		deployFile "${settings}" \
                   "${groupId}" \
                   "${artifacts[i]}" \
                   "${version}" \
                   "${packaging}" \
                   "${directory}/${files[i]}" \
                   "${repository}" \
                   "${url}"
	done

	return $?
}

function deployJarsFromDirectory() {

    local settings=$1
    local groupId=$2
    local artifacts=$3
    local version=$4
    local packaging="jar"
    local directory=$5
    local repository=$6
    local url=$7

    deployFilesFromDirectory "${settings}" \
                             "${groupId}" \
                             "${artifacts}" \
                             "${version}" \
                             "${packaging}" \
                             "${directory}" \
                             "${repository}" \
                             "${url}"


	return $?
}

function deployJarsFromDirectoryIntoOSSRH() {

    local settings=$1
    local groupId=$2
    local artifacts=$3
    local version=$4
    local directory=$5
    local repository="ossrh"
    local url="https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"

    deployJarsFromDirectory "${settings}" \
                            "${groupId}" \
                            "${artifacts}" \
                            "${version}" \
                            "${directory}" \
                            "${repository}" \
                            "${url}"

	return $?
}

function deployClassifiersIntoOSSRH() {

    local settings=$1
    local groupId=$2
    local packaging="jar"
    local sources=$3
    local javadoc=$4
    local version=$5
    local repository="ossrh"
    local url="https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"

	files=("${sources}" "${javadoc}")
    classifiers=("sources" "javadoc")

	for ((i=0; i < ${#files[@]}; i++)); do	
		deployClassifier "${settings}" \
                         "${groupId}" \
                         "${classifiers[i]}" \
                         "${version}" \
                         "${packaging}" \
                         "${files[i]}" \
                         "${repository}" \
                         "${url}"
	done

	return $?
}