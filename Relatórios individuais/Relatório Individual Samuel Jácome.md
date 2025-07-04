  Projeto UFGCarona

Desenvolvedor: Samuel Jácome Ribeiro Brito

1. Introdução

Esse relatório tem como objetivo apresentar a evoluçaõ do projeto UFGCarona. A primeira versão (v1.0) do projeto era baseada em uma interface de console, o meu trabalho para com o projeto (v2.0) foi fazer a implementação do frontend do projeto utilizando a estrutura Swing.

3. Arquitetura (v2.0)

A versão 2.0 do projeto adota o padrão MVC (Model-View-Controller), que facilita o desenvolvimento e a organização do código.
.UFGCaronaApp.java (Classe Principal)
   . Essa é a classe de entrada da aplicação, responsável por iniciar o sistema;
   . É aqui que a primeira View é instanciada e exibida (no caso, a LoginView), e o LoginController correspondente é inicializado para gerenciar a interação inicial do usuário;

.Model (package UFGCarona.model)

  . contém a lógica principal inicial do projeto feito pelo Iker (v1.0), realizei algumas modificações para que ficasse de acordo com as classes View e Controller correspondentes;
   . Entidades: As classes de entidades foram bem estruturadas com atributos, construtores e métodos getters/setters. A herança (Motorista estende usuário, ou AlunoUFG estende Usuario) é utilizada para modelar as relações entre os diferentes tipos de usuários;

   .Validação Inicial: a validação de email "@discente.ufg.br" no construtor Usuario é mantida no uso do cadastro/login na interface, porém estava tendo problemas por implementar validação da matrícula junto, decidi por manter apenas a validação do email como método de validação;

   
   .DAOs (Data Access Objects)
     .Cada entidade principal possui seu DAO correspondente;
     . O DatabaseManager centraliza a conexão e a criação de tabelas, tornando o acesso ao banco de dados consistente;
   
   .Interface IAvaliavel: permite que diferentes tipos de usuários ou entidades possam receber e calcular avaliações de forma polimórifca.


.View (package UFGCarona.view)
  . As classes View são a camada de apresetação do programa. elas são responsáveis pelo o que o usuário vê e interage com a GUI, elas que recebem as entradas e exibem as saídas para o usuário, porem não fazer parte do processo de lógica do programa;
  
  .Estrutura Swing: componentes de uma biblioteca de interface gráfica (import javax.swing.*), como JFrame, JDialog, JPanel, JButton, JTextField, JList, etc;

.Controller (package UFGCarona.controller)
  . Os controladores fazem a ligação das classes Model com a View, eles recebem a View correspondente e interagem com os DAOs do Model para realizar as operações;
  . OS controladores instanciam os DAOs para interagir com o banco de dados;

  
3. Considerações Finais

   . Na minha visão o projeto apesar de funcional, ficou bastante simples e com muitos pontos a melhorar, mas eu penso que o aprendizado e o desenvolvimento da capacidade de resolução de problemas trabalhados nesse projeto é algo a ser levado pra frente, penso que isso seja o mais importante, mais do que apenas o resultado final em si. Caso eu fosse fazer outro trabalho desse tipo com a experiência adiquirida e com um tempo maior de trabalho, eu com certeza faria um projeto de um calibre melhor e com mais facilidade.
