# API Argon2id Authentication

Abaixo estarão algumas informações técnicas sobre o desenvolvimento deste projeto.

Este sistema visa aplicar as técnicas de criptografia usando o Argon2 e realizar todo o processo de autenticação através do login de usuários e a validação de tokens para consumo da API. 
Mais abaixo teremos os endpoints para consumo, e como consumir.

Para o desenvolvimento, foram usadas as seguintes tecnologias:

1. Java (Corretto 21.0.4) || (21.0.4-amzn)
2. Spring Boot (3.3.4)
3. PostgreSQL (16)
4. Argon2 (2.11)
5. Docker (4.37.1)

Obs: para mais informações revisar o *pom.xml* do projeto

## Pré-requisitos para desenvolvimento

Para executar o projeto e desenvolver, antes de tudo, é necessário certificar-se de que o seu computador contém as tecnologias necessárias.

### 1. Instalação do Docker

Clique [aqui](https://www.docker.com/get-started/) para ser levado diretamente à página de download do Docker. Não se preocupe, o site irá automaticamente selecionar a versão mais adequada de acordo com o seu Sistema Operacional. Caso necessário, terá também um botão ao lado que levará para a documentação do Docker, para entender o funcionamento da instalação.

Com o Docker instalado e rodando, será necessário agora realizar o pull das imagens do Dockerhub, imagens que forem necessárias para o desenvolvimento e será necessário para a execução do projeto na sua máquina.

### 2. Instalação do PostgreSQL 16 via imagem no Docker

Para isso, abra o terminal do seu editor de código, copie e cole os seguintes comandos:
`docker pull postgres:16`

Este comando irá realizar o download da imagem do PostgreSQL na versão 16 e será armazenada em seu computador. Caso este comando já foi executado anteriormente, passe para a próxima etapa.

### 3. Instalação do Java 21.0.4 Amazon

Para sistemas baseados em UNIX, é possível usar a instalação do SDKMAN! para realizar o management das versões do Java em seu computador, tornando fácil a troca de versões de acordo com os seus projetos.

Para saber mais como instalá-lo, clique [aqui](https://sdkman.io/install)

## Para subir o projeto

Com os pré-requisitos realizados e configurados, será necessário iniciar o container do PostgreSQL - para que seja possível interagir com o sistema e consumir os endpoints que a API fornece.

```bash
#! Comando para rodar a imagem do PostgreSQL e criar um container
docker run -d \
--name generic_database \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=api-auth \
-p 5432:5432 \
postgres:16
```

## Endpoints para consumo

Nesta seção será apresentada todos os endpoints desenvolvidas para o usuário consumir.

### 1. Criação de um usuário

```bash
curl --location 'http://localhost:8080/users/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"John Doe",
    "email":"john.doe@mail.com",
    "dateOfBirth": "1994-10-14",
    "password": "p@ssw0rd-2024"
}'
```

### 2. Realizar login na API

```bash
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"john.doe@mail.com.br",
    "password": "p@ssw0rd-2024"
}'
```

### 3. Retornar todos os usuários cadastrados

```bash
curl --location 'http://localhost:8080/users/all' \
--header 'Authorization: {auth-token}'
```

Para usá-lo, será necessário realizar o login primeiramente. Através do login será retornado o token que deve ser usado para usar no header Authentication.

### 4. Vincular um escopo a um usuário

```bash
curl --location 'http://localhost:8080/config/user-scope/{userId}' \
--header 'Authorization: {auth-token}' \
--header 'Content-Type: application/json' \
--data '{
    "scopeId": 1
}'
```

Neste caso é necessário recuperar o ID do usuário e informá-lo na URL e usar o token que é gerado na hora do login.

### 5. Consultar todos os escopos de um usuário

```bash
curl --location 'http://localhost:8080/config/user-scope/{userId}'
```

### 6. Criar um escopo

```bash
curl --location 'http://localhost:8080/config/scope' \
--header 'Authorization: {auth-token}' \
--header 'Content-Type: application/json' \
--data '{
    "name": "test.suite"
}'
```

### 7. Consultar escopos criados

```bash
curl --location --request GET 'http://localhost:8080/config/scope/all' \
--header 'Authorization: {auth-token}' \
--header 'Content-Type: application/json' \
--data '{
    "name": "users.readonly"
}'
```