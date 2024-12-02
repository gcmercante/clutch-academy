# Microserviços

## course-service (mongoDB)
### Responsabilidades
- Criar e salvar cursos
- Retornar todos cursos disponiveis
- Retornar cursos com base em um filtro (categoria, nome, instrutor)
- Desativar um curso
- Atualizar um curso

## user-service (Postgresql e mongoDB)
### Responsabilidade
- Criar novos usuarios e instrutores
- Desativar usuarios e instrutores
- Atualizar usuarios e instrutores
- Retornar informação de usuários e instrutores (baseado na autenticação)

## auth-service (keycloak?)
### Responsabilidades
- Autenticar usuarios e instrutores
- Manejar o permissionamento dos endpoints dos serviços (Ex.: user normal não pode ter acesso a alterar o curso de alguma forma)

## upload-service (streaming http2?)
### Responsabilidades
- Receber arquivos de video e ou outros tipos de arquivos e salva-los na nuvem
- Retornar os arquivos salvos que estão atrelados a determinado curso

## monitoring-service (kafka/rabbitmq)
### Responsabilidades
- Monitoramento de todos os serviços
- Irá fazer o log de maneira centralizada de todos os serviços
- Enviar notificações de estado dos serviços caso esteja em estado critico

## global-api (go?)
### Responsabilidades
- Facilitar a integração do futuro frontend com os serviços backend
- Somente fará a ponte entre o front e o backend, não irá conhecer regras de negócio, exceto autenticação correta