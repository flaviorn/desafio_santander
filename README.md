# Desafio Santander
Api com desafio para o Santander

## Endpoints da API

### Realiza login e retorna token

```http
  POST /login
```
#### Requisição

- Parâmetros

Nenhum parâmetro é necessário

- Headers

Nenhum header é necessário

- Body (JSON) 

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `clientId` | `string` | **Obrigatório**. Client Id para realizar o login |
| `clientSecret` | `string` | **Obrigatório**. Client Secret para realizar o login |

#### Resposta

- Body (JSON) 

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `token` | `string` | Token retornado após login realizado com sucesso |

### Retorna os dados de um CEP

```http
  GET /cep/${id}
```
#### Requisição

- Parâmetros

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório**. Envio do valor númerico do cep, sem pontuação |

- Headers

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `Authorization`      | `string` | **Obrigatório**. Bearer Token para a requisição |


- Body (JSON) 

Nenhum body é necessário

#### Resposta

- Body (JSON) 

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `cep` | `string` | Informação sobre o cep da requisição |
| `logradouro` | `string` | Informação sobre o logradouro da requisição |
| `complemento` | `string` | Informação sobre o complemento da requisição |
| `unidade` | `string` | Informação sobre o unidade da requisição |
| `bairro` | `string` | Informação sobre o bairro da requisição |
| `localidade` | `string` | Informação sobre o localidade da requisição |
| `uf` | `string` | Informação sobre o uf da requisição |
| `estado` | `string` | Informação sobre o estado da requisição |
| `regiao` | `string` | Informação sobre o regiao da requisição |
| `ibge` | `string` | Informação sobre o ibge da requisição |
| `gia` | `string` | Informação sobre o gia da requisição |
| `ddd` | `string` | Informação sobre o ddd da requisição |
| `siafi` | `string` | Informação sobre o siafi da requisição |


## Detalhes do desafio
- Realizar operação de busca de cep (api externa mockada);
- Logs das consultas precisam ser gravadas (Com horario e dados em banco);
- Conceitos do SOLID;
- Java 11 ou +;
- Utilizar Docker;
- AWS


## Exemplo de requisicao na Api ViaCep:

```http
  GET https://viacep.com.br/ws/04543011/json/
```

Resposta:
```json
{
    "cep": "01001-000",
    "logradouro": "Praça da Sé",
    "complemento": "lado ímpar",
    "bairro": "Sé",
    "localidade": "São Paulo",
    "uf": "SP",
    "ibge": "3550308",
    "gia": "1004",
    "ddd": "11",
    "siafi": "7107"
}
