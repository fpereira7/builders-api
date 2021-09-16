# builders-api
Projeto para Etapa de Validação - Vaga Senior Java Developer - Cód. 413

Link da aplicação:
https://francisco-builder.herokuapp.com/swagger-ui.html#/Clientes

## Configuração

Aplicação desenvolvida com as seguintes versões:

- Apache Maven: 3.5.3
- JDK: 11
- Spring Boot: 2.5.4
- Banco de dados: MySql

## 1.	Como	compilar	e	executar	a	sua	aplicação

Com o CMD aberto na raiz no projeto, execute uma das seguintes opções: 
 
- mvn spring-boot:run
- mvn install -DskipTest && cd target && java -jar builders-api-0.0.1-SNAPSHOT.jar

Acesso:
- A aplicação será executada na porta 8080.
- Endereço do Swagger: http://localhost:8080/swagger-ui.html

## 2.	Requisições via Postman

- Importar o arquivos Builders.postman_collection.json no Postman. Este arquivo está na raiz do projeto.

## 3. Como	executar	os	testes

- Com o CMD aberto na raiz no projeto, execute: mvn test

## 4. Executando a aplicação pelo docker

- Abrir o cmd na raiz do projeto, onde está o arquivo docker-compose.yml. Neste arquivo tem as configurações necessárias para baixar e executar o backend e configurar o MySql.
- Executar o comando -> docker compose up
- Aplicação será iniciada na porta 8080 e o mysql na porta 3306 por default
- Acessar o link: http://localhost:8080/swagger-ui.html#/Clientes

## Observações
- Fiz uma carga inicial com 15 registros no banco de dados, caso tenha necessidade de subir a aplicação para debugar e validar os fluxos.
- Na Collection do Postman, não defini variavel de ambiente. Basta usar localhost e porta default como mencionado anteriormente.
- O código foi escrito em português, mas consigo trabalhar escrevendo código em inglês.
