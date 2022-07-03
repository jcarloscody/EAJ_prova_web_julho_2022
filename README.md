# Projeto desenvolvido como resolução de prova UFRN

## Equipe:
- Gustavo Maciel
- Josué Carlos


# TEST

```sh
1) Crie um projeto com Spring Initializr incluindo Spring Boot Dev Tools, Lombok, Spring Web, Thymeleaf, Spring Data JPA, PostgreSQL Driver e Spring Validator. Crie a classe do modelo conforme o tema escolhido, lembre-se que você precisa adicionar pelo menos 7 atributos (ID, Deleted, ImageUri). Adicione as restrições (validações) do modelo. (0,5 ponto)
```

```sh
2) Crie a classe do usuário (id,username, password, admin, etc) que deve implementar a interface UserDetails. A aplicação os usuários poderão assumir 2 papeis (roles) “ROLE_ADMIN” e “ROLE_USER”. (0,5)
```

```sh
3) Prepare seus templates Thymeleaf para uso de bootstrap. Simplifique suas páginas utilizando fragments para separar, no mínimo, as partes de cabeçalho, principal e rodapé. A sugestão é o uso do template HTML disponível no [link](https://startbootstrap.com/template/shop-homepage) (1,0 ponto)
```

```sh
4) Implemente a rota de (“/index”) para, a partir de uma solicitação do tipo GET, gerar uma resposta
contendo no corpo um HTML que contém uma tabela ou similar de todos os itens (linhas) que estão
presentes no banco de dados e que não estão deletados (deleted == null). Você deve exibir a
imagem de cada um dos itens da lista. Para cada item listado adicione um link para a rota
“/adicionarCarrinho” passando como parâmetro para tal rota o ID do item escolhido. Por fim, adicione
na página gerada pela rota “/index” um link para a rota “/verCarrinho”. Adicione um cookie na resposta
chamado “visita” com a data e hora do acesso ao site. Esse cookie deve ser permanente e durar
24hs. (1,0 ponto)
```

```sh
5) Implemente a rota de (“/admin”) para, a partir de uma solicitação do tipo GET, gerar uma resposta
contendo no corpo um HTML que contém uma tabela de todos os itens (linhas) que estão presentes
no banco de dados e que não estão deletados (deleted == null). Para cada item listado adicione um
link para a rota “/editar” e “/deletar” passando como parâmetro para tal rota o ID do item escolhido.
Por fim, adicione na página gerada pela rota “/admin” um link para a rota “/cadastro”. (1,0 pontos)
```

```sh
6) Implemente a rota de (“/cadastro”) para, a partir de uma solicitação do tipo GET, gerar uma
resposta contendo no corpo um formulário HTML para cadastro de um item do seu tema. O formulário
deve conter um input de envio de arquivos para envio da imagem. O formulário deve conter tag para
tratamento de erros utilizando o Thymeleaf. O formulário deve enviar os dados da solicitação através
do método POST para a rota “/salvar”. (1,0 ponto)
```