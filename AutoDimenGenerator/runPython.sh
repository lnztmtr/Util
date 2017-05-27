#! /bin/bash

function getNewDimenFile(){
  python changeValue.py "1.68"  > dimen_x1_68.xml
}

function main(){
    getNewDimenFile
}

main $1
