# 📦 Sistema de Vendas e Estoque — API REST

API desenvolvida com **Spring Boot** para gerenciar produtos, vendas e estoque, com validação automática de disponibilidade e rollback transacional.

---

## 🚀 Tecnologias

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **MariaDB / MySQL**
- **Hibernate**
- **Postman / Insomnia** (para testes)

---

## 📁 Estrutura do Projeto
src/
├─ main/
│ ├─ java/com/controleestoque/api_estoque/
│ │ ├─ controller/
│ │ ├─ dto/
│ │ ├─ model/
│ │ ├─ repository/
│ │ ├─ service/
│ │ └─ exception/
│ └─ resources/
│ └─ application.properties

---

## 🔧 Configuração

### 1. Banco de Dados
Crie o banco no MariaDB/MySQL:
```sql
CREATE DATABASE estoque_db;
```

### 2. Configurações do arquivo application.properties
Crie o banco no MariaDB/MySQL:
```sql
spring.datasource.url=jdbc:mariadb://localhost:3306/vendas_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

## 🖥️ Inicializando o projeto

### 1. Clone o repositório
 ```bash
   git clone https://github.com/anajuca/ControleEstoque-20240407.git
   ```

### 2. Acesse o diretório do projeto
 ```bash
   cd ControleEstoque-20240407
   ```

### 3. Execute a API
 ```bash
   mvn spring-boot:run
   ```

## 🧪 Testando a  API

Você pode usar o Postman / Insomnia / Thunder Client (VSCode) para testar os endpoints.

## 🩰 **Autoria**  
Feito por [**Ana Júlia**](https://github.com/anajuca).  
