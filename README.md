# Survey Alerts

Projeto Spring Boot para gerar alertas baseado em respostas de questionários.

## Tecnologias

- Java 17\+
- Spring Boot
- Maven
- H2 Database (para testes)

## Endpoints

### Pesquisas (Survey)

#### Criar uma nova pesquisa
`POST /survey`

**Body:**
```json
{
  "pointOfSale": "Loja 1",
  "expectedPrice": "5",
  "product": "Arroz",
  "answers": [
    {
      "question": "Qual a situação do produto?",
      "answerData": "Produto ausente na gondola"
    },
    {
      "question": "Qual o preço do produto?",
      "answerData": "6"
    }
  ]
}

```


#### Gerar alerta
`POST /alert`

#### Retornar todas as pesquisas
`GET /survey`

#### Retornar todas os alertas
`GET /alert`
#### Retornar todos os e-mails
`GET /mail`

### Usuários (User)

#### Criar um novo usuário
`POST /user`

**Body:**
```json
{
  "login": "mariasilva",
  "name": "Maria Silva",
  "email": "mariasilva@email.com",
  "created": "2024-06-10T10:00:00",
  "lastUpdate": "2024-06-10T10:00:00",
  "active": true
}

```
#### Retornar todos os usuários
`GET /user`
#### Retornar usuários ativos por IDs
`GET /user/by-ids?id=1&id=2`

Na raiz do repositório está disponível uma collection criada no Postman com todas as requisições necessárias para testar os endpoints. Basta fazer o download e importar no seu postman: [https://github.com/RaquelAmbroziof/api-clientes-e-veiculos/blob/main/teste.postman_collection.json](https://github.com/ambrozioraquel/surveys-alert/blob/master/surveys.postman_collection.json)


