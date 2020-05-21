#!/bin/sh

name=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="artifactId"]/text()' pom.xml)
version=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="version"]/text()' pom.xml)

mvn clean install

docker build \
--build-arg NAME=${name} \
--build-arg VERSION=${version} \
--tag ${name}:latest \
--tag ${name}:${version} \
--label ${name}=${version} .
