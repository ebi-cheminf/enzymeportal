#!/bin/bash
# Creates an initial mega-mapper index with both Swiss-Prot and TrEMBL enzymes.
# Param:
# $1: directory for the created lucene index.

#DOWNLOAD_BASE=ftp://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/complete
EBINOCLE_DATA=/ebi/extserv/projects/ebinocle/data
UNIPROT_DATA=$EBINOCLE_DATA/uniprot/latest
SWISSPROT=$UNIPROT_DATA/uniprot_sprot.xml
TREMBL=$UNIPROT_DATA/uniprot_trembl.xml

cd $(dirname $0)/..
mvn clean package

CP=.
for JAR in target/*.jar
do
    CP=$CP:$JAR
done

java -classpath $CP uk.ac.ebi.ep.mm.UniprotIndexer \
    -xmlFile $SWISSPROT -indexDir $1 \
&& java -classpath $CP uk.ac.ebi.ep.mm.UniprotIndexer \
    -xmlFile $TREMBL -indexDir $1

