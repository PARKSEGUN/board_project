#!/usr/bin/env bash

#보통 외부파일을 import할때 사용하는 구문
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)  #stop.sh가 속해있는 경로
source ${ABSDIR}/profile.sh #profile.sh import 개념, profile안에있는 함수 사용가능

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ] #-z는 문자열길이가 0이면 참
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi