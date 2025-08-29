## 📚 CoffeeBank
Este projeto é uma API REST desenvolvida em Java com Spring Boot, que simula procedimentos simples.

## ✅ Funcionalidades
📖 Cliente: CRUD completo.

👤 Usuários: CRUD completo.

🔍 Validações de regras de negócio:


❌ Tratamento global de exceções com mensagens claras e padronizadas

🧪 Testes unitários e de integração com JUnit 5 e Mockito


## 🛠️ Tecnologias utilizadas
Java 24

Spring Boot

Spring Web

Spring Data JPA

Hibernate Validator (Bean Validation)

PostgreSQL

Lombok

MapStruct

JUnit 5

Mockito

Kafka

JWT

Docker

## 📡Endopoints Cliente:
Cadastrar cliente.
- Ao realizar o cadastro do cliente, o sistema de forma automatica utilizando kafka realiza a criação da conta Bancaria.
- `POST /api/cliente`
```
{
  "dadosPessoais": {
    "nome": "João teste",
    "email": "teste@teste.com",
    "sexo": "MASCULINO",
    "cpf": "12345678910",
    "telefone": "33333333",
    "dataNascimento": "1992-02-02",
    "nacionalidade": "Brasileiro"
  },
  "endereco": {
    "rua": "Rua teste",
    "numero": "83",
    "bairro": "BairroTeste",
    "cidade": "Sao Paulo",
    "complemento": "Casa",
    "pais": "Brasil"
  }
}
```

Pesquisa cliente.
- Informar o codigo do cliente na url para buscar o usuario.
- `GET /api/cliente/1`
  
Deleta cliente.
- Informar o codigo do cliente na url para deletar o usuario.
- `DELETE /api/cliente/1`

Atualiza cliente.
- Informar o codigo do cliente na url
- Apenas os campos abaixo são permitidos atualizar com exceção do CPF.
- O CPF do codigo do cliente informado na URL precisa ser o mesmo do corpo enviado.
- `PUT /api/cliente/1`
```
{
  "dadosPessoais": {
    "nome": "João teste",
    "email": "teste@teste.com",
    "sexo": "MASCULINO",
    "cpf": "12345678910",
    "telefone": "33333333",
    "dataNascimento": "1992-02-02",
    "nacionalidade": "Brasileiro"
  },
  "endereco": {
    "rua": "Rua teste",
    "numero": "83",
    "bairro": "BairroTeste",
    "cidade": "Sao Paulo",
    "complemento": "Casa",
    "pais": "Brasil"
  }
}
```
## 📡Endopoints Usuario:

Criar usuario.
- Na criação voce precisa informar a role, pois o acesso tambem é limitado ao mesmo e nao basta der só o token.
- `POST /usuario`
```
{
    "usuario": "João",
    "senha": "6033",
    "role": "ADMIN"
}
```
Gerar token.
- Informar as credenciais usuario e senha.
- A role o sistema ja pesquisa automaticamente e verifica se voce pode acessar a rota.
- `GET /api/usuario/token`
```
{
    "usuario": "João",
    "senha": "6033",
}
```

