# **Projeto Banco JAVER - API de Gerenciamento de Clientes**

Este é um projeto para a criação de uma API RESTful que realiza o gerenciamento de clientes no Banco JAVER, incluindo a criação, atualização, exclusão e obtenção de informações dos clientes. A API também disponibiliza funcionalidades como consulta do score de crédito e validações para garantir que os dados inseridos no sistema sejam consistentes e válidos.

## **Tecnologias Utilizadas**

- **Java 17**: A API foi construída utilizando a versão 17 do Java, uma versão de LTS (Long-Term Support) que oferece desempenho e estabilidade aprimorados.
- **Spring Boot 3.4.x**: Framework utilizado para construir a aplicação, proporcionando uma base robusta e escalável para a API REST.
- **Spring Data JPA**: Usado para a integração com o banco de dados, permitindo o uso de repositórios e consultas simples e avançadas.
- **Swagger (OpenAPI 3.0)**: Para documentação da API e facilitar a interação com a interface gráfica de requisições.
- **Jakarta Validation**: Usado para validação de dados de entrada no corpo das requisições.
- **H2 Database** (ou outro banco de dados relacional): Utilizado para armazenamento das informações dos clientes.

## **Objetivo do Projeto**

O objetivo principal do Banco JAVER é fornecer uma interface para a gestão de clientes, permitindo que sejam realizadas operações como:

- Criação de clientes
- Atualização de dados de clientes
- Exclusão de clientes
- Consulta de dados de clientes por ID
- Consulta de score de crédito de clientes

Além disso, a API implementa validações para garantir que os dados fornecidos estejam corretos, como verificar a unicidade do telefone e se o nome do cliente contém ao menos 4 caracteres.

## **EndPoints da API**

A API disponibiliza os seguintes **endpoints** para interação com os dados dos clientes:

### **1. Criar um novo cliente**

- **Método**: `POST`
- **URL**: `/api/v1/clientes`
- **Descrição**: Cria um novo cliente com os dados fornecidos no corpo da requisição.
- **Body**:
    ```json
    {
        "nome": "João Silva",
        "telefone": "123456789",
        "correntista": true,
        "saldoCc": 5000.00
    }
    ```
- **Respostas**:
    - **201**: Cliente criado com sucesso.
    - **400**: Requisição inválida (ex: campos faltando ou inválidos).
    - **409**: Telefone já cadastrado no sistema.

### **2. Obter um cliente por ID**

- **Método**: `GET`
- **URL**: `/api/v1/clientes/{id}`
- **Descrição**: Obtém os detalhes de um cliente baseado no ID fornecido.
- **Respostas**:
    - **200**: Cliente encontrado.
    - **404**: Cliente não encontrado.

### **3. Obter o score de crédito do cliente**

- **Método**: `GET`
- **URL**: `/api/v1/clientes/score/{id}`
- **Descrição**: Obtém o score de crédito do cliente baseado no ID fornecido.
- **Respostas**:
    - **200**: Score de crédito encontrado.
    - **404**: Cliente não encontrado.

### **4. Atualizar dados de um cliente**

- **Método**: `PUT`
- **URL**: `/api/v1/clientes/{id}`
- **Descrição**: Atualiza os dados de um cliente baseado no ID fornecido.
- **Body**:
    ```json
    {
        "nome": "João Silva Atualizado",
        "telefone": "987654321",
        "correntista": true,
        "saldoCc": 10000.00
    }
    ```
- **Respostas**:
    - **200**: Cliente atualizado com sucesso.
    - **404**: Cliente não encontrado.
    - **400**: Erro de validação (ex: nome com menos de 4 caracteres ou saldo inválido para não correntistas).
    - **409**: Telefone já cadastrado no sistema.

### **5. Excluir um cliente**

- **Método**: `DELETE`
- **URL**: `/api/v1/clientes/{id}`
- **Descrição**: Exclui o cliente baseado no ID fornecido.
- **Respostas**:
    - **204**: Cliente excluído com sucesso.
    - **404**: Cliente não encontrado.

## **Requisitos**

Antes de rodar a aplicação, certifique-se de ter os seguintes requisitos instalados:

- **JDK 17** ou superior.
- **Maven** ou **Gradle** para gerenciamento de dependências e execução do projeto.
- **Banco de dados**: O projeto utiliza H2 Database por padrão, mas pode ser configurado para usar outros bancos como MySQL ou PostgreSQL.

## **Como Rodar o Projeto**

1. **Clone o repositório**:
    ```bash
    git clone https://github.com/Gino0053/Projeto-Final---Java.git
    ```

2. **Entre na pasta do projeto**:
    ```bash
    cd projeto-java
    ```

3. **Compile o projeto**:
    Com Maven:
    ```bash
    mvn clean install
    ```
    Ou com Gradle:
    ```bash
    gradle build
    ```

4. **Execute o projeto**:
    Com Maven:
    ```bash
    mvn spring-boot:run
    ```
    Ou com Gradle:
    ```bash
    gradle bootRun
    ```

5. O servidor estará rodando na **porta 8080** por padrão.

## **Documentação da API (Swagger)**

A documentação da API pode ser acessada via Swagger. Após rodar a aplicação, acesse o seguinte endereço no navegador:

```
http://localhost:8085/swagger-ui/index.html
```

Lá você poderá visualizar todos os endpoints disponíveis, suas descrições, parâmetros de entrada, exemplos de respostas, e até mesmo testar as requisições diretamente pela interface gráfica.

## **Validações Implementadas**

A API realiza diversas validações antes de realizar operações como a criação e atualização de clientes:

1. **Validação de telefone único**: Verifica se o telefone já está cadastrado no sistema antes de permitir a criação ou atualização de um cliente.
2. **Validação de nome**: O nome do cliente deve ter pelo menos 4 caracteres.
3. **Validação de saldo**: Um cliente que não é correntista não pode ter um saldo de conta corrente maior que zero.

## **Estrutura de Diretórios**

A estrutura do projeto é organizada da seguinte forma:

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── gruiz
│   │   │           └── projeto_java
│   │   │               ├── exception
│   │   │               ├── proxy
│   │   │               ├── repository
│   │   │               ├── response
│   │   │               ├── service
│   │   │               ├── util
│   │   │               └── web
│   │   │                   └── controller
│   │   └── resources
│   │       └── application.properties
├── target
└── pom.xml (ou build.gradle)
```

## **Conclusão**

Este projeto proporciona uma API robusta para o gerenciamento de clientes em um banco digital. Utilizando boas práticas de programação, o projeto visa ser uma base sólida para um sistema real de gestão de clientes. A documentação está bem definida, e a validação de dados ajuda a garantir a integridade das informações no sistema.

Se você tiver dúvidas ou sugestões, fique à vontade para contribuir ou entrar em contato!
