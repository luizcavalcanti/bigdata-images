#!/bin/bash

#### variáveis de ambiente ####
#HADOOP_BASE=/usr/local/Cellar/hadoop/1.2.1/libexec
HFS_BASE_DIR=/user/luiz/orto
HFS_INPUT_DIR=$HFS_BASE_DIR/input
HFS_OUTPUT_DIR=$HFS_BASE_DIR/output

# Compila o programa
ant

# Limpa o diretorio de saida no HFS
$HADOOP/bin/hadoop dfs -rmr $HFS_OUTPUT_DIR

# Executa o experimento
$HADOOP/bin/hadoop jar dist/bigdata-images.jar br.edu.ufam.icomp.FeatureExtractor $HFS_INPUT_DIR $HFS_OUTPUT_DIR