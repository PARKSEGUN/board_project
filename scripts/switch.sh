#!/usr/bin/env bash

#profile.sh 를 사용하기위함
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    #하나의 문장을 만들어서 파이프라인(|)으로 넘겨주기위함
    #엔진엑스가 변경할 프록지 주소를 생성합니다.
    #쌍따옴표를 사용해야한다
    #사용하지 않으면 $service_url을 그래돌 인식하지 못하고 변수를 찾게된다.

    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
  #| sudo tee /etc/nginx/conf.d/service-url.inc -> 앞에서 넘겨준 문장을 service-url.inc에 덮어쓰기
    echo "> 엔진엑스 Reload"
    sudo service nginx reload
    #reload 설정을 다시 불러오기 restart를 하면 끊기는 현상이있지만 reload는 끊기는 현상이없다.
  #다만 중요한 설저들은 restart를 사용합니다
}
