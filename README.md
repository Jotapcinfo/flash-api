# ⚡ STAR Labs Order Management API

Sistema completo de gestão de pedidos incluindo autenticação com JWT, controle de estoque, relatórios financeiros e documentação Swagger.

## 📌 Tecnologias

- Java 21
- Spring Boot 3.5
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Maven
- Swagger (OpenAPI 3)
- JUnit

## 📦 Funcionalidades

✅ Cadastro, edição e remoção de usuários  
✅ Autenticação e registro com token JWT  
✅ Cadastro e listagem de produtos e categorias  
✅ Controle de estoque automático por status do pedido  
✅ Lançamento e gestão de pedidos com itens e pagamento  
✅ Relatórios por status, por usuário e balanço geral  
✅ Documentação Swagger pronta para testes  
✅ Retorno de mensagens de erro padronizadas

## 📊 Relatórios

- `/orders/report/total-by-status` → Total de pedidos por status
- `/orders/report/total-by-user?userId=1` → Total por status de um usuário e valor total
- `/orders/report/balance` → Balanço geral: estoque, pedidos aprovados, capital total
- `/users/{id}/orders` → Lista de pedidos do usuário + total geral

## 🔐 Autenticação

Autenticação baseada em JWT:  
- `POST /auth/register` → Criação de novo usuário  
- `POST /auth/login` → Geração de token com email/senha

- Inserção automática do admin ao iniciar a aplicação

Token deve ser enviado em chamadas autenticadas no header: Authorization: Bearer SEU_TOKEN


## 🧪 Dados de exemplo

O projeto inclui um script SQL com base populada, incluindo:

- 3 usuários
- 3 categorias
- 5 produtos
- 3 pedidos (com itens e pagamentos)
- Estoque inicial configurado

📄 Licença:
Este projeto é open-source e está sob a licença MIT.

Acesse a documentação:

Swagger UI: http://localhost:8080/swagger-ui/index.html

Feito com 💙 por Jefferson Moreno
