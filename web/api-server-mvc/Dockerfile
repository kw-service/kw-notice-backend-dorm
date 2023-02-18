### 해당 파일을 루트 디렉토리로 옮겨서 사용하면 됩니다. ###
FROM openjdk:17

# jar 파일 옮기기
COPY api-server-mvc/build/libs/*.jar app.jar

# 시크릿 파일 옮기기
COPY fcm-key.json fcm-key.json

# 실행하기
ENTRYPOINT java -jar app.jar