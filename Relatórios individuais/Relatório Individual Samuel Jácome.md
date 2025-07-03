Relatório Simplificado - Projeto UFGCarona

Desenvolvedor: [Seu Nome]
Repositório: [Link GitHub]

1. O Que Foi Feito
Desenvolvi um sistema de caronas universitárias em Java com:
- Cadastro/login de motoristas e passageiros
- Persistência em SQLite (tabelas: Usuários, Veículos, Motoristas, Caronas)
- Funcionalidades:

Motoristas oferecem caronas

Passageiros solicitam caronas

Avaliação de motoristas

2. Principais Contribuições
Backend e Frontend (Java + SQLite)
Organização das pastas e implementação de classes view e controllers
Classes Principais:

Usuario (superclasse) → Motorista e Passageiro

MotoristaUFG com sistema de avaliações

Carona, Veiculo e Avaliacao

Banco de Dados:

Criei o esquema do BD (tabelas + relações)

Implementei DAOs para salvar/buscar dados

Correções Importantes:

Arrumei um erro que estava tendo ao tentar fazer login como um motorista

Validei formatos (ex: placa de veículo)

Verificação direta no DB Browser for SQLite

3. Dificuldades Superadas
🔧 Problema: Login de motoristas não funcionava
Solução: Adicionei verificação de conta ativa

🔧 Problema: Erro ao oferecer uma nova carona
Solução: apaguei os dados do arquivo .bd, reiniciando as tabelas do banco de dados

4. Resultado Final
Sistema funcional com:

Cadastro/login

Oferta/solicitação de caronas

Persistência em banco de dados
