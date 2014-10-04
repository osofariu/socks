package socks

import spock.lang.Specification

import static junit.framework.Assert.assertTrue
import static socks.Sock.SockSize.large
import static socks.Sock.SockSize.small
import static socks.Sock.SockColor.black
import static socks.Sock.SockColor.white

class SocksSortTest extends Specification {

    SocksSort subject;

    def setup() {
        subject = new SocksSort()
    }

    def "when no socks return list of four empty lists"() {
        expect:
        assertTrue subject.isEmpty()
    }

    def "when two socks that don't match then return them in separate lists"() {
        def sock1 = new Sock(size: large, color: white)
        def sock2 = new Sock(size: small, color: white)
        subject.socks = [sock1, sock2]

        when:
        subject.sort()

        then:
        assert [sock1] == subject.getSortedSocksOfType('large,white')
        assert [sock2] == subject.getSortedSocksOfType('small,white')
    }

    def "when two socks match they will be paired together in the right category"() {
        def sock1 = new Sock(size: large, color: white)
        def sock2 = new Sock(size: large, color: white)
        subject.socks = [sock1, sock2]

        when:
        subject.sort()

        then:
        assert [sock1, sock2] == getPairForTypeAtIndex('large,white', 0)
    }

    def "when four socks match they will be paired together in the right category"() {
        def sock1 = new Sock(size: large, color: white)
        def sock2 = new Sock(size: large, color: white)
        def sock3 = new Sock(size: large, color: white)
        def sock4 = new Sock(size: large, color: white)
        subject.socks = [sock1, sock2, sock3, sock4]

        when:
        subject.sort()

        then:
        assert [sock1, sock2] == getPairForTypeAtIndex('large,white', 0)
        assert [sock3, sock4] == getPairForTypeAtIndex('large,white', 1)
    }

    def "when there's  matched pair and a single sock the sock does not get paired"() {
        def sock1 = new Sock(size: large, color: white)
        def sock2 = new Sock(size: large, color: white)
        def sock3 = new Sock(size: large, color: white)
        subject.socks = [sock1, sock2, sock3]

        when:
        subject.sort()

        then:
        assert [sock1, sock2] == getPairForTypeAtIndex('large,white', 0)
        assert sock3 == getPairForTypeAtIndex('large,white', 1)
    }

    def "we can also handle smallBlack socks"() {
        def sock = new Sock(size: small, color: black)
        subject.socks = [sock]

        when:
        subject.sort()

        then:
        assert sock == getPairForTypeAtIndex('small,black', 0)
    }


    def "we can also handle largeBlack socks"() {
        def sock = new Sock(size: large, color: black)
        subject.socks = [sock]

        when:
        subject.sort()

        then:
        assert sock == getPairForTypeAtIndex('large,black', 0)
    }

    def getPairForTypeAtIndex(String type, int index) {
        return subject.getSortedSocksOfType(type)[index]
    }
}