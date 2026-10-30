# Biblioteca — CloudTech Solutions

Exercício 2 — Aula 11 de Desenvolvimento Backend (Prof. Me Daniel Vieira).

Aplicação web em Spring Boot para o gerenciamento do acervo de uma biblioteca:
consultar livros disponíveis, cadastrar novos livros e alterar informações dos
livros já existentes.

## Tecnologias

- Java 21
- Spring Boot 4.1.1 (`spring-boot-starter-webmvc`)
- Maven
- JUnit 5 + MockMvc (`spring-boot-starter-webmvc-test`)
- GitHub Actions (pipeline CI)

## Como executar

```bash
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8082`.

Para gerar o JAR e rodar os testes:

```bash
mvn clean package
java -jar target/biblioteca-0.0.1-SNAPSHOT.jar
```

## Endpoints

| Método | Endpoint               | Descrição                           | Item |
|--------|------------------------|-------------------------------------|------|
| GET    | `/`                    | Mensagem de boas-vindas             | a    |
| GET    | `/livros`              | Lista os livros do acervo           | b    |
| POST   | `/livros/cadastrar`    | Cadastra um novo livro              | c    |
| PUT    | `/livros/alterar/{id}` | Altera as informações de um livro   | d    |
| GET    | `/livros/{id}`         | Consulta um livro pelo ID           | e    |
| GET    | `/documentacao`        | Documentação dos endpoints          | f    |
| GET    | `/versao`              | Versão da aplicação                 | —    |

### a) Página inicial

`GET /`

```json
{
  "mensagem": "Bem-vindo ao sistema de gerenciamento da Biblioteca CloudTech Solutions"
}
```

### b) Consultar livros disponíveis

`GET /livros`

```json
[
  {
    "id": 1,
    "titulo": "Introdução à Programação",
    "autor": "João Silva",
    "editora": "Tech Books",
    "ano": 2022,
    "categoria": "Tecnologia",
    "status": "Disponível"
  }
]
```

### c) Cadastrar novo livro

`POST /livros/cadastrar`

```json
{
  "titulo": "Banco de Dados para Iniciantes",
  "autor": "Maria Souza",
  "editora": "EduTech",
  "ano": 2023,
  "categoria": "Informática",
  "status": "Disponível"
}
```

Retorna `201 Created` com o livro cadastrado (o `id` é gerado automaticamente),
ou `400 Bad Request` quando o título não é informado.

### d) Alterar informações de um livro

`PUT /livros/alterar/{id}`

```json
{
  "titulo": "Banco de Dados Essencial",
  "autor": "Maria Souza",
  "editora": "EduTech",
  "ano": 2024,
  "categoria": "Banco de Dados",
  "status": "Emprestado"
}
```

Retorna `200 OK` com o livro atualizado, ou `404 Not Found` se o ID não existir.

### e) Consultar livro por ID

`GET /livros/{id}` → `200 OK` com o livro, ou `404 Not Found`.

```json
{
  "id": 2,
  "titulo": "Redes de Computadores",
  "autor": "Carlos Mendes",
  "editora": "Net Books",
  "ano": 2021,
  "categoria": "Redes",
  "status": "Disponível"
}
```

### f) Documentação dos endpoints

`GET /documentacao`

```json
{
  "endpoints": [
    "/",
    "/livros",
    "/livros/cadastrar",
    "/livros/alterar/{id}",
    "/livros/{id}",
    "/documentacao"
  ]
}
```

## Testes

Testes automatizados (10 testes, MockMvc):

```bash
mvn test
```

Testes manuais com **Thunder Client** (VS Code): importe a coleção
[`tests/thunder-collection_biblioteca.json`](tests/thunder-collection_biblioteca.json)
em *Thunder Client → Collections → Import*. A coleção cobre todos os endpoints,
incluindo os casos de erro `404`.

## Estrutura do projeto

```
biblioteca/
├── src/
│   ├── main/java/com/senai/biblioteca/
│   │   ├── BibliotecaApplication.java
│   │   ├── controller/   (HomeController, LivroController)
│   │   └── models/       (Livro)
│   └── test/java/com/senai/biblioteca/controller/
├── tests/                (coleção do Thunder Client)
├── README.md
├── .gitignore
└── .github/
    └── workflows/
        └── pipeline.yml
```

## Pipeline CI/CD

O workflow [`.github/workflows/pipeline.yml`](.github/workflows/pipeline.yml)
roda a cada push e pull request em `main`/`master`: configura o Java 21, executa
`mvn clean package` (compilação + testes) e publica o JAR como artefato da execução.

## Observações

Os dados ficam em memória (lista dentro do controller), como previsto para o
escopo da aula — ao reiniciar a aplicação, o acervo volta aos registros iniciais.
