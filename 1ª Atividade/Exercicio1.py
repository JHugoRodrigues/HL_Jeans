# 1. Ler uma matriz 5x5 e gerar outra em que cada elemento é o cubo do elemento
# respectivo na matriz original. Imprima depois o elemento do meio dessa nova matriz.

matriz = []
for i in range(5):
    linha = []
    for j in range(5):
        valor = float(input(f"Digite o elemento [{i}][{j}]: "))
        linha.append(valor)
    matriz.append(linha)

matriz_cubos = [[elemento ** 3 for elemento in linha] for linha in matriz]

elemento_meio = matriz_cubos[2][2]

print("\nMatriz original:")
for linha in matriz:
    print(linha)

print("\nMatriz dos cubos:")
for linha in matriz_cubos:
    print(linha)

print(f"\n O elemento do meio da nova matriz é: {elemento_meio}")

# 2. Faça um algoritmo para ler uma matriz 3x3 real e imprimir a soma dos elementos da
# diagonal principal. Generalize para uma matriz NxN (2x2, 3x3, 4x4... NxN).

n = int(input("Digite a dimensão N da matriz quadrada: "))

matriz = []
for i in range(n):
    linha = []
    for j in range(n):
        valor = float(input(f"Digite o elemento [{i}][{j}]: "))
        linha.append(valor)
    matriz.append(linha)

soma_diagonal = sum(matriz[i][i] for i in range(n))

print("\nMatriz:")
for linha in matriz:
    print(linha)

print(f"\nSoma dos elementos da diagonal principal: {soma_diagonal}")

# 3. Ler uma matriz 4x3 real e imprimir a soma dos elementos de uma linha fornecida pelo
# usuário.

matriz = []
for i in range(4):
    linha = []
    for j in range(3):
        valor = float(input(f"Digite o elemento [{i}][{j}]: "))
        linha.append(valor)
    matriz.append(linha)

linha_escolhida = int(input("Digite o número da linha que deseja somar (0 a 3): "))

if 0 <= linha_escolhida < 4:
    soma_linha = sum(matriz[linha_escolhida])
    print(f"Soma dos elementos da linha {linha_escolhida}: {soma_linha}")
else:
    print("Linha inválida!")

# 4. Faça um algoritmo para criar e exibir a matriz identidade 4x4. Não é permitido que o
# usuário insira valores, a matriz deverá ser construída automaticamente através de laços.

matriz_identidade = []

for i in range(4):
    linha = []
    for j in range(4):
        if i == j:
            linha.append(1)
        else:
            linha.append(0)
    matriz_identidade.append(linha)

print("Matriz identidade 4x4:")
for linha in matriz_identidade:
    print(linha)

# 5. Crie uma matriz 7x8 onde cada elemento é a soma dos índices da sua posição dentro da
# matriz.

matriz = []

for i in range(7):
    linha = []
    for j in range(8):
        linha.append(i + j)
    matriz.append(linha)

print("Matriz 7x8 (cada elemento = soma dos índices):")
for linha in matriz:
    print(linha)

# 6. Faça um algoritmo para ler duas matrizes reais A e B 5x5, e criar uma matriz C de modo
# que: Cij = 1 se Aij da matriz A existe em algum lugar na matriz B; Cij = 0 se Aij da matriz A
# não existir em B.

A = []
B = []

print("Digite os valores da matriz A:")
for i in range(5):
    linha = []
    for j in range(5):
        valor = float(input(f"A[{i}][{j}]: "))
        linha.append(valor)
    A.append(linha)

print("\nDigite os valores da matriz B:")
for i in range(5):
    linha = []
    for j in range(5):
        valor = float(input(f"B[{i}][{j}]: "))
        linha.append(valor)
    B.append(linha)

valores_B = {elem for linha in B for elem in linha}  # conjunto com todos os valores de B

C = []
for i in range(5):
    linha_C = []
    for j in range(5):
        if A[i][j] in valores_B:
            linha_C.append(1)
        else:
            linha_C.append(0)
    C.append(linha_C)

print("\nMatriz C (1 se A[i][j] existe em B, 0 caso contrário):")
for linha in C:
    print(linha)

# 7. Faça um algoritmo para ler um vetor com 20 elementos inteiros. Modifique o vetor de
# modo que o primeiro elemento passe para a última posição, e desloque todos os outros
# elementos uma posição para a esquerda. Imprima depois o vetor.

vetor = []
for i in range(20):
    valor = int(input(f"Digite o elemento {i+1}: "))
    vetor.append(valor)

print("\nAntes:", vetor)

primeiro = vetor.pop(0)  # remove o primeiro
vetor.append(primeiro)   # adiciona no final

print("Depois:", vetor)

# 8. Uma matriz é triangular superior se todos os elementos abaixo da diagonal principal são
# 0. Ler uma matriz 4x4 e exibir uma mensagem dizendo se ela é ou não é triangular superior.

matriz = []

for i in range(4):
    linha = []
    for j in range(4):
        valor = float(input(f"Digite o elemento [{i}][{j}]: "))
        linha.append(valor)
    matriz.append(linha)

eh_triangular_superior = True

for i in range(4):
    for j in range(i):
        if matriz[i][j] != 0:
            eh_triangular_superior = False
            break

print("\nMatriz digitada:")
for linha in matriz:
    print(linha)

if eh_triangular_superior:
    print("\nA matriz É triangular superior.")
else:
    print("\nA matriz NÃO é triangular superior.")

# 9. A empresa Pregotex Corporation possui uma tabela para representar as vendas dos seus
# produtos (em barris) durante o ano. Cada linha representa um produto e as colunas são
# os meses do ano.

produtos = ["Prego", "Porca", "Arruela", "Parafuso"]
meses = ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"]

vendas = []

for i in range(len(produtos)):
    print(f"\nDigite as vendas do produto {produtos[i]} nos 12 meses:")
    linha = []
    for j in range(len(meses)):
        valor = float(input(f"{meses[j]}: "))
        linha.append(valor)
    vendas.append(linha)

# a) Total de vendas anuais por produto
print("\nTotal de vendas anuais por produto:")
for i in range(len(produtos)):
    total_produto = sum(vendas[i])
    print(f"{produtos[i]}: {total_produto}")

# b) Total de vendas da empresa em cada mês
print("\nTotal de vendas da empresa em cada mês:")
for j in range(len(meses)):
    total_mes = sum(vendas[i][j] for i in range(len(produtos)))
    print(f"{meses[j]}: {total_mes}")
