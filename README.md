# Gerenciamento Clientes

Gerenciamento Clientes é uma aplicação web para gerenciamento de clientes, utilizando Quarkus, JSF e integração com o serviço ViaCEP para consulta de endereços.

## Funcionalidades

- Cadastro de clientes com validação de campos.
- Edição de clientes.
- Exclusão de clientes.
- Listagem de clientes com filtro por nome.
- Consulta de endereço pelo CEP utilizando o serviço ViaCEP.

## Pré-requisitos

- Java 17

## Configuração

1. Clone o repositório:

```sh
git clone https://github.com/JustKac/gerenciamento-clientes.git
cd gerenciamento-clientes
```

2. Configure o Maven Wrapper:

```sh
./mvnw clean install
```

3. Configure o banco de dados H2 (configuração padrão para perfil local):

O arquivo `src/main/resources/application.properties` já está configurado para utilizar o banco de dados H2 em memória.

## Executando a Aplicação

1. Execute a aplicação utilizando o Maven Wrapper:

```sh
./mvnw quarkus:dev
```

2. Acesse a aplicação no navegador:

```
http://localhost:8080/index.xhtml
```

## Estrutura do Projeto

- `src/main/java/br/com/fsbr/gerenciamento_clientes`: Contém o código fonte da aplicação.
- `src/main/webapp`: Contém os arquivos JSF (XHTML), recursos web (CSS) e configuração (XML).

## Endpoints

- `/index.xhtml`: Página inicial com a listagem de clientes.
- `/form.xhtml`: Página de cadastro e edição de clientes.

## Tecnologias Utilizadas

- Quarkus
- JSF (JavaServer Faces)
- H2 Database
- Maven
- ViaCEP API