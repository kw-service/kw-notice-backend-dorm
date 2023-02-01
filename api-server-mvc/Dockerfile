FROM openjdk:17
# jar 파일 옮기기
COPY *.jar app.jar

# 시크릿 파일 옮기기
COPY fcm-key.json fcm-key.json

# 실행하기
ENTRYPOINT java -jar app.jar