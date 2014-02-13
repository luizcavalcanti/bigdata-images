#!/bin/bash

#### variáveis de ambiente ####
HADOOP_BASE=/usr/local/Cellar/hadoop/1.2.1/libexec
HFS_BASE_DIR=/user/luiz/orto
HFS_UNCOMPRESSED_DIR=$HFS_BASE_DIR/uncompressed
HFS_COMPRESSED_DIR=$HFS_BASE_DIR/input
HFS_OUTPUT_DIR=$HFS_BASE_DIR/output
EXTERNAL_DATA=~/Movies/aerial/dump/

# limpa todos os diretorios de dados no HDFS
echo Cleaning previous data...
$HADOOP_BASE/bin/hadoop dfs -rmr $HFS_UNCOMPRESSED_DIR
$HADOOP_BASE/bin/hadoop dfs -rmr $HFS_COMPRESSED_DIR
$HADOOP_BASE/bin/hadoop dfs -rmr $HFS_OUTPUT_DIR

# Copia imagens descomprimidas para diretorio temporario
echo Copying pristine data...
$HADOOP_BASE/bin/hadoop dfs -put $EXTERNAL_DATA $HFS_UNCOMPRESSED_DIR/

# transformas as imagens em um unico arquivo de dados (SequenceFile)
echo Creating SequenceFile...
$HADOOP_BASE/bin/hadoop jar dist/bigdata-images.jar br.edu.ufam.icomp.io.ImageToSequenceFile $HFS_UNCOMPRESSED_DIR $HFS_COMPRESSED_DIR

# Limpa diretorio temporario
echo Cleaning temp data...
$HADOOP_BASE/bin/hadoop dfs -rmr $HFS_UNCOMPRESSED_DIR

echo We are ready to go