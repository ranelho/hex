FROM openjdk:21-slim

WORKDIR /app

# Copiar o JAR compilado da aplicação
COPY target/*.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java","-jar","/app/app.jar"]
