# spring_boot_study_project
스프링 관련 학습용 프로젝트

## 리포지토리 하나에 여러 프로젝트 관리방법

0. 자식 리포지토리 생성
1. github 빈 레포지토리 생성(부모 리포지토리)
2. 빈 리포지토리 clone
3. 빈 리포지토리 디렉토리 이동
4. git remote add `이름` `자식 깃 주소`
5. git subtree add --prefix=`프로젝트이름` `자식 깃 허브주소` `자식 깃 허브 브랜치명`
6. git push origin main

## 에러
`fatal: index has modifications.  Cannot add.`

### 깃 최신화
1. git pull
2. git add . 
3. 처음 순서의 0번부터 다시 시작
