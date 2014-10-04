package socks

class Sock {
    enum SockSize {small, large}
    enum SockColor {white, black}

    SockSize size
    SockColor color

    String type() {
        return size.toString() + "," + color.toString()
    }
}
