FROM java:8
WORKDIR /
ADD build/libs/auth-service.jar auth-service.jar
EXPOSE 5000
CMD java - jar -Dspring.profiles.active=local auth-service.jar 
