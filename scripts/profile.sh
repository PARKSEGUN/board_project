#!/usr/bin/env bash

#쉬고있는 것이 누구고 그 스프링부트의 포트값

# 쉬고 있는 profile 찾기: real1이 사용중이면 real2가 쉬고 있고, 반대면 real1이 쉬고 있음
function find_idle_profile()
{
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)
    #현재 엔진엑스가 바라보고 있는 스프링 부트가 정상적으로 수행 중인지 확인
    #응답값은 HttpStatus로 받는다.

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        CURRENT_PROFILE=real2
        #크다면 real2 사용
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
        #아니면 엔진엑스가 바라보고 있는 스프링 부트 사용
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
      IDLE_PROFILE=real2
      #IDLE_PROFILE 은 쉬고있는 profile 1이 사용중이라면 2가 쉬고있는 profile
    else
      IDLE_PROFILE=real1
      #반대
    fi

    echo "${IDLE_PROFILE}"
    # bash는 return value가 안되니 *제일 마지막줄에 echo로 해서 결과 출력*후, 클라이언트에서 값을 사용한다
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile) #이렇게 bash는 echo로 출력후에 사용가능

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}