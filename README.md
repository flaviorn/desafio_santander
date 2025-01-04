# desafio_santander
Api com desafio para vaga ao Santander

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

## Comando para executar wiremock
```bash
java -jar wiremock-standalone-3.10.0.jar --port 9090 --root-dir ./desafio_santander/wiremock
```