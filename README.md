# Repositório Backend Spring Boot

API Backend do projeto de gerenciamento de agendas, clientes e materiais do site da empresa Solarize

## Instalação: 
```bash
  git clone https://github.com/Projeto-de-extensao-Grupo-06/spring-boot.git
```

## Variáveis de Ambiente: 
Colocar no application.properties
```env
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.format_sql=true

  spring.h2.console.enabled=true
  spring.datasource.url=jdbc:h2:mem:banco 
```
    
## Documentação da API

Gerar documentação .yaml com a OpenAPI e colocar no APIHub

## Deploy

Gerar docker para rodar aplicação.

---

## 1. Criar Dockerfile

Na raiz do projeto, crie o `Dockerfile`:

```dockerfile
# Usar a imagem oficial do OpenJDK
FROM openjdk:21-jdk-alpine

WORKDIR /app
# JAR gerado
COPY target/myapp.jar app.jar
# Porta
EXPOSE 8080

# Comando rodar
ENTRYPOINT ["java","-jar","app.jar"]
```

> Substitua `myapp.jar` pelo nome do JAR gerado pelo Maven (`target/*.jar`).

---

## 2. Build da aplicação

```bash
  mvn clean package
```

> O JAR ficará em `target/`.

---

## 3. Construir imagem Docker

Na raiz do projeto (onde está o Dockerfile), rode:

```bash
  docker build -t myapp:latest .
```

* `myapp` é o nome da imagem Docker.

---

## 4. Rodar a aplicação

```bash
  docker run -d -p 8080:8080 --name spring-boot myapp:latest
```

* `-d` → roda em background
* `-p 8080:8080` → mapeia a porta do host para a do container
* `--name` → nome do container

---

## 5. Verificar se a aplicação está rodando

```bash
  docker ps
```

---

## 6. Comandos úteis pós-deploy

* Parar

```bash
docker stop spring-boot
```

* Remover

```bash
docker rm spring-boot
```

* Ver logs do container:

```bash
docker logs -f spring-boot
```

* Entrar no container:

```bash
docker exec -it spring-boot sh
```

* Limpar imagens antigas (opcional):

```bash
docker system prune -a
```

---



