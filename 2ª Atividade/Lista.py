class Lista:
    def __init__(self):
        self.__lista = []

    def add(self, elemento):
        """Adiciona um elemento ao final da lista"""
        self.__lista.append(elemento)

    def add_pos(self, pos, elemento):
        """Adiciona um elemento em uma posição específica"""
        if 0 <= pos <= len(self.__lista):
            self.__lista.insert(pos, elemento)
        else:
            print("Posição inválida")

    def get(self, pos):
        """Retorna o elemento da posição informada"""
        if 0 <= pos < len(self.__lista):
            return self.__lista[pos]
        print("Posição inválida")

    def set(self, pos, elemento):
        """Altera o valor em uma posição específica"""
        if 0 <= pos < len(self.__lista):
            self.__lista[pos] = elemento
        else:
            print("Posição inválida")

    def delete(self, pos):
        """Remove o elemento da posição informada"""
        if 0 <= pos < len(self.__lista):
            del self.__lista[pos]
        else:
            print("Posição inválida")

    def size(self):
        """Retorna o tamanho atual da lista"""
        return len(self.__lista)

    def empty(self):
        """Verifica se está vazia"""
        return len(self.__lista) == 0
