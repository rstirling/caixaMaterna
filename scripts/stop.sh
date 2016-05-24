#!/bin/bash
PIDFILE=caixa.pid
PID=`cat $PIDFILE`

array=(${PID//@/ })
echo "${array[0]}"
kill "${array[0]}"