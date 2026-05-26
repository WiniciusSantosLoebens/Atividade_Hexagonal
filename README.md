# Arquitetura_Hexagonal

# Serviço Auditor de Falhas (DLQ)

Este serviço independente foi desenvolvido como parte da atividade prática da disciplina de Arquitetura de Software. O objetivo principal é consumir mensagens de falha estacionadas em uma Dead Letter Queue (DLQ) do AWS SQS, realizar uma triagem de severidade baseada no volume de itens do pedido e persistir esses registros em um banco de dados para futura análise de administradores e desenvolvedores.

---

## Decisão Arquitetural: Arquitetura Hexagonal (Ports and Adapters)

Para o desenvolvimento deste microsserviço de apoio, eu escolhi a **Arquitetura Hexagonal**. 

### Por que essa escolha faz sentido para este serviço?
O propósito deste sistema é puramente focado em **integração e processamento de infraestrutura**: ele recebe dados de um provedor de mensageria externo (AWS SQS), aplica uma regra de triagem e envia para um mecanismo de persistência (Banco de Dados).

A Arquitetura Hexagonal brilha neste cenário porque estabelece um **isolamento severo do Core (Domínio)**. O coração do sistema (a lógica que define se um erro é crítico ou não) reside no centro da aplicação e é protegido do mundo exterior. Ele é escrito em Java Puro, não possuindo nenhum acoplamento com o Spring Framework, com as bibliotecas da AWS ou com o Hibernate/JPA.

Se amanhã a empresa decidir trocar o AWS SQS por Apache Kafka, ou migrar o banco de dados H2/PostgreSQL para um banco NoSQL (como MongoDB), **absolutamente nenhuma linha de código da regra de negócio precisará ser alterada ou retestada**, pois apenas os adaptadores externos serão substituídos.

---

## Organização do Projeto e Justificativa das Pastas

O projeto foi estruturado seguindo rigorosamente a divisão de camadas do padrão hexagonal, garantindo que a direção das dependências sempre aponte de fora para dentro.



### 1. Camada `domain` (O Núcleo)
É o centro do hexágono. Contém a inteligência do sistema e não conhece nenhuma tecnologia externa.
* **`domain/model/DeadLetterMessage.java`**: Objeto de valor que mapeia os dados do pedido que falhou. Ele possui o método `getTotalAmount()`, que encapsula a lógica de somar a quantidade de todos os itens do pedido.
* **`domain/model/AuditLog.java`**: A entidade de domínio que representa o log final de auditoria. Escolhi colocar a lógica de **Triagem de Severidade** (`HIGH`, `MEDIUM`, `LOW`) diretamente no construtor desta classe através do método privado `defineSeverity()`. Dessa forma, o próprio objeto garante sua consistência interna baseada nas regras de negócio do enunciado.

### 2. Camada `ports` (As Fronteiras)
As portas são interfaces que definem os contratos de comunicação com o domínio.
* **`ports/in/ProcessDeadLetterUseCase.java` (Porta de Entrada / Driving Port)**: Define o contrato de como o mundo externo pode acionar o nosso domínio. Qualquer mecanismo que queira iniciar uma auditoria precisa chamar o método `execute()`.
* **`ports/out/AuditRepositoryPort.java` (Porta de Saída / Driven Port)**: Define o que o domínio precisa que a infraestrutura faça (salvar o log), sem que o domínio saiba *onde* ou *como* isso será salvo.

### 3. Camada `domain/service` (O Maestro)
* **`AuditService.java`**: Implementa a porta de entrada (`ProcessDeadLetterUseCase`). Ele recebe os dados, coordena a criação do `AuditLog` (onde a severidade é calculada) e aciona a porta de saída (`AuditRepositoryPort`) para salvar o registro. Ele depende apenas de interfaces, respeitando o princípio de Inversão de Dependência.

### 4. Camada `adapters` (A Tecnologia)
Aqui ficam as implementações tecnológicas que obedecem ao domínio. É onde o Spring Boot, a AWS e o JPA ganham vida.
* **`adapters/in/sqs/DlqConsumer.java` (Adaptador de Entrada)**: Escuta ativamente a fila do SQS através da anotação `@SqsListener`. Ele captura o JSON bruto, converte para um DTO e repassa para o caso de uso do domínio.
  * *Garantia de Resiliência (Requisito 1):* Foi implementado um bloco `try/catch`. Caso ocorra qualquer erro no salvamento do banco de dados, o adaptador relança a exceção. Isso impede o Spring de enviar o *Acknowledge* (confirmação de recebimento) para a AWS, garantindo que a mensagem **só saia da DLQ após o salvamento seguro no banco**.
* **`adapters/out/database/` (Adaptador de Saída)**: Contém a entidade JPA (`AuditLogEntity`), o repositório do Spring Data (`SpringDataAuditRepository`) e a classe `DatabaseAdapter`. 
  * *Por que criar AuditLog e AuditLogEntity?* Na arquitetura hexagonal, o modelo de domínio (`AuditLog`) não pode ser poluído com anotações de banco de dados (`@Entity`, `@Table`, `@Id`). Por isso, o adaptador recebe o objeto do domínio, traduz para a entidade do JPA e faz a persistência física.

---

## Tecnologias Utilizadas

* **Java 17 / 21**
* **Spring Boot 3.x**
* **Spring Cloud AWS SQS** (Para integração com a fila de falhas)
* **Spring Data JPA** (Para persistência de dados)
* **H2 Database / PostgreSQL** (Mecanismo de banco de dados)
* **Jackson ObjectMapper** (Para manipulação e armazenamento do payload JSON bruto)

---
