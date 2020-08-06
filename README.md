<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <img alt="npm" src="https://img.shields.io/badge/npm-%3E%3D%205.5.0-blue">
  <img alt="node" src="https://img.shields.io/badge/node-%3E%3D%209.3.0-blue">
  <img alt="license" src="https://img.shields.io/github/license/woowacourse/atdd-subway-2020">
</p>

<br>

# 레벨2 최종 미션 - 지하철 노선도 애플리케이션

## 🎯 요구사항
- [프론트엔드 미션](https://github.com/woowacourse/atdd-subway-2020/blob/master/frontend-mission.md)
- [백엔드 미션](https://github.com/woowacourse/atdd-subway-2020/blob/master/backend-mission.md)

## 🤔 미션 제출 방법
- 진행 방식은 오프라인 코딩 테스트와 동일하다.
- 저장소를 Fork하여 자신의 저장소에서 미션 구현을 완료하고, Pull Request를 통해 미션을 제출한다.
- Pull Request를 보낸 후 woowa_course@woowahan.com로 메일을 발송한다.

## 😌 레벨2 최종 미션을 임하는 자세
레벨2 과정을 스스로의 힘으로 구현했다는 것을 증명하는데 집중해라
- [ ] 기능 목록을 잘 작성한다.  
- [ ] 자신이 구현한 기능에 대해 인수 테스트를 작성한다.
- [ ] 자신이 구현한 코드에 대해 단위 테스트를 작성한다.
- [ ] TDD 사이클 이력을 볼 수 있도록 커밋 로그를 잘 작성한다.
- [ ] 사용자 친화적인 예외처리를 고민한다.
- [ ] 읽기 좋은 코드를 만든다.

## 🚀 Getting Started

### Install
#### npm 설치
```
cd frontend
npm install
```
> `frontend` 디렉토리에서 수행해야 합니다.

### Usage
#### webpack server 구동
```
npm run dev
```
#### application 구동
```
./gradlew bootRun
```
<br>

## 📝 License

This project is [MIT](https://github.com/woowacourse/atdd-subway-2020/blob/master/LICENSE.md) licensed.

### 기능 목록

1. 경로 조회 응답 결과에 요금 정보 추가

- 요금 계산 기준에 맞는 요금 정보도 함께 응답하도록 구현한다.
    - 요금 계산은 먼저 기본 운임 가격에서 이용 거리에 따라 추가요금 까지 계산한다.
    - 경로에 포함된 노선 중 가장 높은 추가 요금을 가진 금액을 더한다.
    - 로그인한 회원의 연령을 확인해서 연령별 할인 정책을 적용한다.
