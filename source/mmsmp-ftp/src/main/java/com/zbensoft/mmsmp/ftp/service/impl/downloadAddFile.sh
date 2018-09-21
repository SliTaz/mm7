#!/bin/bash
ftp -n<<!
open 10.199.69.33
user sporder Sub_201303
binary
cd /90103
lcd /files/mmsmp/ftp/SubscribeInfo/90103
prompt
mget *20151213*.req
close
bye
!