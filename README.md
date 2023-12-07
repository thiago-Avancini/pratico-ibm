# Configuração do Projeto #

## Tecnologias aplicadas ##
* Docker para containerização da aplicação
* Docker Compose para subir os containers de banco de dados e aplicação
* Liquibase para versionamento do banco de dados
* MYSQL para persistência de dados
* Gradle para gerenciamento de dependência e build da aplicação
* Postman para requisições HTTP

### Como executar aplicação ###

* Baixar o código do [GitHub](https://github.com/)
* Na pasta raiz executar o comando "./gradlew clean build" para gerar o artefato da aplicação
* Executar o docker compose para subir o banco de dados MYSQL: **docker-compose -f mysql.yaml up --build**
* Após o container do MYSQL estiver levantado com sucesso, executar o docker compose para subir a aplicação: **docker-compose -f app.yaml up --build**


### Como testar a aplicação pelo POSTMAN ###
Baixar a collection do Postman: <https://drive.google.com/file/d/1PvmK4LjLuWqnLO3VxEjx6S5K3i2O5n5S/view?usp=sharing>

A aplicação requer um usuário Admin, que é criado automaticamente ao subir a aplicação pela primeira vez.

Em todas as requisições o token é atribuído à variável "x-Auth-Token" de forma automática. 
Os passoas a seguir são o workflow que deve ser seguido para testar todos os endpoints com sucesso:

* Autenticar como usuario admin (**Login:** admin **Senha:** admin123) -> Request Login
* Criar um novo gerente -> Request Novo Usuário (Body: Nome(String), Sobrenome(String), CPF(String), Tipo Usuário(String = GERENTE))
* Autenticar como usuario gerente (**Login e senha fornecidos no passo anterior**)
* Criar um novo cliente -> Request Nova Conta (Body: Nome(String), Sobrenome(String), CPF(String))
* A partir do momento que a conta está criada já podem ser utilizados as demais requisições -> Request Movimentação, Request Saldo e Request Extrato

Movimentação abarca depósitos, saques e transferências. Todas elas possuem o mesmo body:

```json
    {
        "contaMovimento": "numero-conta-destino",
        "contaProprietaria": "numero-conta-que-está-realizando-movimentação",
        "tipoMovimentacao": "TIPO MOVIMENTACAO",
        "valor": 0.0,
        "descricao": "descrição"
    }
```

* Tipo movimentação deve ser: **TRANSFERENCIA_RECEBIDA**, **TRANSFERENCIA_ENVIADA**, **SAQUE** ou **DEPOSITO**;
* Os campos _tipoMovimento_, _valor_ e _descricao_ são obrigatórios;
* Nos dois tipos de transferência enviada deverão ser informados, além dos campos obrigatórios, os campos _contaMovimento_ e _contaProprietaria_;
* Os depósitos devem ter **SOMENTE** o campo _contaProprietaria_ preenchido;
* No saque só deve ser informada a _contaProprietaria_;

Quanto ao extrato e saldo, o corpo das requisiçõies seguem o mesmo padrão e deve ser informado o número da conta:

```json
    {
        "numeroConta": "numero-conta"
    }
```



### Regras aplicadas ###
Algumas regras de negócio aplicadas não necessáriamente se aplicam ao mundo real. O intuito dessa aplicação se limita a mostrar meus conhecimentos das tecnolodias.

* Usuários admin podem **APENAS** criar usuários do tipo gerente
* Usuários gerentes pode **APENAS** criar usuários do tipo cliente
* Um CPF pode estar atrelado a um usuario e conta somente(no mundo real, um cliente poderia ter um usuário único porém com múltiplas contas por CPF) 
* Não foram consideradas, contas pessoa juridica (CNPJ), apenas para simplicação da solução
* **SOMENTE** clientes poderão realizar movimentações, requisitar saldos e extratos
