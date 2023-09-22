## Cadastro de um novo autor

É necessário cadastrar um novo autor no sistema. Todo autor tem um nome, email e uma descrição. Também queremos saber o instante exato que ele foi registrado.

### Restrições
O instante não pode ser nulo <br>
O email é obrigatório <br>
O email tem que ter formato válido <br>
O email deve ser único <br>
O nome é obrigatório <br>
A descrição é obrigatória e não pode passar de 400 caracteres

### Resultado esperado
Um novo autor criado e status 200 retornado 

## Curls de exemplo

### Cadastro de novo autor
curl -X POST http://localhost:8080/autor -H 'Content-Type: application/json' -d '{"nome":"Autor", "email":"autor@gmail.com", "descricao":"descricao"}'
