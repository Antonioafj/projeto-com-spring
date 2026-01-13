# 🎵 ScreenSound - Gerenciador de Músicas & Artistas

<div align="center">
  <img src="https://img.shields.io/badge/Status-Concluido-blue" alt="Status">
  <img src="https://img.shields.io/badge/Java-17+-orange" alt="Java">
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-green" alt="Spring Boot">
  <img src="https://img.shields.io/badge/PostgreSQL-Managed-blue" alt="Postgres">
</p>

## 📖 Descrição do Projeto
O **ScreenSound** é uma aplicação Java desenvolvida para organizar e gerenciar um catálogo musical. O projeto permite cadastrar artistas e suas respectivas músicas, armazenando tudo em um banco de dados relacional. 

O foco técnico principal foi a implementação de relacionamentos entre entidades, consultas derivadas (Derived Queries) com **Spring Data JPA** e a organização de uma aplicação CLI (Command Line Interface) robusta.

---

## 🚀 Funcionalidades
- `Cadastro de Artistas`: Registro de artistas com classificação por tipo (Solo, Dupla ou Banda).
- `Vínculo de Músicas`: Cadastro de músicas associadas diretamente a um artista já existente.
- `Busca Especializada`: Localização de músicas por nome do artista ou trechos do nome.
- `Listagem Geral`: Visualização de todos os artistas e músicas persistidos no banco.
- `Integração com ChatGPT (Opcional)`: Estrutura preparada para buscar informações sobre artistas via IA.

---

## 🛠️ Técnicas e Tecnologias Utilizadas
- **Java 17+**: Linguagem de programação.
- **Spring Boot**: Framework base para o desenvolvimento da aplicação.
- **Spring Data JPA**: Abstração da camada de persistência e mapeamento objeto-relacional (ORM).
- **PostgreSQL**: Banco de dados utilizado para persistência dos dados.
- **Maven**: Gerenciamento de dependências e build.

## 🔧 Como rodar o projeto
1. **Clone o repositório:**
   git clone https://github.com/Antonioafj/projeto-com-spring.git

2. **Configuração do Banco:**
   - Crie um banco de dados chamado `screen_sound` no seu PostgreSQL.
   - Configure o `username` e `password` no arquivo `src/main/resources/application.properties`.

3. **Execução:**
   - Rode a aplicação através da sua IDE ou use o comando:
     mvn spring-boot:run

## 📂 Estrutura do Projeto
- **Model**: Classes `Artista` (com Enum `TipoArtista`) e `Musica`.
- **Repository**: Interface `ArtistaRepository` com consultas personalizadas.
- **Principal**: Lógica de interação com o usuário via console.

---

## 👤 Autor
<table>
  <tr>
    <td align="center">
      <a href="https://github.com/Antonioafj">
        <img src="https://avatars.githubusercontent.com/u/167789057?v=4" width="115px;" alt="Antonio Alves Ferreira Jr."/><br>
        <sub><b>Antonio Alves Ferreira Jr.</b></sub>
      </a>
    </td>
  </tr>
</table>

---
*Projeto desenvolvido durante a formação Java da Alura*
