#include <iostream>
#include <thread>
#include <string>
#include <WinSock2.h>
#include <ws2tcpip.h> // For sockaddr_in
#include "json.hpp"

using json = nlohmann::json;

#pragma comment(lib, "ws2_32.lib") // <- This doesn't compile properly even with this

//When compiling use: g++ Client.cpp -lws2_32



std::string returnJSON(const std::string& receivedData) {
    size_t startPos = receivedData.find('{'); // Find the position of the opening brace
    size_t endPos = receivedData.rfind('}'); // Find the position of the closing brace
    if (startPos != std::string::npos && endPos != std::string::npos && startPos < endPos) {
        return receivedData.substr(startPos, endPos - startPos + 1); // Extract the JSON substring
    } else {
        return ""; // JSON substring not found or invalid
    }
}

void parseJSON(const std::string& jsonString) {
    json data = json::parse(jsonString);

    double x = data["x"];
    double y = data["y"];

    std::cout << "x: " << x << ", y: " << y << std::endl;
}

void listenToServer(SOCKET socket) {
    char buffer[1024];
    while (true) {
        int bytesReceived = recv(socket, buffer, sizeof(buffer), 0);
        if (bytesReceived == SOCKET_ERROR) {
            std::cerr << "Error receiving data from server" << std::endl;
            break;
        } else if (bytesReceived == 0) {
            std::cerr << "Connection closed by server" << std::endl;
            break;
        } else {
            // Process received data
            std::string msg(buffer, bytesReceived);
            std::string receivedJSON = returnJSON(msg);
            parseJSON(receivedJSON);
        }
    }
}

int main() {
    WSADATA wsaData;
    int result = WSAStartup(MAKEWORD(2, 2), &wsaData);
    if (result != 0) {
        std::cerr << "WSAStartup failed with error: " << result << std::endl;
        return 1;
    }

    std::cout << "Hello, World!" << std::endl;
    SOCKET mySocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

    sockaddr_in serverAddr;

    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(1337);
    inet_pton(AF_INET, "127.0.0.1", &serverAddr.sin_addr);

    if(connect(mySocket, (SOCKADDR*)&serverAddr, sizeof(serverAddr)) != SOCKET_ERROR) {
        std::cout << "Connected" << std::endl;
    } else {
        int error = WSAGetLastError();
        std::cerr << "Error: Connect failed with error code " << error << std::endl;
    }

    std::thread listenerThread(listenToServer, mySocket);

    listenerThread.join();
    return 0;
}