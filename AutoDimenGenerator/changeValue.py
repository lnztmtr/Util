#!/usr/bin/env python
import os
import os.path
import re
import sys

def findDimenNumber(multiple):
  fileDimen = open("./dimen.xml","r")
  index = 0
  for line in fileDimen:
    line_value = line
    re_key = '>([0-9]+)(dp|sp)<'
    re_pat_key = re.compile(re_key)
    search_result = re_pat_key.search(line)
    if search_result != None: 
      if index == 0:
        print('<!-- normal size x'+ multiple + ' -->')
        index = 1
      search_result_list = re_pat_key.search(line).groups()
      search_num = search_result_list[0]
      turn_to_num = int(search_num) * float(multiple)
      float2Num = round(turn_to_num,1)
      str_num = str(float2Num)
      if search_result_list[1] == 'sp':
        result_value = re_pat_key.sub('>'+str_num+'sp<',line)
      else:
        result_value = re_pat_key.sub('>'+str_num+'dp<',line)
      print(result_value)
    else:
      print(line_value)

findDimenNumber(sys.argv[1])
