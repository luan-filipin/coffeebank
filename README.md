# 📚 CoffeeBank
API REST desenvolvida em **Java com Spring Boot**, que simula procedimentos bancários simples.

---

## ✅ Funcionalidades
- 📖 **Cliente:** CRUD completo.
- 👤 **Usuário:** criação de credenciais e controle de acesso por roles.
- 💸 **Transações:** depósito e saque.
- 🔍 **Validações de regras de negócio:**
  - Tratamento global de exceções com mensagens claras e padronizadas.
- 🧪 **Testes unitários e de integração:** com **JUnit 5** e **Mockito**.
- 🔑 **Autenticação:** todas as requisições exigem autenticação, exceto a criação de login e senha.

---

## 🛠️ Tecnologias utilizadas
- **Java 24**
- **Spring Boot** (Web, Data JPA, Security)
- **Hibernate Validator (Bean Validation)**
- **PostgreSQL**
- **Lombok**
- **MapStruct**
- **Kafka**
- **JWT**
- **Docker**
- **JUnit 5 + Mockito**

---

## 📡 Endpoints

### 👤 Cliente
- **Cadastrar cliente**
  - Ao realizar o cadastro, o sistema utiliza Kafka para disparar um evento que cria a conta.
  - `POST /api/cliente`
  ```json
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

Pesquisa cliente.
- Informar o código do cliente na URL.
- `GET /api/cliente/1`
  
Deleta cliente.
- Informar o codigo do cliente na url para deletar o usuario.
- Ao deletar, o sistema dispara um evento Kafka para remover a conta associada.
- `DELETE /api/cliente/1`

Atualiza cliente.
- Informar o código do cliente na URL.
- Apenas alguns campos podem ser alterados (exceto CPF).
- O CPF do codigo do cliente informado na URL precisa ser o mesmo do corpo enviado.
- `PUT /api/cliente/1`
```json
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
- Necessário informar a role. O token sozinho não garante acesso sem role adequada.
- `POST /api/usuario`
```json
{
    "usuario": "João",
    "senha": "6033",
    "role": "ADMIN"
}
```
Gerar token.
- Informar as credenciais usuario e senha.
- O sistema valida a role automaticamente.
- `GET /api/usuario/token`
```json
{
    "usuario": "João",
    "senha": "6033",
}
```

## 💸Endopoints Transações:

Depositar saldo na conta.
- Ao enviar o corpo, um evento Kafka é criado. O consumer processa o evento e dispara o service para adicionar saldo.
- Validações:
  - O valor não pode ser null nem negativo. 
- `POST /api//transacoes/depositar`
```json
{
    "codigoCliente": 1,
    "valor": 152.23,
}
```

Sacar valor da conta.
- Ao enviar o corpo, um evento Kafka é criado. O consumer processa o evento e dispara o service para subtrair saldo.
- Validações:
  - O valor não pode ser null nem negativo.
  - Não é permitido sacar mais que o saldo disponível.
- `POST /api//transacoes/sacar`
```json
{
    "codigoCliente": 1,
    "valor": 90.00,
}
```
