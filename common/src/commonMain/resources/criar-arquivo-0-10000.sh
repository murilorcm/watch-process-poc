#!/bin/bash

# Nome do arquivo
filename="/mnt/c/Users/muril/git/watch-process-poc/common/src/commonMain/resources/meu-arquivo.txt"

# Criar um arquivo vazio
touch $filename

# Loop para inserir números de 0 a 10000
for ((i=0; i<=10000; i++))
do
  echo "$i" >> $filename
done


cat $filename