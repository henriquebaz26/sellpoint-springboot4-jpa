<div align="center">

<img src="https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring_Boot-4.0.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/JPA%2FHibernate-ORM-59666C?style=for-the-badge&logo=hibernate&logoColor=white"/>
<img src="https://img.shields.io/badge/PostgreSQL-DB-4169E1?style=for-the-badge&logo=postgresql&logoColor=white"/>
<img src="https://img.shields.io/badge/H2-In--Memory-0072C6?style=for-the-badge&logo=h2&logoColor=white"/>

<br/><br/>

# 🛒 SellPoint

**API RESTful de e-commerce desenvolvida com Spring Boot 4 e JPA/Hibernate**

*Gerenciamento completo de usuários, produtos, pedidos, categorias e pagamentos*

</div>

---

## 📋 Sobre o Projeto

O **SellPoint** é uma API REST para e-commerce que modela um domínio completo de vendas. O projeto foi desenvolvido com foco em boas práticas de arquitetura Spring, mapeamento objeto-relacional avançado (chave composta, relacionamentos bidirecionais, herança de PK) e tratamento centralizado de erros.

### Funcionalidades

- ✅ CRUD completo de **Usuários**
- ✅ Consulta de **Pedidos** com itens, total calculado e status
- ✅ Consulta de **Produtos** com categorias associadas
- ✅ Consulta de **Categorias**
- ✅ Relacionamento **Pedido → Pagamento** (1:1 com @MapsId)
- ✅ **Itens de pedido** com chave composta (Pedido + Produto)
- ✅ Tratamento global de erros com respostas padronizadas
- ✅ Perfis separados para desenvolvimento (PostgreSQL) e teste (H2 in-memory)

---

## 🏗️ Arquitetura

O projeto segue o padrão de arquitetura em camadas do ecossistema Spring:

```
src/main/java/com/sellpoint/SellPoint/
│
├── config/              # Configuração de seed de dados (perfil test)
├── entities/            # Entidades JPA (domínio)
│   ├── OrderEnums/      # Enum OrderStatus com código numérico
│   └── pk/              # Chave composta OrderItemPK (@Embeddable)
├── repositories/        # Interfaces Spring Data JPA
├── services/            # Regras de negócio + exceções de serviço
│   └── exceptions/
└── resources/           # Controllers REST
    └── exceptions/      # Handler global (@ControllerAdvice)
```

**Fluxo:** `Resource (Controller)` → `Service` → `Repository` → `Banco de Dados`

---

## 🗂️ Modelo de Dados

```
User ──────────────── Order ──────────── Payment
  (1)          (N)    (1)         (1)    (PK compartilhada)
                       │
                       │ (N)
                   OrderItem ──── (N) ──── Product
                  (PK composta)              │
                                            (N)
                                        Category
                                   (tb_product_category)
```

| Entidade | Tabela | Descrição |
|---|---|---|
| `User` | `tb_user` | Cliente do sistema |
| `Order` | `tb_order` | Pedido com status e data |
| `OrderItem` | `tb_order_item` | Item com chave composta (Order + Product) |
| `Product` | `tb_product` | Produto com preço e descrição |
| `Category` | `tb_category` | Categoria (N:M com Product) |
| `Payment` | `tb_payment` | Pagamento 1:1 com Order |

### Status do Pedido (OrderStatus)

| Código | Status |
|---|---|
| 1 | `WAITING_PAYMENT` |
| 2 | `PAID` |
| 3 | `SHIPPED` |
| 4 | `DELIVERED` |
| 5 | `CANCELED` |

---

## 🚀 Endpoints da API

### Usuários
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/users` | Listar todos os usuários |
| `GET` | `/users/{id}` | Buscar usuário por ID |
| `POST` | `/users` | Cadastrar novo usuário |
| `PUT` | `/users/{id}` | Atualizar nome, e-mail e telefone |
| `DELETE` | `/users/{id}` | Remover usuário |

### Pedidos
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/orders` | Listar todos os pedidos |
| `GET` | `/orders/{id}` | Buscar pedido por ID (com itens e total) |

### Produtos
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/products` | Listar todos os produtos |
| `GET` | `/products/{id}` | Buscar produto por ID |

### Categorias
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/categories` | Listar todas as categorias |
| `GET` | `/categories/{id}` | Buscar categoria por ID |

---

## ⚙️ Como Executar

### Pré-requisitos

- Java 25+
- Maven 3.9+
- PostgreSQL (para perfil `dev`)

### Clonando o repositório

```bash
git clone https://github.com/henriquebaz26/sellpoint-springboot4-jpa.git
cd sellpoint-springboot4-jpa
```

### Perfil de Teste (H2 in-memory — sem configuração extra)

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```

- Console H2 disponível em: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb` · Usuário: `sa` · Senha: *(vazia)*
- Dados de exemplo são carregados automaticamente ao iniciar

### Perfil de Desenvolvimento (PostgreSQL)

1. Crie o banco de dados:

```sql
CREATE DATABASE db_sellpoint;
```

2. Execute o script de criação das tabelas:

```bash
psql -U postgres -d db_sellpoint -f script.sql
```

3. Ajuste as credenciais em `src/main/resources/application-dev.properties` se necessário.

4. Inicie a aplicação:

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

---

## 🧪 Exemplos de Requisição

### Criar usuário
```http
POST /users
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@email.com",
  "phone": "11999999999",
  "password": "minhasenha"
}
```

### Atualizar usuário
```http
PUT /users/1
Content-Type: application/json

{
  "name": "João Souza",
  "email": "joao.souza@email.com",
  "phone": "11988888888"
}
```

### Resposta de erro padrão
```json
{
  "timestamp": "2026-04-05T12:00:00Z",
  "status": 404,
  "error": "Resource not found",
  "message": "Resource not found. Id 99",
  "path": "/users/99"
}
```

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 25 | Linguagem principal |
| Spring Boot | 4.0.5 | Framework base |
| Spring Data JPA | — | Persistência e repositórios |
| Hibernate | — | ORM / mapeamento relacional |
| Spring Web MVC | — | Camada REST |
| PostgreSQL | — | Banco de dados de produção |
| H2 Database | — | Banco in-memory para testes |
| Jackson | — | Serialização JSON |
| Maven | — | Gerenciador de build |

---

## 📁 Estrutura do Projeto

```
sellpoint-springboot4-jpa/
├── src/
│   ├── main/
│   │   ├── java/com/sellpoint/SellPoint/
│   │   │   ├── SellPointApplication.java
│   │   │   ├── config/
│   │   │   │   └── TestConfig.java
│   │   │   ├── entities/
│   │   │   │   ├── Category.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── OrderItem.java
│   │   │   │   ├── Payment.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── User.java
│   │   │   │   ├── OrderEnums/
│   │   │   │   │   └── OrderStatus.java
│   │   │   │   └── pk/
│   │   │   │       └── OrderItemPK.java
│   │   │   ├── repositories/
│   │   │   ├── services/
│   │   │   └── resources/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-test.properties
│   └── test/
├── script.sql
├── pom.xml
└── mvnw
```

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<div align="center">

Desenvolvido por **Henrique Baz** · [GitHub](https://github.com/henriquebaz26)

</div>
