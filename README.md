# Sistema de Cadastro Java Swing + MySQL

Aplicação desktop em Java Swing organizada em MVC. A tela inicial permite cadastrar um login e uma senha ou entrar com uma conta existente. Após entrar, o sistema abre a tela de cadastro de nomes.

## Requisitos

- JDK 17 ou superior
- MySQL Server em execução na porta 3306
- VS Code com Extension Pack for Java (opcional)
- MySQL Connector/J em `lib/`

O Connector/J 8.3.0 está incluído em `lib/mysql-connector-j-8.3.0.jar`.

## Configurar o banco

Execute `database/schema.sql` no MySQL Workbench. O script cria o banco `db_sistema` com as duas tabelas necessárias: `usuarios` e `autenticacao`.

Por padrão a conexão usa o usuário `root` e senha vazia. Para ajustar, defina as variáveis de ambiente `DB_USER` e `DB_PASSWORD` antes de iniciar a aplicação.

## Executar no VS Code

Abra esta pasta no VS Code e execute `src/br/com/sistema/Main.java`. A configuração em `.vscode/settings.json` inclui os fontes e o driver JDBC.

Também é possível compilar e executar no terminal:

```powershell
New-Item -ItemType Directory -Force out
javac -encoding UTF-8 -cp "lib/mysql-connector-j-8.3.0.jar" -d out (Get-ChildItem src -Recurse -Filter *.java | ForEach-Object FullName)
java -cp "out;lib/mysql-connector-j-8.3.0.jar" br.com.sistema.Main
```

Na tela inicial, preencha login e senha e escolha **Novo Usuário** para criar a conta. Depois use **Entrar** para acessar o cadastro de nomes. As senhas são armazenadas como hash PBKDF2 com salt individual.

