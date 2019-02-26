Star Wars - API
====


Pré-requisitos
----

 * Banco de dados MySQL
 * Java 8
 

Setup
---

Criar um schema no banco de dados MySQl e ajustar a configurações no application.yml

Criar a tabela Planet, o script para criação está disponível no arquivo schema.sql


Executar os testes da API
---

    $> mvn test 


Executar a API
---

    $> mvn spring-boot:run


Exemplos de requisções
---

Recuperando planetas da API swapi.co

	curl -X GET http://localhost:8082/starwars/api/v1/planets/swapi -H 'cache-control: no-cache'

Inserindo um planeta


	curl -X POST \
	  http://localhost:8082/starwars/api/v1/planets \
	  -H 'Content-Type: application/json' \
	  -H 'cache-control: no-cache' \
	  -d '{
	   "id":2,
	   "name":"Earth",
	   "climate":"temperate",
	   "terrain":"forest"
	}'

Recuperando um planeta por ID

	curl -X GET \
	  http://localhost:8082/starwars/api/v1/planets/ids/2 \
	  -H 'cache-control: no-cache'


Recuperando um planeta por Nome


	curl -X GET \
	  http://localhost:8082/starwars/api/v1/planets/names/Mars \
	  -H 'cache-control: no-cache'

Recuperando planetas


	curl -X GET \
	  http://localhost:8082/starwars/api/v1/planets \
	  -H 'cache-control: no-cache'
	  
Atualizando um planeta


	curl -X PUT \
	  http://localhost:8082/starwars/api/v1/planets \
	  -H 'Content-Type: application/json' \
	  -H 'cache-control: no-cache' \
	  -d '{
	   "id": 14,
	   "name":"Pluto",
	   "climate":"temperate",
	   "terrain":"forest"
	}'
	

Excluindo um planeta

	curl -X DELETE \
	  http://localhost:8082/starwars/api/v1/planets/ids/1 \
	  -H 'cache-control: no-cache'
	  
	  