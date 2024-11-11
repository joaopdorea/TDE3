# Etapa 1: Build
FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /app

# Copia o arquivo pom.xml e instala as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte do projeto e compila o projeto
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Execução
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o JAR compilado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8081

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
