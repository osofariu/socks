package socks

class SocksSort {
    def socks = [] as ArrayList
    def sortedSocks = [] as HashMap<String, ArrayList>
    def socksWaitingForMatch = [] as HashMap<String, Sock>

    def sort() {
        socks.each { Sock sock ->
            if (awaitingMatchFor(sock)) {
                addSockPairToSortedSocks(sock)
                removeSockFromWaitingForMatch(sock.type())
            } else
                saveSockToWaitingForMatch(sock)
        }
        addUnmatchedSocksToSortedSocks()
    }

    def removeSockFromWaitingForMatch(sockType) {
        socksWaitingForMatch.remove(sockType)
    }

    def saveSockToWaitingForMatch(Sock sock) {
        socksWaitingForMatch[sock.type()] = sock
    }

    def awaitingMatchFor(sock) {
        socksWaitingForMatch[sock.type()] != null
    }

    def addSockPairToSortedSocks(sock) {
        def matchedSock = socksWaitingForMatch[sock.type()]
        addSockPairToSortedSocks(matchedSock, sock)
    }

    def addUnmatchedSocksToSortedSocks() {
        socksWaitingForMatch.each {sockType, sock ->
            createEmptyListOfTypeFor(sock)
            sortedSocks[sockType] << sock
        }
    }

    def addSockPairToSortedSocks(Sock first, Sock second) {
        createEmptyListOfTypeFor(first)
        sortedSocks[first.type()] << [first, second]
    }

    def createEmptyListOfTypeFor(Sock sock) {
        if (sortedSocks[sock.type()] == null)
            sortedSocks[sock.type()] = []
    }

    def isEmpty() {
        return socks.size() == 0
    }

    def getSortedSocksOfType(String type) {
        return sortedSocks[type]
    }
}
