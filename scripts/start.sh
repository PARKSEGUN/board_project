#!/usr/bin/env bash

#profile.sh 를 사용하기위함
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=board_project

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."
  #-Dsrping 명령어로 자바 프로젝트를 실행할 때
     #
     #.yml, properties등 불러오고 싶은 파일을 명시 해주어야 한다.
     #
     #명시 해주지 않으면 불러올 수 없고, 에러 또한 발생하지 않아 삽질의 위험이 있다.
nohup java -jar \
    -Dspring.config.location=classpath:/application-$IDLE_PROFILE.yml \
    -Dspring.profiles.active=$IDLE_PROFILE \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &