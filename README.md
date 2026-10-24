# Calculadora Distribuída - Paradigmas de Invocação Remota 

Este repositório contém parte da implementação da **Calculadora Distribuída** desenvolvida para a **Atividade 03 (AT03)** da disciplina de **Sistemas Distribuídos**.

O projeto está organizado em três pastas principais, cada uma demonstrando um modelo diferente de comunicação cliente-servidor e invocação remota:

O cenário utilizado para os testes de comunicação foi configurado com o cliente executando em uma máquina virtual Linux. Assim, todos os clientes tentaram se conectar ao servidor chamado srvsockets. Esse nome pode ser alterado no código-fonte de cada projeto.

Lembrando que o sistema permite realizar as quatro operações matemáticas básicas (adição, subtração, multiplicação e divisão), bem como a execução de expressões matemáticas, como, por exemplo: (10 + 3) * 8.


---

## 📁 Estrutura do Repositório

```text
.
├── Socket/      # Comunicação de baixo nível via Java Sockets (TCP)
├── JsonRPC/     # Invocação Remota via JSON-RPC (Servidor Ktor/Kotlin + Cliente Java)
└── RMI/         # Invocação Remota de Objetos via Java RMI (JRMP)
```

---

## 🛠️ Pré-requisitos

- **Java Development Kit (JDK 17+)**
- **Gradle** (para o projeto Ktor/Kotlin no módulo `JsonRPC`)
- IDE de sua preferência (**NetBeans**, **VS Code** ou **IntelliJ IDEA**)
- Editar o arquivos hosts do cliente e cadastrar srvsockets com o ip do servidor
Obs. Comando para executar o arquivo .jar
java -cp JavaProjectServerSocket_Calculadora.jar CalculadoraServerSocket
java -cp JavaProjectServerSocket_Calculadora.jar Calculadora
---

## 🚀 Como Executar cada Projeto

### 1. Pasta `Socket/` (Java Sockets TCP)

Demonstra a comunicação direta e síncrona através de *streams* de bytes via TCP.

1. **Acesse a pasta:**
   ```bash
   cd Socket
   ```
2. **Inicie o Servidor:**
   Execute a classe principal do servidor:
   ```bash
   java ServerSocket_Calculadora
   ```
   *O servidor ficará aguardando conexões na porta `9090`.*

3. **Inicie o Cliente:**
   Em um novo terminal, execute o cliente:
   ```bash
   java ClientSocket_Calculadora
   ```
4. **Utilização:** Insira a operação e os números solicitados no menu interativo do terminal.

---

### 2. Pasta `JsonRPC/` (JSON-RPC via Ktor/Kotlin)

Demonstra o modelo de Invocação de Procedimento Remoto (RPC) utilizando requisições HTTP POST codificadas em formato JSON.

1. **Descompactar o arquivo JsonRPC.tar.gz**
   ```bash
   tar -xzvf JsonRPC.tar.gz

2. **Acesse a pasta:**
   ```bash
   cd JsonRPC
   ```
3. **Inicie o Servidor (Ktor/Kotlin):**
   Execute o servidor utilizando o Gradle Wrapper:
   ```bash
   ./gradlew run
   ```
   *(No Windows, utilize: `gradlew.bat run`)*  
   *O servidor Ktor será iniciado na porta `8080`.*

4. **Inicie o Cliente (Java):**
   Em um novo terminal, compile e execute o cliente Java:
   ```bash
   javac ClienteJsonRpc.java
   java ClienteJsonRpc
   ```
5. **Utilização:** Escolha a operação matemática para enviar as chamadas JSON-RPC ao servidor.

---

### 3. Pasta `RMI/` (Java Remote Method Invocation)

Demonstra a invocação remota de métodos de objetos Java distribuídos via protocolo JRMP.

1. **Acesse a pasta:**
   ```bash
   cd RMI
   ```
2. **Inicie o Servidor RMI (`rmiregistry`):**
   ```bash
   java Calculadora
   ```
3. **Inicie o Cliente RMI:**
   Em outro terminal, execute o cliente para obter a referência remota e invocar os métodos:
   ```bash
   java CalculadoraCliente
   ```
   
## 📊 Resumo das Portas Utilizadas

| Módulo | Protocolo Base | Porta Padrão |
| :--- | :--- | :--- |
| **Socket** | TCP | `9090` |
| **JsonRPC** | HTTP / JSON | `8080` |
| **RMI** | JRMP / TCP | `1099` |
