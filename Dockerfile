# Usando JDK 17 leve (Alpine)
FROM eclipse-temurin:24-jdk-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiando o jar compilado para dentro do container
COPY target/coffebank-0.0.1-SNAPSHOT.jar app.jar

# Porta que a aplicação vai expor
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java","-jar","app.jar"]
