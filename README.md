Repositório Backend Spring Boot
API Backend do projeto para gerenciamento/controle de caixa e faturamento.

Instalação:
  git clone https://github.com/projeto-grupo-07/spring-boot
 
Variáveis de Ambiente:
Colocar no application.properties

  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.format_sql=true

  spring.h2.console.enabled=true
  spring.datasource.url=jdbc:h2:mem:banco 
Documentação da API
Gerar documentação .yaml com a OpenAPI e colocar no APIHub

Deploy
Gerar docker para rodar aplicação.

1. Criar Dockerfile
Na raiz do projeto, crie o Dockerfile:

# Usar a imagem oficial do OpenJDK
FROM openjdk:21-jdk-alpine

WORKDIR /app
# JAR gerado
COPY target/myapp.jar app.jar
# Porta
EXPOSE 8080

# Comando rodar
ENTRYPOINT ["java","-jar","app.jar"]
Substitua myapp.jar pelo nome do JAR gerado pelo Maven (target/*.jar).

2. Build da aplicação
  mvn clean package
O JAR ficará em target/.

3. Construir imagem Docker
Na raiz do projeto (onde está o Dockerfile), rode:

  docker build -t myapp:latest .
myapp é o nome da imagem Docker.
4. Rodar a aplicação
  docker run -d -p 8080:8080 --name spring-boot myapp:latest
-d → roda em background
-p 8080:8080 → mapeia a porta do host para a do container
--name → nome do container
5. Verificar se a aplicação está rodando
  docker ps
6. Comandos úteis pós-deploy
Parar
docker stop spring-boot
Remover
docker rm spring-boot
Ver logs do container:
docker logs -f spring-boot
Entrar no container:
docker exec -it spring-boot sh
Limpar imagens antigas (opcional):
docker system prune -a
