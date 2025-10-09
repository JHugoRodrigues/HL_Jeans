class Pilha:
    def __init__(self, capacidade):
        self.__capacidade = capacidade
        self.__pilha = []

    def push(self, elemento):
        """Adiciona um elemento ao topo da pilha"""
        if len(self.__pilha) < self.__capacidade:
            self.__pilha.append(elemento)
        else:
            print("Pilha cheia!")

    def pop(self):
        """Remove o elemento do topo"""
        if not self.empty():
            return self.__pilha.pop()
        print("Pilha vazia")

    def top(self):
        """Retorna o elemento do topo sem removê-lo"""
        if not self.empty():
            return self.__pilha[-1]
        print("Pilha vazia")

    def size(self):
        """Retorna o tamanho atual"""
        return len(self.__pilha)

    def empty(self):
        """Verifica se está vazia"""
        return len(self.__pilha) == 0
