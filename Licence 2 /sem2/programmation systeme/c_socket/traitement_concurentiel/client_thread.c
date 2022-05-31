#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>

typedef struct User
{
  char nom[30];
  int age;
} User;


int main(void)
{
    int socketCLient = socket(AF_INET, SOCK_STREAM, 0);
    struct sockaddr_in addrCLient;
    addrCLient.sin_addr.s_addr = inet_addr("127.0.0.1");
    addrCLient.sin_family = AF_INET;
    addrCLient.sin_port = htons(30000);
    connect(socketCLient, (const struct sockaddr *)&addrCLient, sizeof(addrCLient));
    printf("connecte \n");

    User user;
    char msg[33];
    recv(socketCLient, msg, 32, 0);
    printf("%s \n ", msg);
    scanf("%s %d", user.nom, &user.age);
    send(socketCLient, &user, sizeof(user), 0);
    close(socketCLient);
    return 0;
}

