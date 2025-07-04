
# Relatório Individual de Implementação - Projeto UFGCarona  
**Responsável:** Iker Marciel Lopategui

## 1. Minhas Obrigações e Responsabilidades na Implementação

Como responsável pela implementação do projeto UFGCarona, minhas obrigações foram:

- **Compreender e Traduzir Requisitos:** Entender as necessidades do aplicativo de caronas para a UFG e traduzi-las em soluções técnicas.
- **Modelagem de Software:** Desenvolver diagramas UML (casos de uso, classes, sequência) em PlantUML para visualizar a arquitetura e o comportamento do sistema.
- **Implementação de Código:** Escrever o código-fonte em Java, seguindo os padrões de projeto estabelecidos.
- **Aplicação de Conceitos Técnicos:** Utilizar e aplicar conceitos como herança, persistência de dados (ORM básico), JDBC e SQLite.
- **Depuração e Resolução de Problemas:** Identificar, diagnosticar e corrigir erros de compilação e de tempo de execução, assegurando o funcionamento correto da aplicação.
- **Documentação Técnica:** Criar e manter resumos, explicações e documentação de apoio (READMEs) para o projeto.
- **Adaptação a Ferramentas:** Garantir a compatibilidade e o funcionamento do código no ambiente BlueJ.

## 2. Implementações e Contribuições Realizadas

### Modelagem Inicial (PlantUML)

- Criação de **Diagramas de Caso de Uso** (visão geral, ator Aluno, e cenário de teste prático no BlueJ).
- Implementação de **Diagramas de Classes** para estruturar as entidades (Usuário, Motorista, Veículo, Carona, etc.).
- Desenvolvimento de **Diagramas de Sequência** para ilustrar o fluxo de operações (ex: Pedir Carona).
- Correção de erros de sintaxe nos diagramas PlantUML.

### Estrutura de Classes Java (POO)

- **Hierarquia de Usuários:** Superclasse `Usuario` e subclasses `Motorista`, `Passageiro` e `MotoristaUFG`.
- **Classes de Entidade:** `Veiculo`, `Carona`, `Avaliacao`, `StatusCarona` (enum).
- **Construtores Duplos:** Para criação e reconstrução de objetos com/sem ID.
- **Getters e Setters:** Implementados para interação com a camada de persistência.

### Persistência de Dados (SQLite e JDBC)

- **DatabaseManager.java:**
  - Gerenciamento da conexão com o banco de dados `ufgcarona.db`.
  - Criação de tabelas (`Usuarios`, `Veiculos`, `Motoristas`, `Caronas`, `Avaliacoes`), com chaves primárias, únicas e estrangeiras.
  - Ativação de chaves estrangeiras com `PRAGMA foreign_keys = ON`.

### Padrão DAO (Data Access Object)

- Implementação de `UsuarioDAO`, `VeiculoDAO`, `PassageiroDAO`, `MotoristaDAO`, `CaronaDAO`.
- Métodos implementados: `save`, `findById`, `findAvailableRides`, `requestCarona`.
- Uso de **transações** para garantir consistência no `MotoristaDAO.save()`.

#### Depuração da Persistência

- Correção de erros como:
  - Violações de `CONSTRAINT_UNIQUE` (placa, e-mail).
  - Falhas de `CONSTRAINT_NOTNULL` (e-mail).
  - Problemas de `JOIN` e integridade de dados.
- Utilização de `System.out.println` e análise externa com **DB Browser for SQLite**.

### Frontend Básico e Testes (BlueJ)

- Criação da classe `UFGCaronaApp.java` como driver de teste em console.
- Orientações para testes práticos no BlueJ (método `main`, Bancada de Objetos).
- Sugestões para um frontend em **JavaFX programático**, compatível com o BlueJ.

## 3. Conclusão

Como responsável pela implementação, acredito que o projeto UFGCarona estabeleceu uma base de dados sólida e funcional. A camada de persistência em SQLite e JDBC foi construída de forma robusta, abordando e superando complexidades como herança, chaves estrangeiras e transações. As classes de entidade foram bem modeladas, e os DAOs fornecem uma interface clara para a interação com o banco de dados.

Este trabalho pavimenta o caminho para o desenvolvimento da interface gráfica e a futura expansão das funcionalidades do aplicativo.
