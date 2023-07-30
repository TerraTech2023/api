<h1 align="center">
 Terra Tech 
</h1>
<div align="center">
   <img src="https://img.shields.io/static/v1?label=Tipo&message=Projeto&color=8257E5&labelColor=000000" alt="Projeto" />
</div>


Api que fazer a ponte entre o cliente e os coletores visando facilitar o descarte de lixo reciclavel

## Tecnologias  
- [PostgreSQL](https://www.postgresql.org/)
- [Docker](https://www.docker.com/)
- [Spring Framework](https://spring.io)
   - [Spring Boot](https://spring.io/projects/spring-boot) 
   - [Spring Security](https://spring.io/projects/spring-security)
   - [Spring JPA](https://spring.io/projects/spring-data-jpa)
   - [Spring MVC](https://docs.spring.io/spring-framework/reference/web.html)

## Práticas Utilizadas
- SOLID
- Observer
- Injeção de Dependencia
- Geração de automatica do Swagger com Springdoc
- Tratamento de resposta de erro

## Como Executar Localmente

1. clone o repositorio
2. builde projeto:
```
$ ./mvnw clean package
```
3. execute:
```
$ java -jar target/api-terra-tech-0.0.1-SNAPSHOT.jar
```

## Endpoints da Api

endereço da api [localhost:8080/api](http://localhost:8080/api)


## 1. Swagger
   ```
   /documentation
   ```
## 2. Auth
   - POST
     
   - cadastro
       
     ```
     /auth/register
     ```
     
     Enviar :
     
     ```
      {
         "name": "John Doe",
         "email": "john@doe.com",
         "password": "password",
         "confirmPassword": "password",
         "dateOfBirth": "YYYY-mm-dd",
         "role": "USER or COLLECTOR"
      }
     ```
     
     - login
       
     ```
     /auth/login
     ```
     
     Enviar:
     
     ```
     {
       "email": "john@doe.com",
       "password": "password",
     }
     ```

## 3. Usuário
   - GET
     ```
     /v1/users/{id}
     ```
     Resposta:
     ```
      {
         "id": 1,
         "name": "John Doe",
         "email": "john@doe.com",
         "password": "password",
         "dateOfBirth": "YYYY-mm-dd",
         "address": {
              "cep": "00000000",
              "number": "00"
         },
         "residues": []
      }
     ```
   - PUT
     ```
     /v1/users/{id}
     ```
   - DELETE
     ```
     /v1/users/{id}
     ```
   - NOTIFY
     ```
     /v1/users/notify
     ```
## 4. coletores
   - GET
     ```
     /v1/collectors/{id}
     ```
     Resposta:
     ```
      {
         "id": 1,
         "name": "John Doe",
         "email": "john@doe.com",
         "password": "password",
         "dateOfBirth": "YYYY-mm-dd",
      }
     ```
   - PUT
     ```
     /v1/collectors/{id}
     ```
   - DELETE
     ```
     /v1/collectors/{id}
     ```
   - RECEIVE - NOTIFICATION
     ```
     /v1/collectors/notification
     ```
## 5. residuo
   - GET
     ```
     /residue/{id}
     ```
     Resposta :
     ```
      {
         "id": 1,
         "tipo": "PLASTICO",
         "estado": "PENDENTE",
         "quantidade": "1",
         "descricao": "descricão",
      }
     ```
   - POST
      - cadastro 
     ```
     /residue/register
     ```
     Enviar :
     ```
      {
         "id": 1,
         "tipo": "PLASTICO",
         "estado": "PENDENTE",
         "quantidade": "1",
         "descricao": "descricão",
      }
     ```
   - PUT
     ```
     /residue/{id}
     ```
   - DELETE
     ```
     /residue/{id}
     ```
