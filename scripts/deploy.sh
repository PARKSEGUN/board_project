#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=board_project

echo "> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl board_project | grep jar | awk '{print $1}')
#pgrep -fl board_project | grep jar -> 애플리케이션 이름으로된 jar 프로그램 찾기
#awk '{print $1}' -> 해당 ID를 찾는다.

echo "현재 구동중인 어플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar $JAR_NAME &
#nohup java -jar $REPOSITORY/jar/$JAR_NAME &
#nohup java -jar \
#    -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
#    -Dspring.profiles.active=real \
#    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &