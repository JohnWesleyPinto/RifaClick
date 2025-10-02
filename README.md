
# RifaClick - Sistema de Gestão de Rifas

## Descrição

O **RifaClick** é um sistema de gestão de rifas que permite a criação de rifas, a compra de bilhetes, e a realização de sorteios de forma simples e intuitiva. Ele fornece uma API RESTful documentada com Swagger para interações programáticas, além de autenticação com JWT para garantir a segurança dos dados.

---

## Funcionalidades

1. **Cadastro de Usuários**: Os usuários podem se registrar e fazer login para acessar as funcionalidades do sistema.
2. **Gestão de Rifas**: Administradores podem criar e gerenciar rifas, incluindo a definição de prêmios, quantidade de bilhetes e preços.
3. **Compra de Bilhetes**: Os usuários podem comprar bilhetes para rifas ativas, com o status de pagamento.
4. **Sorteio de Rifas**: Administradores podem realizar o sorteio de uma rifa para selecionar um vencedor.
5. **Documentação da API**: A API está documentada com Swagger para facilitar o uso.

---

## Pré-requisitos

Antes de rodar a aplicação, você precisa ter instalado:

- **Java 21+** (ou uma versão mais recente compatível com o Spring Boot 3.x)
- **Maven** para gerenciar as dependências do projeto
- **PostgreSQL** ou outro banco de dados relacional configurado 
- **Spring Boot 3.x** com dependências adequadas.

---

## Instalação

### 1. Clone o repositório

Clone o repositório para a sua máquina local:

```bash
git clone https://github.com/JohnWesleyPinto/RifaClick.git
```

### 2. Instale as dependências do projeto

Dentro do diretório do projeto, execute o seguinte comando para instalar as dependências:

```bash
mvn clean install
```

### 3. Configuração do banco de dados

O sistema utiliza o **PostgreSQL** por padrão. Para configurá-lo, crie o banco de dados e configure a URL de conexão no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/rifa_db
spring.datasource.username=johnpinto
spring.datasource.password=teste123
spring.jpa.hibernate.ddl-auto=update
```

Substitua a URL do banco de dados, o nome do banco, e as credenciais conforme necessário.

### 4. Configuração do Swagger

A documentação da API está disponível através do Swagger na URL `/swagger-ui/` após a aplicação ser executada.

---

## Como Rodar a Aplicação

### 1. Execute o comando Maven para iniciar a aplicação

Dentro do diretório do projeto, execute:

```bash
mvn spring-boot:run
```

Isso vai iniciar a aplicação na porta `8080`.

---

## Funcionalidades da API

### 1. **Login e Registro**

- **Endpoint de Login**:  
  `POST /auth/login`  
  Corpo:
  ```json
  {
    "email": "admin@admin.com",
    "senha": "admin"
  }
  ```
  Retorno:
  ```json
  {
    "token": "jwt_token",
    "usuario": {
      "id": 1,
      "nome": "admin",
      "email": "admin@admin.com",
      "role": "ADMIN"
    }
  }
  ```

- **Endpoint de Registro**:  
  `POST /auth/register`  
  Corpo:
  ```json
  {
    "nome": "John Doe",
    "email": "john.doe@example.com",
    "senha": "password123"
  }
  ```
  Retorno:
  ```json
  {
    "mensagem": "Usuário registrado com sucesso",
    "usuarioId": 1
  }
  ```

### 2. **Gestão de Rifas**

- **Criar uma Rifa** (Admin):
  `POST /rifas`  
  Corpo:
  ```json
  {
    "nome": "Rifa de Carro",
    "premio": "Carro Novo",
    "quantidadeBilhetes": 100,
    "precoBilhete": 50.00,
    "dataInicio": "2025-10-01T00:00:00",
    "dataFim": "2025-12-01T00:00:00"
  }
  ```

- **Listar Rifas**:
  `GET /rifas`

- **Visualizar uma Rifa**:
  `GET /rifas/{id}`

### 3. **Compra de Bilhetes**

- **Comprar Bilhete**:
  `POST /bilhetes`  
  Corpo:
  ```json
  {
    "rifaId": 1,
    "usuarioId": 1,
    "statusPagamento": true
  }
  ```

- **Visualizar Bilhete**:
  `GET /bilhetes/{id}`

### 4. **Sorteio da Rifa** (Admin)

- **Realizar Sorteio**:
  `POST /rifas/{id}/sorteio`

---

## Swagger UI

A documentação da API está disponível no Swagger UI após iniciar a aplicação. Acesse a URL:

```
http://localhost:8080/swagger-ui/
```

---

## Exceções e Tratamento de Erros

O sistema possui tratamento de exceções para garantir uma comunicação clara em caso de erros. Alguns exemplos de erro incluem:

- **Credenciais Inválidas**: Quando o login falha.
- **Rifa não Encontrada**: Quando uma rifa solicitada não existe.
- **Pagamento Não Confirmado**: Quando um bilhete é comprado sem confirmar o pagamento.

A resposta de erro segue o formato:

```json
{
  "timestamp": "2025-10-01T21:57:30.623",
  "status": 400,
  "mensagem": "Erro: Detalhes do erro"
}
```

---

## Contribuindo

Sinta-se à vontade para contribuir com o projeto! Para isso, faça o seguinte:

1. Faça um fork deste repositório.
2. Crie uma nova branch: `git checkout -b minha-branch`.
3. Faça suas modificações.
4. Envie as alterações: `git commit -am 'Adicionando nova feature'`.
5. Faça o push para a branch: `git push origin minha-branch`.
6. Abra um Pull Request.

---
