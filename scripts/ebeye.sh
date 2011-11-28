#!/bin/bash
# Indexes EB-Eye XML files into an existing mega-mapper index.
# Params:
# $1: EB-Eye XML file (ChEBI/ChEMBL-target)
# $2: directory containing an existing mega-mapper lucene index

cd $(dirname $0)/..
mvn clean package

CP=.
for JAR in target/*.jar
do
    CP=$CP:$JAR
done

echo "Starting EB-Eye import - $(date)"
java -classpath $CP uk.ac.ebi.ep.mm.EbeyeParser -xmlFile $1
echo "Finished EB-Eye import - $(date)"
