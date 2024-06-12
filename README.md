# FilasHospital e DataHospital

A aplicação consiste em dois projetos diferentes
- FilasHospital: Aplicação responsável pela construção do rabbitmq e envio de mensagens para DataHospital. Pode-se interagir com os métodos que envia mensagens através de requisições htttp.
- DataHospital: Aplicação responsável pela construção do banco de dados postegreSQL. Pode-se interagir com ela através das mensagens amqp que recebe de FilasHospital, disparando métodos que manipulam o banco de dados.
