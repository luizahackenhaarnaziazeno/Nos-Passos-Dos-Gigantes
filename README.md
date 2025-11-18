# 👣 Nos Passos dos Gigantes 🍀

> *"Você herdou uma grana da sua tia-avó Claudemira e agora pode realizar um sonho de criança: ir para a Irlanda passear na Calçada dos Gigantes!"*

## 📖 Sobre o Projeto

Este projeto resolve o desafio proposto pela Tia Claudemira para garantir a herança (e não deixá-la para o gato Fluffy). O objetivo é encontrar o **menor número de passos** para atravessar um mapa de terreno acidentado, indo de um ponto de partida ('S') até o ponto mais alto ('z').

### 🏔️ Regras do Desafio
Baseado nas condições do terreno:
* **O Mapa:** Representado por letras onde 'a' é o mais baixo e 'z' o mais alto. O início 'S' equivale a 'a'.
* **Movimentação:** É permitido mover-se para qualquer um dos 8 vizinhos (horizontal, vertical e diagonal).
* **Restrição de Subida:** Devido ao preparo físico, só é possível subir **no máximo 1 nível** de altura por vez (ex: de 'b' para 'c').
* **Descida:** "Para baixo todo santo ajuda". É possível descer qualquer diferença de altura.

## 🛠️ Tecnologias e Solução

O problema foi modelado como uma busca de menor caminho em um grafo não ponderado.

* **Linguagem:** Java
* **Algoritmo:** Busca em Largura (**BFS** - Breadth-First Search). A BFS foi escolhida pois garante encontrar o caminho mais curto em passos quando o custo de movimento é uniforme.
* **Estruturas de Dados:** `Queue` (Fila) para o algoritmo de busca e matrizes auxiliares para rastrear distâncias e pais (para reconstrução do caminho).

### ✨ Funcionalidades
* **Visualização Colorida:** O terminal exibe o mapa usando códigos ANSI:
    * <span style="color:red">Vermelho</span>: Caminho final encontrado.
    * <span style="color:yellow">Amarelo</span>: Áreas exploradas/visitadas.
    * <span style="color:cyan">Azul/Branco</span>: Terreno inexplorado.
* **Benchmark:** Exibe o tempo de execução (em milissegundos) para cada solução.
* **Menu Interativo:** Permite selecionar múltiplos casos de teste sem reiniciar o programa.

## 🚀 Como Executar

### Pré-requisitos
* Java JDK instalado (versão 8 ou superior).
* Terminal com suporte a cores ANSI (VS Code, IntelliJ, Bash, etc).

### ⚠️ Configuração Importante
Antes de rodar, você precisa ajustar o caminho da pasta onde estão os arquivos de teste (`casoa.txt`, etc).

1.  Abra o arquivo `NosPassosDosGigantes.java`.
2.  Procure pela linha dentro do `main` que contém o caminho do arquivo:
    ```java
    File file = new File("C:\\projects\\Facul\\T2 Jb\\Nos-Passos-Dos-Gigantes\\casosdeteste\\" + arquivo);
    ```
3.  **Altere este caminho** para o local onde a pasta `casosdeteste` está no seu computador.

### Compilação e Execução

No terminal, navegue até a pasta do projeto e execute:

```bash
# Compilar
javac NosPassosDosGigantes.java

# Rodar
java NosPassosDosGigantes
