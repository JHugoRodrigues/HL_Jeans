class Fila:
    def __init__(self, capacidade):
        self.__capacidade = capacidade
        self.__fila = []

    def enqueue(self, elemento):
        """Adiciona um elemento ao final da fila"""
        if len(self.__fila) < self.__capacidade:
            self.__fila.append(elemento)
        else:
            print("Fila cheia")

    def dequeue(self):
        """Remove o primeiro elemento da fila"""
        if not self.empty():
            return self.__fila.pop(0)
        print("Fila vazia")

    def head(self):
        """Retorna o primeiro elemento sem remover"""
        if not self.empty():
            return self.__fila[0]
        print("Fila vazia")

    def size(self):
        """Retorna o tamanho atual"""
        return len(self.__fila)

    def empty(self):
        """Verifica se está vazia"""
        return len(self.__fila) == 0
